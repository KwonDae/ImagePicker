/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerConfigurationSelectionOrdered
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_group_create
import platform.darwin.dispatch_group_enter
import platform.darwin.dispatch_group_leave
import platform.darwin.dispatch_group_notify
import platform.posix.memcpy

@Composable
actual fun rememberImagePickerLauncher(
    onResult: (List<Any>) -> Unit,
    scope: CoroutineScope?,
    selectionMode: SelectionMode,
): ImagePickerLauncher {
    @OptIn(ExperimentalForeignApi::class)
    val delegate =
        object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(
                picker: PHPickerViewController,
                didFinishPicking: List<*>,
            ) {
                picker.dismissViewControllerAnimated(flag = true, completion = null)
                @Suppress("UNCHECKED_CAST")
                val results = didFinishPicking as List<PHPickerResult>

                val dispatchGroup = dispatch_group_create()
                val imageData = mutableListOf<ByteArray>()

                for (result in results) {
                    dispatch_group_enter(dispatchGroup)
                    result.itemProvider.loadDataRepresentationForTypeIdentifier(
                        typeIdentifier = "public.image",
                    ) { nsData, _ ->
                        scope?.launch(Dispatchers.Main) {
                            nsData?.let {
                                val bytes = ByteArray(it.length.toInt())
                                memcpy(bytes.refTo(0), it.bytes, it.length)
                                imageData.add(bytes)
                            }
                            dispatch_group_leave(dispatchGroup)
                        }
                    }
                }

                dispatch_group_notify(dispatchGroup, dispatch_get_main_queue()) {
                    scope?.launch(Dispatchers.Main) {
                        onResult(imageData)
                    }
                }
            }
        }

    return remember {
        ImagePickerLauncher(
            onLaunch = {
                val pickerController = createPHPickerViewController(delegate, selectionMode)
                UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                    pickerController,
                    true,
                    null,
                )
            },
        )
    }
}

private fun createPHPickerViewController(
    delegate: PHPickerViewControllerDelegateProtocol,
    selection: SelectionMode,
): PHPickerViewController {
    val pickerViewController =
        PHPickerViewController(
            configuration =
            when (selection) {
                is SelectionMode.Multiple ->
                    PHPickerConfiguration().apply {
                        // selectionLimit = 0 -> infinite
                        setSelectionLimit(selectionLimit = 0)
                        setFilter(filter = PHPickerFilter.imagesFilter)
                        setSelection(selection = PHPickerConfigurationSelectionOrdered)
                    }

                SelectionMode.Single ->
                    PHPickerConfiguration().apply {
                        setSelectionLimit(selectionLimit = 1)
                        setFilter(filter = PHPickerFilter.imagesFilter)
                        setSelection(selection = PHPickerConfigurationSelectionOrdered)
                    }
            },
        )
    pickerViewController.delegate = delegate
    return pickerViewController
}

actual class ImagePickerLauncher actual constructor(
    private val onLaunch: () -> Unit,
) {
    actual fun launch() {
        onLaunch()
    }
}
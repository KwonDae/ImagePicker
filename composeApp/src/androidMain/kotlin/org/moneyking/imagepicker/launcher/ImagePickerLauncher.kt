/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil3.Uri
import coil3.toUri
import kotlinx.coroutines.CoroutineScope

@Composable
actual fun rememberImagePickerLauncher(
    onResult: (List<Any>) -> Unit,
    scope: CoroutineScope?,
    selectionMode: SelectionMode,
): ImagePickerLauncher {
    return when (selectionMode) {
        SelectionMode.Single -> {
            singlePhotoPicker { uri ->
                onResult(listOf(uri))
            }
        }

        is SelectionMode.Multiple -> {
            multiplePhotoPicker(onResult)
        }
    }
}

@Composable
private fun singlePhotoPicker(
    onResult: (Uri) -> Unit,
): ImagePickerLauncher {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                onResult(it.toString().toUri())
            }
        }
    )
    val imagePickerLauncher = remember {
        ImagePickerLauncher(
            onLaunch = {
                singlePhotoPickerLauncher.launch((PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)))
            }
        )
    }
    return imagePickerLauncher
}

@Composable
private fun multiplePhotoPicker(
    onResult: (List<Any>) -> Unit,
): ImagePickerLauncher {
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            onResult(uriList.map { uri ->
                uri.toString().toUri()
            })
        }
    )
    val imagePickerLauncher = remember {
        ImagePickerLauncher(
            onLaunch = {
                multiplePhotoPickerLauncher.launch((PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)))
            }
        )
    }
    return imagePickerLauncher
}

actual class ImagePickerLauncher actual constructor(
    private val onLaunch: () -> Unit,
) {
    actual fun launch() {
        onLaunch()
    }
}

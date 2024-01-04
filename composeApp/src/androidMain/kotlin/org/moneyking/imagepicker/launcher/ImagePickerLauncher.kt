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
import androidx.compose.ui.platform.LocalContext
import coil3.Uri
import coil3.toUri

@Composable
actual fun rememberImagePickerLauncher(
    selectionMode: SelectionMode,
    onResult: (List<Uri>) -> Unit,
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
            onResult(uri.toString().toUri())
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
    onResult: (List<Uri>) -> Unit,
): ImagePickerLauncher {
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            onResult(uriList.map { it.toString().toUri() })
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
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import androidx.compose.runtime.Composable
import coil3.Uri

@Composable
actual fun rememberImagePickerLauncher(
    selectionMode: SelectionMode,
    onResult: (List<Uri>) -> Unit,
): ImagePickerLauncher {
    TODO()
}

actual class ImagePickerLauncher actual constructor(
    onLaunch: () -> Unit
) {
    actual fun launch() {
        TODO()
    }
}

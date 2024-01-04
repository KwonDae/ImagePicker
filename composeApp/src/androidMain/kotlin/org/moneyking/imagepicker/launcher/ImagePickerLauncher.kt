/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import androidx.compose.runtime.Composable
import coil3.Uri
import kotlinx.coroutines.CoroutineScope

@Composable
actual fun rememberImagePickerLauncher(
    selectionMode: SelectionMode,
    scope: CoroutineScope,
    onResult: (List<Uri>) -> Unit,
): ImagePickerLauncher {
    TODO()
}

actual class ImagePickerLauncher actual constructor(
    selectionMode: SelectionMode,
    onLaunch: () -> Unit
) {
    actual fun launch() {
        TODO()
    }
}

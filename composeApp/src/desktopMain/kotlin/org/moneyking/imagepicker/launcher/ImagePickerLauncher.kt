/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

@Composable
actual fun rememberImagePickerLauncher(
    onResult: (List<Any>) -> Unit,
    scope: CoroutineScope?,
    selectionMode: SelectionMode,
): ImagePickerLauncher {
    TODO()
}

actual class ImagePickerLauncher actual constructor(
    private val onLaunch: () -> Unit,
) {
    actual fun launch() {
        TODO()
    }
}

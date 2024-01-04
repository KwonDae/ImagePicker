/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import androidx.compose.runtime.Composable
import coil3.Uri
import kotlinx.coroutines.CoroutineScope

/**
 * @param selectionMode [SelectionMode.Single] or [SelectionMode.Multiple]
 * @param scope [CoroutineScope] to launch the picker
 * @param onResult callback to receive the result
 * Uri - Coil3 의 Uri 로 내부함수 중 String.toUri() 를 통해 변환 가능
 */
@Composable
expect fun rememberImagePickerLauncher(
    selectionMode: SelectionMode = SelectionMode.Single,
    onResult: (List<Uri>) -> Unit,
): ImagePickerLauncher

sealed class SelectionMode {
    data object Single : SelectionMode()
    data object Multiple : SelectionMode()
}

expect class ImagePickerLauncher(
    onLaunch: () -> Unit
) {
    fun launch()
}

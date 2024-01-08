/**
 * @author Daewon on 04,January,2024
 *
 */

package org.moneyking.imagepicker.launcher

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


@Composable
actual fun rememberImagePickerLauncher(
    onResult: (List<ByteArray>) -> Unit,
    scope: CoroutineScope,
    selectionMode: SelectionMode,
): ImagePickerLauncher {
    return when (selectionMode) {
        SelectionMode.Single -> {
            singlePhotoPicker(
                onResult = { uri -> onResult(listOf(uri)) },
                scope = scope,
            )
        }

        is SelectionMode.Multiple -> {
            multiplePhotoPicker(
                onResult = onResult,
                scope = scope,
            )
        }
    }
}

@Composable
private fun singlePhotoPicker(
    onResult: (ByteArray) -> Unit,
    scope: CoroutineScope,
): ImagePickerLauncher {
    val context = LocalContext.current
//    val contentResolver = LocalContext.current.contentResolver
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                scope.launch {
                    val byteArray = createByteArrayFromUri(context, it)
                    onResult(byteArray)
                }
                // uriToByteArray(contentResolver, it)?.let { onResult(it) }
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
    onResult: (List<ByteArray>) -> Unit,
    scope: CoroutineScope,
): ImagePickerLauncher {
    val context = LocalContext.current
//    val contentResolver = LocalContext.current.contentResolver
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            scope.launch {
                val byteArrayList = uriList.map { uri ->
                    createByteArrayFromUri(context, uri)
                }
                onResult(byteArrayList)
            }
//            onResult(uriList.map { uri ->
//                uriToByteArray(contentResolver, uri)!!
//            })
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

// 동기적으로 uri 를 byteArray 로 변환(속도가 빠름)
fun uriToByteArray(contentResolver: ContentResolver, uri: Uri): ByteArray? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
            return byteArrayOutputStream.toByteArray()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}

// coil3 를 이용하여 비동기적으로 uri 를 byteArray 로 변환(속도가 느림)
@OptIn(ExperimentalCoilApi::class)
suspend fun createByteArrayFromUri(context: Context, uri: Uri): ByteArray {
    val imageLoader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(uri)
        .build()

    val result = (imageLoader.execute(request) as SuccessResult).image
    val bitmap = result.asDrawable(context.resources).toBitmap()

    val stream = ByteArrayOutputStream()
    // PNG 형식으로 이미지를 압축할 경우, 시간이 너무 오래 걸림...
    // bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

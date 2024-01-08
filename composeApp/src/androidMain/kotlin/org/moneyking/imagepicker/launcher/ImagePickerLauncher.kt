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
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


@Composable
actual fun rememberImagePickerLauncher(
    onResult: (List<ByteArray>) -> Unit,
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
    onResult: (ByteArray) -> Unit,
): ImagePickerLauncher {
    val context = LocalContext.current
    val contentResolver = LocalContext.current.contentResolver
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { it ->
                uriToByteArray(contentResolver, it)?.let { onResult(it) }
                // createByteArrayFromUrl(context, it)
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
): ImagePickerLauncher {
    val context = LocalContext.current
    val contentResolver = LocalContext.current.contentResolver
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            onResult(uriList.map { uri ->
                uriToByteArray(contentResolver, uri)!!
                // createByteArrayFromUrl(context, uri)
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

@OptIn(ExperimentalCoilApi::class)
suspend fun createByteArrayFromUrl(context: Context, uri: Uri): ByteArray {
    val imageLoader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(uri)
        .build()

    val result = (imageLoader.execute(request) as SuccessResult).image
    val bitmap = result.asDrawable(context.resources).toBitmap()

    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

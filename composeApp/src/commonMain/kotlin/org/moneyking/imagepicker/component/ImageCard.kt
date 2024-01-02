package org.moneyking.imagepicker.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.aakira.napier.Napier
import org.moneyking.imagepicker.theme.Gray200

@Composable
fun ImageCard(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Gray200),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Gallery Image",
                    contentScale = ContentScale.Crop,
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Success -> {
                                Napier.d("load success")
                            }

                            is AsyncImagePainter.State.Error -> {
                                Napier.d("${state.result.throwable}")
                            }

                            else -> {}
                        }
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "Image Card",
            )
        }
    }
}
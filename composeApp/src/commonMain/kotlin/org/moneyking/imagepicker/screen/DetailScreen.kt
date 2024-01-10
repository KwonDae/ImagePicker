package org.moneyking.imagepicker.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mxalbert.zoomable.Zoomable
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import org.moneyking.imagepicker.MR
import org.moneyking.imagepicker.navigation.DetailScreenComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    image: ByteArray,
    component: DetailScreenComponent,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(MR.strings.detail),
                        fontFamily = fontFamilyResource(MR.fonts.Pretendard.semiBold),
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            component.goBack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = stringResource(MR.strings.back_button),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Zoomable {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(MR.strings.detail_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}
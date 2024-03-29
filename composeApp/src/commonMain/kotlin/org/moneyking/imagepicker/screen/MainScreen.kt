package org.moneyking.imagepicker.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import org.moneyking.imagepicker.MR
import org.moneyking.imagepicker.component.HorizontalPagerIndicator
import org.moneyking.imagepicker.component.ImageCard
import org.moneyking.imagepicker.component.ImagePickerButton
import org.moneyking.imagepicker.launcher.SelectionMode
import org.moneyking.imagepicker.launcher.rememberImagePickerLauncher
import org.moneyking.imagepicker.navigation.MainScreenComponent
import org.moneyking.imagepicker.navigation.MainScreenEvent
import org.moneyking.imagepicker.util.extension.aspectRatioBasedOnOrientation
import org.moneyking.imagepicker.util.newImageLoader

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalCoilApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    component: MainScreenComponent,
    modifier: Modifier = Modifier,
    debug: Boolean = false
) {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context, debug)
    }
    val imageList by component.imageList.subscribeAsState()
    val scope = rememberCoroutineScope()
    val singlePhotoPicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { imageData ->
            component.onEvent(MainScreenEvent.UpdateImageList(imageData))
        }
    )
    val multiplePhotoPicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Multiple,
        scope = scope,
        onResult = { imageDataList ->
            component.onEvent(MainScreenEvent.UpdateImageList(imageDataList))
        }
    )

    MaterialTheme {
        val pageCount = imageList.size
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val pagerHeight = 320.dp

        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(MR.strings.main),
                            fontFamily = fontFamilyResource(MR.fonts.Pretendard.semiBold),
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                        )
                    },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (imageList.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.height(pagerHeight),
                        contentPadding = PaddingValues(horizontal = 32.dp),
                    ) { index ->
                        val image = imageList[index]
                        ImageCard(
                            image = image,
                            modifier = Modifier
                                .aspectRatioBasedOnOrientation(1f)
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    component.onEvent(MainScreenEvent.UpdateImageIndex(index))
                                    component.onEvent(MainScreenEvent.ClickImageCard(image))
                                },
                        )
                    }
                    if (pageCount > 1) {
                        HorizontalPagerIndicator(
                            pageCount = pageCount,
                            currentPage = pagerState.currentPage,
                            targetPage = pagerState.targetPage,
                            currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
                ImagePickerButton(
                    onClick = {
                        singlePhotoPicker.launch()
                    },
                    text = stringResource(MR.strings.pick_single_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                ImagePickerButton(
                    onClick = {
                        multiplePhotoPicker.launch()
                    },
                    text = stringResource(MR.strings.pick_multiple_images),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                ImagePickerButton(
                    onClick = {
                        component.onEvent(MainScreenEvent.UpdateImageList(emptyList()))
                    },
                    text = stringResource(MR.strings.reset),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

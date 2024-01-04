package org.moneyking.imagepicker

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.Uri
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import org.moneyking.imagepicker.component.HorizontalPagerIndicator
import org.moneyking.imagepicker.component.ImageCard
import org.moneyking.imagepicker.component.ImagePickerButton
import org.moneyking.imagepicker.launcher.SelectionMode
import org.moneyking.imagepicker.launcher.rememberImagePickerLauncher
import org.moneyking.imagepicker.util.newImageLoader

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun App(
    debug: Boolean = false
) {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context, debug)
    }

    var images by remember { mutableStateOf(listOf<Uri>()) }
    val singlePhotoPicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        onResult = { uri ->
            images = uri
        }
    )
    val multiplePhotoPicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Multiple,
        onResult = { uriList ->
            images = uriList
        }
    )

    MaterialTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val pageCount = images.size
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val pagerHeight = 320.dp

        Scaffold(snackbarHost = {
            SnackbarHost(snackbarHostState)
        }) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (images.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.height(pagerHeight),
                        contentPadding = PaddingValues(horizontal = 32.dp),
                    ) { index ->
                        ImageCard(
                            imageUrl = images[index],
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                    HorizontalPagerIndicator(
                        pageCount = pageCount,
                        currentPage = pagerState.currentPage,
                        targetPage = pagerState.targetPage,
                        currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                ImagePickerButton(
                    onClick = {
                        singlePhotoPicker.launch()
                    },
                    text = "Pick Single Image",
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
                    text = "Pick Multiple Images",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                ImagePickerButton(
                    onClick = {
                        images = emptyList()
                    },
                    text = "Init",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                )
            }
        }
    }
}

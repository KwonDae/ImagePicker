package org.moneyking.imagepicker.util

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.fetch.NetworkFetcher
import coil3.memory.MemoryCache
import coil3.request.crossfade
import coil3.util.DebugLogger

@OptIn(ExperimentalCoilApi::class)
fun newImageLoader(
    context: PlatformContext,
    debug: Boolean = false,
): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            add(NetworkFetcher.Factory())
        }
        .memoryCache {
            MemoryCache.Builder()
                // Set the max size to 25% of the app's available memory.
                .maxSizePercent(context, percent = 0.25)
                .build()
        }
        // Show a short crossfade when loading images asynchronously.
        .crossfade(true)
        // Enable logging if this is a debug build.
        .apply {
            if (debug) {
                logger(DebugLogger())
            }
        }
        .build()
}
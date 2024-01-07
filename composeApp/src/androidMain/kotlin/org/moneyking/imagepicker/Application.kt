package org.moneyking.imagepicker

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import org.jetbrains.compose.components.resources.BuildConfig
import org.moneyking.imagepicker.util.newImageLoader

class Application : Application(), SingletonImageLoader.Factory {
    /**
     * If you use the singleton ImageLoader on Android,
     * create a custom ImageLoader in your Application's SingletonImageLoader.Factory:
     * https://coil-kt.github.io/coil/upgrading_to_coil3/#network-images
     */
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return newImageLoader(this, BuildConfig.DEBUG)
    }
}

/**
 * @author Daewon on 01,January,2024
 *
 */

package org.moneyking.imagepicker

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import org.jetbrains.compose.components.resources.BuildConfig
import util.newImageLoader

class Application : Application(), SingletonImageLoader.Factory {
        override fun newImageLoader(context: PlatformContext): ImageLoader {
            return newImageLoader(this, BuildConfig.DEBUG)
    }
}

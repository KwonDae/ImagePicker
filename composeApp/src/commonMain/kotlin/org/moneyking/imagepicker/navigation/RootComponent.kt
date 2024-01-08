package org.moneyking.imagepicker.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.MainScreen,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext,
    ): Child {
        return when (config) {
            Configuration.MainScreen -> {
                Child.MainScreen(
                    MainScreenComponent(
                        componentContext = context,
                        onNavigateToDetail = { byteArray ->
                            navigation.pushNew(Configuration.DetailScreen(image = byteArray))
                        }
                    )
                )
            }

            is Configuration.DetailScreen -> {
                Child.DetailScreen(
                    DetailScreenComponent(
                        image = config.image,
                        componentContext = context,
                        onGoBack = {
                            navigation.pop()
                        }
                    )
                )
            }
        }
    }

    sealed class Child {
        data class MainScreen(val component: MainScreenComponent) : Child()
        data class DetailScreen(val component: DetailScreenComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object MainScreen : Configuration()

        @Serializable
        data class DetailScreen(
            val image: ByteArray,
        ) : Configuration() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || this::class != other::class) return false

                other as DetailScreen

                return image.contentEquals(other.image)
            }

            override fun hashCode(): Int {
                return image.contentHashCode()
            }
        }
    }
}
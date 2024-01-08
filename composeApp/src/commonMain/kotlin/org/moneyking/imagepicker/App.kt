package org.moneyking.imagepicker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import org.moneyking.imagepicker.navigation.RootComponent
import org.moneyking.imagepicker.screen.DetailScreen
import org.moneyking.imagepicker.screen.MainScreen

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.MainScreen -> MainScreen(instance.component)
                is RootComponent.Child.DetailScreen -> DetailScreen(
                    image = instance.component.image,
                    component = instance.component,
                )
            }
        }
    }
}
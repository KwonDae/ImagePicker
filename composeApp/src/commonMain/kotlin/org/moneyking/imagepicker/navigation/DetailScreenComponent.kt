package org.moneyking.imagepicker.navigation

import com.arkivanov.decompose.ComponentContext

class DetailScreenComponent(
    val image: ByteArray,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit,
): ComponentContext by componentContext {
    fun goBack() {
        onGoBack()
    }
}
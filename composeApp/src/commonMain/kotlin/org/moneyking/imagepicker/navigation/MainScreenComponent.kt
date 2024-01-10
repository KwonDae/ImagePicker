package org.moneyking.imagepicker.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.aakira.napier.Napier

class MainScreenComponent(
    componentContext: ComponentContext,
    private val onNavigateToDetail: (ByteArray) -> Unit,
): ComponentContext by componentContext {
    private var _imageList = MutableValue(emptyList<ByteArray>())
    val imageList: Value<List<ByteArray>> = _imageList

    private var _index = MutableValue(-1)
    val index: Value<Int> = _index

    fun onEvent(event: MainScreenEvent) {
        when(event) {
            is MainScreenEvent.ClickImageCard -> {
                onNavigateToDetail(imageList.value[index.value])
            }

            is MainScreenEvent.UpdateImageIndex -> {
                _index.value = event.index
                Napier.d("${index.value}")
            }

            is MainScreenEvent.UpdateImageList -> {
                _imageList.value = event.imageList
                Napier.d("${imageList.value}")
            }
        }
    }
}
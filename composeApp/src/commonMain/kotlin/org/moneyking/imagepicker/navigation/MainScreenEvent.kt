package org.moneyking.imagepicker.navigation

sealed interface MainScreenEvent {
    data class ClickImageCard(val image: Any): MainScreenEvent
    data class UpdateImageIndex(val index: Int): MainScreenEvent
    data class UpdateImageList(val imageList: List<ByteArray>): MainScreenEvent
}
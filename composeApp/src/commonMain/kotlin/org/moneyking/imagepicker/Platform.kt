package org.moneyking.imagepicker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
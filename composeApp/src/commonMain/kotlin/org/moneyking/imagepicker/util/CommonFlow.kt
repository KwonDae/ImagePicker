/**
 * @author Daewon on 01,January,2024
 *
 */

package org.moneyking.imagepicker.util

import kotlinx.coroutines.flow.Flow

expect class CommonFlow<T>(flow: Flow<T>): Flow<T>

fun <T> Flow<T>.toCommonFlow() = CommonFlow(this)

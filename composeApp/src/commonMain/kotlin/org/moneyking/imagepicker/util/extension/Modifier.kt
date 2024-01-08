package org.moneyking.imagepicker.util.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints

fun Modifier.aspectRatioBasedOnOrientation(aspectRatio: Float): Modifier {
    return this.then(
        object : LayoutModifier {
            override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
                val width = constraints.maxWidth
                val height = constraints.maxHeight

                val targetWidth: Int
                val targetHeight: Int

                if (width <= height) {
                    targetWidth = width
                    targetHeight = (width / aspectRatio).toInt()
                } else {
                    targetHeight = height
                    targetWidth = (height * aspectRatio).toInt()
                }

                val placeable = measurable.measure(
                    Constraints.fixed(targetWidth, targetHeight),
                )

                return layout(placeable.width, placeable.height) {
                    placeable.placeRelative(0, 0)
                }
            }
        },
    )
}
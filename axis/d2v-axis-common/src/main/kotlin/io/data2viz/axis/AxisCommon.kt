package io.data2viz.axis

import io.data2viz.color.colors
import io.data2viz.scale.LinearScale
import io.data2viz.viz.ParentItem


expect fun YO():String

/**
 * Create an Axis 
 */
fun ParentItem.axis(orient: Orient, scale: LinearScale<Double>, init:AxisElement.() -> Unit = {}) {
    AxisElement(orient, scale)
            .apply(init)
            .render(this)
}


class AxisElement(val orient: Orient, val scale: LinearScale<Double>)  {

    var tickValues:List<Double> = listOf()
    var tickSizeInner = 6.0
    var tickSizeOuter = 6.0
    var tickPadding = 3.0

    val k = if (orient == Orient.TOP || orient == Orient.LEFT) -1 else 1

    fun render(content:ParentItem) {

        val values = tickValues
        val spacing = tickSizeInner.coerceAtLeast(0.0) + tickPadding
        val range0 = scale.range.first() + 0.5
        val range1 = scale.range.last() + 0.5

        with(content){
            path { 
                stroke = colors.black
                fill = null
                strokeWidth = 1.0

                if(orient == Orient.LEFT || orient == Orient.RIGHT) {
                    moveTo(tickSizeOuter * k, range0)
                    lineTo(0.5, range0)
                    lineTo(0.5, range1)
                    lineTo(tickSizeOuter * k, range1)
                } else {
                    moveTo(range0, tickSizeOuter * k)
                    lineTo(range0, 0.5)
                    lineTo(range1, 0.5)
                    lineTo(range1, tickSizeOuter * k)
                }

            }
        }

    }


}

enum class Orient {
    TOP, BOTTOM, LEFT, RIGHT
}
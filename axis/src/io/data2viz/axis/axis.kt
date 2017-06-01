package io.data2viz.axis


import io.data2viz.color.colors.black
import io.data2viz.svg.ParentElement
import io.data2viz.core.ticks
import io.data2viz.svg.TextAnchor


/**
 * Implementation of Axis. This version is only display a vertical axis on the left
 * (labels on the left of the axis).
 * Todo implement orientations (top, right, botton, left).
 */
class Axis (val min: Number, val max: Number, val scale: (Number) -> Number) {
    fun appendTo(parent: ParentElement){

        val ticks = ticks(min, max, 10)
        ticks.forEach { tick ->
            parent.g {
                transform { translate(y = this@Axis.scale(tick)) }
                line (x2 = -6 )
                text {
                    textAnchor = TextAnchor.end
                    style {
                        fontFamily("sans-serif")
                        fontSize("12px")
                    }
                    x = - 9
                    y = 0
                    setAttribute("dy", ".32em")
                    text = tick.toString()
                }
            }
        }

        parent.path {
            strokeWidth = "1"
            stroke = black
            path {
                moveTo(-6, scale(max))
                horizontalDeltaTo(0)
                verticalLineDeltaTo(scale(min))
                horizontalDeltaTo(-6)
            }
            fill
            setAttribute("fill", "none" )
        }
    }
}

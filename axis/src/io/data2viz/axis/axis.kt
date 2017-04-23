package io.data2viz.axis


import io.data2viz.color.colors.black
import io.data2viz.svg.ParentElement

class Axis (var values:List<Any>) {
    fun appendTo(parent: ParentElement){

        (0..10).forEach {
            val data = 20 * it
            parent.g {
                transform { translate(0, 210 - data) }
                line (x2 = -6 )
                text {
                    style {
                        setStyle("text-anchor", "end")
                        fontFamily("sans-serif")
                        fontSize("12px")
                    }
                    x = - 9
                    y = 0
                    setAttribute("dy", ".32em")
                    text = data.toString()
                }
            }
        }

        parent.path {
            strokeWidth = "1"
            stroke = black
            shape {
                moveTo(-6)
                horizontalDeltaTo(0)
                verticalLineDeltaTo(210)
                horizontalDeltaTo(-6)
            }
            fill
            setAttribute("fill", "none" )
        }
    }
}

package io.data2viz

import io.data2viz.color.colors
import io.data2viz.transition.transition
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz

fun main(args: Array<String>) {
    selectOrCreateSvg().apply {
        setAttribute("width", "1200")
        viz {

            (1..50).forEach {
                rect {
                    x = 10.0 * 2 * it
                    y = 10.0
                    width = 10.0
                    height = 10.0
                    fill = colors.grey

                    transition {
                        x = 110.0
                        y = 110.0
                    }
                }
            }

        }
    }

}
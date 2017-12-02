package io.data2viz.axis

import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import io.data2viz.scale.scaleLinear


@Suppress("unused")
fun showViz() {

    val scaleX = scaleLinear {
        domain = listOf(.0, 15000.0)
        range = listOf(.0, 500.0)
    }

    val ticks = (1..14).map { it * 1000.0 }


    val svgElement = selectOrCreateSvg().apply {
        setAttribute("width", "600")
        setAttribute("height", "600")
    }

    svgElement.viz {
        group {
            transform {
                translate(x = 30.0, y = 10.0)
            }
            axis(Orient.TOP, scaleX) { tickValues = ticks }
        }
        group {
            transform {
                translate(x = 30.0, y = 20.0)
            }
            axis(Orient.BOTTOM, scaleX) { tickValues = ticks }
        }
        group {
            transform {
                translate(x = 10.0, y = 30.0)
            }
            axis(Orient.LEFT, scaleX) { tickValues = ticks }
        }
        group {
            transform {
                translate(x = 20.0, y = 30.0)
            }
            axis(Orient.RIGHT, scaleX) { tickValues = ticks }
        }
    }

}

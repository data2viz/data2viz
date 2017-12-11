package io.data2viz.axis

import io.data2viz.scale.scales
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz


@Suppress("unused")
fun showViz() {

    val scaleX = scales.continuous.linear {
        domain = listOf(.0, 15000.0)
        range = listOf(.0, 500.0)
    }

    val ticks = (1..14).map { it * 1000.0 }


    val svgElement = selectOrCreateSvg().apply {
        setAttribute("width", "600")
        setAttribute("height", "600")
    }
    

    svgElement.viz {
//        setStyle("font-family:sans-serif; font-size:14px")
        axisExample()
    }

}

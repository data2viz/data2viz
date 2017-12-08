package io.data2viz.examples.naturalLogScale

import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import io.data2viz.scale.scaleLinear


@Suppress("unused")

fun main(args: Array<String>) {
    
    val root = selectOrCreateSvg().apply {
        setAttribute("width", "${width + margins.hMargins}")
        setAttribute("height", "${height + margins.vMargins}")
    }

    root.viz {
        naturalLogScale()
    }

}

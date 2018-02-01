package io.data2viz.examples.sankey

import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz


@Suppress("unused")
fun main(args: Array<String>) {
    
    val root = selectOrCreateSvg().apply {
        setAttribute("width", "$vizWidth")
        setAttribute("height", "$vizHeight")
    }

    root.viz {
        sankeyViz()
    }

}

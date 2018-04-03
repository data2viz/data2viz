package io.data2viz.examples.chord

import io.data2viz.examples.lineOfSight.vizHeight
import io.data2viz.examples.lineOfSight.lineOfSightViz
import io.data2viz.examples.lineOfSight.vizWidth
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz


@Suppress("unused")

fun main(args: Array<String>) {

    val root = selectOrCreateSvg().apply {
        setAttribute("width", "$vizWidth")
        setAttribute("height", "$vizHeight")
    }

    root.viz {
        lineOfSightViz()
    }

}

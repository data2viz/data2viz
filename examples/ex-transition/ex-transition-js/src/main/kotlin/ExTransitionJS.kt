package io.data2viz.transition

import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz

fun main(args: Array<String>) {
    selectOrCreateSvg().apply {
        setAttribute("width", "1200")

        viz {
            rectanglesWithTransition()
        }
    }

}

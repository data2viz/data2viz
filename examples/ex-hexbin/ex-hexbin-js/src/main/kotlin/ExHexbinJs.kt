@file:Suppress("DEPRECATION")

import io.data2viz.viz.ParentElement
import io.data2viz.viz.selectOrCreateSvg


fun main(args: Array<String>) {
    val svg = selectOrCreateSvg().apply { 
        setAttribute("width", vizWidth.toString())
        setAttribute("height", vizHeight.toString())
    }
    svg.append((root as ParentElement).domElement)
}

@file:Suppress("DEPRECATION")

import io.data2viz.viz.ParentElement
import io.data2viz.viz.selectOrCreateSvg
import kotlin.browser.window
import kotlin.js.Date

fun main(args: Array<String>) {
    val svg = selectOrCreateSvg().apply { 
        setAttribute("width", width.toString())
        setAttribute("height", height.toString())
    }
    svg.append((root as ParentElement).domElement)
}

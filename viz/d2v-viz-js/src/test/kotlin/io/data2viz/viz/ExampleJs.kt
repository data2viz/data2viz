package io.data2viz.viz


@Suppress("unused")
fun commonViz() {

    val svgElement = selectOrCreateSvg().apply {
        setAttribute("width", "500")
        setAttribute("height", "500")
    }

    svgElement.viz {
        commonViz()
    }

}

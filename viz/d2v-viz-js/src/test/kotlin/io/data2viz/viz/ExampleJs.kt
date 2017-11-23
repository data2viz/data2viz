package io.data2viz.viz


val data = listOf(
        Domain(10.0, 10.0),
        Domain(20.0, 40.0),
        Domain(30.0, 90.0)
)


@Suppress("unused")
fun showViz() {

    val svgElement = selectOrCreateSvg().apply {
        setAttribute("width", "500")
        setAttribute("height", "500")
    }

    svgElement.viz {
        commonViz(data)
    }

}

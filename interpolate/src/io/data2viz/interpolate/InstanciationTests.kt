package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.red
import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import io.data2viz.test.DomUtils
import kotlin.browser.document

class InstanciationTests : StringSpec() {

    init {

        // instanciations
        val interpolator = interpolateRgb(arrayListOf(red, blue))
        val valueToColor = scale.linear.numberToColor(0 linkedTo red, 1 linkedTo blue)

        // uses
        "using interpolator" {displayScaleGradient(interpolator)}
        "using scale" {displayScaleGradient(valueToColor)}
    }

    fun displayScaleGradient(domainToViz: (Number) -> Color, width: Int = 800) {
        DomUtils.body.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")

                    (0 until width).forEach { index ->
                        appendChild(
                                document.createElementNS(namespace.svg, "rect").apply {
                                    setAttribute("fill", domainToViz(index / (width - 1).toFloat()).rgbHex)
                                    setAttribute("x", "$index")
                                    setAttribute("y", "0")
                                    setAttribute("width", "1")
                                    setAttribute("height", "30")
                                }
                        )
                    }
                }
        )
    }

    fun node(name: String) = document.createElementNS(namespace.svg, name)

}

package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.HSL
import io.data2viz.color.colors
import io.data2viz.color.colors.blue
import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.test.DomUtils
import kotlin.browser.document

class HSLTests : StringSpec() {

    init {
        "HSL SHORT linear interpolation [(300°, 100%, 16%), (38°, 100%, 50%)] see https://github.com/d3/d3-interpolate#interpolateHsl for reference" {
            val iterator = interpolateHsl(HSL(300.deg, 1.0, .16), HSL(38.deg, 1.0, .5))
            displaySmallGradient(iterator, 888)
        }

        "HSL LONG linear interpolation [(300°, 100%, 16%), (38°, 100%, 50%)] see https://github.com/d3/d3-interpolate#interpolateHsl for reference" {
            val iterator = interpolateHslLong(HSL(300.deg, 1.0, .16), HSL(38.deg, 1.0, .5))
            displaySmallGradient(iterator, 888)
        }

        "HSL SHORT linear interpolation [(38°, 100%, 16%), (300°, 100%, 50%)] see https://github.com/d3/d3-interpolate#interpolateHsl for reference" {
            val iterator = interpolateHsl(HSL(38.deg, 1.0, .5), HSL(300.deg, 1.0, .16))
            displaySmallGradient(iterator, 888)
        }



        "HSL SHORT linear interpolation [(38°, 100%, 16%), (300°, 100%, 50%)] see https://github.com/d3/d3-interpolate#interpolateHsl for reference" {
            val iterator = interpolateHslLong(HSL(38.deg, 1.0, .5), HSL(300.deg, 1.0, .16))
            displaySmallGradient(iterator, 888)
        }
    }

    fun displaySmallGradient(percentToColor: (Float) -> HSL, width: Int = 256) {
        DomUtils.body.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", percentToColor(index / (width - 1).toFloat()).toRgba().rgbHex)
                                    setAttribute("x", "$index")
                                    setAttribute("y", "0")
                                    setAttribute("width", "1")
                                    setAttribute("height", "20")
                                }
                        )
                    }
                }
        )
    }

    fun node(name: String) = document.createElementNS(namespace.svg, name)
}

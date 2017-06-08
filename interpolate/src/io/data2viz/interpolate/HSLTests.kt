package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.HSL
import io.data2viz.core.namespace
import io.data2viz.math.deg
import io.data2viz.test.ExecutionContext
import io.data2viz.test.HTMLExecutionContext
import io.data2viz.test.StringSpec
import kotlin.browser.document

class HSLTests : StringSpec() {

    init {
        "HSL SHORT linear interpolation [(300°, 100%, 25%), (38°, 100%, 50%)] see https://github.com/d3/d3-interpolate#interpolateHsl for reference" {context ->
            val iterator = interpolateHsl(HSL(300.deg, 1.0, .25), HSL(38.deg, 1.0, .5))
            displaySmallGradient(context, iterator, 888, imageReference = "http://data2viz.io/img/hsl.png")
            iterator(0.5).toRgba().rgbHex shouldBe Color(0xbf0023).rgbHex
        }

        "HSL LONG linear interpolation [(300°, 100%, 25%), (38°, 100%, 50%)]" {context ->
            val iterator = interpolateHslLong(HSL(300.deg, 1.0, .25), HSL(38.deg, 1.0, .5))
            displaySmallGradient(context, iterator, 888, imageReference = "http://data2viz.io/img/hslLong.png")
            iterator(0.5).toRgba().rgbHex shouldBe Color(0x00bf9c).rgbHex
        }

        "HSL SHORT linear interpolation [(38°, 100%, 50%), (300°, 100%, 25%)]" {context ->
            val iterator = interpolateHsl(HSL(38.deg, 1.0, .5), HSL(300.deg, 1.0, .25))
            displaySmallGradient(context, iterator, 888, imageReference = "http://data2viz.io/img/hslReverse.png")
            iterator(0.5).toRgba().rgbHex shouldBe Color(0xbf0023).rgbHex
        }

        "HSL LONG linear interpolation [(38°, 100%, 50%), (300°, 100%, 25%)]" {context ->
            val iterator = interpolateHslLong(HSL(38.deg, 1.0, .5), HSL(300.deg, 1.0, .25))
            displaySmallGradient(context, iterator, 888, imageReference = "http://data2viz.io/img/hslLongReverse.png")
            iterator(0.5).toRgba().rgbHex shouldBe Color(0x00bf9c).rgbHex
        }
    }

    fun displaySmallGradient(context:ExecutionContext, percentToColor: (Float) -> HSL, width: Int = 256, imageReference: String? = null) {

        if (context !is HTMLExecutionContext)  return
        context.element.appendChild(
                nodeSVG("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                                nodeSVG("rect").apply {
                                    setAttribute("fill", percentToColor(index / (width).toFloat()).toRgba().rgbHex)
                                    setAttribute("x", "$index")
                                    setAttribute("y", "0")
                                    setAttribute("width", "1")
                                    setAttribute("height", "20")
                                }
                        )
                    }
                }
        )
        if (imageReference != null) {
            val div = document.createElement("div")
            div.appendChild(
                    document.createElement("img").apply {
                        setAttribute("src", imageReference)
                        setAttribute("height", "20")
                        setAttribute("width", "$width")
                    }
            )
            context.element.appendChild(div)
        }
    }

    fun nodeSVG(name: String) = document.createElementNS(namespace.svg, name)
}

package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.green
import io.data2viz.color.colors.red
import io.data2viz.color.colors.white
import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import io.data2viz.test.DomUtils
import io.data2viz.test.ExecutionContext
import io.data2viz.test.HTMLExecutionContext
import kotlin.browser.document

class RGBTests : StringSpec() {

    init {
        "RGB interpolation color -> sameColor" {
            val iterator = interpolateRgb(colors.darkolivegreen, colors.darkolivegreen)
            iterator(-1) shouldBe colors.darkolivegreen
            iterator(0) shouldBe colors.darkolivegreen
            iterator(0.5) shouldBe colors.darkolivegreen
            iterator(1) shouldBe colors.darkolivegreen
            iterator(2) shouldBe colors.darkolivegreen
        }

        "RGB interpolation color -> sameColor (gamma = 2)" {
            val iterator = interpolateRgb(colors.darkolivegreen, colors.darkolivegreen, gamma = 2.0)
            iterator(-1) shouldBe iterator(0)
            iterator(0) shouldBe iterator(0.5)
            iterator(0.5) shouldBe iterator(1)
            iterator(1) shouldBe iterator(2)
        }

        "Spline RGB interpolation color -> sameColor" {
            val iterator = interpolateRgbBasis(listOf(colors.darkolivegreen, colors.darkolivegreen))
            iterator(-1) shouldBe colors.darkolivegreen
            iterator(0) shouldBe colors.darkolivegreen
            iterator(0.5) shouldBe colors.darkolivegreen
            iterator(1) shouldBe colors.darkolivegreen
            iterator(2) shouldBe colors.darkolivegreen
        }

        "Cyclical spline RGB interpolation darkolivegreen -> darkolivegreen" {
            val iterator = interpolateRgbBasis(listOf(colors.darkolivegreen, colors.darkolivegreen), cyclical = true)
            iterator(-1) shouldBe colors.darkolivegreen
            iterator(0) shouldBe colors.darkolivegreen
            iterator(0.5) shouldBe colors.darkolivegreen
            iterator(1) shouldBe colors.darkolivegreen
            iterator(2) shouldBe colors.darkolivegreen
        }

        "Linear RGB interpolation white -> blue" {
            val iterator = interpolateRgb(white, blue)
            iterator(-1) shouldBe white
            iterator(0) shouldBe white
            iterator(0.5) shouldBe Color(0x8080ff)
            iterator(1) shouldBe blue
            iterator(2) shouldBe blue
        }

        "Linear RGB interpolation blue -> white" {
            val iterator = interpolateRgb(blue, white)
            iterator(-1) shouldBe blue
            iterator(0) shouldBe blue
            iterator(0.5) shouldBe Color(0x8080ff)
            iterator(1) shouldBe white
            iterator(2) shouldBe white
        }

        "RGB spline interpolation [G, B, R, B, G]" { context ->
            val iterator = interpolateRgbBasis(listOf(green, blue, red, blue, green))
            displaySmallGradient(context, iterator, 880)
            iterator(-1) shouldBe green
            iterator(0) shouldBe green
            iterator(1) shouldBe green
            iterator(2) shouldBe green
        }

        "RGB cyclical spline interpolation [G, B, R, B]" { context ->
            val iterator = interpolateRgbBasis(listOf(green, blue, red, blue), cyclical = true)
            displaySmallGradient(context, iterator, 880)
            iterator(-1) shouldBe iterator(0)
            iterator(0) shouldBe iterator(1)
            iterator(1) shouldBe iterator(2)
        }

        "RGB spline interpolation colorbrewSpline reference (https://bl.ocks.org/mbostock/048d21cf747371b11884f75ad896e5a5)" { context ->
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x8e0152), Color(0xc51b7d), Color(0xde77ae), Color(0xf1b6da),
                    Color(0xfde0ef), Color(0xf7f7f7), Color(0xe6f5d0), Color(0xb8e186), Color(0x7fbc41),
                    Color(0x4d9221), Color(0x276419)))
            displaySmallGradient(context, iterator, 880, imageReference = "http://data2viz.io/img/colorbrewSpline.png")
        }

        "RGB cyclical spline interpolation colorbrewSpline reference" { context ->
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x8e0152), Color(0xc51b7d), Color(0xde77ae), Color(0xf1b6da),
                    Color(0xfde0ef), Color(0xf7f7f7), Color(0xe6f5d0), Color(0xb8e186), Color(0x7fbc41),
                    Color(0x4d9221), Color(0x276419)), cyclical = true)
            displaySmallGradient(context, iterator, 880, imageReference = "http://data2viz.io/img/colorbrewSplineClosed.png")
        }

        "RGB linear interpolation [#800080, #ffa200] see https://github.com/d3/d3-interpolate#interpolateRgb for reference" { context ->
            val iterator = interpolateRgb(Color(0x800080), Color(0xffa200))
            displaySmallGradient(context, iterator, 888, imageReference = "http://data2viz.io/img/rgb.png")
        }

        "RGB linear interpolation [#800080, #ffa200] corrected gamma 2.2 see https://github.com/d3/d3-interpolate#interpolateRgb for reference" { context ->
            val iterator = interpolateRgb(Color(0x800080), Color(0xffa200), 2.2)
            displaySmallGradient(context, iterator, 888, imageReference = "http://data2viz.io/img/rgbGamma.png")
        }
    }

    fun displaySmallGradient(context:ExecutionContext, percentToColor: (Float) -> Color, width: Int = 256, imageReference: String? = null) {
        if (context !is HTMLExecutionContext)  return
        context.element.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", percentToColor(index / (width).toFloat()).rgbHex)
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

    fun node(name: String) = document.createElementNS(namespace.svg, name)
}

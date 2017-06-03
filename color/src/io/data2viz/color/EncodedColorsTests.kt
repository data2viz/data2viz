package io.data2viz.color

import io.data2viz.color.EncodedColors.Companion.category10
import io.data2viz.color.EncodedColors.Companion.category20
import io.data2viz.color.EncodedColors.Companion.category20b
import io.data2viz.color.EncodedColors.Companion.category20c
import io.data2viz.color.EncodedColors.Companion.inferno
import io.data2viz.color.EncodedColors.Companion.magma
import io.data2viz.color.EncodedColors.Companion.plasma
import io.data2viz.color.EncodedColors.Companion.viridis
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.darkcyan
import io.data2viz.color.colors.darkolivegreen
import io.data2viz.color.colors.lightyellow
import io.data2viz.color.colors.papayawhip
import io.data2viz.color.colors.white
import io.data2viz.core.namespace
import io.data2viz.test.DomUtils.Companion.body
import io.data2viz.test.StringSpec
import kotlin.browser.document

class EncodedColorsTests : StringSpec() {

    init {
        "category10" { category10.colors.size shouldBe 10; displaySmallGradient(category10) }
        "category20" { category20.colors.size shouldBe 20; displaySmallGradient(category20) }
        "category20b" { category20b.colors.size shouldBe 20; displaySmallGradient(category20b) }
        "category20c" { category20c.colors.size shouldBe 20; displaySmallGradient(category20c) }

        "viridis"   { testAndGraph(viridis) }
        "magma"     { testAndGraph(magma) }
        "inferno"   { testAndGraph(inferno) }
        "plasma"    { testAndGraph(plasma) }

        "Linear RGB interpolation white -> blue" {
            val it = rgbInterpolator(white, blue)
            displaySmallGradient(it)
        }

        "Linear RGB interpolation blue -> white" {
            val it = rgbInterpolator(blue, white)
            displaySmallGradient(it)
        }

        "Linear RGB interpolation lightyellow -> darkolivegreen (63px wide)" {
            val it = rgbInterpolator(lightyellow, darkolivegreen)
            displaySmallGradient(it, 63)
        }

        "Linear RGB interpolation darkcyan -> papayawhip GAMMA 2.2" {
            val it = rgbInterpolator(darkcyan, papayawhip, 2.2f)
            displaySmallGradient(it, 800)
        }

        "Linear RGB interpolation darkcyan -> papayawhip GAMMA 0.6" {
            val it = rgbInterpolator(darkcyan, papayawhip, 0.6f)
            displaySmallGradient(it, 800)
        }

        "Linear RGB interpolation darkcyan -> papayawhip" {
            val it = rgbInterpolator(darkcyan, papayawhip)
            displaySmallGradient(it, 800)
        }

        "RGB spline interpolation darkcyan -> papayawhip" {
            val it = rgbSpline(arrayOf(darkcyan, papayawhip))
            displaySmallGradient(it, 800)
        }

        "RGB cyclical spline interpolation [G, B, R, B]" {
            val it = rgbSpline(arrayOf(Color(0x00ff00), Color(0x0000ff), Color(0xff0000), Color(0x0000ff)), cyclical = true)
            displaySmallGradient(it, 800)
        }

        "RGB spline interpolation [G, B, R, B, G]" {
            val it = rgbSpline(arrayOf(Color(0x00ff00), Color(0x0000ff), Color(0xff0000), Color(0x0000ff), Color(0x00ff00)))
            displaySmallGradient(it, 800)
        }

        "RGB  interpolation [G, B, R, B, G]" {
            val it4 = rgbInterpolator(Color(0x00ff00), Color(0x0000ff))
            val it5 = rgbInterpolator(Color(0x0000ff), Color(0xff0000))
            val it6 = rgbInterpolator(Color(0xff0000), Color(0x0000ff))
            val it7 = rgbInterpolator(Color(0x0000ff), Color(0x00ff00))

            displaySmallGradient(it4, 200)
            displaySmallGradient(it5, 200)
            displaySmallGradient(it6, 200)
            displaySmallGradient(it7, 200)
        }

        "Cyclical RGB cyclical spline interpolation " {
            val it = rgbSpline(arrayOf(Color(0xff0000), Color(0x00ff00), Color(0xff0000), Color(0x00ff00)), cyclical = true)
            displaySmallGradient(it, 800)
        }

        "Cyclical RGB standard spline interpolation " {
            val it = rgbSpline(arrayOf(Color(0xff0000), Color(0x00ff00), Color(0xff0000), Color(0x00ff00), Color(0xff0000)))
            displaySmallGradient(it, 800)
        }

        "Cyclical RGB spline interpolation [#8e0152, #f7f7f7, #276419]" {
            val it = rgbSpline(arrayOf(Color(0x8e0152), Color(0xf7f7f7), Color(0x276419)), cyclical = true)
            displaySmallGradient(it, 800)
        }

        "RGB spline interpolation [#8e0152, #f7f7f7, #276419]" {
            val it = rgbSpline(arrayOf(Color(0x8e0152), Color(0xf7f7f7), Color(0x276419)))
            displaySmallGradient(it, 800)
        }

        "RGB interpolation [#8e0152, #f7f7f7, #276419] (for reference)" {
            val it = rgbInterpolator(Color(0x8e0152), Color(0xf7f7f7))
            val it2 = rgbInterpolator(Color(0xf7f7f7), Color(0x276419))
            displaySmallGradient(it, 400)
            displaySmallGradient(it2, 400)
        }

        // ["#8e0152", "#c51b7d", "#de77ae", "#f1b6da", "#fde0ef", "#f7f7f7", "#e6f5d0", "#b8e186", "#7fbc41", "#4d9221", "#276419"]
        "RGB spline interpolation [#8e0152, #c51b7d, #de77ae, #f1b6da, #fde0ef, #f7f7f7, #e6f5d0, #b8e186, #7fbc41, #4d9221, #276419]" {
            val it = rgbSpline(arrayOf(Color(0x8e0152), Color(0xc51b7d), Color(0xde77ae), Color(0xf1b6da),
                    Color(0xfde0ef), Color(0xf7f7f7), Color(0xe6f5d0), Color(0xb8e186), Color(0x7fbc41),
                    Color(0x4d9221), Color(0x276419)))
            displaySmallGradient(it, 800)
        }

        "RGB spline interpolation [darkcyan, papayawhip, darkolivegreen, blue, lightyellow]" {
            val it = rgbSpline(arrayOf(darkcyan, papayawhip, darkolivegreen, blue, lightyellow))
            displaySmallGradient(it, 800)
        }

        "RGB linear interpolation [darkcyan, papayawhip, darkolivegreen, blue, lightyellow] (for reference)" {
            val it = rgbInterpolator(darkcyan, papayawhip)
            val it2 = rgbInterpolator(papayawhip, darkolivegreen)
            val it3 = rgbInterpolator(darkolivegreen, blue)
            val it4 = rgbInterpolator(blue, lightyellow)
            displaySmallGradient(it, 200)
            displaySmallGradient(it2, 200)
            displaySmallGradient(it3, 200)
            displaySmallGradient(it4, 200)
        }
    }

    fun displaySmallGradient(interpolator: (Float) -> Color, width: Int = 256) {
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", interpolator(index / (width - 1).toFloat()).rgbHex)
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

    fun displaySmallGradient(colors: EncodedColors) {
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "${30 * colors.colors.size}")
                    setAttribute("height", "30")
                    colors.colors.forEachIndexed { index, color ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", color.rgbHex)
                                    setAttribute("x", "${index * 30}")
                                    setAttribute("y", "0")
                                    setAttribute("width", "30")
                                    setAttribute("height", "30")
                                }
                        )
                    }
                }
        )
    }

    fun testAndGraph(gradient: EncodedColors) {

        //generate a list of 6, 10, 20 colors from the gradient.
        listOf(6, 10, 20).forEach { size ->
            val colors = (0..size - 1)
                    .map { gradient.color(it.toDouble() / (size - 1)) }
            println(colors.joinToString(transform = { it.rgbHex }))
        }

        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "400")
                    setAttribute("height", "20")
                    (0..399).forEach {
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", gradient.color(it.toDouble() / 400).rgbHex)
                                    setAttribute("x", "${it}")
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

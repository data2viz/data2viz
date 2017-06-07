package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors
import io.data2viz.color.colors.blue
import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import io.data2viz.test.DomUtils
import kotlin.browser.document

class RGBTests : StringSpec() {

    init {
        "Linear RGB interpolation darkolivegreen -> darkolivegreen -> darkolivegreen" {
            val iterator = interpolateRgb(arrayListOf(colors.darkolivegreen, colors.darkolivegreen, colors.darkolivegreen))
            displaySmallGradient(iterator)
        }

        "Exponential RGB interpolation darkolivegreen -> darkolivegreen -> darkolivegreen (1.001 gamma)" {
            val iterator = interpolateRgb(arrayListOf(colors.darkolivegreen, colors.darkolivegreen, colors.darkolivegreen), gamma = 1.001)
            displaySmallGradient(iterator)
        }

        "Spline RGB interpolation darkolivegreen -> darkolivegreen -> darkolivegreen" {
            val iterator = interpolateRgbBasis(arrayListOf(colors.darkolivegreen, colors.darkolivegreen, colors.darkolivegreen))
            displaySmallGradient(iterator)
        }

        "Cyclical spline RGB interpolation darkolivegreen -> darkolivegreen -> darkolivegreen" {
            val iterator = interpolateRgbBasis(arrayListOf(colors.darkolivegreen, colors.darkolivegreen, colors.darkolivegreen), cyclical = true)
            displaySmallGradient(iterator)
        }

        "Linear RGB interpolation white -> blue" {
            val iterator = interpolateRgb(arrayListOf(colors.white, blue))
            displaySmallGradient(iterator)
        }

        "Linear RGB interpolation blue -> white" {
            val iterator = interpolateRgb(arrayListOf(blue, colors.white))
            displaySmallGradient(iterator)
        }

        "Linear RGB interpolation lightyellow -> darkolivegreen (63px wide)" {
            val percentToColor = interpolateRgb(arrayListOf(colors.lightyellow, colors.darkolivegreen))
            displaySmallGradient(percentToColor, 63)
        }

        "Linear RGB interpolation darkcyan -> papayawhip GAMMA 2.2" {
            val iterator = interpolateRgb(arrayListOf(colors.darkcyan, colors.papayawhip), 2.2)
            displaySmallGradient(iterator, 800)
        }

        "Linear RGB interpolation darkcyan -> papayawhip GAMMA 0.6" {
            val iterator = interpolateRgb(arrayListOf(colors.darkcyan, colors.papayawhip), 0.6)
            displaySmallGradient(iterator, 800)
        }

        "Linear RGB interpolation darkcyan -> papayawhip" {
            val iterator = interpolateRgb(arrayListOf(colors.darkcyan, colors.papayawhip))
            displaySmallGradient(iterator, 800)
        }

        "RGB spline interpolation darkcyan -> papayawhip" {
            val iterator = interpolateRgbBasis(arrayListOf(colors.darkcyan, colors.papayawhip))
            displaySmallGradient(iterator, 800)
        }

        "RGB cyclical spline interpolation [G, B, R, B]" {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x00ff00), Color(0x0000ff), Color(0xff0000), Color(0x0000ff)), cyclical = true)
            displaySmallGradient(iterator, 800)
        }

        "RGB spline interpolation [G, B, R, B, G]" {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x00ff00), Color(0x0000ff), Color(0xff0000), Color(0x0000ff), Color(0x00ff00)))
            displaySmallGradient(iterator, 800)
        }

        "RGB  interpolation [G, B, R, B, G]" {
            val iterator = interpolateRgb(arrayListOf(Color(0x00ff00), Color(0x0000ff), Color(0xff0000), Color(0x0000ff), Color(0x00ff00)))
            displaySmallGradient(iterator, 800)
        }

        "Cyclical RGB cyclical spline interpolation " {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0xff0000), Color(0x00ff00), Color(0xff0000), Color(0x00ff00)), cyclical = true)
            displaySmallGradient(iterator, 800)
        }

        "Cyclical RGB standard spline interpolation " {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0xff0000), Color(0x00ff00), Color(0xff0000), Color(0x00ff00), Color(0xff0000)))
            displaySmallGradient(iterator, 800)
        }

        "Cyclical RGB spline interpolation [#8e0152, #f7f7f7, #276419]" {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x8e0152), Color(0xf7f7f7), Color(0x276419)), cyclical = true)
            displaySmallGradient(iterator, 800)
        }

        "RGB spline interpolation [#8e0152, #f7f7f7, #276419]" {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x8e0152), Color(0xf7f7f7), Color(0x276419)))
            displaySmallGradient(iterator, 800)
        }

        "RGB interpolation [#8e0152, #f7f7f7, #276419]" {
            val iterator = interpolateRgb(arrayListOf(Color(0x8e0152), Color(0xf7f7f7), Color(0x276419)))
            displaySmallGradient(iterator, 800)
        }

        // ["#8e0152", "#c51b7d", "#de77ae", "#f1b6da", "#fde0ef", "#f7f7f7", "#e6f5d0", "#b8e186", "#7fbc41", "#4d9221", "#276419"]
        "RGB spline interpolation [#8e0152, #c51b7d, #de77ae, #f1b6da, #fde0ef, #f7f7f7, #e6f5d0, #b8e186, #7fbc41, #4d9221, #276419]" {
            val iterator = interpolateRgbBasis(arrayListOf(Color(0x8e0152), Color(0xc51b7d), Color(0xde77ae), Color(0xf1b6da),
                    Color(0xfde0ef), Color(0xf7f7f7), Color(0xe6f5d0), Color(0xb8e186), Color(0x7fbc41),
                    Color(0x4d9221), Color(0x276419)))
            displaySmallGradient(iterator, 800)
        }

        "RGB spline interpolation [darkcyan, papayawhip, darkolivegreen, blue, lightyellow]" {
            val iterator = interpolateRgbBasis(arrayListOf(colors.darkcyan, colors.papayawhip, colors.darkolivegreen, blue, colors.lightyellow))
            displaySmallGradient(iterator, 800)
        }

        "RGB linear interpolation [darkcyan, papayawhip, darkolivegreen, blue, lightyellow]" {
            val iterator = interpolateRgb(arrayListOf(colors.darkcyan, colors.papayawhip, colors.darkolivegreen, blue, colors.lightyellow))
            displaySmallGradient(iterator, 800)
        }

        "RGB linear interpolation [#800080, #ff0200] see https://github.com/d3/d3-interpolate#interpolateRgb for reference" {
            val iterator = interpolateRgb(arrayListOf(Color(0x800080), Color(0xffa200)))
            displaySmallGradient(iterator, 888)
        }

        "RGB linear interpolation [#800080, #ff0200] corrected gamma 2.2 see https://github.com/d3/d3-interpolate#interpolateRgb for reference" {
            val iterator = interpolateRgb(arrayListOf(Color(0x800080), Color(0xffa200)), 2.2)
            displaySmallGradient(iterator, 888)
        }
    }

    fun displaySmallGradient(percentToColor: (Float) -> Color, width: Int = 256) {
        DomUtils.body.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", percentToColor(index / (width - 1).toFloat()).rgbHex)
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

package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.color.Colors.Web.blue
import io.data2viz.color.Colors.Web.green
import io.data2viz.color.Colors.Web.red
import io.data2viz.color.Colors.Web.white
import io.data2viz.test.TestBase
import io.data2viz.test.namespace
import kotlin.browser.document
import kotlin.test.Test

class RGBTestsJS : TestBase() {

    @Test
    fun rgbSplineInterpolationGBRBG() {
        val iterator = interpolateRgbBasis(listOf(green, blue, red, blue, green))
        displaySmallGradient("rgbSplineInterpolationGBRBG", iterator, 880)
        iterator(-1.0) shouldBe green
        iterator(0.0) shouldBe green
        iterator(1.0) shouldBe green
        iterator(2.0) shouldBe green
    }

    @Test
    fun rgbSplineInterpolationGBRB() {
        val iterator = interpolateRgbBasis(listOf(green, blue, red, blue), cyclical = true)
        displaySmallGradient("rgbSplineInterpolationGBRB", iterator, 880)
        iterator(-1.0) shouldBe iterator(0.0)
        iterator(0.0) shouldBe iterator(1.0)
        iterator(1.0) shouldBe iterator(2.0)
    }

    /**
     * https://bl.ocks.org/mbostock/048d21cf747371b11884f75ad896e5a5)
     * "RGB spline interpolation colorbrewSpline reference (
     */
    @Test
    fun rgbSplineInterpolationColorbrewSpline() {
        val iterator = interpolateRgbBasis(
            arrayListOf(
                RgbColor(0x8e0152), RgbColor(0xc51b7d), RgbColor(0xde77ae), RgbColor(0xf1b6da),
                RgbColor(0xfde0ef), RgbColor(0xf7f7f7), RgbColor(0xe6f5d0), RgbColor(0xb8e186), RgbColor(0x7fbc41),
                RgbColor(0x4d9221), RgbColor(0x276419)
            )
        )
        displaySmallGradient(
            "rgbSplineInterpolationColorbrewSpline",
            iterator,
            880,
            imageReference = "http://data2viz.io/img/colorbrewSpline.png"
        )
    }


    @Test
    fun rgbCyclicalSplineInterpolationColorbrewSpline() {
        val iterator = interpolateRgbBasis(
            arrayListOf(
                RgbColor(0x8e0152), RgbColor(0xc51b7d), RgbColor(0xde77ae), RgbColor(0xf1b6da),
                RgbColor(0xfde0ef), RgbColor(0xf7f7f7), RgbColor(0xe6f5d0), RgbColor(0xb8e186), RgbColor(0x7fbc41),
                RgbColor(0x4d9221), RgbColor(0x276419)
            ), cyclical = true
        )
        displaySmallGradient(
            "rgbCyclicalSplineInterpolationColorbrewSpline",
            iterator,
            880,
            imageReference = "http://data2viz.io/img/colorbrewSplineClosed.png"
        )
    }

    @Test
    fun rgbLinearInterpolatio_800080_ffa200() {
        val iterator = interpolateRgb(RgbColor(0x800080), RgbColor(0xffa200))
        displaySmallGradient(
            "rgbLinearInterpolatio_800080_ffa200",
            iterator,
            888,
            imageReference = "http://data2viz.io/img/rgb.png"
        )
    }

    /**
     *  see https://github.com/d3/d3-interpolate#interpolateRgb for reference"
     */
    @Test
    fun rgbLinearInterpolation_800080_ffa200_corrected_gamma_2_2() {
        val iterator = interpolateRgb(RgbColor(0x800080), RgbColor(0xffa200), 2.2)
        displaySmallGradient(
            "rgbLinearInterpolation_800080_ffa200_corrected_gamma_2_2",
            iterator,
            888,
            imageReference = "http://data2viz.io/img/rgbGamma.png"
        )
    }
}

fun displaySmallGradient(
        context: String,
        percentToColor: (Double) -> Color,
        width: Int = 256,
        imageReference: String? = null
) {
    if (browserEnabled) {

        h2(context)
        document.body?.appendChild(
            node("svg").apply {
                setAttribute("width", "$width")
                setAttribute("height", "20")
                setAttribute("x", "0")
                setAttribute("y", "0")
                (0 until width).forEach { index ->
                    appendChild(
                        node("rect").apply {
                            setAttribute("fill", percentToColor(index / width.toDouble()).rgbHex)
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
            document.body?.appendChild(div)
        }
    }

}

fun node(name: String) = document.createElementNS(namespace.svg, name)

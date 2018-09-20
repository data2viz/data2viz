package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.green
import io.data2viz.color.colors.red
import io.data2viz.color.colors.white
import io.data2viz.test.namespace
import io.data2viz.test.TestBase
import kotlin.browser.document
import kotlin.test.Test

class RGBTests : TestBase() {

    @Test
    fun interpolateSameColor() {
        val iterator = interpolateRgb(colors.darkolivegreen, colors.darkolivegreen)
        iterator(-1) shouldBe colors.darkolivegreen
        iterator(0) shouldBe colors.darkolivegreen
        iterator(0.5) shouldBe colors.darkolivegreen
        iterator(1) shouldBe colors.darkolivegreen
        iterator(2) shouldBe colors.darkolivegreen
    }

    @Test
    fun interpolateSameColorGamma2() {
        val iterator = interpolateRgb(colors.darkolivegreen, colors.darkolivegreen, gamma = 2.0)
        iterator(-1) shouldBe iterator(0)
        iterator(0) shouldBe iterator(0.5)
        iterator(0.5) shouldBe iterator(1)
        iterator(1) shouldBe iterator(2)
    }


    @Test
    fun splineRGBSameColor() {
        val iterator = interpolateRgbBasis(listOf(colors.darkolivegreen, colors.darkolivegreen))
        iterator(-1) shouldBe colors.darkolivegreen
        iterator(0) shouldBe colors.darkolivegreen
        iterator(0.5) shouldBe colors.darkolivegreen
        iterator(1) shouldBe colors.darkolivegreen
        iterator(2) shouldBe colors.darkolivegreen
    }


    @Test
    fun cyclicalSplineRGBSameColor() {
        val iterator = interpolateRgbBasis(listOf(colors.darkolivegreen, colors.darkolivegreen), cyclical = true)
        iterator(-1) shouldBe colors.darkolivegreen
        iterator(0) shouldBe colors.darkolivegreen
        iterator(0.5) shouldBe colors.darkolivegreen
        iterator(1) shouldBe colors.darkolivegreen
        iterator(2) shouldBe colors.darkolivegreen
    }

    @Test
    fun linearRGBInterpolationWhiteToBlue() {
        val iterator = interpolateRgb(white, blue)
        iterator(-1) shouldBe white
        iterator(0) shouldBe white
        iterator(0.5) shouldBe Color(0x8080ff)
        iterator(1) shouldBe blue
        iterator(2) shouldBe blue
    }

    @Test
    fun linearRGBInterpolationBlueToWhite() {
        val iterator = interpolateRgb(blue, white)
        iterator(-1) shouldBe blue
        iterator(0) shouldBe blue
        iterator(0.5) shouldBe Color(0x8080ff)
        iterator(1) shouldBe white
        iterator(2) shouldBe white
    }


    @Test
    fun rgbSplineInterpolationGBRBG() {
        val iterator = interpolateRgbBasis(listOf(green, blue, red, blue, green))
        displaySmallGradient("rgbSplineInterpolationGBRBG", iterator, 880)
        iterator(-1) shouldBe green
        iterator(0) shouldBe green
        iterator(1) shouldBe green
        iterator(2) shouldBe green
    }

    @Test
    fun rgbSplineInterpolationGBRB() {
        val iterator = interpolateRgbBasis(listOf(green, blue, red, blue), cyclical = true)
        displaySmallGradient("rgbSplineInterpolationGBRB", iterator, 880)
        iterator(-1) shouldBe iterator(0)
        iterator(0) shouldBe iterator(1)
        iterator(1) shouldBe iterator(2)
    }

    /**
     * https://bl.ocks.org/mbostock/048d21cf747371b11884f75ad896e5a5)
     * "RGB spline interpolation colorbrewSpline reference (
     */
    @Test
    fun rgbSplineInterpolationColorbrewSpline() {
        val iterator = interpolateRgbBasis(
            arrayListOf(
                Color(0x8e0152), Color(0xc51b7d), Color(0xde77ae), Color(0xf1b6da),
                Color(0xfde0ef), Color(0xf7f7f7), Color(0xe6f5d0), Color(0xb8e186), Color(0x7fbc41),
                Color(0x4d9221), Color(0x276419)
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
                Color(0x8e0152), Color(0xc51b7d), Color(0xde77ae), Color(0xf1b6da),
                Color(0xfde0ef), Color(0xf7f7f7), Color(0xe6f5d0), Color(0xb8e186), Color(0x7fbc41),
                Color(0x4d9221), Color(0x276419)
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
        val iterator = interpolateRgb(Color(0x800080), Color(0xffa200))
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
        val iterator = interpolateRgb(Color(0x800080), Color(0xffa200), 2.2)
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
        percentToColor: (Float) -> Color,
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
            document.body?.appendChild(div)
        }
    }

}

fun node(name: String) = document.createElementNS(namespace.svg, name)

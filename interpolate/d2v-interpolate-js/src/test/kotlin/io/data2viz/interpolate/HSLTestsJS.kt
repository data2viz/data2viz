package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.HslColor
import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.math.Percent
import io.data2viz.test.namespace
import io.data2viz.math.deg
import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.browser.document
import kotlin.test.Test

class HSLTestsJS : TestBase() {

    /**
     * "HSL SHORT linear interpolation [(300°, 100%, 25%), (38°, 100%, 50%)]
     * see https://github.com/d3/d3-interpolate#interpolateHsl for reference
     */
    @Test
    fun hslShortLinearInterpolation() {

        val iterator = hslInterpolator(Colors.hsl(300.deg, 1.0, .25), Colors.hsl(38.deg, 1.0, .5))
        displaySmallGradient("HslColor", iterator, 888, imageReference = "http://data2viz.io/img/hsl.png")
        iterator(50.pct).toRgb().rgbHex shouldBe RgbColor(0xbf0023).rgbHex
    }

    /**
     * "HSL LONG linear interpolation [(300°, 100%, 25%), (38°, 100%, 50%)]"
     */
    @Test
    fun hslLongLinearInterpolation() {
        val iterator = hslLongInterpolator(Colors.hsl(300.deg, 1.0, .25), Colors.hsl(38.deg, 1.0, .5))
        displaySmallGradient("HslColor Long", iterator, 888, imageReference = "http://data2viz.io/img/hslLong.png")
        iterator(50.pct).toRgb().rgbHex shouldBe RgbColor(0x00bf9c).rgbHex
    }

    /**
     * "HSL SHORT linear interpolation [(38°, 100%, 50%), (300°, 100%, 25%)]"
     */
    @Test
    fun hslShortLinea() {
        val iterator = hslInterpolator(Colors.hsl(38.deg, 1.0, .5), Colors.hsl(300.deg, 1.0, .25))
        displaySmallGradient("HslColor Reverse", iterator, 888, imageReference = "http://data2viz.io/img/hslReverse.png")
        iterator(50.pct).toRgb().rgbHex shouldBe RgbColor(0xbf0023).rgbHex
    }

    /**
     * "HSL LONG linear interpolation [(38°, 100%, 50%), (300°, 100%, 25%)]"
     */
    @Test
    fun hslLongLinearInterpol() {
        val iterator = hslLongInterpolator(Colors.hsl(38.deg, 1.0, .5), Colors.hsl(300.deg, 1.0, .25))
        displaySmallGradient(
            "HslColor Long Reverse",
            iterator,
            888,
            imageReference = "http://data2viz.io/img/hslLongReverse.png"
        )
        iterator(50.pct).toRgb().rgbHex shouldBe RgbColor(0x00bf9c).rgbHex
    }

    fun displaySmallGradient(
        context: String,
        percentToColor: (Percent) -> Color,
        width: Int = 256,
        imageReference: String? = null
    ) {

        if (browserEnabled) {


            h2(context)
            document.body?.appendChild(
                nodeSVG("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                            nodeSVG("rect").apply {
                                setAttribute("fill", percentToColor(Percent(index / width.toDouble())).toRgb().rgbHex)
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

    fun nodeSVG(name: String) = document.createElementNS(namespace.svg, name)
}

package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.green
import io.data2viz.color.colors.red
import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import kotlin.browser.document

class ScaleTests : StringSpec() {

    init {
        val valueToColor = scale.linear.numberToColor(0 linkedTo red, 100 linkedTo blue)

        "FloatToColor LINEAR(0-> Red, 100->Blue) -50 should be red" { valueToColor(-50).rgbHex shouldBe red.rgbHex }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 0 should be red" { valueToColor(0).rgbHex shouldBe red.rgbHex }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 50.01 should be 0x800080 (50 is just between 0x7f007f and 0x800080)" { valueToColor(50.01).rgbHex shouldBe Color(0x800080).rgbHex }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 100 should be blue" { valueToColor(100).rgbHex shouldBe blue.rgbHex }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 150 should be blue" { valueToColor(150).rgbHex shouldBe blue.rgbHex }

        /*val valueToColor = scale.linear.numberToColor(0 linkedTo red, 100 linkedTo blue)
        "FloatToColor LINEAR(0-> Red, 100->Blue) 0 to 100" { displayScaleGradient(valueToColor, 0f, 100f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 50 to 150" { displayScaleGradient(valueToColor, 50f, 150f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) -50 to 50" { displayScaleGradient(valueToColor, -50f, 50f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) -100 to 200" { displayScaleGradient(valueToColor, -100f, 200f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 100 to 0" { displayScaleGradient(valueToColor, 100f, 0f) }*/
    }

    /*fun displayScaleGradient(domainToViz: (Float) -> Color, start: Float, end: Float, width: Int = 800) {
        val body = document.querySelector("body")!!
        body.appendChild(
                document.createElementNS(namespace.svg, "svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "30")

                    val tr: Float = (end - start) / width.toFloat()

                    (0 until width).forEach { index ->
                        appendChild(
                                document.createElementNS(namespace.svg, "rect").apply {
                                    setAttribute("fill", domainToViz((index.toFloat()) * tr + start).rgbHex)
                                    setAttribute("x", "$index")
                                    setAttribute("y", "0")
                                    setAttribute("width", "1")
                                    setAttribute("height", "30")
                                }
                        )
                    }
                }
        )
    }*/

}

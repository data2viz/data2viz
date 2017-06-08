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
        "FloatToColor LINEAR(0-> Red, 100->Blue) 50.01 should be 0x800080 (50 is just between 0x7f007f and 0x800080)" { valueToColor(50).rgbHex shouldBe Color(0x800080).rgbHex }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 100 should be blue" { valueToColor(100).rgbHex shouldBe blue.rgbHex }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 150 should be blue" { valueToColor(150).rgbHex shouldBe blue.rgbHex }
    }
}

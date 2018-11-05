package io.data2viz.interpolate

import io.data2viz.color.RgbColor
import io.data2viz.color.Colors.Web.blue
import io.data2viz.color.Colors.Web.red
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleTests : TestBase () {

    val valueToColor = scale.linear.numberToColor(0.0 linkedTo red, 100.0 linkedTo blue)

    @Test fun floatToColorLinearRedToBlue0to100WithMinus50IsRed() { valueToColor(-50.0).rgbHex shouldBe red.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With0IsRed()       { valueToColor(0.0).rgbHex shouldBe red.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With100IsBlue()    { valueToColor(100.0).rgbHex shouldBe blue.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With150IsBlue()    { valueToColor(150.0).rgbHex shouldBe blue.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With50Is800080()    { valueToColor(50.0).rgbHex shouldBe RgbColor(0x800080).rgbHex }
}

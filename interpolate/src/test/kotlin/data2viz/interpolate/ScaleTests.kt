package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.red
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleTests : TestBase () {

    val valueToColor = scale.linear.numberToColor(0 linkedTo red, 100 linkedTo blue)

    @Test fun floatToColorLinearRedToBlue0to100WithMinus50IsRed() { valueToColor(-50).rgbHex shouldBe red.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With0IsRed()       { valueToColor(0).rgbHex shouldBe red.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With100IsBlue()    { valueToColor(100).rgbHex shouldBe blue.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With150IsBlue()    { valueToColor(150).rgbHex shouldBe blue.rgbHex }
    @Test fun floatToColorLinearRedToBlue0to100With50Is800080()    { valueToColor(50).rgbHex shouldBe Color(0x800080).rgbHex }
}

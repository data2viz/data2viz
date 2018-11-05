package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.col
import io.data2viz.test.TestBase
import kotlin.test.Test

class HCLTests : TestBase() {

    @Test
    fun HCLInterpolationTeal2Pink() {
        val iterator = hclInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe "#1a8a9d".col
        iterator(0.25) shouldBe "#4092b3".col
        iterator(0.50) shouldBe "#8f9fd0".col
        iterator(0.60) shouldBe "#ada4d4".col
        iterator(0.75) shouldBe "#d3acd4".col
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HCLInterpolationWhiteToBlue() {
        val iterator = hclInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe "#ede0ff".col
        iterator(0.25) shouldBe "#dcc4ff".col
        iterator(0.50) shouldBe "#b38bff".col
        iterator(0.60) shouldBe "#a074ff".col
        iterator(0.75) shouldBe "#7e52ff".col
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun HCLInterpolationWhiteToBlack() {
        val iterator = hclInterpolator(Colors.Web.white, Colors.Web.black)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe "#dadada".col
        iterator(0.25) shouldBe "#b9b9b9".col
        iterator(0.50) shouldBe "#777777".col
        iterator(0.60) shouldBe "#5e5e5e".col
        iterator(0.75) shouldBe "#3b3b3b".col
        iterator(1.0) shouldBe Colors.Web.black
        iterator(2.0) shouldBe Colors.Web.black
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HCLInterpolationBlueToWhite() {
        val iterator = hclInterpolator(Colors.Web.blue, Colors.Web.white)
        iterator(-1.0) shouldBe Colors.Web.blue
        iterator(0.0) shouldBe Colors.Web.blue
        iterator(0.13) shouldBe "#5b33ff".col
        iterator(0.25) shouldBe "#7e52ff".col
        iterator(0.50) shouldBe "#b38bff".col
        iterator(0.60) shouldBe "#c4a2ff".col
        iterator(0.75) shouldBe "#dcc4ff".col
        iterator(1.0) shouldBe Colors.Web.white
        iterator(2.0) shouldBe Colors.Web.white
    }

    @Test
    fun HCLInterpolationShort() {
        val iterator = hclInterpolator("#810082".col, "#ffa600".col)
        iterator(-1.0) shouldBe "#810082".col
        iterator(0.0) shouldBe "#810082".col
        iterator(0.13) shouldBe "#a6007a".col
        iterator(0.25) shouldBe "#c20070".col
        iterator(0.50) shouldBe "#ed2f54".col
        iterator(0.60) shouldBe "#f84847".col
        iterator(0.75) shouldBe "#ff6c33".col
        iterator(1.0) shouldBe "#ffa600".col
        iterator(2.0) shouldBe "#ffa600".col
    }

    // TODO
//    @Test
//    fun HCLInterpolationLong() {
//        val iterator = interpolateHclLong("#810082".col, "#ffa600".col)
//        iterator(-1.0) shouldBe "#810082".col
//        iterator(0.0) shouldBe "#810082".col
//        iterator(0.13) shouldBe Colors.rgb(68, 72, 190)
//        iterator(0.25) shouldBe Colors.rgb(0, 107, 217)
//        iterator(0.50) shouldBe Colors.rgb(0, 154, 167)
//        iterator(0.60) shouldBe Colors.rgb(0, 167, 120)
//        iterator(0.75) shouldBe Colors.rgb(42, 180, 37)
//        iterator(1.0) shouldBe "#ffa600".col
//        iterator(2.0) shouldBe "#ffa600".col
//    }
}
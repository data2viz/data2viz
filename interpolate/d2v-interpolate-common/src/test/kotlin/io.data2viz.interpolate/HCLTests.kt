package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.col
import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test

class HCLTests : TestBase() {

    @Test
    fun HCLInterpolationTeal2Pink() {
        val iterator = hclInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-100.pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe "#1a8a9d".col
        iterator(25.pct) shouldBe "#4092b3".col
        iterator(50.pct) shouldBe "#8f9fd0".col
        iterator(60.pct) shouldBe "#ada4d4".col
        iterator(75.pct) shouldBe "#d3acd4".col
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HCLInterpolationWhiteToBlue() {
        val iterator = hclInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe "#ede0ff".col
        iterator(25.pct) shouldBe "#dcc4ff".col
        iterator(50.pct) shouldBe "#b38bff".col
        iterator(60.pct) shouldBe "#a074ff".col
        iterator(75.pct) shouldBe "#7e52ff".col
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun HCLInterpolationWhiteToBlack() {
        val iterator = hclInterpolator(Colors.Web.white, Colors.Web.black)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe "#dadada".col
        iterator(25.pct) shouldBe "#b9b9b9".col
        iterator(50.pct) shouldBe "#777777".col
        iterator(60.pct) shouldBe "#5e5e5e".col
        iterator(75.pct) shouldBe "#3b3b3b".col
        iterator(100.pct) shouldBe Colors.Web.black
        iterator(200.pct) shouldBe Colors.Web.black
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HCLInterpolationBlueToWhite() {
        val iterator = hclInterpolator(Colors.Web.blue, Colors.Web.white)
        iterator(-100.pct) shouldBe Colors.Web.blue
        iterator(0.pct) shouldBe Colors.Web.blue
        iterator(13.pct) shouldBe "#5b33ff".col
        iterator(25.pct) shouldBe "#7e52ff".col
        iterator(50.pct) shouldBe "#b38bff".col
        iterator(60.pct) shouldBe "#c4a2ff".col
        iterator(75.pct) shouldBe "#dcc4ff".col
        iterator(100.pct) shouldBe Colors.Web.white
        iterator(200.pct) shouldBe Colors.Web.white
    }

    @Test
    fun HCLInterpolationShort() {
        val iterator = hclInterpolator("#810082".col, "#ffa600".col)
        iterator(-100.pct) shouldBe "#810082".col
        iterator(0.pct) shouldBe "#810082".col
        iterator(13.pct) shouldBe "#a6007a".col
        iterator(25.pct) shouldBe "#c20070".col
        iterator(50.pct) shouldBe "#ed2f54".col
        iterator(60.pct) shouldBe "#f84847".col
        iterator(75.pct) shouldBe "#ff6c33".col
        iterator(100.pct) shouldBe "#ffa600".col
        iterator(200.pct) shouldBe "#ffa600".col
    }

    // TODO
//    @Test
//    fun HCLInterpolationLong() {
//        val iterator = interpolateHclLong("#810082".col, "#ffa600".col)
//        iterator(-100.pct) shouldBe "#810082".col
//        iterator(0.pct) shouldBe "#810082".col
//        iterator(13.pct) shouldBe Colors.rgb(68, 72, 190)
//        iterator(25.pct) shouldBe Colors.rgb(0, 107, 217)
//        iterator(50.pct) shouldBe Colors.rgb(0, 154, 167)
//        iterator(60.pct) shouldBe Colors.rgb(0, 167, 120)
//        iterator(75.pct) shouldBe Colors.rgb(42, 180, 37)
//        iterator(100.pct) shouldBe "#ffa600".col
//        iterator(200.pct) shouldBe "#ffa600".col
//    }
}
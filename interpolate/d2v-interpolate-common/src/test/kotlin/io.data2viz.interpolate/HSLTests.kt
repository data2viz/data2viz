package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.color.col
import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test

class HSLTests : TestBase() {

    @Test
    fun HSLInterpolationTeal2Pink() {
        val iterator = hslInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator((-100).pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe RgbColor(0x006ba9)
        iterator(25.pct) shouldBe RgbColor(0x003dd0)
        iterator(50.pct) shouldBe RgbColor(0x7c21ff)
        iterator(60.pct) shouldBe RgbColor(0xc540ff)
        iterator(75.pct) shouldBe RgbColor(0xff70ee)
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationWhiteToBlue() {
        val iterator = hslInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator((-100).pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe RgbColor(0xdedeff)
        iterator(25.pct) shouldBe RgbColor(0xbfbfff)
        iterator(50.pct) shouldBe RgbColor(0x8080ff)
        iterator(60.pct) shouldBe RgbColor(0x6666ff)
        iterator(75.pct) shouldBe RgbColor(0x4040ff)
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationBlueToWhite() {
        val iterator = hslInterpolator(Colors.Web.blue, Colors.Web.white)
        iterator((-100).pct) shouldBe Colors.Web.blue
        iterator(0.pct) shouldBe Colors.Web.blue
        iterator(25.pct) shouldBe RgbColor(0x4040ff)
        iterator(40.pct) shouldBe RgbColor(0x6666ff)
        iterator(50.pct) shouldBe RgbColor(0x8080ff)
        iterator(75.pct) shouldBe RgbColor(0xbfbfff)
        iterator(87.pct) shouldBe RgbColor(0xdedeff)
        iterator(100.pct) shouldBe Colors.Web.white
        iterator(200.pct) shouldBe Colors.Web.white
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun HSLInterpolationWhiteToBlack() {
        val iterator = hslInterpolator(Colors.Web.white, Colors.Web.black)
        iterator((-100).pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe RgbColor(0xdedede)
        iterator(25.pct) shouldBe RgbColor(0xbfbfbf)
        iterator(50.pct) shouldBe RgbColor(0x808080)
        iterator(60.pct) shouldBe RgbColor(0x666666)
        iterator(75.pct) shouldBe RgbColor(0x404040)
        iterator(100.pct) shouldBe Colors.Web.black
        iterator(200.pct) shouldBe Colors.Web.black
    }

    @Test
    fun HSLInterpolationShort() {
        val iterator = hslInterpolator("#810082".col, "#ffa600".col)
        iterator((-100).pct) shouldBe "#810082".col
        iterator(0.pct) shouldBe "#810082".col
        iterator(13.pct) shouldBe Colors.rgb(146, 0, 116)
        iterator(25.pct) shouldBe Colors.rgb(161, 0, 96)
        iterator(50.pct) shouldBe Colors.rgb(193, 0, 34)
        iterator(60.pct) shouldBe Colors.rgb(205, 0, 3)
        iterator(75.pct) shouldBe Colors.rgb(224, 53, 0)
        iterator(100.pct) shouldBe "#ffa600".col
        iterator(200.pct) shouldBe "#ffa600".col
    }

    @Test
    fun HSLInterpolationLong() {
        val iterator = hslLongInterpolator("#810082".col, "#ffa600".col)
        iterator((-100).pct) shouldBe "#810082".col
        iterator(0.pct) shouldBe "#810082".col
        iterator(13.pct) shouldBe Colors.rgb(63, 0, 146)
        iterator(25.pct) shouldBe Colors.rgb(0, 15, 161)
        iterator(50.pct) shouldBe Colors.rgb(0, 193, 158)
        iterator(60.pct) shouldBe Colors.rgb(0, 205, 79)
        iterator(75.pct) shouldBe Colors.rgb(59, 224, 0)
        iterator(100.pct) shouldBe "#ffa600".col
        iterator(200.pct) shouldBe "#ffa600".col
    }
}
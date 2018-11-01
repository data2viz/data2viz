package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.test.TestBase
import kotlin.test.Test

class HSLTests : TestBase() {

    @Test
    fun HSLInterpolationTeal2Pink() {
        val iterator = interpolateHsl(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe RgbColor(0x006ba9)
        iterator(0.25) shouldBe RgbColor(0x003dd0)
        iterator(0.50) shouldBe RgbColor(0x7c21ff)
        iterator(0.60) shouldBe RgbColor(0xc540ff)
        iterator(0.75) shouldBe RgbColor(0xff70ee)
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationWhiteToBlue() {
        val iterator = interpolateHsl(Colors.Web.white, Colors.Web.blue)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe RgbColor(0xdedeff)
        iterator(0.25) shouldBe RgbColor(0xbfbfff)
        iterator(0.5) shouldBe RgbColor(0x8080ff)
        iterator(0.60) shouldBe RgbColor(0x6666ff)
        iterator(0.75) shouldBe RgbColor(0x4040ff)
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationBlueToWhite() {
        val iterator = interpolateHsl(Colors.Web.blue, Colors.Web.white)
        iterator(-1.0) shouldBe Colors.Web.blue
        iterator(0.0) shouldBe Colors.Web.blue
        iterator(0.25) shouldBe RgbColor(0x4040ff)
        iterator(0.40) shouldBe RgbColor(0x6666ff)
        iterator(0.5) shouldBe RgbColor(0x8080ff)
        iterator(0.75) shouldBe RgbColor(0xbfbfff)
        iterator(0.87) shouldBe RgbColor(0xdedeff)
        iterator(1.0) shouldBe Colors.Web.white
        iterator(2.0) shouldBe Colors.Web.white
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun HSLInterpolationWhiteToBlack() {
        val iterator = interpolateHsl(Colors.Web.white, Colors.Web.black)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe RgbColor(0xdedede)
        iterator(0.25) shouldBe RgbColor(0xbfbfbf)
        iterator(0.5) shouldBe RgbColor(0x808080)
        iterator(0.60) shouldBe RgbColor(0x666666)
        iterator(0.75) shouldBe RgbColor(0x404040)
        iterator(1.0) shouldBe Colors.Web.black
        iterator(2.0) shouldBe Colors.Web.black
    }
}
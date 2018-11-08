package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class HSLTests : TestBase() {

    @Test
    fun HSLInterpolationTeal2Pink() {
        val iterator = hslInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe Colors.rgb(0x006ba9, 1.0)
        iterator(0.25) shouldBe Colors.rgb(0x003dd0)
        iterator(0.50) shouldBe Colors.rgb(0x7c21ff)
        iterator(0.60) shouldBe Colors.rgb(0xc540ff)
        iterator(0.75) shouldBe Colors.rgb(0xff70ee)
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationWhiteToBlue() {
        val iterator = hslInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe 0xdedeff.col
        iterator(0.25) shouldBe 0xbfbfff.col
        iterator(0.5) shouldBe  0x8080ff.col
        iterator(0.60) shouldBe 0x6666ff.col
        iterator(0.75) shouldBe 0x4040ff.col
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationBlueToWhite() {
        val iterator = hslInterpolator(Colors.Web.blue, Colors.Web.white)
        iterator(-1.0) shouldBe Colors.Web.blue
        iterator(0.0) shouldBe Colors.Web.blue
        iterator(0.25) shouldBe 0x4040ff.col
        iterator(0.40) shouldBe 0x6666ff.col
        iterator(0.5) shouldBe 0x8080ff.col
        iterator(0.75) shouldBe 0xbfbfff.col
        iterator(0.87) shouldBe 0xdedeff.col
        iterator(1.0) shouldBe Colors.Web.white
        iterator(2.0) shouldBe Colors.Web.white
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun HSLInterpolationWhiteToBlack() {
        val iterator = hslInterpolator(Colors.Web.white, Colors.Web.black)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe 0xdedede.col
        iterator(0.25) shouldBe 0xbfbfbf.col
        iterator(0.5) shouldBe 0x808080.col
        iterator(0.60) shouldBe 0x666666.col
        iterator(0.75) shouldBe 0x404040.col
        iterator(1.0) shouldBe Colors.Web.black
        iterator(2.0) shouldBe Colors.Web.black
    }

    @Test
    fun HSLInterpolationShort() {
        val iterator = hslInterpolator("#810082".col, "#ffa600".col)
        iterator(-1.0) shouldBe "#810082".col
        iterator(0.0) shouldBe "#810082".col
        iterator(0.13) shouldBe Colors.rgb(146, 0, 116)
        iterator(0.25) shouldBe Colors.rgb(161, 0, 96)
        iterator(0.50) shouldBe Colors.rgb(193, 0, 34)
        iterator(0.60) shouldBe Colors.rgb(205, 0, 3)
        iterator(0.75) shouldBe Colors.rgb(224, 53, 0)
        iterator(1.0) shouldBe "#ffa600".col
        iterator(2.0) shouldBe "#ffa600".col
    }

    @Test
    fun HSLInterpolationLong() {
        val iterator = hslLongInterpolator("#810082".col, "#ffa600".col)
        iterator(-1.0) shouldBe "#810082".col
        iterator(0.0) shouldBe "#810082".col
        iterator(0.13) shouldBe Colors.rgb(63, 0, 146)
        iterator(0.25) shouldBe Colors.rgb(0, 15, 161)
        iterator(0.50) shouldBe Colors.rgb(0, 193, 158)
        iterator(0.60) shouldBe Colors.rgb(0, 205, 79)
        iterator(0.75) shouldBe Colors.rgb(59, 224, 0)
        iterator(1.0) shouldBe "#ffa600".col
        iterator(2.0) shouldBe "#ffa600".col
    }
}
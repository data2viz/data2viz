package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class HSLTests : TestBase() {

    @Test
    fun HSLInterpolationTeal2Pink() {
        val iterator = hslInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-100.pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe Colors.rgb(0x006ba9, 1.0)
        iterator(25.pct) shouldBe Colors.rgb(0x003dd0)
        iterator(50.pct) shouldBe Colors.rgb(0x7c21ff)
        iterator(60.pct) shouldBe Colors.rgb(0xc540ff)
        iterator(75.pct) shouldBe Colors.rgb(0xff70ee)
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }

    @Test
    // TODO : test in D3 when the 2 colors delta is < 180.deg... here will produce same interpolation as HSLShort...
    fun HSLShortInterpolation() {
        val start = Colors.hsl(300.deg, 1.0, .25)
        val end = Colors.hsl(38.deg, 1.0, .5)
        val iterator = hslInterpolator(start, end)
        iterator(-100.pct) shouldBe start
        iterator(0.pct) shouldBe start
        iterator(50.pct) shouldBe Colors.hsl(349.deg, 1.0, 0.375)
        iterator(100.pct) shouldBe end
        iterator(200.pct) shouldBe end
    }

    @Test
    fun HSLLongInterpolation() {
        val start = Colors.hsl(300.deg, 1.0, .25)
        val end = Colors.hsl(38.deg, 1.0, .5)
        val iterator = hslLongInterpolator(start, end)
        iterator(-100.pct) shouldBe start
        iterator(0.pct) shouldBe start
//        iterator(13.pct) shouldBe Colors.rgb(0x006ba9, 1.0)
//        iterator(25.pct) shouldBe Colors.rgb(0x003dd0)
        iterator(50.pct) shouldBe Colors.hsl(169.deg, 1.0, 0.375)
//        iterator(60.pct) shouldBe Colors.rgb(0xc540ff)
//        iterator(75.pct) shouldBe Colors.rgb(0xff70ee)
        iterator(100.pct) shouldBe end
        iterator(200.pct) shouldBe end
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationWhiteToBlue() {
        val iterator = hslInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe 0xdedeff.col
        iterator(25.pct) shouldBe 0xbfbfff.col
        iterator(50.pct) shouldBe  0x8080ff.col
        iterator(60.pct) shouldBe 0x6666ff.col
        iterator(75.pct) shouldBe 0x4040ff.col
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun HSLInterpolationBlueToWhite() {
        val iterator = hslInterpolator(Colors.Web.blue, Colors.Web.white)
        iterator(-100.pct) shouldBe Colors.Web.blue
        iterator(0.pct) shouldBe Colors.Web.blue
        iterator(25.pct) shouldBe 0x4040ff.col
        iterator(40.pct) shouldBe 0x6666ff.col
        iterator(50.pct) shouldBe 0x8080ff.col
        iterator(75.pct) shouldBe 0xbfbfff.col
        iterator(87.pct) shouldBe 0xdedeff.col
        iterator(100.pct) shouldBe Colors.Web.white
        iterator(200.pct) shouldBe Colors.Web.white
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun HSLInterpolationWhiteToBlack() {
        val iterator = hslInterpolator(Colors.Web.white, Colors.Web.black)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe 0xdedede.col
        iterator(25.pct) shouldBe 0xbfbfbf.col
        iterator(50.pct) shouldBe 0x808080.col
        iterator(60.pct) shouldBe 0x666666.col
        iterator(75.pct) shouldBe 0x404040.col
        iterator(100.pct) shouldBe Colors.Web.black
        iterator(200.pct) shouldBe Colors.Web.black
    }

    @Test
    fun HSLInterpolationShort() {
        val iterator = hslInterpolator("#810082".col, "#ffa600".col)
        iterator(-100.pct) shouldBe "#810082".col
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
        iterator(-100.pct) shouldBe "#810082".col
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
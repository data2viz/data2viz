package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.color.color
import io.data2viz.test.TestBase
import kotlin.test.Test

class LABTests : TestBase() {

    @Test
    fun LABInterpolationTeal2Pink() {
        val iterator = interpolateLab(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe "#438989".color
        iterator(0.25) shouldBe "#629192".color
        iterator(0.50) shouldBe "#99a2a5".color
        iterator(0.60) shouldBe "#aea8ac".color
        iterator(0.75) shouldBe "#cdb1b8".color
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun LABInterpolationWhiteToBlue() {
        val iterator = interpolateLab(Colors.Web.white, Colors.Web.blue)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe "#ede0ff".color
        iterator(0.25) shouldBe "#dcc4ff".color
        iterator(0.50) shouldBe "#b38bff".color
        iterator(0.60) shouldBe "#a074ff".color
        iterator(0.75) shouldBe "#7e52ff".color
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    // WHITE & BLACK = ACHROMATIC, case even more specific !
    @Test
    fun LABInterpolationWhiteToBlack() {
        val iterator = interpolateLab(Colors.Web.white, Colors.Web.black)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe "#dadada".color
        iterator(0.25) shouldBe "#b9b9b9".color
        iterator(0.50) shouldBe "#777777".color
        iterator(0.60) shouldBe "#5e5e5e".color
        iterator(0.75) shouldBe "#3b3b3b".color
        iterator(1.0) shouldBe Colors.Web.black
        iterator(2.0) shouldBe Colors.Web.black
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun LABInterpolationBlueToWhite() {
        val iterator = interpolateLab(Colors.Web.blue, Colors.Web.white)
        iterator(-1.0) shouldBe Colors.Web.blue
        iterator(0.0) shouldBe Colors.Web.blue
        iterator(0.13) shouldBe "#5b33ff".color
        iterator(0.25) shouldBe "#7e52ff".color
        iterator(0.50) shouldBe "#b38bff".color
        iterator(0.60) shouldBe "#c4a2ff".color
        iterator(0.75) shouldBe "#dcc4ff".color
        iterator(1.0) shouldBe Colors.Web.white
        iterator(2.0) shouldBe Colors.Web.white
    }

    @Test
    fun LABInterpolation() {
        val iterator = interpolateLab("#810082".color, "#ffa600".color)
        iterator(-1.0) shouldBe "#810082".color
        iterator(0.0) shouldBe "#810082".color
        iterator(0.13) shouldBe "#952379".color
        iterator(0.25) shouldBe "#a63870".color
        iterator(0.50) shouldBe "#c65e5c".color
        iterator(0.60) shouldBe "#d26c52".color
        iterator(0.75) shouldBe "#e38241".color
        iterator(1.0) shouldBe "#ffa600".color
        iterator(2.0) shouldBe "#ffa600".color
    }
}
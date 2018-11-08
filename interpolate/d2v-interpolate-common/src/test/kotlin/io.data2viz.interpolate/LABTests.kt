package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class LABTests : TestBase() {

    @Test
    fun LABInterpolationTeal2Pink() {
        val iterator = labInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe "#438989".col
        iterator(0.25) shouldBe "#629192".col
        iterator(0.50) shouldBe "#99a2a5".col
        iterator(0.60) shouldBe "#aea8ac".col
        iterator(0.75) shouldBe "#cdb1b8".col
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun LABInterpolationWhiteToBlue() {
        val iterator = labInterpolator(Colors.Web.white, Colors.Web.blue)
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
    fun LABInterpolationWhiteToBlack() {
        val iterator = labInterpolator(Colors.Web.white, Colors.Web.black)
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
    fun LABInterpolationBlueToWhite() {
        val iterator = labInterpolator(Colors.Web.blue, Colors.Web.white)
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
    fun LABInterpolation() {
        val iterator = labInterpolator("#810082".col, "#ffa600".col)
        iterator(-1.0) shouldBe "#810082".col
        iterator(0.0) shouldBe "#810082".col
        iterator(0.13) shouldBe "#952379".col
        iterator(0.25) shouldBe "#a63870".col
        iterator(0.50) shouldBe "#c65e5c".col
        iterator(0.60) shouldBe "#d26c52".col
        iterator(0.75) shouldBe "#e38241".col
        iterator(1.0) shouldBe "#ffa600".col
        iterator(2.0) shouldBe "#ffa600".col
    }
}
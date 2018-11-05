package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.col
import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test

class LABTests : TestBase() {

    @Test
    fun LABInterpolationTeal2Pink() {
        val iterator = labInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator((-100).pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe "#438989".col
        iterator(25.pct) shouldBe "#629192".col
        iterator(50.pct) shouldBe "#99a2a5".col
        iterator(60.pct) shouldBe "#aea8ac".col
        iterator(75.pct) shouldBe "#cdb1b8".col
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }

    // WHITE = ACHROMATIC, case specific !
    @Test
    fun LABInterpolationWhiteToBlue() {
        val iterator = labInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator((-100).pct) shouldBe Colors.Web.white
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
    fun LABInterpolationWhiteToBlack() {
        val iterator = labInterpolator(Colors.Web.white, Colors.Web.black)
        iterator((-100).pct) shouldBe Colors.Web.white
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
    fun LABInterpolationBlueToWhite() {
        val iterator = labInterpolator(Colors.Web.blue, Colors.Web.white)
        iterator((-100).pct) shouldBe Colors.Web.blue
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
    fun LABInterpolation() {
        val iterator = labInterpolator("#810082".col, "#ffa600".col)
        iterator((-100).pct) shouldBe "#810082".col
        iterator(0.pct) shouldBe "#810082".col
        iterator(13.pct) shouldBe "#952379".col
        iterator(25.pct) shouldBe "#a63870".col
        iterator(50.pct) shouldBe "#c65e5c".col
        iterator(60.pct) shouldBe "#d26c52".col
        iterator(75.pct) shouldBe "#e38241".col
        iterator(100.pct) shouldBe "#ffa600".col
        iterator(200.pct) shouldBe "#ffa600".col
    }
}
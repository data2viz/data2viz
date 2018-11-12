package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class RGBTests : TestBase() {

    @Test
    fun interpolateSameColor() {
        val iterator = rgbDefaultInterpolator(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen)
        iterator(-100.pct) shouldBe Colors.Web.darkolivegreen
        iterator(0.pct) shouldBe Colors.Web.darkolivegreen
        iterator(50.pct) shouldBe Colors.Web.darkolivegreen
        iterator(100.pct) shouldBe Colors.Web.darkolivegreen
        iterator(200.pct) shouldBe Colors.Web.darkolivegreen
    }

    /*@Test
    fun interpolateSameColorGamma2() {
        val iterator = rgbDefaultInterpolator(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen)
        iterator(-100.pct) shouldBe iterator(0.pct)
        iterator(0.pct) shouldBe iterator(0.5)
        iterator(50.pct) shouldBe iterator(100.pct)
        iterator(100.pct) shouldBe iterator(200.pct)
    }*/

    @Test
    fun splineRGBSameColor() {
        val iterator = rgbBasisInterpolator(listOf(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen))
        iterator(-100.pct) shouldBe Colors.Web.darkolivegreen
        iterator(0.pct) shouldBe Colors.Web.darkolivegreen
        iterator(50.pct) shouldBe Colors.Web.darkolivegreen
        iterator(100.pct) shouldBe Colors.Web.darkolivegreen
        iterator(200.pct) shouldBe Colors.Web.darkolivegreen
    }


    @Test
    fun cyclicalSplineRGBSameColor() {
        val iterator =
            rgbBasisClosedInterpolator(listOf(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen))
        iterator(-100.pct) shouldBe Colors.Web.darkolivegreen
        iterator(0.pct) shouldBe Colors.Web.darkolivegreen
        iterator(50.pct) shouldBe Colors.Web.darkolivegreen
        iterator(100.pct) shouldBe Colors.Web.darkolivegreen
        iterator(200.pct) shouldBe Colors.Web.darkolivegreen
    }

    @Test
    fun RGBInterpolationWhiteToBlue() {
        val iterator = rgbDefaultInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe 0xdedeff.col
        iterator(25.pct) shouldBe 0xbfbfff.col
        iterator(50.pct) shouldBe 0x8080ff.col
        iterator(60.pct) shouldBe 0x6666ff.col
        iterator(75.pct) shouldBe 0x4040ff.col
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    @Test
    fun RGBInterpolationTeal2Pink() {
        val iterator = rgbDefaultInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-100.pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe 0x21888a.col
        iterator(25.pct) shouldBe 0x409093.col
        iterator(50.pct) shouldBe 0x80a0a6.col
        iterator(60.pct) shouldBe 0x99a6ad.col
        iterator(75.pct) shouldBe 0xbfb0b8.col
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }

    @Test
    fun linearRGBInterpolationWhiteToBlue() {
        val iterator = rgbLinearInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe 0xeeeeff.col
        iterator(25.pct) shouldBe 0xddddff.col
        iterator(50.pct) shouldBe 0xb4b4ff.col
        iterator(60.pct) shouldBe 0xa1a1ff.col
        iterator(75.pct) shouldBe 0x8080ff.col
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    @Test
    fun linearRGBInterpolationTeal2Pink() {
        val iterator = rgbLinearInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-100.pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe 0x5c8a8c.col
        iterator(25.pct) shouldBe 0x809396.col
        iterator(50.pct) shouldBe 0xb4a3aa.col
        iterator(60.pct) shouldBe 0xc6a9b1.col
        iterator(75.pct) shouldBe 0xddb2bb.col
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }
}
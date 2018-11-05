package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.math.pct
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
        iterator(0.5) shouldBe iterator(100.pct)
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
        iterator(13.pct) shouldBe RgbColor(0xdedeff)
        iterator(25.pct) shouldBe RgbColor(0xbfbfff)
        iterator(50.pct) shouldBe RgbColor(0x8080ff)
        iterator(60.pct) shouldBe RgbColor(0x6666ff)
        iterator(75.pct) shouldBe RgbColor(0x4040ff)
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    @Test
    fun RGBInterpolationTeal2Pink() {
        val iterator = rgbDefaultInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-100.pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe RgbColor(0x21888a)
        iterator(25.pct) shouldBe RgbColor(0x409093)
        iterator(50.pct) shouldBe RgbColor(0x80a0a6)
        iterator(60.pct) shouldBe RgbColor(0x99a6ad)
        iterator(75.pct) shouldBe RgbColor(0xbfb0b8)
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }

    @Test
    fun linearRGBInterpolationWhiteToBlue() {
        val iterator = rgbLinearInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-100.pct) shouldBe Colors.Web.white
        iterator(0.pct) shouldBe Colors.Web.white
        iterator(13.pct) shouldBe RgbColor(0xeeeeff)
        iterator(25.pct) shouldBe RgbColor(0xddddff)
        iterator(50.pct) shouldBe RgbColor(0xb4b4ff)
        iterator(60.pct) shouldBe RgbColor(0xa1a1ff)
        iterator(75.pct) shouldBe RgbColor(0x8080ff)
        iterator(100.pct) shouldBe Colors.Web.blue
        iterator(200.pct) shouldBe Colors.Web.blue
    }

    @Test
    fun linearRGBInterpolationTeal2Pink() {
        val iterator = rgbLinearInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-100.pct) shouldBe Colors.Web.teal
        iterator(0.pct) shouldBe Colors.Web.teal
        iterator(13.pct) shouldBe RgbColor(0x5c8a8c)
        iterator(25.pct) shouldBe RgbColor(0x809396)
        iterator(50.pct) shouldBe RgbColor(0xb4a3aa)
        iterator(60.pct) shouldBe RgbColor(0xc6a9b1)
        iterator(75.pct) shouldBe RgbColor(0xddb2bb)
        iterator(100.pct) shouldBe Colors.Web.pink
        iterator(200.pct) shouldBe Colors.Web.pink
    }
}
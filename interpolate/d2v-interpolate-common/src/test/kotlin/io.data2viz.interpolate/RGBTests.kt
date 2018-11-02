package io.data2viz.interpolate

import io.data2viz.color.Colors
import io.data2viz.color.RgbColor
import io.data2viz.test.TestBase
import kotlin.test.Test

class RGBTests : TestBase() {

    @Test
    fun interpolateSameColor() {
        val iterator = rgbDefaultInterpolator(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen)
        iterator(-1.0) shouldBe Colors.Web.darkolivegreen
        iterator(0.0) shouldBe Colors.Web.darkolivegreen
        iterator(0.5) shouldBe Colors.Web.darkolivegreen
        iterator(1.0) shouldBe Colors.Web.darkolivegreen
        iterator(2.0) shouldBe Colors.Web.darkolivegreen
    }

    /*@Test
    fun interpolateSameColorGamma2() {
        val iterator = rgbDefaultInterpolator(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen)
        iterator(-1.0) shouldBe iterator(0.0)
        iterator(0.0) shouldBe iterator(0.5)
        iterator(0.5) shouldBe iterator(1.0)
        iterator(1.0) shouldBe iterator(2.0)
    }*/

    @Test
    fun splineRGBSameColor() {
        val iterator = rgbBasisInterpolator(listOf(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen))
        iterator(-1.0) shouldBe Colors.Web.darkolivegreen
        iterator(0.0) shouldBe Colors.Web.darkolivegreen
        iterator(0.5) shouldBe Colors.Web.darkolivegreen
        iterator(1.0) shouldBe Colors.Web.darkolivegreen
        iterator(2.0) shouldBe Colors.Web.darkolivegreen
    }


    @Test
    fun cyclicalSplineRGBSameColor() {
        val iterator =
            rgbBasisClosedInterpolator(listOf(Colors.Web.darkolivegreen, Colors.Web.darkolivegreen))
        iterator(-1.0) shouldBe Colors.Web.darkolivegreen
        iterator(0.0) shouldBe Colors.Web.darkolivegreen
        iterator(0.5) shouldBe Colors.Web.darkolivegreen
        iterator(1.0) shouldBe Colors.Web.darkolivegreen
        iterator(2.0) shouldBe Colors.Web.darkolivegreen
    }

    @Test
    fun RGBInterpolationWhiteToBlue() {
        val iterator = rgbDefaultInterpolator(Colors.Web.white, Colors.Web.blue)
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

    @Test
    fun RGBInterpolationTeal2Pink() {
        val iterator = rgbDefaultInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe RgbColor(0x21888a)
        iterator(0.25) shouldBe RgbColor(0x409093)
        iterator(0.50) shouldBe RgbColor(0x80a0a6)
        iterator(0.60) shouldBe RgbColor(0x99a6ad)
        iterator(0.75) shouldBe RgbColor(0xbfb0b8)
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    @Test
    fun linearRGBInterpolationWhiteToBlue() {
        val iterator = rgbLinearInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe RgbColor(0xeeeeff)
        iterator(0.25) shouldBe RgbColor(0xddddff)
        iterator(0.5) shouldBe RgbColor(0xb4b4ff)
        iterator(0.60) shouldBe RgbColor(0xa1a1ff)
        iterator(0.75) shouldBe RgbColor(0x8080ff)
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    @Test
    fun linearRGBInterpolationTeal2Pink() {
        val iterator = rgbLinearInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe RgbColor(0x5c8a8c)
        iterator(0.25) shouldBe RgbColor(0x809396)
        iterator(0.50) shouldBe RgbColor(0xb4a3aa)
        iterator(0.60) shouldBe RgbColor(0xc6a9b1)
        iterator(0.75) shouldBe RgbColor(0xddb2bb)
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }
}
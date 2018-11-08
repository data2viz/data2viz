package io.data2viz.interpolate

import io.data2viz.color.*
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
        iterator(0.13) shouldBe 0xdedeff.col
        iterator(0.25) shouldBe 0xbfbfff.col
        iterator(0.5) shouldBe 0x8080ff.col
        iterator(0.60) shouldBe 0x6666ff.col
        iterator(0.75) shouldBe 0x4040ff.col
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    @Test
    fun RGBInterpolationTeal2Pink() {
        val iterator = rgbDefaultInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe 0x21888a.col
        iterator(0.25) shouldBe 0x409093.col
        iterator(0.50) shouldBe 0x80a0a6.col
        iterator(0.60) shouldBe 0x99a6ad.col
        iterator(0.75) shouldBe 0xbfb0b8.col
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }

    @Test
    fun linearRGBInterpolationWhiteToBlue() {
        val iterator = rgbLinearInterpolator(Colors.Web.white, Colors.Web.blue)
        iterator(-1.0) shouldBe Colors.Web.white
        iterator(0.0) shouldBe Colors.Web.white
        iterator(0.13) shouldBe 0xeeeeff.col
        iterator(0.25) shouldBe 0xddddff.col
        iterator(0.5) shouldBe 0xb4b4ff.col
        iterator(0.60) shouldBe 0xa1a1ff.col
        iterator(0.75) shouldBe 0x8080ff.col
        iterator(1.0) shouldBe Colors.Web.blue
        iterator(2.0) shouldBe Colors.Web.blue
    }

    @Test
    fun linearRGBInterpolationTeal2Pink() {
        val iterator = rgbLinearInterpolator(Colors.Web.teal, Colors.Web.pink)
        iterator(-1.0) shouldBe Colors.Web.teal
        iterator(0.0) shouldBe Colors.Web.teal
        iterator(0.13) shouldBe 0x5c8a8c.col
        iterator(0.25) shouldBe 0x809396.col
        iterator(0.50) shouldBe 0xb4a3aa.col
        iterator(0.60) shouldBe 0xc6a9b1.col
        iterator(0.75) shouldBe 0xddb2bb.col
        iterator(1.0) shouldBe Colors.Web.pink
        iterator(2.0) shouldBe Colors.Web.pink
    }
}
package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleTests : TestBase() {

    @Test
    fun linear_no_interpolation() {
        val scale = linearScaleDouble()

        scale.domainsToRanges(
                DomainToRange(.0, .0),
                DomainToRange(1.0, 1.0)
        )

        scale(.5) shouldBe .5
        scale(0.1) shouldBe 0.1
        scale(0.133) shouldBe 0.133
        scale(0.9) shouldBe 0.9

        scale.domainsToRanges(
                DomainToRange(.0, 1.0),
                DomainToRange(1.0, .0)
        )

        scale(.5) shouldBe 0.5
        scale(0.1) shouldBe 0.9
        scale(0.133) shouldBe 0.867
        scale(0.9) shouldBeWithTolerance 0.1            // TODO find why it is in fact 0.09999999999999998
    }

    @Test
    fun linear_Number() {
        val scale = linearScaleDouble()

        scale.domainsToRanges(
                DomainToRange(.0, 100.0),
                DomainToRange(100.0, .0)
        )

        scale(50.0) shouldBe 50.0
        scale(10.0) shouldBe 90.0
        scale(13.3) shouldBe 86.7
        scale(90.0) shouldBe 10.0
    }



    @Test
    fun linear_Number_clamp_noclamp() {
        val scale = linearScaleDouble()

        scale.domainsToRanges(
                DomainToRange(.0, .0),
                DomainToRange(100.0, -100.0)
        )

        scale(50.0) shouldBe -50.0
        scale(10.0) shouldBeWithTolerance -10.0
        scale(13.3) shouldBeWithTolerance -13.3
        scale(90.0) shouldBe -90.0
        scale(-20.0) shouldBe 20.0
        scale(132.0) shouldBeWithTolerance -132.0

        scale.clamp = true

        scale(50.0) shouldBe -50.0
        scale(10.0) shouldBeWithTolerance -10.0
        scale(13.3) shouldBeWithTolerance -13.3
        scale(90.0) shouldBe -90.0
        scale(-20.0) shouldBe 0.0
        scale(132.0) shouldBeWithTolerance -100.0
    }

    @Test
    fun linear_Number_invert() {
        val scale = linearScaleDouble()

        scale.domainsToRanges(
                DomainToRange(.0, 100.0),
                DomainToRange(100.0, .0)
        )

        scale.invert(50.0) shouldBe 50.0
        scale.invert(90.0) shouldBe 10.0
        scale.invert(86.7) shouldBeWithTolerance 13.3
        scale.invert(10.0) shouldBe 90.0
    }

    @Test
    fun linear_Number_multiple_ranges() {
        val scale = linearScaleDouble()

        // x -> 10x between 0 and 10
        // x -> 20x between 10 and 20
        scale.domainsToRanges(
                DomainToRange(.0, 0.0),
                DomainToRange(10.0, 100.0),
                DomainToRange(20.0, 300.0)
        )

        scale(0.0) shouldBe 0.0
        scale(10.0) shouldBe 100.0
        scale(20.0) shouldBe 300.0
        scale(5.0) shouldBe 50.0
        scale(15.0) shouldBe 200.0
        scale(17.0) shouldBe 240.0
    }

    @Test
    fun linear_Number_multiple_ranges_invert() {
        val scale = linearScaleDouble()

        // x -> 10x between 0 and 10
        // x -> 20x between 10 and 20
        scale.domainsToRanges(
                DomainToRange(.0, 0.0),
                DomainToRange(10.0, 100.0),
                DomainToRange(20.0, 300.0)
        )

        scale.invert(0.0) shouldBe 0.0
        scale.invert(100.0) shouldBe 10.0
        scale.invert(300.0) shouldBe 20.0
        scale.invert(50.0) shouldBe 5.0
        scale.invert(200.0) shouldBe 15.0
        scale.invert(240.0) shouldBe 17.0
    }

    @Test
    fun linear_HSL() {
        val scale = linearScaleHSL()

        scale.domainsToRanges(
                DomainToRange(.0, HSL()),
                DomainToRange(100.0, HSL(180.deg))
        )

        scale(50.0) shouldBe HSL(90.deg)
        scale(20.0) shouldBe HSL(36.deg)
        scale(90.0) shouldBe HSL(162.deg)
    }
}
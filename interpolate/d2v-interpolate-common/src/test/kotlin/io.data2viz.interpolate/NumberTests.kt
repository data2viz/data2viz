package io.data2viz.interpolate

import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test

class NumberTests : TestBase() {

    @Test
    fun interpolate() {
        val f = interpolateNumber(10, 20)
        f(-20.pct) shouldBeClose 8.0
        f(0.pct) shouldBeClose 10.0
        f(20.pct) shouldBeClose 12.0
        f(40.pct) shouldBeClose 14.0
        f(100.pct) shouldBeClose 20.0
        f(120.pct) shouldBeClose 22.0
    }

    @Test
    fun interpolateSameValue() {
        val f = interpolateNumber(10, 10)
        f(-20.pct) shouldBe 10.0
        f(0.pct) shouldBe 10.0
        f(20.pct) shouldBe 10.0
        f(40.pct) shouldBe 10.0
        f(100.pct) shouldBe 10.0
        f(120.pct) shouldBe 10.0
    }

    // TODO ADD TESTS OVER LIMITS !!
    // TODO add tests for time, log, power...
    @Test
    fun uninterpolate() {
        val f = uninterpolateNumber(10.0, 20.0)
        f(8.0) shouldBe -20.pct
        f(10.0) shouldBe 0.pct
        f(12.0) shouldBe 20.pct
        f(14.0) shouldBe 40.pct
        f(20.0) shouldBe 100.pct
        f(22.0) shouldBe 120.pct
    }

    @Test
    fun uninterpolateSameValues() {
        val f = uninterpolateNumber(10.0, 10.0)
        f(8.0) shouldBe 0.pct
        f(10.0) shouldBe 0.pct
        f(12.0) shouldBe 0.pct
        f(14.0) shouldBe 0.pct
        f(20.0) shouldBe 0.pct
        f(22.0) shouldBe 0.pct
    }


    /**
     * scale linear (0..100) to (0..1) : 50 should be 0.5
     */
    @Test
    fun domainToReverse() {
        val domainToViz = scale.linear.numberToNumber(
            0.0 linkedTo 0.0,
            100.0 linkedTo 1.0
        )

        domainToViz(50.0) shouldBe 0.5
    }

    @Test
    fun linearWithNegative() {
        val domainToViz = scale.linear.numberToNumber(
            0.0 linkedTo -100.0,
            100.0 linkedTo 100.0
        )

        domainToViz(50.0) shouldBe .0
    }

    @Test
    fun linearToReverse() {
        val domainToViz = scale.linear.numberToNumber(
            0.0 linkedTo 100.0,
            100.0 linkedTo 0.0
        )

        domainToViz(60.0) shouldBe 40.0
    }
}

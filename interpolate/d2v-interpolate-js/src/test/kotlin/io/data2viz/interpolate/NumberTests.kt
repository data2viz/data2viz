package io.data2viz.interpolate

import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test

class NumberTests : TestBase() {

    @Test
    fun interpolate() {
        val f = interpolateNumber(10, 20)
        f(20.pct) shouldBeClose 12.0
    }

    // TODO ADD TESTS OVER LIMITS !!
    // TODO add tests for time, log, power...
    @Test
    fun uninterpolate() {
        val f = uninterpolateNumber(10.0, 20.0)
        f(12.0) shouldBe 20.pct
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

        domainToViz(50.0) shouldBe 0
    }

    @Test
    fun linearToReverse() {
        val domainToViz = scale.linear.numberToNumber(
            0.0 linkedTo 100.0,
            100.0 linkedTo 0.0
        )

        domainToViz(60.0) shouldBe 40
    }

}

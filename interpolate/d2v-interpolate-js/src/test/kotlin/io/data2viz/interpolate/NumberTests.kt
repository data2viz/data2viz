package io.data2viz.interpolate

import io.data2viz.test.TestBase
import kotlin.test.Test

class NumberTests : TestBase() {

    @Test
    fun interpolate() {
        val f = interpolateNumber(10, 20)
        f(0.2) shouldBe 12.0
    }

    @Test
    fun uninterpolate() {
        val f = uninterpolateNumber(10, 20)
        f(12) shouldBe 0.2
    }


    /**
     * scale linear (0..100) to (0..1) : 50 should be 0.5
     */
    @Test
    fun domainToReverse() {
        val domainToViz = scale.linear.numberToNumber(
                0 linkedTo 0,
                100 linkedTo 1)

        domainToViz(50) shouldBe 0.5
    }

    @Test
    fun linearWithNegative() {
        val domainToViz = scale.linear.numberToNumber(
                0 linkedTo -100,
                100 linkedTo 100)

        domainToViz(50) shouldBe 0
    }

    @Test
    fun linearToReverse() {
        val domainToViz = scale.linear.numberToNumber(
                0 linkedTo 100,
                100 linkedTo 0)

        domainToViz(60) shouldBe 40
    }

}

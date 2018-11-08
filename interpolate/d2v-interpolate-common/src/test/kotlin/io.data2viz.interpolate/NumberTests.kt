package io.data2viz.interpolate

import io.data2viz.test.TestBase
import kotlin.test.Test

class NumberTests : TestBase() {

    @Test
    fun interpolate() {
        val f = interpolateNumber(10, 20)
        f(0.2) shouldBeClose 12.0
    }

    @Test
    fun uninterpolate() {
        val f = uninterpolateNumber(10.0, 20.0)
        f(12.0) shouldBeClose 0.2
    }
}

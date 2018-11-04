package io.data2viz.math


import io.data2viz.test.TestBase
import kotlin.test.Test


class AngleTests : TestBase() {

    @Test
    fun numberToAngle() {
        10.deg shouldEqual 10.0.deg
        180.deg shouldEqual PI.rad
    }

    @Test
    fun angleOperators() {
        (10.deg + 10.deg) shouldEqual 20.deg
        2 * PI.rad shouldEqual 360.deg
        PI.rad * 2 shouldEqual 360.deg
    }
}

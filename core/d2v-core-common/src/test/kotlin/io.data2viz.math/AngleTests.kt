package io.data2viz.math

import io.data2viz.test.TestBase
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import kotlin.test.Test

class AngleTests : TestBase() {

    @Test
    fun angleOperators() {
        val PIOVER3 = PI / 3
        val angle = 60.deg

        angle shouldBe PIOVER3.rad
        (angle == PIOVER3.rad) shouldBe true
        (angle == 50.deg) shouldBe false
        (angle != 50.deg) shouldBe true

        angle.deg shouldBeClose 60.0
        angle.rad shouldBe PIOVER3

        angle.sin shouldBe sin(PIOVER3)
        angle.cos shouldBe cos(PIOVER3)
        angle.tan shouldBe tan(PIOVER3)

        90.deg shouldBe (PI/2).rad
        180.deg shouldBe PI.rad
        0.deg shouldBe 0.rad

        angle shouldBe +angle
        angle shouldBe +60.deg

        -angle shouldBe -60.deg

        540.deg.normalize() shouldBe 180.deg
        -540.deg.normalize() shouldBe -180.deg

        angle * -1 shouldBe -angle
        angle * -1 shouldBe -60.deg
        angle * -2 shouldBe -120.deg
        -1 * angle shouldBe -60.deg
        -2 * angle shouldBe -120.deg

        angle * 2 shouldBe 120.deg
        2 * angle shouldBe 120.deg

        (angle - 90.deg).rad shouldBeClose -30.deg.rad
        (angle + 90.deg).rad shouldBeClose 150.deg.rad
        (angle + -90.deg).rad shouldBeClose -30.deg.rad

        angle / 2 shouldBe 30.deg
        angle / 3 shouldBe 20.deg
        angle / -3 shouldBe -20.deg
    }
}

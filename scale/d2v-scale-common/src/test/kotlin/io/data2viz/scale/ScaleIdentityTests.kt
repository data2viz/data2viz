package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleIdentityTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun identity_x_return_y_equals_x() {
        val scale = identityScale()

        scale(1.0) shouldBe 1.0
        scale(100.0) shouldBe 100.0
        scale(24.0) shouldBe 24.0
        scale(78.6355) shouldBe 78.6355
        scale(-100.0) shouldBe -100.0
        scale(-24.0) shouldBe -24.0
        scale(-78.6355) shouldBe -78.6355

        scale.range(10.0, 56.20)
        scale(1.0) shouldBe 1.0
        scale(100.0) shouldBe 100.0
        scale(24.0) shouldBe 24.0
        scale(78.6355) shouldBe 78.6355
        scale(-100.0) shouldBe -100.0
        scale(-24.0) shouldBe -24.0
        scale(-78.6355) shouldBe -78.6355
    }




    // TODO : add more scale tests
}
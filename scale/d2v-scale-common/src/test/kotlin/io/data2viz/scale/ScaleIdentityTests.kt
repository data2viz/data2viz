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
    }

    @Test
    fun identity_invert_y_return_x_equals_y() {
        val scale = identityScale()

        scale.invert(1.0) shouldBe 1.0
        scale.invert(100.0) shouldBe 100.0
        scale.invert(24.0) shouldBe 24.0
        scale.invert(78.6355) shouldBe 78.6355
        scale.invert(-100.0) shouldBe -100.0
        scale.invert(-24.0) shouldBe -24.0
        scale.invert(-78.6355) shouldBe -78.6355
    }

    @Test
    fun identity_domain_range_clamp_are_final_values_exceptions() {
        val scale = identityScale()

        scale.range shouldBe arrayListOf(.0, 1.0)
        scale.domain shouldBe arrayListOf(.0, 1.0)
        scale.clamp shouldBe false
    }

    // TODO : add more scale tests
}
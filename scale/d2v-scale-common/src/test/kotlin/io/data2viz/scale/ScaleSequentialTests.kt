package io.data2viz.scale

import io.data2viz.math.*
import io.data2viz.test.TestBase
import kotlin.test.Test

@Suppress("unused", "FunctionName")
class ScaleSequentialTests : TestBase() {

    val identity = { t: Percent -> t.value }

    @Test
    fun sequential_has_expected_defaults() {
        val scale = Scales.continuous.sequential(identity)

        scale.domain shouldBe intervalOf(.0, 1.0)
        scale.clamp shouldBe false
        scale(-.5) shouldBeClose -.5
        scale(0.0) shouldBeClose 0.0
        scale(0.5) shouldBeClose 0.5
        scale(1.5) shouldBeClose 1.5
    }

    @Test
    fun sequential_enable_clamping() {
        val scale = Scales.continuous.sequential(identity)
        scale.clamp = true

        scale(-.5) shouldBeClose .0
        scale(0.0) shouldBeClose 0.0
        scale(0.5) shouldBeClose 0.5
        scale(1.0) shouldBeClose 1.0
        scale(1.5) shouldBeClose 1.0
    }

    @Test
    fun sequential_x_domain_return_y_range() {
        val scale = Scales.continuous.sequential(identity)
        scale.domain = intervalOf(-1.2, 2.4)

        scale(-1.2) shouldBeClose 0.0
        scale( 0.6) shouldBeClose 0.5
        scale( 2.4) shouldBeClose 1.0
    }

    @Test
    fun sequential_interpolator_sets_interpolator() {
        val scale = Scales.continuous.sequential(identity)
        scale.clamp = true
        scale.domain = intervalOf(1.0, 3.0)
        scale.interpolator = { t: Percent -> 2 * t.value }

        scale(-.5) shouldBeClose .0
        scale(.0) shouldBeClose .0
        scale(.5) shouldBeClose .0
        scale(1.0) shouldBeClose .0
        scale(2.0) shouldBeClose 1.0
        scale(3.0) shouldBeClose 2.0
        scale(4.0) shouldBeClose 2.0

        scale.clamp = false
        scale(-.5) shouldBeClose -1.5
        scale(.0) shouldBeClose -1.0
        scale(.5) shouldBeClose -0.5
        scale(1.0) shouldBeClose .0
        scale(2.0) shouldBeClose 1.0
        scale(3.0) shouldBeClose 2.0
        scale(4.0) shouldBeClose 3.0
    }

}
package io.data2viz.scale

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

@Suppress("unused", "FunctionName")
class ScaleSequentialTests : TestBase() {

    val identity = { t: Double -> t }

    @Test
    fun sequential_has_expected_defaults_LEGACY() {
        val scale = scaleSequential(identity)

        scale.domain shouldBe arrayListOf(.0, 1.0)
        scale.clamp shouldBe false
        scale(-.5) shouldBe -.5
        scale(0.0) shouldBe 0.0
        scale(0.5) shouldBe 0.5
        scale(1.5) shouldBe 1.5
    }

    @Test
    fun sequential_enable_clamping_LEGACY() {
        val scale = scaleSequential(identity)
        scale.clamp = true

        scale(-.5) shouldBe .0
        scale(0.0) shouldBe 0.0
        scale(0.5) shouldBe 0.5
        scale(1.0) shouldBe 1.0
        scale(1.5) shouldBe 1.0
    }

    @Test
    fun sequential_x_domain_return_y_range() {
        val scale = scaleSequential(identity)
        scale.domain = arrayListOf(-1.2, 2.4)

        scale(-1.2) shouldBe 0.0
        scale( 0.6) shouldBe 0.5
        scale( 2.4) shouldBe 1.0
    }

    @Test
    fun sequential_domain_more_than_2_elements_raise_exception() {
        val scale = scaleSequential(identity)
        shouldThrow<IllegalArgumentException> { scale.domain = arrayListOf(.1); return }
        shouldThrow<IllegalArgumentException> { scale.domain = listOf(.2, .3, .6); return }
    }

    @Test
    fun sequential_returns_copy_of_elements() {
        val scale = scaleSequential(identity)
        scale.clamp = true
        scale.domain = listOf(1.0, 3.0)

        val array = scale.domain.toMutableList()
        array.add(4.0)
        array.size shouldBe 3
        scale.domain.size shouldBe 2
    }

    @Test
    fun sequential_interpolator_sets_interpolator() {
        val scale = scaleSequential(identity)
        scale.clamp = true
        scale.domain = listOf(1.0, 3.0)
        scale.interpolator = { t: Double -> 2 * t }

        scale(-.5) shouldBe .0
        scale(.0) shouldBe .0
        scale(.5) shouldBe .0
        scale(1.0) shouldBe .0
        scale(2.0) shouldBe 1.0
        scale(3.0) shouldBe 2.0
        scale(4.0) shouldBe 2.0

        scale.clamp = false
        scale(-.5) shouldBe -1.5
        scale(.0) shouldBe -1.0
        scale(.5) shouldBe -0.5
        scale(1.0) shouldBe .0
        scale(2.0) shouldBe 1.0
        scale(3.0) shouldBe 2.0
        scale(4.0) shouldBe 3.0
    }

}
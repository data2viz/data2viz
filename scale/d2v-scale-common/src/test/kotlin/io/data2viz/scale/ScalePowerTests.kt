package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScalePowerTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun power_sets_the_exponent_to_the_specified_value_LEGACY() {
        val scale = powerScale(.5)

        scale.domain(1.0, 2.0)
        scale.range(.0, 1.0)
        scale(1.0) shouldBe (.0 plusOrMinus epsilon)
        scale(1.5) shouldBe (0.5425821 plusOrMinus epsilon)
        scale(2.0) shouldBe (1.0 plusOrMinus epsilon)
        scale.exponent shouldBe .5

        scale.exponent = 2.0
        scale(1.0) shouldBe (.0 plusOrMinus epsilon)
        scale(1.5) shouldBe (0.41666667 plusOrMinus epsilon)
        scale(2.0) shouldBe (1.0 plusOrMinus epsilon)
        scale.exponent shouldBe 2.0

        scale.exponent = -1.0
        scale(1.0) shouldBe (.0 plusOrMinus epsilon)
        scale(1.5) shouldBe (0.6666667 plusOrMinus epsilon)
        scale(2.0) shouldBe (1.0 plusOrMinus epsilon)
        scale.exponent shouldBe -1.0
    }

    @Test
    fun power_sets_the_exponent_does_not_change_domain_or_range_LEGACY() {
        val scale = powerScale()

        scale.domain(1.0, 2.0)
        scale.range(3.0, 4.0)
        scale.exponent = .5
        scale.domain shouldBe arrayListOf(1.0, 2.0)
        scale.range shouldBe arrayListOf(3.0, 4.0)

        scale.exponent = 2.0
        scale.domain shouldBe arrayListOf(1.0, 2.0)
        scale.range shouldBe arrayListOf(3.0, 4.0)
    }

    @Test
    fun power_round_does_round_number_LEGACY() {
        val scale = powerScaleRound()

        scale.domain(.0, 1.0)
        scale.range(.0, 10.0)
        scale(0.59) shouldBe 6.0
    }

    @Test
    fun power_nice_count_extends_the_domain_to_match_the_desired_ticks_LEGACY() {
        val scale = powerScale()
        scale.range(.0, 1.0)

        scale.domain(.0, .96)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, 1.0)

        scale.domain(.0, 96.0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, 100.0)

        scale.domain(.96, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(1.0, .0)

        scale.domain(96.0, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(100.0, .0)

        scale.domain(.0, -.96)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, -1.0)

        scale.domain(.0, -96.0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, -100.0)

        scale.domain(-.96, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(-1.0, .0)

        scale.domain(-96.0, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(-100.0, .0)

        scale.domain(-.1, 51.1)
        scale.nice(8)
        scale.domain shouldBe arrayListOf(-10.0, 60.0)
    }
}
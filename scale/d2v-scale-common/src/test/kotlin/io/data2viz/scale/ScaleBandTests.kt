package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleBandTests : TestBase() {


    @Test
    fun band_has_expected_defaults() {
        val scale = Scales.band<Int>()

        scale.domain shouldBe listOf()
        scale.range shouldBe intervalOf(.0, 1.0)
        scale.bandwidth shouldBeClose 1.0
        scale.step shouldBeClose 1.0
        scale.round shouldBe false
        scale.paddingInner shouldBeClose .0
        scale.paddingOuter shouldBeClose .0
        scale.align shouldBeClose .5
    }

    @Test
    fun band_value_computes_discrete_bands_in_a_continuous_range() {
        val scale = Scales.band<String>()
        scale.range = intervalOf(.0, 960.0)

        scale("foo") shouldBe Double.NaN

        scale.domain = listOf("foo", "bar")
        scale("foo") shouldBeClose 0.0
        scale("bar") shouldBeClose 480.0

        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(.0, 120.0)
        scale("a") shouldBeClose 0.0
        scale("b") shouldBeClose 40.0
        scale("c") shouldBeClose 80.0
        scale.bandwidth shouldBeClose 40.0

        scale.padding = .2
        scale("a") shouldBeClose 7.5
        scale("b") shouldBeClose 45.0
        scale("c") shouldBeClose 82.5
        scale.bandwidth shouldBeClose 30.0
    }

    @Test
    fun band_value_returns_undefined_for_values_outside_the_domain() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")

        scale("d") shouldBe Double.NaN
        scale("e") shouldBe Double.NaN
        scale("f") shouldBe Double.NaN
    }

    @Test
    fun band_value_does_not_implicitly_add_values_to_the_domain() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")

        scale("d")
        scale("e")
        scale.domain shouldBe listOf("a", "b", "c")
    }

    @Test
    fun band_step_returns_the_distance_between_the_starts_of_adjacent_bands() {
        val scale = Scales.band<String>()
        scale.range = intervalOf(.0, 960.0)

        scale.domain = listOf("a")
        scale.step shouldBeClose 960.0

        scale.domain = listOf("a", "b")
        scale.step shouldBeClose 480.0

        scale.domain = listOf("a", "b", "c")
        scale.step shouldBeClose 320.0

        scale.padding = .5
        scale.domain = listOf("a")
        scale.step shouldBeClose 640.0

        scale.domain = listOf("a", "b")
        scale.step shouldBeClose 384.0
    }

    @Test
    fun band_bandwidth_returns_the_width_of_the_band() {
        val scale = Scales.band<String>()
        scale.range = intervalOf(.0, 960.0)

        scale.domain = listOf()
        scale.bandwidth shouldBeClose 960.0

        scale.domain = listOf("a")
        scale.bandwidth shouldBeClose 960.0

        scale.domain = listOf("a", "b")
        scale.bandwidth shouldBeClose 480.0

        scale.domain = listOf("a", "b", "c")
        scale.bandwidth shouldBeClose 320.0

        scale.padding = .5
        scale.domain = listOf()
        scale.bandwidth shouldBeClose 480.0

        scale.domain = listOf("a")
        scale.bandwidth shouldBeClose 320.0

        scale.domain = listOf("a", "b")
        scale.bandwidth shouldBeClose 192.0
    }

    @Test
    fun band_domain_computes_reasonable_band_and_step_values() {
        val scale = Scales.band<String>()
        scale.range = intervalOf(.0, 960.0)
        scale.domain = listOf()

        scale.step shouldBeClose 960.0
        scale.bandwidth shouldBeClose 960.0

        scale.padding = .5
        scale.step shouldBeClose 960.0
        scale.bandwidth shouldBeClose 480.0

        scale.padding = 1.0
        scale.step shouldBeClose 960.0
        scale.bandwidth shouldBeClose .0
    }

    @Test
    fun band_domain_value_compute_a_reasonable_singleton_band_even_with_padding() {
        val scale = Scales.band<String>()
        scale.range = intervalOf(.0, 960.0)
        scale.domain = listOf("foo")

        scale("foo") shouldBeClose .0
        scale.step shouldBeClose 960.0
        scale.bandwidth shouldBeClose 960.0

        scale.padding = .5
        scale("foo") shouldBeClose 320.0
        scale.step shouldBeClose 640.0
        scale.bandwidth shouldBeClose 320.0

        scale.padding = 1.0
        scale("foo") shouldBeClose 480.0
        scale.step shouldBeClose 480.0
        scale.bandwidth shouldBeClose .0
    }

    @Test
    fun band_domain_values_recomputes_the_bands() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(.0, 100.0)

        scale("a") shouldBeClose .0
        scale("b") shouldBeClose 33.333333
        scale("c") shouldBeClose 66.666666

        scale.round = true
        scale("a") shouldBeClose .0          // TODO 1.0
        scale("b") shouldBeClose 33.0        // TODO 34.0
        scale("c") shouldBeClose 66.0        // TODO 67.0

        scale.domain = listOf("a", "b", "c", "d")
        scale("a") shouldBeClose .0
        scale("b") shouldBeClose 25.0
        scale("c") shouldBeClose 50.0
        scale("d") shouldBeClose 75.0
    }

    @Test
    fun band_domain_makes_a_copy_of_the_domain() {
        val scale = Scales.band<String>()
        val domain = arrayListOf("red", "blue")

        scale.domain = domain
        domain.add("green")
        scale.domain shouldBe listOf("red", "blue")
    }

    @Test
    fun band_range_values_can_be_descending() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(120.0, .0)

        scale("a") shouldBeClose 80.0
        scale("b") shouldBeClose 40.0
        scale("c") shouldBeClose 0.0
        scale.bandwidth shouldBeClose 40.0

        scale.padding = .2
        scale("a") shouldBeClose 82.5
        scale("b") shouldBeClose 45.0
        scale("c") shouldBeClose 7.5
        scale.bandwidth shouldBeClose 30.0
    }


    @Test
    fun band_paddingInner_p_specifies_the_inner_padding() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(120.0, .0)
        scale.round = true
        scale.paddingInner = .1

        scale("a") shouldBeClose 83.0
        scale("b") shouldBeClose 42.0
        scale("c") shouldBeClose 1.0
        scale.bandwidth shouldBeClose 37.0

        scale.paddingInner = .2
        scale.bandwidth shouldBeClose 34.0
    }

    @Test
    fun band_paddingInner_coerces_padding_to_0_1() {
        val scale = Scales.band<String>()

        scale.paddingInner = 1.0
        scale.paddingInner shouldBeClose 1.0

        scale.paddingInner = -1.0
        scale.paddingInner shouldBeClose .0

        scale.paddingInner = 2.0
        scale.paddingInner shouldBeClose 1.0

        scale.paddingInner = Double.NaN
        scale.paddingInner shouldBe Double.NaN
    }

    @Test
    fun band_paddingOutr_p_specifies_the_outer_padding() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(120.0, .0)
        scale.round = true
        scale.paddingInner = .2
        scale.paddingOuter = .1

        scale("a") shouldBeClose 84.0
        scale("b") shouldBeClose 44.0
        scale("c") shouldBeClose 4.0
        scale.bandwidth shouldBeClose 32.0

        scale.paddingOuter = 1.0
        scale("a") shouldBeClose 75.0
        scale("b") shouldBeClose 50.0
        scale("c") shouldBeClose 25.0
        scale.bandwidth shouldBeClose 20.0
    }

    @Test
    fun band_paddingOuter_coerces_padding_to_0_1() {
        val scale = Scales.band<String>()

        scale.paddingOuter = 1.0
        scale.paddingOuter shouldBeClose 1.0

        scale.paddingOuter = -1.0
        scale.paddingOuter shouldBeClose .0

        scale.paddingOuter = 2.0
        scale.paddingOuter shouldBeClose 1.0

        scale.paddingOuter = Double.NaN
        scale.paddingOuter shouldBe Double.NaN
    }

    @Test
    fun band_round_true_computes_discrete_rounded_bands_in_a_continuous_range() {
        val scale = Scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(.0, 100.0)
        scale.round = true

        scale("a") shouldBeClose .0          // TODO 1.0
        scale("b") shouldBeClose 33.0        // TODO 34.0
        scale("c") shouldBeClose 66.0        // TODO 67.0
        scale.bandwidth shouldBeClose 33.0

        scale.padding = .2
        scale("a") shouldBeClose 7.0
        scale("b") shouldBeClose 38.0
        scale("c") shouldBeClose 69.0
        scale.bandwidth shouldBeClose 25.0
    }

    // TODO align tests for padding & round
    // TODO : add more scale tests
}
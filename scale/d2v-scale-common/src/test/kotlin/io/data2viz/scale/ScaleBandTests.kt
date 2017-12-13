package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleBandTests : TestBase() {


    @Test
    fun band_has_expected_defaults_LEGACY() {
        val scale = scales.band<Int>()

        scale.domain shouldBe listOf()
        scale.range shouldBe intervalOf(.0, 1.0)
        scale.bandwidth shouldBe 1.0
        scale.step shouldBe 1.0
        scale.round shouldBe false
        scale.paddingInner shouldBe .0
        scale.paddingOuter shouldBe .0
        scale.align shouldBe .5
    }

    @Test
    fun band_value_computes_discrete_bands_in_a_continuous_range_LEGACY() {
        val scale = scales.band<String>()
        scale.range = intervalOf(.0, 960.0)

        scale("foo") shouldBe Double.NaN

        scale.domain = listOf("foo", "bar")
        scale("foo") shouldBe 0.0
        scale("bar") shouldBe 480.0

        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(.0, 120.0)
        scale("a") shouldBe 0.0
        scale("b") shouldBe 40.0
        scale("c") shouldBe 80.0
        scale.bandwidth shouldBe 40.0

        scale.padding = .2
        scale("a") shouldBe 7.5
        scale("b") shouldBe 45.0
        scale("c") shouldBe 82.5
        scale.bandwidth shouldBe 30.0
    }

    @Test
    fun band_value_returns_undefined_for_values_outside_the_domain_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")

        scale("d") shouldBe Double.NaN
        scale("e") shouldBe Double.NaN
        scale("f") shouldBe Double.NaN
    }

    @Test
    fun band_value_does_not_implicitly_add_values_to_the_domain_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")

        scale("d")
        scale("e")
        scale.domain shouldBe listOf("a", "b", "c")
    }

    @Test
    fun band_step_returns_the_distance_between_the_starts_of_adjacent_bands_LEGACY() {
        val scale = scales.band<String>()
        scale.range = intervalOf(.0, 960.0)

        scale.domain = listOf("a")
        scale.step shouldBe 960.0

        scale.domain = listOf("a", "b")
        scale.step shouldBe 480.0

        scale.domain = listOf("a", "b", "c")
        scale.step shouldBe 320.0

        scale.padding = .5
        scale.domain = listOf("a")
        scale.step shouldBe 640.0

        scale.domain = listOf("a", "b")
        scale.step shouldBe 384.0
    }

    @Test
    fun band_bandwidth_returns_the_width_of_the_band_LEGACY() {
        val scale = scales.band<String>()
        scale.range = intervalOf(.0, 960.0)

        scale.domain = listOf()
        scale.bandwidth shouldBe 960.0

        scale.domain = listOf("a")
        scale.bandwidth shouldBe 960.0

        scale.domain = listOf("a", "b")
        scale.bandwidth shouldBe 480.0

        scale.domain = listOf("a", "b", "c")
        scale.bandwidth shouldBe 320.0

        scale.padding = .5
        scale.domain = listOf()
        scale.bandwidth shouldBe 480.0

        scale.domain = listOf("a")
        scale.bandwidth shouldBe 320.0

        scale.domain = listOf("a", "b")
        scale.bandwidth shouldBe 192.0
    }

    @Test
    fun band_domain_computes_reasonable_band_and_step_values_LEGACY() {
        val scale = scales.band<String>()
        scale.range = intervalOf(.0, 960.0)
        scale.domain = listOf()

        scale.step shouldBe 960.0
        scale.bandwidth shouldBe 960.0

        scale.padding = .5
        scale.step shouldBe 960.0
        scale.bandwidth shouldBe 480.0

        scale.padding = 1.0
        scale.step shouldBe 960.0
        scale.bandwidth shouldBe .0
    }

    @Test
    fun band_domain_value_compute_a_reasonable_singleton_band_even_with_padding_LEGACY() {
        val scale = scales.band<String>()
        scale.range = intervalOf(.0, 960.0)
        scale.domain = listOf("foo")

        scale("foo") shouldBe .0
        scale.step shouldBe 960.0
        scale.bandwidth shouldBe 960.0

        scale.padding = .5
        scale("foo") shouldBe 320.0
        scale.step shouldBe 640.0
        scale.bandwidth shouldBe 320.0

        scale.padding = 1.0
        scale("foo") shouldBe 480.0
        scale.step shouldBe 480.0
        scale.bandwidth shouldBe .0
    }

    @Test
    fun band_domain_values_recomputes_the_bands_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(.0, 100.0)

        scale("a") shouldBe .0
        scale("b") shouldBeClose 33.333333
        scale("c") shouldBeClose 66.666666

        scale.round = true
        scale("a") shouldBe .0          // TODO 1.0
        scale("b") shouldBe 33.0        // TODO 34.0
        scale("c") shouldBe 66.0        // TODO 67.0

        scale.domain = listOf("a", "b", "c", "d")
        scale("a") shouldBe .0
        scale("b") shouldBe 25.0
        scale("c") shouldBe 50.0
        scale("d") shouldBe 75.0
    }

    @Test
    fun band_domain_makes_a_copy_of_the_domain_LEGACY() {
        val scale = scales.band<String>()
        val domain = arrayListOf("red", "blue")

        scale.domain = domain
        domain.add("green")
        scale.domain shouldBe listOf("red", "blue")
    }

    @Test
    fun band_range_values_can_be_descending_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(120.0, .0)

        scale("a") shouldBe 80.0
        scale("b") shouldBe 40.0
        scale("c") shouldBe 0.0
        scale.bandwidth shouldBe 40.0

        scale.padding = .2
        scale("a") shouldBe 82.5
        scale("b") shouldBe 45.0
        scale("c") shouldBe 7.5
        scale.bandwidth shouldBe 30.0
    }


    @Test
    fun band_paddingInner_p_specifies_the_inner_padding_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(120.0, .0)
        scale.round = true
        scale.paddingInner = .1

        scale("a") shouldBe 83.0
        scale("b") shouldBe 42.0
        scale("c") shouldBe 1.0
        scale.bandwidth shouldBe 37.0

        scale.paddingInner = .2
        scale.bandwidth shouldBe 34.0
    }

    @Test
    fun band_paddingInner_coerces_padding_to_0_1_LEGACY() {
        val scale = scales.band<String>()

        scale.paddingInner = 1.0
        scale.paddingInner shouldBe 1.0

        scale.paddingInner = -1.0
        scale.paddingInner shouldBe .0

        scale.paddingInner = 2.0
        scale.paddingInner shouldBe 1.0

        scale.paddingInner = Double.NaN
        scale.paddingInner shouldBe Double.NaN
    }

    @Test
    fun band_paddingOutr_p_specifies_the_outer_padding_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(120.0, .0)
        scale.round = true
        scale.paddingInner = .2
        scale.paddingOuter = .1

        scale("a") shouldBe 84.0
        scale("b") shouldBe 44.0
        scale("c") shouldBe 4.0
        scale.bandwidth shouldBe 32.0

        scale.paddingOuter = 1.0
        scale("a") shouldBe 75.0
        scale("b") shouldBe 50.0
        scale("c") shouldBe 25.0
        scale.bandwidth shouldBe 20.0
    }

    @Test
    fun band_paddingOuter_coerces_padding_to_0_1_LEGACY() {
        val scale = scales.band<String>()

        scale.paddingOuter = 1.0
        scale.paddingOuter shouldBe 1.0

        scale.paddingOuter = -1.0
        scale.paddingOuter shouldBe .0

        scale.paddingOuter = 2.0
        scale.paddingOuter shouldBe 1.0

        scale.paddingOuter = Double.NaN
        scale.paddingOuter shouldBe Double.NaN
    }

    @Test
    fun band_round_true_computes_discrete_rounded_bands_in_a_continuous_range_LEGACY() {
        val scale = scales.band<String>()
        scale.domain = listOf("a", "b", "c")
        scale.range = intervalOf(.0, 100.0)
        scale.round = true

        scale("a") shouldBe .0          // TODO 1.0
        scale("b") shouldBe 33.0        // TODO 34.0
        scale("c") shouldBe 66.0        // TODO 67.0
        scale.bandwidth shouldBe 33.0

        scale.padding = .2
        scale("a") shouldBe 7.0
        scale("b") shouldBe 38.0
        scale("c") shouldBe 69.0
        scale.bandwidth shouldBe 25.0
    }

    // TODO align tests for padding & round
    // TODO : add more scale tests
}
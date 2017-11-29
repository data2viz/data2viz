package io.data2viz.scale

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleQuantileTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun quantile_expected_defaults_LEGACY() {
        val scale = quantileScale<Double>()

        scale.domain shouldBe arrayListOf()
        scale.range shouldBe arrayListOf()
    }

    @Test
    fun quantile_x_uses_R7_algorithm_to_compute_quantiles_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 3)

        arrayListOf<Double>(3.0, 6.0, 6.9, 7.0, 7.1).map { scale(it) } shouldBe arrayListOf(0, 0, 0, 0, 0)
        arrayListOf<Double>(8.0, 8.9).map { scale(it) } shouldBe arrayListOf(1, 1)
        arrayListOf<Double>(9.0, 9.1, 10.0, 13.0).map { scale(it) } shouldBe arrayListOf(2, 2, 2, 2)
        arrayListOf<Double>(14.9, 15.0, 15.1, 16.0, 20.0).map { scale(it) } shouldBe arrayListOf(3, 3, 3, 3, 3)

        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 9.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        arrayListOf<Double>(3.0, 6.0, 6.9, 7.0, 7.1).map { scale(it) } shouldBe arrayListOf(0, 0, 0, 0, 0)
        arrayListOf<Double>(8.0, 8.9).map { scale(it) } shouldBe arrayListOf(1, 1)
        arrayListOf<Double>(9.0, 9.1, 10.0, 13.0).map { scale(it) } shouldBe arrayListOf(2, 2, 2, 2)
        arrayListOf<Double>(14.9, 15.0, 15.1, 16.0, 20.0).map { scale(it) } shouldBe arrayListOf(3, 3, 3, 3, 3)
    }

    @Test
    fun quantile_x_throw_exception_if_input_value_is_NaN() {
        val scale = quantileScale<Int>()
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 3)

        shouldThrow<IllegalArgumentException> { scale(Double.NaN) }
    }

    @Test
    fun quantile_domain_values_are_sorted_in_ascending_order_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(6.0, 3.0, 7.0, 8.0, 8.0, 13.0, 20.0, 15.0, 16.0, 10.0)

        scale.domain shouldBe arrayListOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
    }

    @Test
    fun quantile_domain_values_are_values_are_allowed_to_be_zero_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(1.0, 2.0, .0, .0)

        scale.domain shouldBe arrayListOf(.0, .0, 1.0, 2.0)
    }

    @Test
    fun quantile_domain_values_NaNs_are_ignored_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(1.0, 2.0, Double.NaN, 7.0, 8.0, Double.NaN, 20.0, Double.NaN)

        scale.domain shouldBe arrayListOf(1.0, 2.0, 7.0, 8.0, 20.0)
    }

    @Test
    fun quantile_quantiles_return_inner_thresholds_LEGACY() {
        val scale = quantileScale<Int>()

        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 3)
        scale.quantiles() shouldBe arrayListOf(7.25, 9.0, 14.5)

        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 9.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 3)
        scale.quantiles() shouldBe arrayListOf(7.5, 9.0, 14.0)
    }

    @Test
    fun quantile_range_cardinality_determines_number_of_quantiles_LEGACY() {
        val scale = quantileScale<Int>()

        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 3)
        scale.quantiles() shouldBe arrayListOf(7.25, 9.0, 14.5)

        scale.range(0, 1)
        scale.quantiles() shouldBe arrayListOf(9.0)

        scale.range(0, 0, 0, 0, 0)
        scale.quantiles() shouldBe arrayListOf(6.8, 8.0, 11.2, 15.2)

        scale.range(0, 0, 0, 0, 0, 0)
        scale.quantiles() shouldBe arrayListOf(6.5, 8.0, 9.0, 13.0, 15.5)
    }

    @Test
    fun quantile_range_values_are_arbitrary_LEGACY() {
        val scale = quantileScale<() -> Unit>()

        val a = {}
        val b = {}
        val c = {}
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(a, b, c, a)

        arrayListOf<Double>(3.0, 6.0, 6.9, 7.0, 7.1).map { scale(it) } shouldBe arrayListOf(a, a, a, a, a)
        arrayListOf<Double>(8.0, 8.9).map { scale(it) } shouldBe arrayListOf(b, b)
        arrayListOf<Double>(9.0, 9.1, 10.0, 13.0).map { scale(it) } shouldBe arrayListOf(c, c, c, c)
        arrayListOf<Double>(14.9, 15.0, 15.1, 16.0, 20.0).map { scale(it) } shouldBe arrayListOf(a, a, a, a, a)
    }

    @Test
    fun quantile_invertExtent_maps_a_value_in_range_to_a_domain_extent_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 3)

        scale.invertExtent(0) shouldBe arrayListOf(3.0, 7.25)
        scale.invertExtent(1) shouldBe arrayListOf(7.25, 9.0)
        scale.invertExtent(2) shouldBe arrayListOf(9.0, 14.5)
        scale.invertExtent(3) shouldBe arrayListOf(14.5, 20.0)
    }

    @Test
    fun quantile_invertExtent_allows_arbitrary_range_values_LEGACY() {
        val scale = quantileScale<() -> Unit>()
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        val a = {}
        val b = {}
        scale.range(a, b)

        scale.invertExtent(a) shouldBe arrayListOf(3.0, 9.0)
        scale.invertExtent(b) shouldBe arrayListOf(9.0, 20.0)
    }

    @Test
    fun quantile_invertExtent_returns_NaN_NaN_when_given_value_is_not_in_the_range_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)

        shouldThrow<IllegalStateException> { scale(10.0) }
        shouldThrow<IllegalStateException> { scale.invertExtent(10) }

        scale.range(4, 10)
        val notFound = arrayListOf(Double.NaN, Double.NaN)
        scale.invertExtent(-1) shouldBe notFound
        scale.invertExtent(0) shouldBe notFound
        scale.invertExtent(2) shouldBe notFound
        scale.invertExtent(20) shouldBe notFound
    }

    fun quantile_invertExtent_returns_first_match_if_duplicate_values_exist_in_range_LEGACY() {
        val scale = quantileScale<Int>()
        scale.domain(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range(0, 1, 2, 0)

        scale.invertExtent(0) shouldBe arrayListOf(3.0, 7.25)
        scale.invertExtent(1) shouldBe arrayListOf(7.25, 9.0)
        scale.invertExtent(20) shouldBe arrayListOf(9.0, 14.5)
    }
}
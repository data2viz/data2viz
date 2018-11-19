@file:Suppress("unused", "FunctionName")

package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.color.Colors
import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleQuantileTests : TestBase() {

    @Test
    fun quantile_expected_defaults() {
        val scale = Scales.Quantized.quantile<Double>()

        scale.domain shouldBe listOf()
        scale.range shouldBe listOf()
    }

    @Test
    fun quantile_repartition_tests() {
        val scale = Scales.Quantized.quantile<Color>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(Colors.Web.black, Colors.Web.red, Colors.Web.green, Colors.Web.blue)

        // domain from 3 to 20
        // quantiles ->     [... 3.0 - 7.25[    [7.25 - 9.0[    [9.0 - 14.5[     [14.5 - 20.0 ...]
        // quantities ->            3               2               2                   3

        scale.quantiles.size shouldBe 3
        scale.quantiles shouldBe listOf(7.25, 9.0, 14.5)

        // OVER SCALE
        scale(.0) shouldBe Colors.Web.black
        scale(30.0) shouldBe Colors.Web.blue

        // LIMITS
        scale(3.0) shouldBe Colors.Web.black
        scale(7.25) shouldBe Colors.Web.red
        scale(11.50) shouldBe Colors.Web.green
        scale(15.75) shouldBe Colors.Web.blue
        scale(20.0) shouldBe Colors.Web.blue

        // SAMPLES
        scale(6.22) shouldBe Colors.Web.black
        scale(8.0) shouldBe Colors.Web.red
        scale(10.33) shouldBe Colors.Web.green
        scale(15.74) shouldBe Colors.Web.blue
        scale(19.9811) shouldBe Colors.Web.blue


        // change quantities
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 8.0, 10.0, 11.0, 13.0, 15.0, 16.0, 20.0)

        // domain from 3 to 20
        // quantiles ->     [... 3.0 - 7.75[    [7.75 - 9.0[    [9.0 - 13.5[     [13.5 - 20.0 ...]
        // quantities ->            3               3                3                   3

        scale.quantiles.size shouldBe 3
        scale.quantiles shouldBe listOf(7.75, 9.0, 13.5)
    }


    // TODO is it ok ? check https://stats.stackexchange.com/questions/376923/quantile-repartition-algorithm-of-d3-js-library
    @Test
    fun quantile_uneven_repartition_tests() {

        // change quantities
        val scale = Scales.Quantized.quantile<Int>()
        scale.range = listOf(1, 2, 3, 4)
        scale.domain = listOf(3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 6.0, 7.0, 8.0, 8.0, 8.0, 10.0, 11.0, 13.0, 15.0, 16.0, 20.0)

        scale.quantiles.size shouldBe 3
        scale.quantiles shouldBe listOf(3.0, 7.0, 10.5)

        scale.domain.map { scale(it) } shouldBe listOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4)
//        scale.domain.map { scale(it) } shouldBe listOf(1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4)   //expected ??!
    }

    @Test
    fun quantile_x_uses_R7_algorithm_to_compute_quantiles() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 3)

        listOf(3.0, 6.0, 6.9, 7.0, 7.1).map { scale(it) } shouldBe listOf(0, 0, 0, 0, 0)
        listOf(8.0, 8.9).map { scale(it) } shouldBe listOf(1, 1)
        listOf(9.0, 9.1, 10.0, 13.0).map { scale(it) } shouldBe listOf(2, 2, 2, 2)
        listOf(14.9, 15.0, 15.1, 16.0, 20.0).map { scale(it) } shouldBe listOf(3, 3, 3, 3, 3)

        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 9.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        listOf(3.0, 6.0, 6.9, 7.0, 7.1).map { scale(it) } shouldBe listOf(0, 0, 0, 0, 0)
        listOf(8.0, 8.9).map { scale(it) } shouldBe listOf(1, 1)
        listOf(9.0, 9.1, 10.0, 13.0).map { scale(it) } shouldBe listOf(2, 2, 2, 2)
        listOf(14.9, 15.0, 15.1, 16.0, 20.0).map { scale(it) } shouldBe listOf(3, 3, 3, 3, 3)
    }

    @Test
    fun quantile_x_throw_exception_if_input_value_is_NaN() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 3)

        shouldThrow<IllegalArgumentException> { scale(Double.NaN) }
    }

    @Test
    fun quantile_domain_values_are_sorted_in_ascending_order() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(6.0, 3.0, 7.0, 8.0, 8.0, 13.0, 20.0, 15.0, 16.0, 10.0)

        scale.domain shouldBe listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
    }

    @Test
    fun quantile_domain_values_are_values_are_allowed_to_be_zero() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(1.0, 2.0, .0, .0)

        scale.domain shouldBe listOf(.0, .0, 1.0, 2.0)
    }

    @Test
    fun quantile_domain_values_NaNs_are_ignored() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(1.0, 2.0, Double.NaN, 7.0, 8.0, Double.NaN, 20.0, Double.NaN)

        scale.domain shouldBe listOf(1.0, 2.0, 7.0, 8.0, 20.0)
    }

    @Test
    fun quantile_quantiles_return_inner_thresholds() {
        val scale = Scales.Quantized.quantile<Int>()

        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 3)
        scale.quantiles shouldBe listOf(7.25, 9.0, 14.5)

        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 9.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 3)
        scale.quantiles shouldBe listOf(7.5, 9.0, 14.0)
    }

    @Test
    fun quantile_range_cardinality_determines_number_of_quantiles() {
        val scale = Scales.Quantized.quantile<Int>()

        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 3)
        scale.quantiles shouldBe listOf(7.25, 9.0, 14.5)

        scale.range = listOf(0, 1)
        scale.quantiles shouldBe listOf(9.0)

        scale.range = listOf(0, 0, 0, 0, 0)
        scale.quantiles shouldBe listOf(6.8, 8.0, 11.2, 15.2)

        scale.range = listOf(0, 0, 0, 0, 0, 0)
        scale.quantiles shouldBe listOf(6.5, 8.0, 9.0, 13.0, 15.5)
    }

    @Test
    fun quantile_range_values_are_arbitrary() {
        val scale = Scales.Quantized.quantile<() -> Unit>()

        val a = {}
        val b = {}
        val c = {}
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(a, b, c, a)

        listOf(3.0, 6.0, 6.9, 7.0, 7.1).map { scale(it) } shouldBe listOf(a, a, a, a, a)
        listOf(8.0, 8.9).map { scale(it) } shouldBe listOf(b, b)
        listOf(9.0, 9.1, 10.0, 13.0).map { scale(it) } shouldBe listOf(c, c, c, c)
        listOf(14.9, 15.0, 15.1, 16.0, 20.0).map { scale(it) } shouldBe listOf(a, a, a, a, a)
    }

    @Test
    fun quantile_invertExtent_maps_a_value_in_range_to_a_domain_extent() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 3)

        scale.invertExtent(0) shouldBe listOf(3.0, 7.25)
        scale.invertExtent(1) shouldBe listOf(7.25, 9.0)
        scale.invertExtent(2) shouldBe listOf(9.0, 14.5)
        scale.invertExtent(3) shouldBe listOf(14.5, 20.0)
    }

    @Test
    fun quantile_invertExtent_allows_arbitrary_range_values() {
        val scale = Scales.Quantized.quantile<() -> Unit>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        val a = {}
        val b = {}
        scale.range = listOf(a, b)

        scale.invertExtent(a) shouldBe listOf(3.0, 9.0)
        scale.invertExtent(b) shouldBe listOf(9.0, 20.0)
    }

    @Test
    fun quantile_invertExtent_returns_NaN_NaN_when_given_value_is_not_in_the_range() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)

        shouldThrow<IllegalStateException> { scale(10.0) }
        shouldThrow<IllegalStateException> { scale.invertExtent(10) }

        scale.range = listOf(4, 10)
        val notFound = listOf(Double.NaN, Double.NaN)
        scale.invertExtent(-1) shouldBe notFound
        scale.invertExtent(0) shouldBe notFound
        scale.invertExtent(2) shouldBe notFound
        scale.invertExtent(20) shouldBe notFound
    }

    fun quantile_invertExtent_returns_first_match_if_duplicate_values_exist_in_range() {
        val scale = Scales.Quantized.quantile<Int>()
        scale.domain = listOf(3.0, 6.0, 7.0, 8.0, 8.0, 10.0, 13.0, 15.0, 16.0, 20.0)
        scale.range = listOf(0, 1, 2, 0)

        scale.invertExtent(0) shouldBe listOf(3.0, 7.25)
        scale.invertExtent(1) shouldBe listOf(7.25, 9.0)
        scale.invertExtent(20) shouldBe listOf(9.0, 14.5)
    }
}
package io.data2viz.scale

import io.data2viz.color.Colors
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleLinearTests : TestBase() {


    @Test
    fun linear_no_interpolation() {
        val scale = scales.continuous.linear {
                    domain = listOf(.0, 1.0)
                    range = listOf(.0, 1.0)
                }

        scale(.5) shouldBeClose .5
        scale(0.1) shouldBeClose 0.1
        scale(0.133) shouldBeClose 0.133
        scale(0.9) shouldBeClose 0.9

        scale.domain = listOf(.0, 1.0)
        scale.range = listOf(1.0, .0)
        scale(.5) shouldBeClose 0.5
        scale(0.1) shouldBeClose 0.9
        scale(0.133) shouldBeClose 0.867
        scale(0.9) shouldBe (0.1 plusOrMinus 1e6)            // TODO find why it is in fact 0.09999999999999998
    }

    @Test
    fun linear_Number() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 100.0)
        scale.range = listOf(100.0, .0)
        scale(50.0) shouldBeClose 50.0
        scale(10.0) shouldBeClose 90.0
        scale(13.3) shouldBeClose 86.7
        scale(90.0) shouldBeClose 10.0
    }


    @Test
    fun linear_Number_clamp_noclamp() {
        val scale = scales.continuous.linear()

        scale.domain = arrayListOf(.0, 100.0)
        scale.range = listOf(.0, -100.0)
        scale(50.0) shouldBeClose -50.0
        scale(10.0) shouldBeClose -10.0
        scale(13.3) shouldBeClose -13.3
        scale(90.0) shouldBeClose -90.0
        scale(-20.0) shouldBeClose 20.0
        scale(132.0) shouldBeClose -132.0

        scale.clamp = true
        scale(50.0) shouldBeClose -50.0
        scale(10.0) shouldBeClose -10.0
        scale(13.3) shouldBeClose -13.3
        scale(90.0) shouldBeClose -90.0
        scale(-20.0) shouldBeClose 0.0
        scale(132.0) shouldBeClose -100.0
    }

    @Test
    fun linear_Number_invert() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 100.0)
        scale.range = listOf(100.0, .0)
        scale.invert(50.0) shouldBeClose 50.0
        scale.invert(90.0) shouldBeClose 10.0
        scale.invert(86.7) shouldBeClose 13.3
        scale.invert(10.0) shouldBeClose 90.0
    }

    @Test
    fun linear_Number_bimap_ascending_descending_domain_and_range_invert() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 100.0)
        scale.range = listOf(100.0, .0)
        scale.invert(50.0) shouldBeClose 50.0
        scale.invert(90.0) shouldBeClose 10.0
        scale.invert(86.7) shouldBeClose 13.3
        scale.invert(10.0) shouldBeClose 90.0

        scale.domain = listOf(100.0, .0)
        scale.range = listOf(.0, 100.0)
        scale.invert(50.0) shouldBeClose 50.0
        scale.invert(90.0) shouldBeClose 10.0
        scale.invert(86.7) shouldBeClose 13.3
        scale.invert(10.0) shouldBeClose 90.0

        scale.domain = listOf(100.0, .0)
        scale.range = listOf(100.0, .0)
        scale.invert(50.0) shouldBeClose 50.0
        scale.invert(90.0) shouldBeClose 90.0
        scale.invert(86.7) shouldBeClose 86.7
        scale.invert(10.0) shouldBeClose 10.0
    }

    @Test
    fun linear_Number_multiple_ranges() {
        val scale = scales.continuous.linear()

        // ASCENDING DOMAIN AND RANGE
        scale.domain = listOf(.0, 10.0, 20.0)
        scale.range = listOf(.0, 100.0, 300.0)
        scale(.0) shouldBeClose .0
        scale(10.0) shouldBeClose 100.0
        scale(20.0) shouldBeClose 300.0
        scale(5.0) shouldBeClose 50.0
        scale(15.0) shouldBeClose 200.0
        scale(17.0) shouldBeClose 240.0

        // DESCENDING DOMAIN
        // ASCENDING RANGE
        scale.domain = listOf(20.0, 10.0, .0)
        scale.range = listOf(.0, 100.0, 300.0)
        scale(.0) shouldBeClose 300.0
        scale(10.0) shouldBeClose 100.0
        scale(20.0) shouldBeClose .0
        scale(5.0) shouldBeClose 200.0
        scale(15.0) shouldBeClose 50.0
        scale(17.0) shouldBeClose 30.0

        // ASCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain = listOf(.0, 10.0, 20.0)
        scale.range = listOf(300.0, 100.0, .0)
        scale(.0) shouldBeClose 300.0
        scale(10.0) shouldBeClose 100.0
        scale(20.0) shouldBeClose .0
        scale(5.0) shouldBeClose 200.0
        scale(15.0) shouldBeClose 50.0
        scale(17.0) shouldBeClose 30.0

        // DESCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain = listOf(20.0, 10.0, .0)
        scale.range = listOf(300.0, 100.0, .0)
        scale(.0) shouldBeClose .0
        scale(10.0) shouldBeClose 100.0
        scale(20.0) shouldBeClose 300.0
        scale(5.0) shouldBeClose 50.0
        scale(15.0) shouldBeClose 200.0
        scale(17.0) shouldBeClose 240.0
    }

    @Test
    fun linear_Number_multiple_ranges_invert() {
        val scale = scales.continuous.linear()

        // ASCENDING DOMAIN AND RANGE
        scale.domain = listOf(.0, 10.0, 20.0)
        scale.range = listOf(.0, 100.0, 300.0)
        scale.invert(0.0) shouldBeClose 0.0
        scale.invert(100.0) shouldBeClose 10.0
        scale.invert(300.0) shouldBeClose 20.0
        scale.invert(50.0) shouldBeClose 5.0
        scale.invert(200.0) shouldBeClose 15.0
        scale.invert(240.0) shouldBeClose 17.0

        // DESCENDING DOMAIN
        // ASCENDING RANGE
        scale.domain = listOf(20.0, 10.0, .0)
        scale.range = listOf(300.0, 100.0, .0)
        scale.invert(0.0) shouldBeClose 0.0
        scale.invert(100.0) shouldBeClose 10.0
        scale.invert(300.0) shouldBeClose 20.0
        scale.invert(50.0) shouldBeClose 5.0
        scale.invert(200.0) shouldBeClose 15.0
        scale.invert(240.0) shouldBeClose 17.0

        // ASCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain = listOf(.0, 10.0, 20.0)
        scale.range = listOf(300.0, 100.0, .0)
        scale.invert(.0) shouldBeClose 20.0
        scale.invert(100.0) shouldBeClose 10.0
        scale.invert(300.0) shouldBeClose .0
        scale.invert(50.0) shouldBeClose 15.0
        scale.invert(200.0) shouldBeClose 5.0
        scale.invert(240.0) shouldBeClose 3.0

        // DESCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain = listOf(20.0, 10.0, .0)
        scale.range = listOf(300.0, 100.0, .0)
        scale.invert(.0) shouldBeClose .0
        scale.invert(100.0) shouldBeClose 10.0
        scale.invert(300.0) shouldBeClose 20.0
        scale.invert(50.0) shouldBeClose 5.0
        scale.invert(200.0) shouldBeClose 15.0
        scale.invert(240.0) shouldBeClose 17.0
    }

    @Test
    fun linear_Number_many_ranges_invert() {
        val scale = scales.continuous.linear()

        // ASCENDING DOMAIN AND DESCENDING RANGE
        scale.domain = listOf(.0,    10.0,   20.0,   30.0,   40.0,   50.0)
        scale.range = listOf(610.0,   310.0, 300.0,  110.0,  100.0,  -200.0)
        scale(0.0)  shouldBeClose 610.0
        scale(10.0) shouldBeClose 310.0
        scale(20.0) shouldBeClose 300.0
        scale(30.0) shouldBeClose 110.0
        scale(40.0) shouldBeClose 100.0
        scale(50.0) shouldBeClose -200.0
        scale.invert(610.0) shouldBeClose .0
        scale.invert(310.0) shouldBeClose 10.0
        scale.invert(300.0) shouldBeClose 20.0
        scale.invert(110.0) shouldBeClose 30.0
        scale.invert(100.0) shouldBeClose 40.0
        scale.invert(-200.0) shouldBeClose 50.0
        scale(-10.0)  shouldBeClose 910.0
        scale(5.0)  shouldBeClose 460.0
        scale(15.0) shouldBeClose 305.0
        scale(25.0) shouldBeClose 205.0
        scale(35.0) shouldBeClose 105.0
        scale(45.0) shouldBeClose -50.0
        scale(60.0) shouldBeClose -500.0
        scale.invert(910.0) shouldBeClose -10.0
        scale.invert(460.0) shouldBeClose 5.0
        scale.invert(305.0) shouldBeClose 15.0
        scale.invert(205.0) shouldBeClose 25.0
        scale.invert(105.0) shouldBeClose 35.0
        scale.invert(-50.0) shouldBeClose 45.0
        scale.invert(-500.0) shouldBeClose 60.0

        scale.clamp = true
        scale(-10.0)  shouldBeClose 610.0
        scale(60.0)  shouldBeClose -200.0
        scale.invert(900.0)  shouldBeClose .0
        scale.invert(-900.0)  shouldBeClose 50.0
    }

    @Test
    fun linear_double_rounded() {
        val scale = scales.continuous.linearRound()

        scale.domain = listOf(.0, 100.0)
        scale.range = listOf(.0, 100.0)
        scale(53.129) shouldBeClose 53.0
        scale(20.0255) shouldBeClose 20.0
        scale(93.899) shouldBeClose 94.0
    }

    @Test
    fun linear_HSL() {
        val scale = scales.colors.linearHSL()

        scale.domain = listOf(.0, 100.0)
        scale.range = listOf(Colors.hsl(0.deg, 1.0, 1.0), Colors.hsl(180.deg, 1.0, 1.0))
        scale(50.0) shouldBe Colors.hsl(90.deg, 1.0, 1.0)
        scale(20.0) shouldBe Colors.hsl(36.deg, 1.0, 1.0)
        scale(90.0) shouldBe Colors.hsl(162.deg, 1.0, 1.0)
    }

    /////////////// TESTS /////////////////////////////////////////

    @Test
    fun linear_expected_defaults_value() {
        val scale = scales.continuous.linear()

        scale.domain.size shouldBe 2
        scale.domain.first() shouldBeClose .0
        scale.domain.last() shouldBeClose 1.0
        scale.clamp shouldBe false
    }

    @Test
    fun linear_expected_scaling_with_defaults_value() {
        val scale = scales.continuous.linear()

        scale.range = listOf(1.0, 2.0)
        scale(.5) shouldBeClose 1.5
    }

    @Test
    fun linear_ignores_extra_range_values_for_smaller_domain() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(-10.0, .0)
        scale.range = listOf(0.0, 10.0, 2000.0)
        scale.clamp = true
        /*scale(-10.0) shouldBeClose .0
        scale(.0) shouldBeClose 10.0
        scale(-5.0) shouldBeClose 5.0
        scale(-15.0) shouldBeClose .0
        scale(10.0) shouldBeClose 10.0*/

        // changed behavior
        shouldThrow<IllegalStateException> { scale(.0) }
    }

    @Test
    fun linear_ignores_extra_domain_values_for_smaller_range() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(-10.0, .0, 2000.0)
        scale.range = listOf(0.0, 10.0)
        scale.clamp = true
        /*scale(-10.0) shouldBeClose .0
        scale(.0) shouldBeClose 10.0
        scale(-5.0) shouldBeClose 5.0
        scale(10.0) shouldBeClose 10.0*/

        // changed behavior
        shouldThrow<IllegalStateException> { scale(.0) }
    }

    @Test
    fun linear_maps_empty_domain_to_range_start_if_not_clamped() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, .0)
        scale.range = listOf(.0, 10.0)
        scale(1.0) shouldBeClose .0
        scale(-20.0) shouldBeClose .0
        scale(2000.0) shouldBeClose .0

        scale.domain = listOf(.0, .0)
        scale.range = listOf(10.0, .0)
        scale(1.0) shouldBeClose 10.0
        scale(-20.0) shouldBeClose 10.0
        scale(2000.0) shouldBeClose 10.0
    }

    @Test
    fun linear_maps_bilinear_domain_to_corresponding_range() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(1.0, 2.0)
        scale.range = listOf(.0, 1.0)
        scale(.5) shouldBeClose -.5
        scale(1.0) shouldBeClose .0
        scale(1.5) shouldBeClose .5
        scale(2.0) shouldBeClose 1.0
        scale(2.5) shouldBeClose 1.5
        scale.invert(-.5) shouldBeClose .5
        scale.invert(.0) shouldBeClose 1.0
        scale.invert(.5) shouldBeClose 1.5
        scale.invert(1.0) shouldBeClose 2.0
        scale.invert(1.5) shouldBeClose 2.5

        scale.clamp = true
        scale(.5) shouldBeClose .0
        scale(1.0) shouldBeClose .0
        scale(1.5) shouldBeClose .5
        scale(2.0) shouldBeClose 1.0
        scale(2.5) shouldBeClose 1.0
        scale.invert(-.5) shouldBeClose 1.0
        scale.invert(.0) shouldBeClose 1.0
        scale.invert(.5) shouldBeClose 1.5
        scale.invert(1.0) shouldBeClose 2.0
        scale.invert(1.5) shouldBeClose 2.0
    }

    @Test
    fun linear_maps_polylinear_domain_with_more_than_2_values_to_corresponding_range() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(1.0, 2.0, 4.0)
        scale.range = listOf(4.0, 2.0, 1.0)
        scale(1.5) shouldBeClose 3.0
        scale.invert(3.0) shouldBeClose 1.5
        scale(3.0) shouldBeClose 1.5
        scale.invert(1.5) shouldBeClose 3.0

        scale.domain = listOf(4.0, 2.0, 1.0)
        scale.range = listOf(1.0, 2.0, 4.0)
        scale(1.5) shouldBeClose 3.0
        scale.invert(3.0) shouldBeClose 1.5
        scale(3.0) shouldBeClose 1.5
        scale.invert(1.5) shouldBeClose 3.0
    }

    @Test
    fun linear_ticks_nice_OK() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(12.0, 87.0)
        scale.nice(5)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBeClose .0
        scale.domain.last() shouldBeClose 100.0

        scale.domain = listOf(12.0, 87.0)
        scale.nice(10)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBeClose 10.0
        scale.domain.last() shouldBeClose 90.0

        scale.domain = listOf(12.0, 87.0)
        scale.nice(100)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBeClose 12.0
        scale.domain.last() shouldBeClose 87.0
    }

    @Test
    fun linear_invert_maps_empty_range_to_domain_start() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(1.0, 2.0)
        scale.range = listOf(.0, .0)
        scale.invert(1.0) shouldBeClose 1.0
        scale.invert(.0) shouldBeClose 1.0
        scale.invert(-1.0) shouldBeClose 1.0

        scale.domain = listOf(2.0, 1.0)
        scale.range = listOf(.0, .0)
        scale.invert(1.0) shouldBeClose 2.0
        scale.invert(.0) shouldBeClose 2.0
        scale.invert(-1.0) shouldBeClose 2.0
    }

    @Test
    fun linear_accept_array_of_values() {
        val scale = scales.continuous.linear()

        scale.domain = arrayListOf()
        scale.domain.size shouldBe 0
        scale.domain shouldBe arrayListOf()

        scale.domain = arrayListOf(1.0, 2.0)
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)

        scale.domain = arrayListOf(1.0, 2.0, 3.0)
        scale.domain.size shouldBe 3
        scale.domain shouldBe arrayListOf(1.0, 2.0, 3.0)
    }

    @Test
    fun linear_no_binding_for_domain_setter() {
        val scale = scales.continuous.linear()

        val array:MutableList<Double> = arrayListOf(1.0, 2.0)
        scale.domain = array
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)

        array.add(3.0)
        array.size shouldBe 3
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)
    }

    @Test
    fun linear_no_binding_for_domain_getter() {
        val scale = scales.continuous.linear()

        scale.domain = arrayListOf(1.0, 2.0)
        val array:MutableList<Double> = scale.domain.toMutableList()
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)

        array.add(3.0)
        array.size shouldBe 3
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)
    }

    @Test
    fun linear_no_binding_for_range_setter() {
        val scale = scales.continuous.linear()

        val array:MutableList<Double> = arrayListOf(1.0, 2.0)
        scale.range = array
        scale.range.size shouldBe 2
        scale.range shouldBe arrayListOf(1.0, 2.0)

        array.add(3.0)
        array.size shouldBe 3
        scale.range.size shouldBe 2
        scale.range shouldBe arrayListOf(1.0, 2.0)
    }

    @Test
    fun linear_no_binding_for_range_getter() {
        val scale = scales.continuous.linear()

        scale.range = arrayListOf(1.0, 2.0)
        val array:MutableList<Double> = scale.range.toMutableList()
        scale.range.size shouldBe 2
        scale.range shouldBe arrayListOf(1.0, 2.0)

        array.add(3.0)
        array.size shouldBe 3
        scale.range.size shouldBe 2
        scale.range shouldBe arrayListOf(1.0, 2.0)
    }

    // TODO tape("linear.rangeRound(range) is an alias for linear.range(range).interpolate(interpolateRound)", function(test) {

    @Test
    fun linear_clamp_is_false_by_default() {
        val scale = scales.continuous.linear()

        scale.clamp shouldBe false
        scale.range = listOf(10.0, 20.0)
        scale(2.0) shouldBeClose 30.0
        scale(-1.0) shouldBeClose .0
        scale.invert(30.0) shouldBeClose 2.0
        scale.invert(.0) shouldBeClose -1.0
    }

    @Test
    fun linear_clamp_restricts_output_to_the_range() {
        val scale = scales.continuous.linear()

        scale.clamp = true
        scale.range = listOf(10.0, 20.0)
        scale(2.0) shouldBeClose 20.0
        scale(-1.0) shouldBeClose 10.0
    }

    @Test
    fun linear_clamp_restricts_input_to_the_domain() {
        val scale = scales.continuous.linear()

        scale.clamp = true
        scale.range = listOf(10.0, 20.0)
        scale.invert(30.0) shouldBeClose 1.0
        scale.invert(.0) shouldBeClose .0
    }

    // TODO tape("linear.interpolate(interpolate) takes a custom interpolator factory", function(test) {

    @Test
    fun linear_nice_is_10_ticks_by_default() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, .96)
        scale.nice()
        scale.domain.first() shouldBeClose .0
        scale.domain.last() shouldBeClose 1.0

        scale.domain = listOf(.0, 96.0)
        scale.nice()
        scale.domain.first() shouldBeClose .0
        scale.domain.last() shouldBeClose 100.0
    }

    @Test
    fun linear_nice_extends_domain_to_match_desired_ticks() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, .96)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, 1.0)

        scale.domain = listOf(.0, 96.0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, 100.0)

        scale.domain = listOf(.96, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(1.0, .0)

        scale.domain = listOf(96.0, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(100.0, .0)

        scale.domain = listOf(.0, -.96)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, -1.0)

        scale.domain = listOf(.0, -96.0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, -100.0)

        scale.domain = listOf(-.96, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(-1.0, .0)

        scale.domain = listOf(-96.0, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(-100.0, .0)

        scale.domain = listOf(-.1, 51.1)
        scale.nice(8)
        scale.domain shouldBe arrayListOf(-10.0, 60.0)
    }

    @Test
    fun linear_nice_extends_domain_to_round_numbers() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(1.1, 10.9)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(1.0, 11.0)

        scale.domain = listOf(10.9, 1.1)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(11.0, 1.0)

        scale.domain = listOf(.7, 11.001)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, 12.0)

        scale.domain = listOf(123.1, 6.7)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(130.0, .0)

        scale.domain = listOf(.0, .49)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, .5)

        scale.domain = listOf(.0, 14.1)
        scale.nice(5)
        scale.domain shouldBe arrayListOf(.0, 20.0)

        scale.domain = listOf(.0, 15.0)
        scale.nice(5)
        scale.domain shouldBe arrayListOf(.0, 20.0)
        scale.range = listOf(.0, 100.0)
        scale(20.0) shouldBeClose 100.0
    }

    @Test
    fun linear_nice_no_effect_on_invalid_domains() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, .0)

        scale.domain = listOf(.5, .5)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.5, .5)
    }

    @Test
    fun linear_nice_only_the_extent_of_a_polydomain() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(1.1, 1.0, 2.0, 3.0, 10.9)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(1.0, 1.0, 2.0, 3.0, 11.0)

        scale.domain = listOf(123.1, 1.0, 2.0, 3.0, -0.9)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(130.0, 1.0, 2.0, 3.0, -10.0)
    }

    @Test
    fun linear_nice_accept_a_tick_to_control_nicing_step() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(12.0, 87.0)
        scale.nice(5)
        scale.domain shouldBe arrayListOf(.0, 100.0)

        scale.domain = listOf(12.0, 87.0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(10.0, 90.0)

        scale.domain = listOf(12.0, 87.0)
        scale.nice(100)
        scale.domain shouldBe arrayListOf(12.0, 87.0)
    }

    @Test
    fun linear_ticks_return_expected_ticks() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 1.0)
        scale.ticks(10).size shouldBe 11
        scale.ticks(10) shouldBe arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(9) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(8) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(7) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(6) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(5) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(4) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(3) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(2) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(1) shouldBe  arrayListOf(0.0,                                              1.0)

        scale.domain = listOf(-100.0, 100.0)
        scale.ticks(10) shouldBe arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(9) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(8) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(7) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(6) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(5) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(4) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(3) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(2) shouldBe  arrayListOf(-100.0,                                    .0,                               100.0)
        scale.ticks(1) shouldBe  arrayListOf(                                           .0                                     )
    }

    @Test
    fun linear_ticks_descending_domain_return_expected_ticks() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(1.0, .0)
        scale.ticks(10).size shouldBe 11
        scale.ticks(10) shouldBe arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
        scale.ticks(9) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
        scale.ticks(8) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
        scale.ticks(7) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(6) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(5) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(4) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(3) shouldBe  arrayListOf(0.0,                     0.5,                     1.0).reversed()
        scale.ticks(2) shouldBe  arrayListOf(0.0,                     0.5,                     1.0).reversed()
        scale.ticks(1) shouldBe  arrayListOf(0.0,                                              1.0).reversed()

        scale.domain = listOf(100.0, -100.0)
        scale.ticks(10) shouldBe arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(9) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(8) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(7) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(6) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(5) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(4) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(3) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(2) shouldBe  arrayListOf(-100.0,                                    .0,                               100.0).reversed()
        scale.ticks(1) shouldBe  arrayListOf(                                           .0                                     ).reversed()
    }

    @Test
    fun linear_ticks_polylinear_domain_return_expected_ticks() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 0.25, 0.9, 1.0)
        scale.ticks(10).size shouldBe 11
        scale.ticks(10) shouldBe arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(9) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(8) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(7) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(6) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(5) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(4) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(3) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(2) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(1) shouldBe  arrayListOf(0.0,                                              1.0)

        scale.domain = listOf(-100.0, .0, 100.0)
        scale.ticks(10) shouldBe arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(9) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(8) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(7) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(6) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(5) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(4) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(3) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(2) shouldBe  arrayListOf(-100.0,                                    .0,                               100.0)
        scale.ticks(1) shouldBe  arrayListOf(                                           .0                                     )
    }

    @Test
    fun linear_ticks_count_should_be_greater_than_zero() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 1.0)
        scale.ticks(0).size shouldBe 0
        scale.ticks(0) shouldBe arrayListOf<Double>()
        scale.ticks(-2).size shouldBe 0
        scale.ticks(-2) shouldBe arrayListOf<Double>()

        // TODO shouldThrow ??
//        shouldThrow<IllegalArgumentException> { scale.ticks(-2) }
//        shouldThrow<IllegalArgumentException> { scale.ticks(Int.MIN_VALUE) }
    }

    @Test
    fun linear_ticks_count_without_arguments_should_be_count_equals_10() {
        val scale = scales.continuous.linear()

        scale.domain = listOf(.0, 1.0)
        scale.ticks().size shouldBe scale.ticks(10).size
    }

    // TODO "linear.tickFormat(count) returns a format suitable for the ticks" etc...
    // TODO "linear.copy() returns a copy with changes to the interpolator are isolated"
}
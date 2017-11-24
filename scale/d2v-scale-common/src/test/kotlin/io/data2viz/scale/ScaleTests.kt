package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun linear_no_interpolation() {
        val scale = linearScaleDouble()

        scale.domain(.0, 1.0)
        scale.range(.0, 1.0)
        scale(.5) shouldBe .5
        scale(0.1) shouldBe 0.1
        scale(0.133) shouldBe 0.133
        scale(0.9) shouldBe 0.9

        scale.domain(.0, 1.0)
        scale.range(1.0, .0)
        scale(.5) shouldBe 0.5
        scale(0.1) shouldBe 0.9
        scale(0.133) shouldBe 0.867
        scale(0.9) shouldBe (0.1 plusOrMinus 1e6)            // TODO find why it is in fact 0.09999999999999998
    }

    @Test
    fun linear_Number() {
        val scale = linearScaleDouble()

        scale.domain(.0, 100.0)
        scale.range(100.0, .0)
        scale(50.0) shouldBe 50.0
        scale(10.0) shouldBe 90.0
        scale(13.3) shouldBe 86.7
        scale(90.0) shouldBe 10.0
    }


    @Test
    fun linear_Number_clamp_noclamp() {
        val scale = linearScaleDouble()

        scale.domain(.0, 100.0)
        scale.range(.0, -100.0)
        scale(50.0) shouldBe -50.0
        scale(10.0) shouldBe (-10.0 plusOrMinus epsilon)
        scale(13.3) shouldBe (-13.3 plusOrMinus epsilon)
        scale(90.0) shouldBe -90.0
        scale(-20.0) shouldBe 20.0
        scale(132.0) shouldBe (-132.0 plusOrMinus epsilon)

        scale.clamp = true
        scale(50.0) shouldBe -50.0
        scale(10.0) shouldBe (-10.0 plusOrMinus epsilon)
        scale(13.3) shouldBe (-13.3 plusOrMinus epsilon)
        scale(90.0) shouldBe -90.0
        scale(-20.0) shouldBe 0.0
        scale(132.0) shouldBe (-100.0 plusOrMinus epsilon)
    }

    @Test
    fun linear_Number_invert() {
        val scale = linearScaleDouble()

        scale.domain(.0, 100.0)
        scale.range(100.0, .0)
        scale.invert(50.0) shouldBe 50.0
        scale.invert(90.0) shouldBe 10.0
        scale.invert(86.7) shouldBe (13.3 plusOrMinus epsilon)
        scale.invert(10.0) shouldBe (90.0 plusOrMinus epsilon)
    }

    @Test
    fun linear_Number_bimap_ascending_descending_domain_and_range_invert() {
        val scale = linearScaleDouble()

        scale.domain(.0, 100.0)
        scale.range(100.0, .0)
        scale.invert(50.0) shouldBe 50.0
        scale.invert(90.0) shouldBe 10.0
        scale.invert(86.7) shouldBe (13.3 plusOrMinus epsilon)
        scale.invert(10.0) shouldBe (90.0 plusOrMinus epsilon)

        scale.domain(100.0, .0)
        scale.range(.0, 100.0)
        scale.invert(50.0) shouldBe 50.0
        scale.invert(90.0) shouldBe 10.0
        scale.invert(86.7) shouldBe (13.3 plusOrMinus epsilon)
        scale.invert(10.0) shouldBe (90.0 plusOrMinus epsilon)

        scale.domain(100.0, .0)
        scale.range(100.0, .0)
        scale.invert(50.0) shouldBe 50.0
        scale.invert(90.0) shouldBe 90.0
        scale.invert(86.7) shouldBe (86.7 plusOrMinus epsilon)
        scale.invert(10.0) shouldBe (10.0 plusOrMinus epsilon)
    }

    @Test
    fun linear_Number_multiple_ranges() {
        val scale = linearScaleDouble()

        // ASCENDING DOMAIN AND RANGE
        scale.domain(.0, 10.0, 20.0)
        scale.range(.0, 100.0, 300.0)
        scale(.0) shouldBe .0
        scale(10.0) shouldBe 100.0
        scale(20.0) shouldBe 300.0
        scale(5.0) shouldBe 50.0
        scale(15.0) shouldBe 200.0
        scale(17.0) shouldBe 240.0

        // DESCENDING DOMAIN
        // ASCENDING RANGE
        scale.domain(20.0, 10.0, .0)
        scale.range(.0, 100.0, 300.0)
        scale(.0) shouldBe 300.0
        scale(10.0) shouldBe 100.0
        scale(20.0) shouldBe .0
        scale(5.0) shouldBe 200.0
        scale(15.0) shouldBe 50.0
        scale(17.0) shouldBe 30.0

        // ASCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain(.0, 10.0, 20.0)
        scale.range(300.0, 100.0, .0)
        scale(.0) shouldBe 300.0
        scale(10.0) shouldBe 100.0
        scale(20.0) shouldBe .0
        scale(5.0) shouldBe 200.0
        scale(15.0) shouldBe 50.0
        scale(17.0) shouldBe 30.0

        // DESCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain(20.0, 10.0, .0)
        scale.range(300.0, 100.0, .0)
        scale(.0) shouldBe .0
        scale(10.0) shouldBe 100.0
        scale(20.0) shouldBe 300.0
        scale(5.0) shouldBe 50.0
        scale(15.0) shouldBe 200.0
        scale(17.0) shouldBe 240.0
    }

    @Test
    fun linear_Number_multiple_ranges_invert() {
        val scale = linearScaleDouble()

        // ASCENDING DOMAIN AND RANGE
        scale.domain(.0, 10.0, 20.0)
        scale.range(.0, 100.0, 300.0)
        scale.invert(0.0) shouldBe 0.0
        scale.invert(100.0) shouldBe 10.0
        scale.invert(300.0) shouldBe 20.0
        scale.invert(50.0) shouldBe 5.0
        scale.invert(200.0) shouldBe 15.0
        scale.invert(240.0) shouldBe 17.0

        // DESCENDING DOMAIN
        // ASCENDING RANGE
        scale.domain(20.0, 10.0, .0)
        scale.range(300.0, 100.0, .0)
        scale.invert(0.0) shouldBe 0.0
        scale.invert(100.0) shouldBe 10.0
        scale.invert(300.0) shouldBe 20.0
        scale.invert(50.0) shouldBe 5.0
        scale.invert(200.0) shouldBe 15.0
        scale.invert(240.0) shouldBe 17.0

        // ASCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain(.0, 10.0, 20.0)
        scale.range(300.0, 100.0, .0)
        scale.invert(.0) shouldBe 20.0
        scale.invert(100.0) shouldBe 10.0
        scale.invert(300.0) shouldBe .0
        scale.invert(50.0) shouldBe 15.0
        scale.invert(200.0) shouldBe 5.0
        scale.invert(240.0) shouldBe 3.0

        // DESCENDING DOMAIN
        // DESCENDING RANGE
        scale.domain(20.0, 10.0, .0)
        scale.range(300.0, 100.0, .0)
        scale.invert(.0) shouldBe .0
        scale.invert(100.0) shouldBe 10.0
        scale.invert(300.0) shouldBe 20.0
        scale.invert(50.0) shouldBe 5.0
        scale.invert(200.0) shouldBe 15.0
        scale.invert(240.0) shouldBe 17.0
    }

    @Test
    fun linear_Number_many_ranges_invert() {
        val scale = linearScaleDouble()

        // ASCENDING DOMAIN AND DESCENDING RANGE
        scale.domain(.0,    10.0,   20.0,   30.0,   40.0,   50.0)
        scale.range(610.0,   310.0, 300.0,  110.0,  100.0,  -200.0)
        scale(0.0)  shouldBe 610.0
        scale(10.0) shouldBe 310.0
        scale(20.0) shouldBe 300.0
        scale(30.0) shouldBe 110.0
        scale(40.0) shouldBe 100.0
        scale(50.0) shouldBe -200.0
        scale.invert(610.0) shouldBe .0
        scale.invert(310.0) shouldBe 10.0
        scale.invert(300.0) shouldBe 20.0
        scale.invert(110.0) shouldBe 30.0
        scale.invert(100.0) shouldBe 40.0
        scale.invert(-200.0) shouldBe 50.0
        scale(-10.0)  shouldBe 910.0
        scale(5.0)  shouldBe 460.0
        scale(15.0) shouldBe 305.0
        scale(25.0) shouldBe 205.0
        scale(35.0) shouldBe 105.0
        scale(45.0) shouldBe -50.0
        scale(60.0) shouldBe -500.0
        scale.invert(910.0) shouldBe -10.0
        scale.invert(460.0) shouldBe 5.0
        scale.invert(305.0) shouldBe 15.0
        scale.invert(205.0) shouldBe 25.0
        scale.invert(105.0) shouldBe 35.0
        scale.invert(-50.0) shouldBe 45.0
        scale.invert(-500.0) shouldBe 60.0

        scale.clamp = true
        scale(-10.0)  shouldBe 610.0
        scale(60.0)  shouldBe -200.0
        scale.invert(900.0)  shouldBe .0
        scale.invert(-900.0)  shouldBe 50.0
    }

    @Test
    fun linear_HSL() {
        val scale = linearScaleHSL()

        scale.domain(.0, 100.0)
        scale.range(HSL(0.deg), HSL(180.deg))
        scale(50.0) shouldBe HSL(90.deg)
        scale(20.0) shouldBe HSL(36.deg)
        scale(90.0) shouldBe HSL(162.deg)
    }

    /////////////// LEGACY TESTS /////////////////////////////////////////

    @Test
    fun linear_expected_defaults_value_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe .0
        scale.domain.last() shouldBe 1.0
        scale.clamp shouldBe false
    }

    @Test
    fun linear_expected_scaling_with_defaults_value_LEGACY() {
        val scale = linearScaleDouble()

        scale.range(1.0, 2.0)
        scale(.5) shouldBe 1.5
    }

    @Test
    fun linear_ignores_extra_range_values_for_smaller_domain_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(-10.0, .0)
        scale.range(0.0, 10.0, 2000.0)
        scale.clamp = true
        scale(-10.0) shouldBe .0
        scale(.0) shouldBe 10.0
        scale(-5.0) shouldBe 5.0
        scale(-15.0) shouldBe .0
        scale(10.0) shouldBe 10.0
    }

    @Test
    fun linear_ignores_extra_domain_values_for_smaller_range_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(-10.0, .0, 2000.0)
        scale.range(0.0, 10.0)
        scale.clamp = true
        scale(-10.0) shouldBe .0
        scale(.0) shouldBe 10.0
        scale(-5.0) shouldBe 5.0
        scale(10.0) shouldBe 10.0
    }

    @Test
    fun linear_maps_empty_domain_to_range_start_if_not_clamped_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, .0)
        scale.range(.0, 10.0)
        scale(1.0) shouldBe .0
        scale(-20.0) shouldBe .0
        scale(2000.0) shouldBe .0

        scale.domain(.0, .0)
        scale.range(10.0, .0)
        scale(1.0) shouldBe 10.0
        scale(-20.0) shouldBe 10.0
        scale(2000.0) shouldBe 10.0
    }

    @Test
    fun linear_maps_bilinear_domain_to_corresponding_range_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(1.0, 2.0)
        scale.range(.0, 1.0)
        scale(.5) shouldBe -.5
        scale(1.0) shouldBe .0
        scale(1.5) shouldBe .5
        scale(2.0) shouldBe 1.0
        scale(2.5) shouldBe 1.5
        scale.invert(-.5) shouldBe .5
        scale.invert(.0) shouldBe 1.0
        scale.invert(.5) shouldBe 1.5
        scale.invert(1.0) shouldBe 2.0
        scale.invert(1.5) shouldBe 2.5

        scale.clamp = true
        scale(.5) shouldBe .0
        scale(1.0) shouldBe .0
        scale(1.5) shouldBe .5
        scale(2.0) shouldBe 1.0
        scale(2.5) shouldBe 1.0
        scale.invert(-.5) shouldBe 1.0
        scale.invert(.0) shouldBe 1.0
        scale.invert(.5) shouldBe 1.5
        scale.invert(1.0) shouldBe 2.0
        scale.invert(1.5) shouldBe 2.0
    }

    @Test
    fun linear_maps_polylinear_domain_with_more_than_2_values_to_corresponding_range_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(1.0, 2.0, 4.0)
        scale.range(4.0, 2.0, 1.0)
        scale(1.5) shouldBe 3.0
        scale.invert(3.0) shouldBe 1.5
        scale(3.0) shouldBe 1.5
        scale.invert(1.5) shouldBe 3.0

        scale.domain(4.0, 2.0, 1.0)
        scale.range(1.0, 2.0, 4.0)
        scale(1.5) shouldBe 3.0
        scale.invert(3.0) shouldBe 1.5
        scale(3.0) shouldBe 1.5
        scale.invert(1.5) shouldBe 3.0
    }

    @Test
    fun linear_ticks_nice_OK_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(12.0, 87.0)
        scale.nice(5)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe .0
        scale.domain.last() shouldBe 100.0

        scale.domain(12.0, 87.0)
        scale.nice(10)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe 10.0
        scale.domain.last() shouldBe 90.0

        scale.domain(12.0, 87.0)
        scale.nice(100)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe 12.0
        scale.domain.last() shouldBe 87.0
    }

    @Test
    fun linear_invert_maps_empty_range_to_domain_start_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(1.0, 2.0)
        scale.range(.0, .0)
        scale.invert(1.0) shouldBe 1.0
        scale.invert(.0) shouldBe 1.0
        scale.invert(-1.0) shouldBe 1.0

        scale.domain(2.0, 1.0)
        scale.range(.0, .0)
        scale.invert(1.0) shouldBe 2.0
        scale.invert(.0) shouldBe 2.0
        scale.invert(-1.0) shouldBe 2.0
    }

    @Test
    fun linear_accept_array_of_values_LEGACY() {
        val scale = linearScaleDouble()

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
    fun linear_no_binding_for_domain_setter_LEGACY() {
        val scale = linearScaleDouble()

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
    fun linear_no_binding_for_domain_getter_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain = arrayListOf(1.0, 2.0)
        val array:MutableList<Double> = scale.domain
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)

        array.add(3.0)
        array.size shouldBe 3
        scale.domain.size shouldBe 2
        scale.domain shouldBe arrayListOf(1.0, 2.0)
    }

    @Test
    fun linear_no_binding_for_range_setter_LEGACY() {
        val scale = linearScaleDouble()

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
    fun linear_no_binding_for_range_getter_LEGACY() {
        val scale = linearScaleDouble()

        scale.range = arrayListOf(1.0, 2.0)
        val array:MutableList<Double> = scale.range
        scale.range.size shouldBe 2
        scale.range shouldBe arrayListOf(1.0, 2.0)

        array.add(3.0)
        array.size shouldBe 3
        scale.range.size shouldBe 2
        scale.range shouldBe arrayListOf(1.0, 2.0)
    }

    // TODO tape("linear.rangeRound(range) is an alias for linear.range(range).interpolate(interpolateRound)", function(test) {

    @Test
    fun linear_clamp_is_false_by_default_LEGACY() {
        val scale = linearScaleDouble()

        scale.clamp shouldBe false
        scale.range(10.0, 20.0)
        scale(2.0) shouldBe 30.0
        scale(-1.0) shouldBe .0
        scale.invert(30.0) shouldBe 2.0
        scale.invert(.0) shouldBe -1.0
    }

    @Test
    fun linear_clamp_restricts_output_to_the_range_LEGACY() {
        val scale = linearScaleDouble()

        scale.clamp = true
        scale.range(10.0, 20.0)
        scale(2.0) shouldBe 20.0
        scale(-1.0) shouldBe 10.0
    }

    @Test
    fun linear_clamp_restricts_input_to_the_domain_LEGACY() {
        val scale = linearScaleDouble()

        scale.clamp = true
        scale.range(10.0, 20.0)
        scale.invert(30.0) shouldBe 1.0
        scale.invert(.0) shouldBe .0
    }

    // TODO tape("linear.interpolate(interpolate) takes a custom interpolator factory", function(test) {

    @Test
    fun linear_nice_is_10_ticks_by_default_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, .96)
        scale.nice()
        scale.domain.first() shouldBe .0
        scale.domain.last() shouldBe 1.0

        scale.domain(.0, 96.0)
        scale.nice()
        scale.domain.first() shouldBe .0
        scale.domain.last() shouldBe 100.0
    }

    @Test
    fun linear_nice_extends_domain_to_match_desired_ticks_LEGACY() {
        val scale = linearScaleDouble()

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

    @Test
    fun linear_nice_extends_domain_to_round_numbers_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(1.1, 10.9)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(1.0, 11.0)

        scale.domain(10.9, 1.1)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(11.0, 1.0)

        scale.domain(.7, 11.001)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, 12.0)

        scale.domain(123.1, 6.7)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(130.0, .0)

        scale.domain(.0, .49)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, .5)

        scale.domain(.0, 14.1)
        scale.nice(5)
        scale.domain shouldBe arrayListOf(.0, 20.0)

        scale.domain(.0, 15.0)
        scale.nice(5)
        scale.domain shouldBe arrayListOf(.0, 20.0)
    }

    @Test
    fun linear_nice_no_effect_on_invalid_domains_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, .0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.0, .0)

        scale.domain(.5, .5)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(.5, .5)
    }

    @Test
    fun linear_nice_only_the_extent_of_a_polydomain_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(1.1, 1.0, 2.0, 3.0, 10.9)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(1.0, 1.0, 2.0, 3.0, 11.0)

        scale.domain(123.1, 1.0, 2.0, 3.0, -0.9)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(130.0, 1.0, 2.0, 3.0, -10.0)
    }

    @Test
    fun linear_nice_accept_a_tick_to_control_nicing_step_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(12.0, 87.0)
        scale.nice(5)
        scale.domain shouldBe arrayListOf(.0, 100.0)

        scale.domain(12.0, 87.0)
        scale.nice(10)
        scale.domain shouldBe arrayListOf(10.0, 90.0)

        scale.domain(12.0, 87.0)
        scale.nice(100)
        scale.domain shouldBe arrayListOf(12.0, 87.0)
    }

    @Test
    fun linear_ticks_return_expected_ticks_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, 1.0)
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

        scale.domain(-100.0, 100.0)
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
    fun linear_ticks_descending_domain_return_expected_ticks_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(1.0, .0)
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

        scale.domain(100.0, -100.0)
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
    fun linear_ticks_polylinear_domain_return_expected_ticks_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, 0.25, 0.9, 1.0)
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

        scale.domain(-100.0, .0, 100.0)
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
    fun linear_ticks_count_should_be_greater_than_zero_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, 1.0)
        scale.ticks(0).size shouldBe 0
        scale.ticks(0) shouldBe arrayListOf<Double>()
        scale.ticks(-2).size shouldBe 0
        scale.ticks(-2) shouldBe arrayListOf<Double>()

        // TODO shouldThrow ??
//        shouldThrow<IllegalArgumentException> { scale.ticks(-2) }
//        shouldThrow<IllegalArgumentException> { scale.ticks(Int.MIN_VALUE) }
    }

    @Test
    fun linear_ticks_count_without_arguments_should_be_count_equals_10_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(.0, 1.0)
        scale.ticks().size shouldBe scale.ticks(10).size
    }

    // TODO "linear.tickFormat(count) returns a format suitable for the ticks" etc...
    // TODO "linear.copy() returns a copy with changes to the interpolator are isolated"
}
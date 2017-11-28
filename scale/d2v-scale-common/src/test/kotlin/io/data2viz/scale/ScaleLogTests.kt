package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleLogTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun log_x_domain_limits_maps_to_y_range_limits() {
        val scale = logScale()
        scale.domain(1.0, 100.0)
        scale.range(.0, 1.0)

        scale(1.0) shouldBe .0
        scale(100.0) shouldBe 1.0
    }

    @Test
    fun log_x_maps_a_number_x_to_a_number_y_LEGACY() {
        val scale = logScale()
        scale.range(.0, 1.0)

        scale.domain(1.0, 2.0)
        scale(.5) shouldBe (-1.0000000 plusOrMinus epsilon)
        scale(1.0) shouldBe (0.0000000 plusOrMinus epsilon)
        scale(1.5) shouldBe (0.5849625 plusOrMinus epsilon)
        scale(2.0) shouldBe (1.0000000 plusOrMinus epsilon)
        scale(1.5) shouldBe (1.3219281 plusOrMinus epsilon)
        scale.base shouldBe 10.0
    }

    @Test
    fun log_invert_x_maps_a_number_x_to_a_number_y_LEGACY() {
        val scale = logScale()
        scale.range(.0, 1.0)

        scale.domain(1.0, 2.0)
        scale.invert(-1.0000000) shouldBe (.5 plusOrMinus epsilon)
        scale.invert(0.0000000) shouldBe (1.0 plusOrMinus epsilon)
        scale.invert(0.5849625) shouldBe (1.5 plusOrMinus epsilon)
        scale.invert(1.0000000) shouldBe (2.0 plusOrMinus epsilon)
        scale.invert(1.3219281) shouldBe (.5 plusOrMinus epsilon)
        scale.base shouldBe 10.0
    }

    @Test
    fun log_base_b_sets_log_base_changing_ticks_LEGACY() {
        val scale = logScale()
        scale.range(.0, 1.0)
        scale.domain(1.0, 32.0)

        scale.base = 2.0
        scale.ticks() shouldBe arrayListOf(1.0, 2.0, 4.0, 8.0, 16.0, 32.0)

        scale.base = kotlin.math.E
        scale.ticks() shouldBe arrayListOf(1.0, 2.71828182846, 7.38905609893, 20.0855369232)
    }

    @Test
    fun log_base_b_tickes_generate_expected_power_of_base_ticks_LEGACY() {
        val scale = logScale()
        scale.range(.0, 1.0)
        scale.domain(0.1, 100.0)

        scale.base = kotlin.math.E
        scale.ticks() shouldBe arrayListOf(0.135335283237, 0.367879441171, 1.0, 2.718281828459, 7.389056098931, 20.085536923188, 54.598150033144)
    }

    @Test
    fun log_nice_nices_domain_extending_it_to_powers_of_ten_LEGACY() {
        val scale = logScale()

        scale.domain(1.1, 10.9)
        scale.nice()
        scale.domain shouldBe arrayListOf(1.0, 100.0)

        scale.domain(10.9, 1.1)
        scale.nice()
        scale.domain shouldBe arrayListOf(100.0, 1.0)

        scale.domain(.7, 11.001)
        scale.nice()
        scale.domain shouldBe arrayListOf(0.1, 100.0)

        scale.domain(123.1, 6.7)
        scale.nice()
        scale.domain shouldBe arrayListOf(1000.0, 1.0)

        scale.domain(0.01, 0.49)
        scale.nice()
        scale.domain shouldBe arrayListOf(0.01, 1.0)

        scale.domain(1.5, 50.0)
        scale.range(.0, 1.0)
        scale.nice()
        scale.domain shouldBe arrayListOf(1.0, 100.0)
        scale(1.0) shouldBe .0
        scale(100.0) shouldBe 1.0
    }


    // TODO : add more scale tests
    // TODO tests throw (domain >0 and <0 ...)
}
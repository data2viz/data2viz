package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleLogTests : TestBase() {

    @Test
    fun log_x_domain_limits_maps_to_y_range_limits() {
        val scale = scaleLog()
        scale.domain = listOf(1.0, 100.0)
        scale.range = listOf(.0, 1.0)

        scale(1.0) shouldBe .0
        scale(100.0) shouldBe 1.0
    }

    @Test
    fun log_x_maps_a_number_x_to_a_number_y_LEGACY() {
        val scale = (scaleLog() as LogScale)
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(1.0, 2.0)
        scale(.5) shouldBeClose -1.0000000
        scale(1.0) shouldBeClose 0.0000000
        scale(1.5) shouldBeClose 0.5849625
        scale(2.0) shouldBeClose 1.0000000
        scale(2.5) shouldBeClose 1.3219282
        scale.base shouldBe 10.0
    }

    
    @Test
    fun log_invert_x_maps_a_number_x_to_a_number_y_LEGACY() {
        val scale =  (scaleLog() as LogScale)
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(1.0, 2.0)
        scale.invert(-1.0000000) shouldBeClose .5
        scale.invert(0.0000000) shouldBeClose 1.0
        scale.invert(0.5849625) shouldBeClose 1.5
        scale.invert(1.0000000) shouldBeClose 2.0
        scale.invert(1.3219281) shouldBeClose 2.5
        scale.base shouldBe 10.0
    }

    @Test
    fun log_base_b_sets_log_base_changing_ticks_LEGACY() {
        val scale =  (scaleLog() as LogScale)
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(1.0, 32.0)

        scale.base = 2.0
        scale.ticks() shouldBe arrayListOf(1.0, 2.0, 4.0, 8.0, 16.0, 32.0)

        scale.base = kotlin.math.E
        scale.ticks() shouldBe arrayListOf(1.0, 2.71828182846, 7.38905609893, 20.0855369232)
    }

    @Test
    fun log_base_b_tickes_generate_expected_power_of_base_ticks_LEGACY() {
        val scale =  (scaleLog() as LogScale)
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(0.1, 100.0)

        scale.base = kotlin.math.E
        scale.ticks() shouldBe arrayListOf(0.135335283237, 0.367879441171, 1.0, 2.718281828459, 7.389056098931, 20.085536923188, 54.598150033144)
    }

    @Test
    fun log_nice_nices_domain_extending_it_to_powers_of_ten_LEGACY() {
        val scale =  scaleLog() 

        scale.domain = listOf(1.1, 10.9)
        scale.nice()
        scale.domain shouldBe arrayListOf(1.0, 100.0)

        scale.domain = listOf(10.9, 1.1)
        scale.nice()
        scale.domain shouldBe arrayListOf(100.0, 1.0)

        scale.domain = listOf(.7, 11.001)
        scale.nice()
        scale.domain shouldBe arrayListOf(0.1, 100.0)

        scale.domain = listOf(123.1, 6.7)
        scale.nice()
        scale.domain shouldBe arrayListOf(1000.0, 1.0)

        scale.domain = listOf(0.01, 0.49)
        scale.nice()
        scale.domain shouldBe arrayListOf(0.01, 1.0)

        scale.domain = listOf(1.5, 50.0)
        scale.range = listOf(.0, 1.0)
        scale.nice()
        scale.domain shouldBe arrayListOf(1.0, 100.0)
        scale(1.0) shouldBe .0
        scale(100.0) shouldBe 1.0
    }


    // TODO : add more scale tests
    // TODO tests throw (domain >0 and <0 ...)
}
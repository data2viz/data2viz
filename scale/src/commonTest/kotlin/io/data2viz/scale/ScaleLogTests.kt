/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleLogTests : TestBase() {

    @Test
    fun log_x_domain_limits_maps_to_y_range_limits() {
        val scale = scaleLog()
        scale.domain = listOf(1.0, 100.0)
        scale.range = listOf(.0, 1.0)

        scale(1.0) shouldBeClose .0
        scale(100.0) shouldBeClose 1.0
    }

    private fun scaleLog(): LogScale  = Scales.Continuous.log() as LogScale

    @Test
    fun log_x_maps_a_number_x_to_a_number_y() {
        val scale = scaleLog()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(1.0, 2.0)
        scale(.5) shouldBeClose -1.0000000
        scale(1.0) shouldBeClose 0.0000000
        scale(1) shouldBeClose 0.0000000
        scale(1.5) shouldBeClose 0.5849625
        scale(2.0) shouldBeClose 1.0000000
        scale(2) shouldBeClose 1.0000000
        scale(2.5) shouldBeClose 1.3219282
        scale.base shouldBeClose 10.0
    }

    
    @Test
    fun log_invert_x_maps_a_number_x_to_a_number_y() {
        val scale =  scaleLog()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(1.0, 2.0)
        scale.invert(-1.0000000) shouldBeClose .5
        scale.invert(0.0000000) shouldBeClose 1.0
        scale.invert(0.5849625) shouldBeClose 1.5
        scale.invert(1.0000000) shouldBeClose 2.0
        scale.invert(1.3219281) shouldBeClose 2.5
        scale.base shouldBeClose 10.0
    }

    @Test
    fun log_base_b_sets_log_base_changing_ticks() {
        val scale =  scaleLog()
        scale.domain = listOf(1.0, 32.0)

        scale.base = 2.0
        scale.ticks() shouldBe arrayListOf(1.0, 2.0, 4.0, 8.0, 16.0, 32.0)

        scale.base = kotlin.math.E
        scale.ticks() shouldBe arrayListOf(1.0, 2.71828182846, 7.38905609893, 20.0855369232)
    }

    @Test
    fun log_base_b_tickes_generate_expected_power_of_base_ticks() {
        val scale =  scaleLog()
        scale.range = listOf(.0, 1.0)
        scale.domain = listOf(0.1, 100.0)

        scale.base = kotlin.math.E
        scale.ticks() shouldBe arrayListOf(0.135335283237, 0.367879441171, 1.0, 2.718281828459, 7.389056098931, 20.085536923188, 54.598150033144)
    }

    @Test
    fun log_nice_nices_domain_extending_it_to_powers_of_ten() {
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
        scale(1.0) shouldBeClose .0
        scale(100.0) shouldBeClose 1.0
    }

    @Test
    fun log_test_ticks() {
        val scale = scaleLog()

        scale.domain = listOf(100.0, 1.0)

        scale.ticks() shouldBe listOf(100.0,
            90.0, 80.0, 70.0, 60.0, 50.0, 40.0, 30.0, 20.0, 10.0,
            9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0)

        scale(50) shouldBeClose 0.150515
    }

    @Test
    fun log_domain_can_take_negative_values() {
        val scale =  scaleLog()

        scale.domain = listOf(-100.0, -1.0)

        // TODO
//        scale.ticks() shouldBe listOf(-100.0,
//            -90.0, -80.0, -70.0, -60.0, -50.0, -40.0, -30.0, -20.0, -10.0,
//            -9.0, -8.0, -7.0, -6.0, -5.0, -4.0, -3.0, -2.0, -1.0)

        scale(-50) shouldBeClose 0.150515
    }

    @Test
    fun log_ticks_generates_the_expected_power_of_ten_for_ascending_ticks() {
        val scale =  scaleLog()

        scale.domain = listOf(1e-1, 1e1)
        scale.ticks() shouldBe listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

        scale.domain = listOf(1e-1, 1e0)
        scale.ticks() shouldBe listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)

        scale.domain = listOf(-1e0, -1e-1)
        scale.ticks() shouldBe listOf(-0.1, -0.2, -0.3, -0.4, -0.5, -0.6, -0.7, -0.8, -0.9, -1.0).reversed()
    }

    @Test
    fun log_ticks_generates_the_expected_power_of_ten_for_descending_domains() {
        val scale =  scaleLog()

        scale.domain = listOf(-1e-1, -1e1)
        scale.ticks() shouldBe listOf(-10.0, -9.0, -8.0, -7.0, -6.0, -5.0, -4.0, -3.0, -2.0, -1.0, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1).reversed()

        scale.domain = listOf(-1e-1, -1e0)
        scale.ticks() shouldBe listOf(-1.0, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1).reversed()

        scale.domain = listOf(1e0, 1e-1)
        scale.ticks() shouldBe listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
    }

    @Test
    fun log_ticks_generate_the_expected_power_of_ten_ticks_for_small_domains() {
        val scale =  scaleLog()

        scale.domain = listOf(1.0, 5.0)
        scale.ticks() shouldBe listOf(1.0, 2.0, 3.0, 4.0, 5.0)

        scale.domain = listOf(5.0, 1.0)
        scale.ticks() shouldBe listOf(5.0, 4.0, 3.0, 2.0, 1.0)
        scale.ticks(20) shouldBe listOf(5.0, 4.8, 4.6, 4.4, 4.2, 4.0, 3.8, 3.6, 3.4, 3.2, 3.0, 2.8, 2.6, 2.4, 2.2, 2.0, 1.8, 1.6, 1.4, 1.2, 1.0)

        scale.domain = listOf(-1.0, -5.0)
        scale.ticks() shouldBe listOf(-1.0, -2.0, -3.0, -4.0, -5.0)

        scale.domain = listOf(-5.0, -1.0)
        scale.ticks() shouldBe listOf(-5.0, -4.0, -3.0, -2.0, -1.0)

        scale.domain = listOf(286.9252014, 329.4978332)
        scale.ticks(1) shouldBe listOf(300.0)
        scale.ticks(2) shouldBe listOf(300.0)
        scale.ticks(3) shouldBe listOf(300.0, 320.0)
        scale.ticks(4) shouldBe listOf(290.0, 300.0, 310.0, 320.0)
        scale.ticks() shouldBe listOf(290.0, 295.0, 300.0, 305.0, 310.0, 315.0, 320.0, 325.0)
    }

    @Test
    fun log_ticks_generate_linear_ticks_when_the_domain_is_small() {
        val scale = scaleLog()

        scale.domain = listOf(41.0, 42.0)
        scale.ticks() shouldBe listOf(41.0, 41.1, 41.2, 41.3, 41.4, 41.5, 41.6, 41.7, 41.8, 41.9, 42.0)

        scale.domain = listOf(42.0, 41.0)
        scale.ticks() shouldBe listOf(42.0, 41.9, 41.8, 41.7, 41.6, 41.5, 41.4, 41.3, 41.2, 41.1, 41.0)

        scale.domain = listOf(1600.0, 1400.0)
        scale.ticks() shouldBe listOf(1600.0, 1580.0, 1560.0, 1540.0, 1520.0, 1500.0, 1480.0, 1460.0, 1440.0, 1420.0, 1400.0)
    }

    // TODO tickformats
}
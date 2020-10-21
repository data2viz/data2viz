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

class ScalePowerTests : TestBase() {

    @Test
    fun copy(){
        val scale = Scales.Continuous.pow {
            domain = listOf(10.0, 1.0)
            range = listOf(.0, 100.0)
        }

        val copy = scale.copy()
        copy.domain shouldBe listOf(10.0, 1.0)
        copy.range shouldBe listOf(.0, 100.0)
    }


    @Test
    fun power_sets_the_exponent_to_the_specified_value() {
        val scale = Scales.Continuous.sqrt()

        scale.domain = listOf(1.0, 2.0)
        scale.range = listOf(.0, 1.0)
        scale(1.0) shouldBeClose .0
        scale(1) shouldBeClose .0
        scale(1.5) shouldBeClose 0.5425821
        scale(2.0) shouldBeClose 1.0
        scale(2) shouldBeClose 1.0
        scale.exponent shouldBeClose .5

        scale.exponent = 2.0
        scale(1.0) shouldBeClose .0
        scale(1.5) shouldBeClose 0.41666667
        scale(2.0) shouldBeClose 1.0
        scale.exponent shouldBeClose 2.0

        scale.exponent = -1.0
        scale(1.0) shouldBeClose .0
        scale(1.5) shouldBeClose 0.6666667
        scale(2.0) shouldBeClose 1.0
        scale.exponent shouldBeClose -1.0
    }

    @Test
    fun power_sets_the_exponent_does_not_change_domain_or_range() {
        val scale = Scales.Continuous.pow()

        scale.domain = listOf(1.0, 2.0)
        scale.range = listOf(3.0, 4.0)
        scale.exponent = .5
        scale.domain shouldBe listOf(1.0, 2.0)
        scale.range shouldBe listOf(3.0, 4.0)

        scale.exponent = 2.0
        scale.domain shouldBe listOf(1.0, 2.0)
        scale.range shouldBe listOf(3.0, 4.0)
    }

    @Test
    fun power_round_does_round_number() {
        val scale = Scales.Continuous.powRound()

        scale.domain = listOf(.0, 1.0)
        scale.range = listOf(.0, 10.0)
        scale(0.59) shouldBeClose 6.0
    }

    @Test
    fun power_nice_count_extends_the_domain_to_match_the_desired_ticks() {
        val scale = Scales.Continuous.pow()
        scale.range = listOf(.0, 1.0)

        scale.domain = listOf(.0, .96)
        scale.nice(10)
        scale.domain shouldBe listOf(.0, 1.0)

        scale.domain = listOf(.0, 96.0)
        scale.nice(10)
        scale.domain shouldBe listOf(.0, 100.0)

        scale.domain = listOf(.96, .0)
        scale.nice(10)
        scale.domain shouldBe listOf(1.0, .0)

        scale.domain = listOf(96.0, .0)
        scale.nice(10)
        scale.domain shouldBe listOf(100.0, .0)

        scale.domain = listOf(.0, -.96)
        scale.nice(10)
        scale.domain shouldBe listOf(.0, -1.0)

        scale.domain = listOf(.0, -96.0)
        scale.nice(10)
        scale.domain shouldBe listOf(.0, -100.0)

        scale.domain = listOf(-.96, .0)
        scale.nice(10)
        scale.domain shouldBe listOf(-1.0, .0)

        scale.domain = listOf(-96.0, .0)
        scale.nice(10)
        scale.domain shouldBe listOf(-100.0, .0)

        scale.domain = listOf(-.1, 51.1)
        scale.nice(8)
        scale.domain shouldBe listOf(-10.0, 60.0)
    }
}
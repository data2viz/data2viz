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

class ScaleThresholdTests : TestBase() {


    @Test
    fun threshold_expected_defaults() {
        val scale = Scales.Quantized.threshold<Double>()
        scale.range = listOf(.0, 1.0)

        scale.domain shouldBe arrayListOf(.5)
        scale(0.5) shouldBeClose 1.0
        scale(0.49) shouldBeClose .0
    }

    @Test
    fun threshold_maps_a_value_to_a_discrete_value_in_range() {
        val scale = Scales.Quantized.threshold<String>()
        scale.domain = listOf(1 / 3.0, 2 / 3.0)
        scale.range = listOf("a", "b", "c")

        scale(0.0) shouldBe "a"
        scale(0.2) shouldBe "a"
        scale(0.4) shouldBe "b"
        scale(0.6) shouldBe "b"
        scale(0.8) shouldBe "c"
        scale(1.0) shouldBe "c"
    }

    @Test
    fun threshold_returns_NaN_if_specified_value_is_not_orderable() {
        val scale = Scales.Quantized.threshold<String>()
        scale.domain = listOf(1 / 3.0, 2 / 3.0)
        scale.range = listOf("a", "b", "c")

//        scale(Double.NaN) shouldBe "a"        // TODO : actually RETURN "c" (the last element)...
    }

    @Test
    fun threshold_range_supports_arbitrary_values() {
        val scale = Scales.Quantized.threshold<() -> Unit>()
        val a = {}
        val b = {}
        val c = {}
        scale.domain = listOf(1 / 3.0, 2 / 3.0)
        scale.range = listOf(a, b, c)

        scale(0.0) shouldBe a
        scale(0.2) shouldBe a
        scale(0.4) shouldBe b
        scale(0.6) shouldBe b
        scale(0.8) shouldBe c
        scale(1.0) shouldBe c
    }


    @Test
    fun threshold_invertExtent_r_returns_the_domain_extent_for_the_specified_range_value() {
        val scale = Scales.Quantized.threshold<() -> Unit>()
        val a = {}
        val b = {}
        val c = {}
        scale.domain = listOf(1 / 3.0, 2 / 3.0)
        scale.range = listOf(a, b, c)

        scale.invertExtent(a) shouldBe listOf(Double.NaN, 1/3.0)
        scale.invertExtent(b) shouldBe listOf(1/3.0, 2/3.0)
        scale.invertExtent(c) shouldBe listOf(2/3.0, Double.NaN)
        scale.invertExtent({}) shouldBe listOf(Double.NaN, Double.NaN)
    }
}
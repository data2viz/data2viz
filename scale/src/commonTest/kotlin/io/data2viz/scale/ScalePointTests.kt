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

import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test

class ScalePointTests : TestBase() {

    @Test
    fun point_has_expected_defaults() {
        val scale = Scales.Discrete.point<Int>()

        scale.domain shouldBe listOf()
        scale.range shouldBe intervalOf(.0, 1.0)
        scale.bandwidth shouldBeClose .0
        scale.step shouldBeClose 1.0
        scale.round shouldBe false
        scale.padding shouldBe 0.pct
        scale.align shouldBe 50.pct
    }

    @Test
    fun point_no_paddingInner_paddingOuter() {
//        val scale = scalePoint<Int>()

//        scale.pa
    }

    @Test
    fun pointScale_is_similar_to_bandscale_paddinginner_1() {
        val pointScale = Scales.Discrete.point<String>()
        pointScale.range = intervalOf(.0, 960.0)
        pointScale.domain = listOf("foo", "bar")

        val bandScale = Scales.Discrete.band<String>()
        bandScale.range = intervalOf(.0, 960.0)
        bandScale.domain = listOf("foo", "bar")
        bandScale.paddingInner = 100.pct

        bandScale.domain shouldBe pointScale.domain
        bandScale.range shouldBe pointScale.range
        bandScale.bandwidth shouldBeClose pointScale.bandwidth
        bandScale.step shouldBeClose pointScale.step
    }

    @Test
    fun point_padding_p_sets_band_outer_padding_to_p() {
        val pointScale = Scales.Discrete.point<String>()
        pointScale.range = intervalOf(.0, 960.0)
        pointScale.domain = listOf("foo", "bar")
        pointScale.padding = 50.pct

        val bandScale = Scales.Discrete.band<String>()
        bandScale.range = intervalOf(.0, 960.0)
        bandScale.domain = listOf("foo", "bar")
        bandScale.paddingInner = 100.pct
        bandScale.paddingOuter = 50.pct

        bandScale.domain shouldBe pointScale.domain
        bandScale.range shouldBe pointScale.range
        bandScale.bandwidth shouldBeClose pointScale.bandwidth
        bandScale.step shouldBeClose pointScale.step
    }

    // TODO align tests for padding & round
    // TODO : add more scale tests
}
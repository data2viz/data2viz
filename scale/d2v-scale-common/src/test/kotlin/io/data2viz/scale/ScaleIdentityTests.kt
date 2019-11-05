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

class ScaleIdentityTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun identity_x_return_y_equals_x() {
        val scale = Scales.Continuous.identity()

        scale(1.0) shouldBeClose 1.0
        scale(100.0) shouldBeClose 100.0
        scale(24.0) shouldBeClose 24.0
        scale(78.6355) shouldBeClose 78.6355
        scale(-100.0) shouldBeClose -100.0
        scale(-24.0) shouldBeClose -24.0
        scale(-78.6355) shouldBeClose -78.6355
    }

    @Test
    fun identity_invert_y_return_x_equals_y() {
        val scale = Scales.Continuous.identity()

        scale.invert(1.0) shouldBeClose 1.0
        scale.invert(100.0) shouldBeClose 100.0
        scale.invert(24.0) shouldBeClose 24.0
        scale.invert(78.6355) shouldBeClose 78.6355
        scale.invert(-100.0) shouldBeClose -100.0
        scale.invert(-24.0) shouldBeClose -24.0
        scale.invert(-78.6355) shouldBeClose -78.6355
    }

    @Test
    fun identity_domain_range_clamp_are_final_values_exceptions() {
        val scale = Scales.Continuous.identity()

        scale.range shouldBe arrayListOf(.0, 1.0)
        scale.domain shouldBe arrayListOf(.0, 1.0)
        scale.clamp shouldBe false
    }

    // TODO : add more scale tests
}
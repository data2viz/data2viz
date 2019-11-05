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

package io.data2viz.shape.curve

import io.data2viz.shape.curves
import kotlin.test.Test

class CurveLinearCloseTest : CurveTest(curves.linearClosed) {

    @Test
    fun line_curve_generates_one_point() {
        line(pt(0,1)) shouldBe "M0,1Z"
    }

    @Test
    fun line_curve_generates_two_points() {
        line(pt(0, 1 ), pt(2,3)) shouldBe "M0,1L2,3Z"
    }

    @Test
    fun line_curve_generates_three_pts() {
        line(pt(0, 1 ), pt(2,3), pt(4,5)) shouldBe "M0,1L2,3L4,5Z"
    }

}

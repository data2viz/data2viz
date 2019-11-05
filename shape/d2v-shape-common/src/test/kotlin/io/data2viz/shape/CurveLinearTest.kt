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

package io.data2viz.shape

import kotlin.test.Test
import kotlin.test.assertEquals

class CurveLinearTest : CurveTest(curves.linear) {

    @Test
    fun line_curve_generates_one_point() {
        assertEquals("M0,1Z".round(), line(pt(0, 1)).round())
    }

    @Test
    fun line_curve_generates_two_points() {
        assertEquals("M0,1L2,3".round(), line(pt(0, 1), pt(2, 3)).round())
    }

    @Test
    fun line_curve_generates_three_pts() {
        assertEquals("M0,1L2,3L4,5".round(), line(pt(0, 1), pt(2, 3), pt(4, 5)).round())
    }

}
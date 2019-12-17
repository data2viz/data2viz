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

package io.data2viz.voronoi

import io.data2viz.geom.Point
import io.data2viz.test.matchers.Matchers
import kotlin.test.Test


class SiteTest: Matchers {


    @Test
    fun sort_same_x_different_y() {
        val sorted = listOf(
                Site(pt(1, 1), 1),
                Site(pt(1, 3), 3),
                Site(pt(1, 2), 2)
        ).sorted()

        sorted[0].index shouldBe 3
        sorted[1].index shouldBe 2
        sorted[2].index shouldBe 1
    }

    @Test
    fun sort_same_y_different_x() {
        val sorted = listOf(
                Site(pt(1, 1), 1),
                Site(pt(3, 1), 3),
                Site(pt(2, 1), 2)
        ).sorted()

        sorted[0].index shouldBe 3
        sorted[1].index shouldBe 2
        sorted[2].index shouldBe 1
    }

    @Test
    fun sort_all_diff() {
        val sorted = listOf(
                Site(pt(3, 1), 1),
                Site(pt(1, 3), 3),
                Site(pt(2, 2), 2)
        ).sorted()

        sorted[0].index shouldBe 3
        sorted[1].index shouldBe 2
        sorted[2].index shouldBe 1

    }

}


fun pt(x: Number, y: Number) = Point(x.toDouble(), y.toDouble())

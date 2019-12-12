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

package io.data2viz.quadtree

import io.data2viz.math.EPSILON
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.math.sqrt
import kotlin.test.Test

class QuadtreeFindTests : TestBase() {

    @Test
    @JsName("quadtree_find_1")
    fun `quadtree find(x, y) returns the closest point to the given (x, y)`() {
        val dx = 17
        val dy = 17
        val quadtree = buildQuadtree()

        (0..(dx * dy)).forEach {
            quadtree.add(arrayOf(it % dx, if (dx == 0) 0 else it / dx))
        }

        quadtree.find(.1, .1) shouldBe arrayOf(0, 0)
        quadtree.find(7.1, 7.1) shouldBe arrayOf(7, 7)
        quadtree.find(.1, 15.9) shouldBe arrayOf(0, 16)
        quadtree.find(15.9, 15.9) shouldBe arrayOf(16, 16)
    }

    @Test
    @JsName("quadtree_find_2")
    fun `quadtree find(x, y, radius) returns the closest point within the search radius to the given (x, y)`() {
        val quadtree = buildQuadtree() {
            add(arrayOf(0, 0))
            add(arrayOf(100, 0))
            add(arrayOf(0, 100))
            add(arrayOf(100, 100))
        }

        quadtree.find(20.0, 20.0, Double.POSITIVE_INFINITY) shouldBe arrayOf(0, 0)
        quadtree.find(20.0, 20.0, 20.0 * sqrt(2.0) + EPSILON) shouldBe arrayOf(0, 0)
        quadtree.find(20.0, 20.0, 20.0 * sqrt(2.0) - EPSILON) shouldBe null
        quadtree.find(0.0, 20.0, 20.0 + EPSILON) shouldBe arrayOf(0, 0)
        quadtree.find(0.0, 20.0, 20.0 - EPSILON) shouldBe null
        quadtree.find(20.0, 0.0, 20.0 + EPSILON) shouldBe arrayOf(0, 0)
        quadtree.find(20.0, 0.0, 20.0 - EPSILON) shouldBe null
    }

    @Test
    @JsName("quadtree_find_3")
    fun `quadtree find(x, y) treats the radius as Infinity`() {
        val quadtree = buildQuadtree() {
            add(arrayOf(0, 0))
            add(arrayOf(100, 0))
            add(arrayOf(0, 100))
            add(arrayOf(100, 100))
        }

        quadtree.find(20.0, 20.0) shouldBe arrayOf(0, 0)
    }
}

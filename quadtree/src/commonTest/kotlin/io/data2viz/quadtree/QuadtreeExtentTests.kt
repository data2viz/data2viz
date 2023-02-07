/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

import io.data2viz.geom.Extent
import io.data2viz.test.TestBase
import kotlin.js.JsName
import kotlin.test.Test

class QuadtreeExtentTests : TestBase() {

    @Test
    @JsName("quadtree_extent_1")
    fun `quadtree extent extends the extent`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, 1.0, 2.0, 6.0)
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, 1.0, 8.0, 9.0)
    }

    @Test
    @JsName("quadtree_extent_2")
    fun `quadtree extent can be inferred by quadtree cover`() {
        val quadtree = buildQuadtree()

        quadtree.cover(.0, .0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree.cover(2.0, 4.0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_extent_3")
    fun `quadtree extent can be inferred by quadtree add`() {
        val quadtree = buildQuadtree()

        quadtree.add(arrayOf(0, 0))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree.add(arrayOf(2, 4))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_extent_4")
    fun `quadtree extent ignores invalid extents`() {
        var quadtree = buildQuadtree() {
            extent = Extent(1.0, Double.NaN, Double.NaN, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN)

        quadtree = buildQuadtree() {
            extent = Extent(Double.NaN, 1.0, .0, Double.NaN)
        }
        quadtree.extent.toArray() shouldBe arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN)

        quadtree = buildQuadtree() {
            extent = Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
        }
        quadtree.extent.toArray() shouldBe arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
    }

    @Test
    @JsName("quadtree_extent_5")
    fun `quadtree extent flips inverted extents`() {
        val quadtree = buildQuadtree() {
            extent = Extent(1.0, 1.0, .0, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 2.0, 2.0)
    }

    @Test
    @JsName("quadtree_extent_6")
    fun `quadtree extent tolerates partially-valid extents`() {
        var quadtree = buildQuadtree() {
            extent = Extent(Double.NaN, .0, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)

        quadtree = buildQuadtree() {
            extent = Extent(.0, Double.NaN, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)

        quadtree = buildQuadtree() {
            extent = Extent(.0, .0, Double.NaN, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 1.0, Double.NaN)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)
    }

    @Test
    @JsName("quadtree_extent_7")
    fun `quadtree extent allows trivial extents`() {
        var quadtree = buildQuadtree() {
            extent = Extent(.0, .0, .0, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree = buildQuadtree() {
            extent = Extent(1.0, 1.0, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)
    }
}

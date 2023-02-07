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

class QuadtreeTests : TestBase() {

    @Test
    @JsName("quadtree_test_1")
    fun `quadtree() creates an empty quadtree`() {
        val quadtree = buildQuadtree()

        quadtree.visit({ _, _, _, _, _ ->
            throw AssertionError("Should not visit an empty tree.")
        })
        quadtree.size() shouldBe 0
        quadtree.extent.toArray() shouldBe Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN).toArray()
        quadtree.root shouldBe null
        quadtree.data() shouldBe listOf()
    }

    @Test
    @JsName("quadtree_test_2")
    fun `quadtree(nodes) is equivalent to quadtree() addAll(nodes)`() {
        val quadtree = quadtree(xAccessor, yAccessor, listOf(arrayOf(0, 0), arrayOf(1, 1)))

        val root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(1, 1)
    }
}

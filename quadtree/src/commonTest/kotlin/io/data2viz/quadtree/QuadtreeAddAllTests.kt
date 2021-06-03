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

import io.data2viz.geom.Point
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeAddAllTests : TestBase() {

    @Test
    @JsName("quadtree_addall_1")
    fun `quadtree addAll(data) ignores points with NaN coordinates`() {
        val quadtree = quadtree<Point>({ point -> point.x }, { point -> point.y })

        quadtree.addAll(listOf(Point(Double.NaN, .0), Point(.0, Double.NaN)))
        quadtree.root shouldBe null

        quadtree.addAll(listOf(Point(.0, .0), Point(1.0, 1.0)))
        val root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe Point(.0, .0)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe Point(1.0, 1.0)
    }

    @Test
    @JsName("quadtree_addall_2")
    fun `quadtree addAll(data) correctly handles the empty array`() {
        val quadtree = buildQuadtree()

        quadtree.addAll(listOf())
        quadtree.root shouldBe null

        quadtree.addAll(listOf(arrayOf(0, 0), arrayOf(1, 1)))
        val root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(1, 1)
    }

    @Test
    @JsName("quadtree_addall_3")
    fun `quadtree addAll(data) computes the extent of the data before adding`() {
        val quadtree = buildQuadtree()
        quadtree.addAll(listOf(arrayOf(4, 4), arrayOf(0, 0), arrayOf(10, 10)))

        val root = quadtree.root as InternalNode
        val node = root.NE_0 as InternalNode
        (node.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        node.NW_1 shouldBe null
        node.SE_2 shouldBe null
        (node.SW_3 as LeafNode).data shouldBe arrayOf(4, 4)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)
    }
}

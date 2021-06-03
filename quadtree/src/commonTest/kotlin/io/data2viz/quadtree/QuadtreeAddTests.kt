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
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeAddTests : TestBase() {

    @Test
    @JsName("quadtree_add_1")
    fun `quadtree add(datum) creates a new point and adds it to the quadtree`() {
        val quadtree = buildQuadtree()

        quadtree.add(arrayOf(0, 0))
        (quadtree.root as LeafNode).data shouldBe arrayOf(0, 0)

        quadtree.add(arrayOf(10, 10))
        var root = (quadtree.root as InternalNode)
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)

        quadtree.add(arrayOf(10, 0))
        root = (quadtree.root as InternalNode)
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(10, 0)
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)

        quadtree.add(arrayOf(0, 10))
        root = (quadtree.root as InternalNode)
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(10, 0)
        (root.SE_2 as LeafNode).data shouldBe arrayOf(0, 10)
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)

        quadtree.add(arrayOf(4, 4))
        root = (quadtree.root as InternalNode)
        val next = root.NE_0 as InternalNode
        (next.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        next.NW_1 shouldBe null
        next.SE_2 shouldBe null
        (next.SW_3 as LeafNode).data shouldBe arrayOf(4, 4)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(10, 0)
        (root.SE_2 as LeafNode).data shouldBe arrayOf(0, 10)
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)
    }

    @Test
    @JsName("quadtree_add_2")
    fun `quadtree add(datum) handles points being on the perimeter of the quadtree bounds`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 1.0, 1.0)
        }

        quadtree.add(arrayOf(0, 0))
        (quadtree.root as LeafNode).data shouldBe arrayOf(0, 0)

        quadtree.add(arrayOf(10, 10))
        var root = (quadtree.root as InternalNode)
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)

        quadtree.add(arrayOf(10, 0))
        root = (quadtree.root as InternalNode)
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(10, 0)
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)

        quadtree.add(arrayOf(0, 10))
        root = (quadtree.root as InternalNode)
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(10, 0)
        (root.SE_2 as LeafNode).data shouldBe arrayOf(0, 10)
        (root.SW_3 as LeafNode).data shouldBe arrayOf(10, 10)
    }

    @Test
    @JsName("quadtree_add_3")
    fun `quadtree add(datum) handles points being to the top of the quadtree bounds`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(1, -1))
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, -2.0, 4.0, 2.0)
    }

    @Test
    @JsName("quadtree_add_4")
    fun `quadtree add(datum) handles points being to the right of the quadtree bounds`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(3, 1))
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_add_5")
    fun `quadtree add(datum) handles points being to the bottom of the quadtree bounds`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(1, 3))
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_add_6")
    fun `quadtree add(datum) handles points being to the left of the quadtree bounds`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(-1, 1))
        }

        quadtree.extent.toArray() shouldBe arrayOf(-2.0, .0, 2.0, 4.0)
    }

    @Test
    @JsName("quadtree_add_7")
    fun `quadtree add(datum) handles coincident points by creating a linked list`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 1.0, 1.0)
        }

        quadtree.add(arrayOf(0, 0))
        (quadtree.root as LeafNode).data shouldBe arrayOf(0, 0)

        quadtree.add(arrayOf(1, 0))
        var root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(1, 0)
        root.SE_2 shouldBe null
        root.SW_3 shouldBe null

        quadtree.add(arrayOf(0, 1))
        root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(1, 0)
        (root.SE_2 as LeafNode).data shouldBe arrayOf(0, 1)
        root.SW_3 shouldBe null

        quadtree.add(arrayOf(0, 1))
        root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        (root.NW_1 as LeafNode).data shouldBe arrayOf(1, 0)
        (root.SE_2 as LeafNode).data shouldBe arrayOf(0, 1)
        ((root.SE_2 as LeafNode).next as LeafNode).data shouldBe arrayOf(0, 1)
        root.SW_3 shouldBe null
    }

    @Test
    @JsName("quadtree_add_8")
    fun `quadtree add(datum) implicitly defines trivial bounds for the first point`() {
        val quadtree = buildQuadtree() {
            add(arrayOf(1, 2))
        }

        quadtree.extent.toArray() shouldBe arrayOf(1.0, 2.0, 2.0, 3.0)
        (quadtree.root as LeafNode).data shouldBe arrayOf(1, 2)
    }
}

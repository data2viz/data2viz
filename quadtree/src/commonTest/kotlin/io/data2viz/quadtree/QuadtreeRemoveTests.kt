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

class QuadtreeRemoveTests : TestBase() {

    @Test
    @JsName("quadtree_remove_1")
    fun `quadtree remove(datum) removes a point and returns the quadtree`() {
        val p0 = arrayOf(1, 1)
        val quadtree = buildQuadtree() {
            add(p0)
        }

        (quadtree.root as LeafNode).data shouldBe p0
        quadtree.remove(p0)
        quadtree.root shouldBe null
    }

    @Test
    @JsName("quadtree_remove_2")
    fun `quadtree remove(datum) removes the only point in the quadtree`() {
        val p0 = arrayOf(1, 1)
        val quadtree = buildQuadtree() {
            add(p0)
        }

        quadtree.remove(p0)
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)
        quadtree.root shouldBe null
        p0 shouldBe arrayOf(1, 1)
    }

    @Test
    @JsName("quadtree_remove_3")
    fun `quadtree remove(datum) removes a first coincident point at the root in the quadtree`() {
        val p0 = arrayOf(1, 1)
        val p1 = arrayOf(1, 1)
        val quadtree = buildQuadtree() {
            add(p0)
            add(p1)
        }

        quadtree.remove(p0)
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)
        (quadtree.root as LeafNode).data shouldBe p1
        p0 shouldBe arrayOf(1, 1)
        p1 shouldBe arrayOf(1, 1)
    }

    @Test
    @JsName("quadtree_remove_4")
    fun `quadtree remove(datum) removes another coincident point at the root in the quadtree`() {
        val p0 = arrayOf(1, 1)
        val p1 = arrayOf(1, 1)
        val quadtree = buildQuadtree() {
            add(p0)
            add(p1)
        }

        quadtree.remove(p1)
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)
        (quadtree.root as LeafNode).data shouldBe p0
        p0 shouldBe arrayOf(1, 1)
        p1 shouldBe arrayOf(1, 1)
    }

    @Test
    @JsName("quadtree_remove_5")
    fun `quadtree remove(datum) removes a non-root point in the quadtree`() {
        val p0 = arrayOf(0, 0)
        val p1 = arrayOf(1, 1)
        val quadtree = buildQuadtree() {
            add(p0)
            add(p1)
        }

        quadtree.remove(p0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)
        (quadtree.root as LeafNode).data shouldBe p1
        p0 shouldBe arrayOf(0, 0)
        p1 shouldBe arrayOf(1, 1)
    }

    @Test
    @JsName("quadtree_remove_6")
    fun `quadtree remove(datum) removes another non-root point in the quadtree`() {
        val p0 = arrayOf(0, 0)
        val p1 = arrayOf(1, 1)
        val quadtree = buildQuadtree() {
            add(p0)
            add(p1)
        }

        quadtree.remove(p1)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)
        (quadtree.root as LeafNode).data shouldBe p0
        p0 shouldBe arrayOf(0, 0)
        p1 shouldBe arrayOf(1, 1)
    }

    @Test
    @JsName("quadtree_remove_7")
    fun `quadtree remove(datum) ignores a point not in the quadtree`() {
        val p0 = arrayOf(0, 0)
        val p1 = arrayOf(1, 1)
        val quadtree0 = buildQuadtree() {
            add(p0)
        }
        val quadtree1 = buildQuadtree() {
            add(p0)
        }

        quadtree0.remove(p1)
        quadtree0.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)
        (quadtree0.root as LeafNode).data shouldBe p0

        quadtree1.size() shouldBe 1
    }

    @Test
    @JsName("quadtree_remove_8")
    fun `quadtree remove(datum) ignores a coincident point not in the quadtree`() {
        val p0 = arrayOf(0, 0)
        val p1 = arrayOf(0, 0)
        val quadtree0 = buildQuadtree() {
            add(p0)
        }
        val quadtree1 = buildQuadtree() {
            add(p0)
        }

        quadtree0.remove(p1)
        quadtree0.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)
        (quadtree0.root as LeafNode).data shouldBe p0

        quadtree1.size() shouldBe 1
    }

    @Test
    @JsName("quadtree_remove_9")
    fun `quadtree remove(datum) another point in the quadtree`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 959.0, 959.0)
            addAll(
                listOf(
                    arrayOf(630, 438),
                    arrayOf(715, 464),
                    arrayOf(523, 519),
                    arrayOf(646, 318),
                    arrayOf(434, 620),
                    arrayOf(570, 489),
                    arrayOf(520, 345),
                    arrayOf(459, 443),
                    arrayOf(346, 405),
                    arrayOf(529, 444)
                )
            )
        }

        val find = quadtree.find(546.0, 440.0)!!
        quadtree.remove(find)

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1024.0, 1024.0)

        val root = quadtree.root as InternalNode
        var node = root.NE_0 as InternalNode
        node.NE_0 shouldBe null
        node.NW_1 shouldBe null
        node.SE_2 shouldBe null

        node = node.SW_3 as InternalNode
        node.NE_0 shouldBe null
        node.NW_1 shouldBe null

        var leaf = node.SE_2 as LeafNode
        leaf.data shouldBe arrayOf(346, 405)
        leaf = node.SW_3 as LeafNode
        leaf.data shouldBe arrayOf(459, 443)

        node = root.NW_1 as InternalNode
        node.NE_0 shouldBe null
        node.NW_1 shouldBe null
        node.SW_3 shouldBe null

        node = node.SE_2 as InternalNode
        leaf = node.NE_0 as LeafNode
        leaf.data shouldBe arrayOf(520, 345)
        leaf = node.NW_1 as LeafNode
        leaf.data shouldBe arrayOf(646, 318)
        leaf = node.SW_3 as LeafNode
        leaf.data shouldBe arrayOf(715, 464)

        node = node.SE_2 as InternalNode
        node.NE_0 shouldBe null
        node.SW_3 shouldBe null
        leaf = node.NW_1 as LeafNode
        leaf.data shouldBe arrayOf(630, 438)
        leaf = node.SE_2 as LeafNode
        leaf.data shouldBe arrayOf(570, 489)

        leaf = root.SE_2 as LeafNode
        leaf.data shouldBe arrayOf(434, 620)
        leaf = root.SW_3 as LeafNode
        leaf.data shouldBe arrayOf(523, 519)
    }
}

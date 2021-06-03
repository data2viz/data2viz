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

class QuadtreeAccessorsTests : TestBase() {

    @Test
    @JsName("quadtree_accessor_1")
    fun `quadtree sets the accessors used by quadtree add`() {
        val quadtree = Quadtree<Point>({ point -> point.x }, { point -> point.y })
        quadtree.add(Point(1.0, 2.0))

        quadtree.extent.toArray() shouldBe arrayOf(1.0, 2.0, 2.0, 3.0)
        (quadtree.root as LeafNode).data shouldBe Point(1.0, 2.0)
    }

    @Test
    @JsName("quadtree_accessor_2")
    fun `quadtree sets the accessors used by quadtree addAll`() {
        val quadtree = Quadtree<Point>({ point -> point.x }, { point -> point.y })
        quadtree.addAll(listOf(Point(1.0, 2.0)))

        quadtree.extent.toArray() shouldBe arrayOf(1.0, 2.0, 2.0, 3.0)
        (quadtree.root as LeafNode).data shouldBe Point(1.0, 2.0)
    }

    @Test
    @JsName("quadtree_accessor_3")
    fun `quadtree sets the accessors used by quadtree remove`() {
        val p0 = Point(.0, 1.0)
        val p1 = Point(1.0, 2.0)
        val quadtree = Quadtree<Point>({ point -> point.x }, { point -> point.y })

        quadtree.add(p0)
        (quadtree.root as LeafNode).data shouldBe Point(.0, 1.0)

        quadtree.add(p1)
        ((quadtree.root as InternalNode).NE_0 as LeafNode).data shouldBe p0
        (quadtree.root as InternalNode).NW_1 shouldBe null
        (quadtree.root as InternalNode).SE_2 shouldBe null
        ((quadtree.root as InternalNode).SW_3 as LeafNode).data shouldBe p1

        quadtree.remove(p1)
        (quadtree.root as LeafNode).data shouldBe Point(.0, 1.0)

        quadtree.remove(p0)
        quadtree.root shouldBe null
    }
}

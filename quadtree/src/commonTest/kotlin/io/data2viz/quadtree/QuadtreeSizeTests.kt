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

import io.data2viz.test.TestBase
import kotlin.js.JsName
import kotlin.test.Test

class QuadtreeSizeTests : TestBase() {

    @Test
    @JsName("quadtree_size_1")
    fun `quadtree size returns the number of points in the quadtree`() {
        val quadtree = buildQuadtree()
        quadtree.size() shouldBe 0

        quadtree.add(arrayOf(0, 0))
        quadtree.add(arrayOf(1, 2))
        quadtree.size() shouldBe 2
    }


    @Test
    @JsName("quadtree_size_2")
    fun `quadtree size correctly counts coincident nodes`() {
        val quadtree = buildQuadtree()
        quadtree.add(arrayOf(0, 0))
        quadtree.add(arrayOf(0, 0))

        quadtree.size() shouldBe 2
    }
}

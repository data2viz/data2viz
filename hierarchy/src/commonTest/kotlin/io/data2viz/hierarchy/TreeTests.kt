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

package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class TreeTests : TestBase() {

    // DO NOT CHANGE VALUES
    val width = 500.0
    val height = 400.0

    data class Hierarchical(
        val value: Int,
        val x: Double = .0,
        val y: Double = .0,
        val subElements: List<Hierarchical>? = null
    )

    val testTreeLight = Hierarchical(
        1, .0, .0, listOf(
            Hierarchical(11, .0, .0),
            Hierarchical(12, .0, .0)
        )
    )

    val testTreeMid =
        Hierarchical(
            1, 277.777777, .0, subElements = listOf(
                Hierarchical(
                    11, 138.888888, 133.333333, subElements = listOf(
                        Hierarchical(111, 55.555555, 266.666666),
                        Hierarchical(112, 111.111111, 266.666666),
                        Hierarchical(113, 166.666666, 266.666666),
                        Hierarchical(
                            114, 222.222222, 266.666666, subElements = listOf(
                                Hierarchical(1141, 194.444444, 400.0),
                                Hierarchical(1142, 250.0, 400.0)
                            )
                        )
                    )
                ),
                Hierarchical(
                    12, 416.666666, 133.333333, subElements = listOf(
                        Hierarchical(
                            121, .0, 250.0, subElements = listOf(
                                Hierarchical(1211, 361.111111, 400.0),
                                Hierarchical(1212, 416.666666, 400.0)
                            )
                        ),
                        Hierarchical(122, 444.444444, 266.666666)
                    )
                )
            )
        )

    @Test
    fun buildTreeLight() {
        val hierarchy = hierarchy(testTreeLight, { it.subElements })
        val treeLayout = TreeLayout<Hierarchical>()
        treeLayout.size(width, height)

        treeLayout.tree(hierarchy)
        val tree  = treeLayout.tree(hierarchy)

        tree.children.size shouldBe 2
        tree.depth shouldBe 0
        tree.height shouldBe 1
    }

    @Test
    fun buildTreeWithExtent() {
        val hierarchy = hierarchy(testTreeMid, { it.subElements })
        val treeLayout = TreeLayout<Hierarchical>()
        treeLayout.size(width, height)

        treeLayout.tree(hierarchy)
        val tree  = treeLayout.tree(hierarchy)

        tree.children.size shouldBe 2
        tree.depth shouldBe 0
        tree.height shouldBe 3
        tree.each { treeNode ->
            treeNode.x shouldBeClose treeNode.data!!.x
            treeNode.y shouldBeClose treeNode.data!!.y
        }
    }

    @Test
    fun buildTreeWithSizeNode() {
        // TODO
        true shouldBe true
    }
}

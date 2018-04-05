package io.data2viz.quadtree

import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeDataTests : TestBase() {

    @Test
    @JsName("quadtree_data_1")
    fun `quadtree data() returns an array of data in the quadtree`() {
        val quadtree = buildQuadtree()
        quadtree.data() shouldBe listOf()

        quadtree.add(arrayOf(0, 0))
        quadtree.add(arrayOf(1, 2))
        quadtree.data()[0] shouldBe arrayOf(0, 0)
        quadtree.data()[1] shouldBe arrayOf(1, 2)
    }

    @Test
    @JsName("quadtree_data_2")
    fun `quadtree data() correctly handles coincident nodes`() {
        val quadtree = buildQuadtree()
        quadtree.addAll(listOf(arrayOf(0, 0), arrayOf(0, 0)))
        quadtree.data()[0] shouldBe arrayOf(0, 0)
        quadtree.data()[1] shouldBe arrayOf(0, 0)
    }
}

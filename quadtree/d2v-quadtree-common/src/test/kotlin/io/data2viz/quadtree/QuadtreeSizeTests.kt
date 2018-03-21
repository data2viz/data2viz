package io.data2viz.quadtree

import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeSizeTests : TestBase() {

    @Test
    @JsName("quadtree_size_1")
    fun `quadtree size() returns the number of points in the quadtree LEGACY`() {
        val quadtree = buildQuadtree()
        quadtree.size() shouldBe 0

        quadtree.add(arrayOf(0, 0))
        quadtree.add(arrayOf(1, 2))
        quadtree.size() shouldBe 2
    }


    @Test
    @JsName("quadtree_size_2")
    fun `quadtree size() correctly counts coincident nodes LEGACY`() {
        val quadtree = buildQuadtree()
        quadtree.add(arrayOf(0, 0))
        quadtree.add(arrayOf(0, 0))

        quadtree.size() shouldBe 2
    }
}

package io.data2viz.quadtree

import io.data2viz.core.Extent
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeTests : TestBase() {

    @Test
    @JsName("quadtree_test_1")
    fun `quadtree() creates an empty quadtree LEGACY`() {
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
    fun `quadtree(nodes) is equivalent to quadtree() addAll(nodes) LEGACY`() {
        val quadtree = quadtree(xAccessor, yAccessor, listOf(arrayOf(0, 0), arrayOf(1, 1)))

        val root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode).data shouldBe arrayOf(0, 0)
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode).data shouldBe arrayOf(1, 1)
    }
}

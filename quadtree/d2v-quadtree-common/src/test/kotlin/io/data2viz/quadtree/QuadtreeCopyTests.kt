package io.data2viz.quadtree

import io.data2viz.core.Extent
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeCopyTests : TestBase() {

    @Test
    @JsName("quadtree_copy_1")
    fun `quadtree copy() returns a copy of this quadtree`() {
        val quadtree = buildQuadtree {
            add(arrayOf(0, 0))
            add(arrayOf(1, 0))
            add(arrayOf(0, 1))
            add(arrayOf(1, 1))
        }
        val copy = quadtree.copy()

        quadtree.extent.toArray() shouldBe copy.extent.toArray()
        quadtree.size() shouldBe copy.size()
        quadtree.root shouldBe copy.root
        quadtree.x shouldBe copy.x
        quadtree.y shouldBe copy.y
        quadtree.data() shouldBe copy.data()
    }

    @Test
    @JsName("quadtree_copy_2")
    fun `quadtree copy() isolates changes to the extent`() {
        val quadtree = buildQuadtree {
            extent = Extent(.0, .0, 1.0, 1.0)
        }
        val copy = quadtree.copy()

        quadtree.add(arrayOf(2, 2))
        copy.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        copy.add(arrayOf(-1, -1))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 2.0, 2.0)
    }

    @Test
    @JsName("quadtree_copy_3")
    fun `quadtree copy() isolates changes to the root when a leaf`() {
        val quadtree = buildQuadtree {
            extent = Extent(.0, .0, 1.0, 1.0)
        }
        var copy = quadtree.copy()
        val p0 = arrayOf(2, 2)

        quadtree.add(p0)
        copy.root shouldBe null

        copy = quadtree.copy()
        (quadtree.root as LeafNode).data shouldBe p0
        (copy.root as LeafNode).data shouldBe p0

        quadtree.remove(p0)
        quadtree.root shouldBe null
        (copy.root as LeafNode).data shouldBe p0
    }

    @Test
    @JsName("quadtree_copy_4")
    fun `quadtree copy() isolates changes to the root when not a leaf`() {
        val p0 = arrayOf(1, 1)
        val p1 = arrayOf(2, 2)
        val p2 = arrayOf(3, 3)
        val quadtree = buildQuadtree {
            extent = Extent(.0, .0, 4.0, 4.0)
            add(p0)
            add(p1)
        }
        var copy = quadtree.copy()

        quadtree.add(p2)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
        var root = quadtree.root as InternalNode
        (root.NE_0 as LeafNode<Array<Int>>).data shouldBe p0
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        var node = (root.SW_3 as InternalNode<Array<Int>>)
        (node.NE_0 as LeafNode<Array<Int>>).data shouldBe p1
        node.NW_1 shouldBe null
        node.SE_2 shouldBe null
        (node.SW_3 as LeafNode<Array<Int>>).data shouldBe p2

        copy.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
        root = copy.root as InternalNode
        (root.NE_0 as LeafNode<Array<Int>>).data shouldBe p0
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        (root.SW_3 as LeafNode<Array<Int>>).data shouldBe p1

        copy = quadtree.copy()
        quadtree.remove(p2)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
        root = copy.root as InternalNode
        (root.NE_0 as LeafNode<Array<Int>>).data shouldBe p0
        root.NW_1 shouldBe null
        root.SE_2 shouldBe null
        node = (root.SW_3 as InternalNode<Array<Int>>)
        (node.NE_0 as LeafNode<Array<Int>>).data shouldBe p1
        node.NW_1 shouldBe null
        node.SE_2 shouldBe null
        (node.SW_3 as LeafNode<Array<Int>>).data shouldBe p2
    }
}

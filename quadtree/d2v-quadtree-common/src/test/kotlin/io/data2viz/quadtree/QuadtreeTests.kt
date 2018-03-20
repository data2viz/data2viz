package io.data2viz.quadtree

import io.data2viz.core.Extent
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test


class PathTests : TestBase() {

    private fun Extent.toArray() = arrayOf(this.x0, this.y0, this.x1, this.y1)
    private fun InternalNode<Array<Int>>.toData(): Array<Any?> {
        val NE =
            if (this.NE_0 == null) null else if (this.NE_0 is LeafNode) (this.NE_0!! as LeafNode).data else (this.NE_0 as InternalNode).toData()
        val NW =
            if (this.NW_1 == null) null else if (this.NW_1 is LeafNode) (this.NW_1!! as LeafNode).data else (this.NW_1 as InternalNode).toData()
        val SE =
            if (this.SE_2 == null) null else if (this.SE_2 is LeafNode) (this.SE_2!! as LeafNode).data else (this.SE_2 as InternalNode).toData()
        val SW =
            if (this.SW_3 == null) null else if (this.SW_3 is LeafNode) (this.SW_3!! as LeafNode).data else (this.SW_3 as InternalNode).toData()
        return arrayOf(NE, NW, SE, SW)
    }

    private fun buildQuadtree() = Quadtree<Array<Int>>({ it[0].toDouble() }, { it[1].toDouble() })
    private fun buildQuadtree(init: Quadtree<Array<Int>>.() -> Unit): Quadtree<Array<Int>> = buildQuadtree().apply(init)

    @Test
    @JsName("quadtree_test_1")
    fun `d3 quadtree() creates an empty quadtree LEGACY`() {
        val quadtree = buildQuadtree()

        //test.equal(q.visit(function(node, x0, y0, x1, y1) { throw new Error; }), q);
        quadtree.size() shouldBe 0
        quadtree.extent.toArray() shouldBe Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN).toArray()
        quadtree.root shouldBe null
        //test.deepEqual(q.data(), []);
    }

    /*
tape("d3.quadtree(nodes) is equivalent to d3.quadtree().addAll(nodes)", function(test) {
  var q = d3_quadtree.quadtree([[0, 0], [1, 1]]);
  test.ok(q instanceof d3_quadtree.quadtree);
  test.deepEqual(q.root(), [{data: [0, 0]},,, {data: [1, 1]}]);
  test.end();
});

tape("d3.quadtree(nodes, x, y) is equivalent to d3.quadtree().x(x).y(y).addAll(nodes)", function(test) {
  var q = d3_quadtree.quadtree([{x: 0, y: 0}, {x: 1, y: 1}], function(d) { return d.x; }, function(d) { return d.y; });
  test.ok(q instanceof d3_quadtree.quadtree);
  test.deepEqual(q.root(), [{data: {x: 0, y: 0}},,, {data: {x: 1, y: 1}}]);
  test.end();
});

     */

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

    @Test
    @JsName("quadtree_cover_1")
    fun `quadtree cover(x, y) sets a trivial extent if the extent was undefined LEGACY`() {
        val quadtree = buildQuadtree()
        quadtree.cover(1.0, 2.0)

        quadtree.extent.toArray() shouldBe arrayOf(1.0, 2.0, 2.0, 3.0)
    }

    @Test
    @JsName("quadtree_cover_2")
    fun `quadtree cover(x, y) sets a non-trivial squarified and centered extent if the extent was trivial LEGACY`() {
        val quadtree = buildQuadtree()
        quadtree.cover(.0, .0)
        quadtree.cover(1.0, 2.0)

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 2.0, 2.0)
    }

    @Test
    @JsName("quadtree_cover_3")
    fun `quadtree cover(x, y) ignores invalid points LEGACY`() {
        val quadtree = buildQuadtree()
        quadtree.cover(.0, .0)
        quadtree.cover(Double.NaN, 12.0)
        quadtree.cover(12.0, Double.NaN)

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)
    }

    @Test
    @JsName("quadtree_cover_5")
    fun `quadtree cover(x, y) repeatedly doubles the existing extent if the extent was non-trivial LEGACY`() {
        var quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(-1.0, -1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(-2.0, -2.0, 2.0, 2.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(1.0, -1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, -2.0, 4.0, 2.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(3.0, -1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, -2.0, 4.0, 2.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(3.0, 3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(1.0, 3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(-1.0, 3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(-2.0, .0, 2.0, 4.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(-1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(-2.0, .0, 2.0, 4.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(-3.0, -3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(-6.0, -6.0, 2.0, 2.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(3.0, -3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, -6.0, 8.0, 2.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(5.0, -3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, -6.0, 8.0, 2.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(5.0, 3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 8.0, 8.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(5.0, 5.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 8.0, 8.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(3.0, 5.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 8.0, 8.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(-3.0, 5.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(-6.0, .0, 2.0, 8.0)

        quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
            cover(-3.0, 3.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(-6.0, .0, 2.0, 8.0)
    }

    @Test
    @JsName("quadtree_cover_6")
    @Suppress("UNCHECKED_CAST")
    fun `quadtree cover(x, y) repeatedly wraps the root node if it has children LEGACY`() {
        var quadtree = buildQuadtree {
            add(arrayOf(0, 0))
            add(arrayOf(2, 2))
        }
        var root = (quadtree.root as InternalNode)
        var toData = root.toData()
        (toData[0] as Array<Int>) shouldBe arrayOf(0, 0)
        toData[1] shouldBe null
        toData[2] shouldBe null
        (toData[3] as Array<Int>) shouldBe arrayOf(2, 2)

        quadtree = buildQuadtree {
            add(arrayOf(0, 0))
            add(arrayOf(2, 2))
            cover(3.0, 3.0)
        }
        root = (quadtree.root as InternalNode)
        toData = root.toData()
        ((toData[0] as Array<Any?>)[0] as Array<Int>) shouldBe arrayOf(0, 0)
        (toData[0] as Array<Any?>)[1] shouldBe null
        (toData[0] as Array<Any?>)[1] shouldBe null
        ((toData[0] as Array<Any?>)[3] as Array<Int>) shouldBe arrayOf(2, 2)
        toData[1] shouldBe null
        toData[2] shouldBe null
        toData[3] shouldBe null

        quadtree = buildQuadtree {
            add(arrayOf(0, 0))
            add(arrayOf(2, 2))
            cover(-3.0, 5.0)
        }
        root = (quadtree.root as InternalNode)
        toData = root.toData()
        toData[0] shouldBe null
        (toData[1] as Array<Any?>)[0] shouldBe null
        (((toData[1] as Array<Any?>)[1] as Array<Any?>)[0] as Array<Int>) shouldBe arrayOf(0, 0)
        ((toData[1] as Array<Any?>)[1] as Array<Any?>)[1] shouldBe null
        ((toData[1] as Array<Any?>)[1] as Array<Any?>)[2] shouldBe null
        (((toData[1] as Array<Any?>)[1] as Array<Any?>)[3] as Array<Int>) shouldBe arrayOf(2, 2)
        (toData[1] as Array<Any?>)[2] shouldBe null
        (toData[1] as Array<Any?>)[3] shouldBe null
        toData[2] shouldBe null
        toData[3] shouldBe null
    }

    /*
    // TODO
tape("quadtree.cover(x, y) repeatedly wraps the root node if it has children", function(test) {
  test.deepEqual(q.copy().cover(-1, 3).root(), [,[{data: [0, 0]},,, {data: [2, 2]}],, ]);
  test.deepEqual(q.copy().cover(3, -1).root(), [,, [{data: [0, 0]},,, {data: [2, 2]}], ]);
  test.deepEqual(q.copy().cover(-1, -1).root(), [,,, [{data: [0, 0]},,, {data: [2, 2]}]]);
  test.deepEqual(q.copy().cover(5, 5).root(), [[[{data: [0, 0]},,, {data: [2, 2]}],,, ],,, ]);
  test.deepEqual(q.copy().cover(5, -3).root(), [,, [,, [{data: [0, 0]},,, {data: [2, 2]}], ], ]);
  test.deepEqual(q.copy().cover(-3, -3).root(), [,,, [,,, [{data: [0, 0]},,, {data: [2, 2]}]]]);
  test.end();
});
*/

    @Test
    @JsName("quadtree_cover_7")
    fun `quadtree cover(x, y) does not wrap the root node if it is a leaf LEGACY`() {
        val quadtree = buildQuadtree {
            cover(.0, .0)
            add(arrayOf(2, 2))
        }

        (quadtree.root as LeafNode).data shouldBe arrayOf(2, 2)

        var q = quadtree.copy()
        q.cover(3.0, 3.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(-1.0, 3.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(3.0, -1.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(-1.0, -1.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(5.0, 5.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(-3.0, 5.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(-3.0, -3.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)

        q = quadtree.copy()
        q.cover(5.0, -3.0)
        (q.root as LeafNode).data shouldBe arrayOf(2, 2)
    }

    @Test
    @JsName("quadtree_cover_8")
    fun `quadtree cover(x, y) does not wrap the root node if it is undefined LEGACY`() {
        val quadtree = buildQuadtree {
            cover(.0, .0)
            cover(2.0, 2.0)
        }

        quadtree.root shouldBe null

        var q = quadtree.copy()
        q.cover(3.0, 3.0)
        q.root shouldBe null

        q = quadtree.copy()
        q.cover(-1.0, 3.0)
        q.root shouldBe null

        q = quadtree.copy()
        q.cover(3.0, -1.0)
        q.root shouldBe null

        q = quadtree.copy()
        q.cover(5.0, 5.0)
        q.root shouldBe null
    }

    @Test
    @JsName("quadtree_copy_1")
    fun `quadtree copy() returns a copy of this quadtree LEGACY`() {
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
        // TODO quadtree.data...
    }

    @Test
    @JsName("quadtree_copy_2")
    fun `quadtree copy() isolates changes to the extent LEGACY`() {
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
    fun `quadtree copy() isolates changes to the root when a leaf LEGACY`() {
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
    fun `quadtree copy() isolates changes to the root when not a leaf LEGACY`() {
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

    @Test
    @JsName("quadtree_add_1")
    fun `quadtree add(datum) creates a new point and adds it to the quadtree LEGACY`() {
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
    fun `quadtree add(datum) handles points being on the perimeter of the quadtree bounds LEGACY`() {
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
    fun `quadtree add(datum) handles points being to the top of the quadtree bounds LEGACY`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(1, -1))
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, -2.0, 4.0, 2.0)
    }

    @Test
    @JsName("quadtree_add_4")
    fun `quadtree add(datum) handles points being to the right of the quadtree bounds LEGACY`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(3, 1))
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_add_5")
    fun `quadtree add(datum) handles points being to the bottom of the quadtree bounds LEGACY`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(1, 3))
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_add_6")
    fun `quadtree add(datum) handles points being to the left of the quadtree bounds LEGACY`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 2.0, 2.0)
            add(arrayOf(-1, 1))
        }

        quadtree.extent.toArray() shouldBe arrayOf(-2.0, .0, 2.0, 4.0)
    }

    @Test
    @JsName("quadtree_add_7")
    fun `quadtree add(datum) handles coincident points by creating a linked list LEGACY`() {
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
    fun `quadtree add(datum) implicitly defines trivial bounds for the first point LEGACY`() {
        val quadtree = buildQuadtree() {
            add(arrayOf(1, 2))
        }

        quadtree.extent.toArray() shouldBe arrayOf(1.0, 2.0, 2.0, 3.0)
        (quadtree.root as LeafNode).data shouldBe arrayOf(1, 2)
    }

    @Test
    @JsName("quadtree_extent_1")
    fun `quadtree extent(extent) extends the extent LEGACY`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, 1.0, 2.0, 6.0)
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, 1.0, 8.0, 9.0)
    }

    @Test
    @JsName("quadtree_extent_2")
    fun `quadtree extent() can be inferred by quadtree cover LEGACY`() {
        val quadtree = buildQuadtree()

        quadtree.cover(.0, .0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree.cover(2.0, 4.0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_extent_3")
    fun `quadtree extent() can be inferred by quadtree add LEGACY`() {
        val quadtree = buildQuadtree()

        quadtree.add(arrayOf(0, 0))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree.add(arrayOf(2, 4))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_extent_4")
    fun `quadtree extent(extent) ignores invalid extents LEGACY`() {
        var quadtree = buildQuadtree() {
            extent = Extent(1.0, Double.NaN, Double.NaN, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN)

        quadtree = buildQuadtree() {
            extent = Extent(Double.NaN, 1.0, .0, Double.NaN)
        }
        quadtree.extent.toArray() shouldBe arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN)

        quadtree = buildQuadtree() {
            extent = Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
        }
        quadtree.extent.toArray() shouldBe arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
    }

    @Test
    @JsName("quadtree_extent_5")
    fun `quadtree extent(extent) flips inverted extents LEGACY`() {
        val quadtree = buildQuadtree() {
            extent = Extent(1.0, 1.0, .0, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 2.0, 2.0)
    }

    @Test
    @JsName("quadtree_extent_6")
    fun `quadtree extent(extent) tolerates partially-valid extents LEGACY`() {
        var quadtree = buildQuadtree() {
            extent = Extent(Double.NaN, .0, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)

        quadtree = buildQuadtree() {
            extent = Extent(.0, Double.NaN, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)

        quadtree = buildQuadtree() {
            extent = Extent(.0, .0, Double.NaN, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 1.0, Double.NaN)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

    }

    @Test
    @JsName("quadtree_extent_7")
    fun `quadtree extent(extent) allows trivial extents LEGACY`() {
        var quadtree = buildQuadtree() {
            extent = Extent(.0, .0, .0, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree = buildQuadtree() {
            extent = Extent(1.0, 1.0, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)

    }

/*

     */

}

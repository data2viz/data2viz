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

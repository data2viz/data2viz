package io.data2viz.quadtree

import io.data2viz.geom.Extent
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeExtentTests : TestBase() {

    @Test
    @JsName("quadtree_extent_1")
    fun `quadtree extent(extent) extends the extent`() {
        val quadtree = buildQuadtree() {
            extent = Extent(.0, 1.0, 2.0, 6.0)
        }

        quadtree.extent.toArray() shouldBe arrayOf(.0, 1.0, 8.0, 9.0)
    }

    @Test
    @JsName("quadtree_extent_2")
    fun `quadtree extent() can be inferred by quadtree cover`() {
        val quadtree = buildQuadtree()

        quadtree.cover(.0, .0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree.cover(2.0, 4.0)
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_extent_3")
    fun `quadtree extent() can be inferred by quadtree add`() {
        val quadtree = buildQuadtree()

        quadtree.add(arrayOf(0, 0))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree.add(arrayOf(2, 4))
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 4.0, 4.0)
    }

    @Test
    @JsName("quadtree_extent_4")
    fun `quadtree extent(extent) ignores invalid extents`() {
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
    fun `quadtree extent(extent) flips inverted extents`() {
        val quadtree = buildQuadtree() {
            extent = Extent(1.0, 1.0, .0, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 2.0, 2.0)
    }

    @Test
    @JsName("quadtree_extent_6")
    fun `quadtree extent(extent) tolerates partially-valid extents`() {
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
    fun `quadtree extent(extent) allows trivial extents`() {
        var quadtree = buildQuadtree() {
            extent = Extent(.0, .0, .0, .0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(.0, .0, 1.0, 1.0)

        quadtree = buildQuadtree() {
            extent = Extent(1.0, 1.0, 1.0, 1.0)
        }
        quadtree.extent.toArray() shouldBe arrayOf(1.0, 1.0, 2.0, 2.0)
    }
}

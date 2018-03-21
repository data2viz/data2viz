package io.data2viz.quadtree

import io.data2viz.core.Extent
import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import kotlin.test.Test

class QuadtreeVisitTests : TestBase() {

    @Test
    @JsName("quadtree_visit_1")
    fun `quadtree visit(callback) visits each node in a quadtree LEGACY`() {
        val results = mutableListOf<List<Double>>()
        val quadtree = buildQuadtree() {
            addAll(listOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(0, 1), arrayOf(1, 1)))
        }

        quadtree.visit({ _, x0, y0, x1, y1 ->
            results.add(listOf(x0, y0, x1, y1))
            false
        })
        results[0] shouldBe listOf(0.0, 0.0, 1.0, 1.0)
        results[1] shouldBe listOf(0.0, 0.0, 0.5, 0.5)
        results[2] shouldBe listOf(0.5, 0.0, 1.0, 0.5)
        results[3] shouldBe listOf(0.0, 0.5, 0.5, 1.0)
        results[4] shouldBe listOf(0.5, 0.5, 1.0, 1.0)
    }

    @Test
    @JsName("quadtree_visit_2")
    fun `quadtree visit(callback) applies pre-order traversal LEGACY`() {
        val results = mutableListOf<List<Double>>()
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 960.0, 960.0)
            addAll(listOf(arrayOf(100, 100), arrayOf(200, 200), arrayOf(300, 300)))
        }

        quadtree.visit({ _, x0, y0, x1, y1 ->
            results.add(listOf(x0, y0, x1, y1))
            false
        })
        results[0] shouldBe listOf(0.0, 0.0, 1024.0, 1024.0)
        results[1] shouldBe listOf(0.0, 0.0, 512.0, 512.0)
        results[2] shouldBe listOf(0.0, 0.0, 256.0, 256.0)
        results[3] shouldBe listOf(0.0, 0.0, 128.0, 128.0)
        results[4] shouldBe listOf(128.0, 128.0, 256.0, 256.0)
        results[5] shouldBe listOf(256.0, 256.0, 512.0, 512.0)
    }

    @Test
    @JsName("quadtree_visit_3")
    fun `quadtree visit(callback) does not recurse if the callback returns true LEGACY`() {
        val results = mutableListOf<List<Double>>()
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 960.0, 960.0)
            addAll(listOf(arrayOf(100, 100), arrayOf(700, 700), arrayOf(800, 800)))
        }

        quadtree.visit({ _, x0, y0, x1, y1 ->
            results.add(listOf(x0, y0, x1, y1))
            x0 > 0
        })
        results[0] shouldBe listOf(0.0, 0.0, 1024.0, 1024.0)
        results[1] shouldBe listOf(0.0, 0.0, 512.0, 512.0)
        results[2] shouldBe listOf(512.0, 512.0, 1024.0, 1024.0)
    }

    @Test
    @JsName("quadtree_visit_4")
    fun `quadtree visit(callback) on an empty quadtree with no bounds does nothing LEGACY`() {
        val results = mutableListOf<List<Double>>()
        val quadtree = buildQuadtree() {
            extent = Extent(.0, .0, 960.0, 960.0)
        }

        quadtree.visit({ _, x0, y0, x1, y1 ->
            results.add(listOf(x0, y0, x1, y1))
            false
        })
        results.size shouldBe 0
    }

    @Test
    @JsName("quadtree_visit_5")
    fun `quadtree visit(callback) on an empty quadtree with bounds does nothing LEGACY`() {
        val results = mutableListOf<List<Double>>()
        val quadtree = buildQuadtree()

        quadtree.visit({ _, x0, y0, x1, y1 ->
            results.add(listOf(x0, y0, x1, y1))
            false
        })
        results.size shouldBe 0
    }
}

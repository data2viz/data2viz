package io.data2viz.voronoi

import io.data2viz.core.Point
import io.data2viz.test.matchers.Matchers
import org.junit.Test


class SiteTest: Matchers {


    @Test
    fun `sort same x different y`() {
        val sorted = listOf(
                Site(pt(1, 1), 1),
                Site(pt(1, 3), 3),
                Site(pt(1, 2), 2)
        ).sorted()

        sorted[0].index shouldBe 3
        sorted[1].index shouldBe 2
        sorted[2].index shouldBe 1
    }

    @Test
    fun `sort same y different x`() {
        val sorted = listOf(
                Site(pt(1, 1), 1),
                Site(pt(3, 1), 3),
                Site(pt(2, 1), 2)
        ).sorted()

        sorted[0].index shouldBe 3
        sorted[1].index shouldBe 2
        sorted[2].index shouldBe 1
    }

    @Test
    fun `sort all diff`() {
        val sorted = listOf(
                Site(pt(3, 1), 1),
                Site(pt(1, 3), 3),
                Site(pt(2, 2), 2)
        ).sorted()

        sorted[0].index shouldBe 3
        sorted[1].index shouldBe 2
        sorted[2].index shouldBe 1

    }

}


fun pt(x: Number, y: Number) = Point(x.toDouble(), y.toDouble())

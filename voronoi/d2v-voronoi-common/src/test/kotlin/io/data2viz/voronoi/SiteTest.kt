package io.data2viz.voronoi

import io.data2viz.geom.Point
import io.data2viz.test.matchers.Matchers
import kotlin.test.Test


class SiteTest: Matchers {


    @Test
    fun sort_same_x_different_y() {
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
    fun sort_same_y_different_x() {
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
    fun sort_all_diff() {
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

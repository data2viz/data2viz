package io.data2viz.voronoi

import io.data2viz.core.Point
import io.data2viz.test.matchers.Matchers
import org.junit.Test
import java.lang.Math.random


class DiagramTests : Matchers {

    fun List<Point>.sites() = mapIndexed { index, point -> Site(point, index) }.toTypedArray()

    @Test
    fun `diagram 1 site`() {
        Diagram(listOf(pt(10, 10)).sites())
    }

    @Test
    fun `diagram 5 sites`() {
        val diagram = Diagram(listOf(
                pt(10, 10),
                pt(20, 10),
                pt(10, 20),
                pt(20, 20),
                pt(15, 15)
        ).sites())

        val triangles = diagram.triangles()
        triangles.size shouldBe 4

        val polygons = diagram.polygons()
        polygons.filterNotNull().size shouldBe polygons.size
        polygons.size shouldBe 5

        diagram.find(pt(14, 10)) shouldBe diagram.cells!![0]?.site

        polygons.all { polygon ->
            polygon.all { point ->
                point != null
            }
        } shouldBe true

    }

    @Test
    fun `diagram 2 sites`() {
        val diagram = Diagram(listOf(
                pt(10, 10),
                pt(30, 10)
        ).sites())

        println(diagram)
    }

    @Test
    fun `diagram 16384 sites`() {
        val points = (1..16384).map {
            pt(random() * 100, random() * 100)
        }

        val diagram = Diagram(points.sites(), clipEnd = Point(100.0, 100.0))
        val polygons = diagram.polygons()
                .mapIndexed { i, polygon ->
                    var x = 0.0
                    var y = 0.0
                    polygon.forEach { point -> x += point.x; y += point.y }
                    return@mapIndexed Site(Point(x / polygon.size, y / polygon.size), i)
                }.toTypedArray()



        println("polygons.count :: ${polygons.size}")
        val links = diagram.links()
        println("links.count :: ${links.size}")
        val triangles = diagram.triangles()
        println("triangles.count :: ${triangles.size}")
    }

}


package io.data2viz.geom

import io.data2viz.test.JsName
import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.math.sqrt
import kotlin.test.Test

class PolygonTests : TestBase() {

    @Test
    @JsName("PolygonTest1")
    fun `polygonArea(polygon) returns the expected value for closed counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0), Point(.0, .0)))

        poly.area shouldBeClose 1.0
    }

    @Test
    @JsName("PolygonTest2")
    fun `polygonArea(polygon) returns the expected value for closed clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0), Point(.0, .0)))
        poly.area shouldBeClose -1.0

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0), Point(1.0, 1.0)))
        poly2.area shouldBeClose -1.5
    }

    @Test
    @JsName("PolygonTest3")
    fun `polygonArea(polygon) returns the expected value for open counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0)))
        poly.area shouldBeClose 1.0
    }

    @Test
    @JsName("PolygonTest4")
    fun `polygonArea(polygon) returns the expected value for open clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0)))
        poly.area shouldBeClose -1.0

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0)))
        poly2.area shouldBeClose -1.5
    }

    @Test
    @JsName("PolygonTest5")
    fun `polygonArea(polygon) returns the expected value for a very large polygon`() {
        val stop = 1e8.toInt()
        val step = 1e4.toInt()
        val points = mutableListOf<Point>()

        (0 until stop step step).forEach { points.add(Point(.0, it.toDouble())) }
        (0 until stop step step).forEach { points.add(Point(it.toDouble(), stop.toDouble())) }
        (stop - step downTo 0 step step).forEach { points.add(Point(stop.toDouble(), it.toDouble())) }
        (stop - step downTo 0 step step).forEach { points.add(Point(it.toDouble(), .0)) }

        val poly = Polygon(points)
        poly.area shouldBeClose (1e16 - 5e7)
    }

    @Test
    @JsName("PolygonTest6")
    fun `polygonCentroid(points) returns the expected value for closed counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0), Point(.0, .0)))

        poly.centroid.x shouldBeClose .5
        poly.centroid.y shouldBeClose .5
    }

    @Test
    @JsName("PolygonTest7")
    fun `polygonCentroid(points) returns the expected value for closed clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0), Point(.0, .0)))
        poly.centroid.x shouldBeClose .5
        poly.centroid.y shouldBeClose .5

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0), Point(1.0, 1.0)))
        poly2.centroid.x shouldBeClose 2.0
        poly2.centroid.y shouldBeClose 2.0
    }

    @Test
    @JsName("PolygonTest8")
    fun `polygonCentroid(points) returns the expected value for open counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0)))
        poly.centroid.x shouldBeClose .5
        poly.centroid.y shouldBeClose .5
    }

    @Test
    @JsName("PolygonTest9")
    fun `polygonCentroid(points) returns the expected value for open clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0)))
        poly.centroid.x shouldBeClose .5
        poly.centroid.y shouldBeClose .5

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0)))
        poly2.centroid.x shouldBeClose 2.0
        poly2.centroid.y shouldBeClose 2.0
    }

    @Test
    @JsName("PolygonTest10")
    fun `polygonCentroid(polygon) returns the expected value for a very large polygon`() {
        val stop = 1e8.toInt()
        val step = 1e4.toInt()
        val points = mutableListOf<Point>()

        (0 until stop step step).forEach { points.add(Point(.0, it.toDouble())) }
        (0 until stop step step).forEach { points.add(Point(it.toDouble(), stop.toDouble())) }
        (stop - step downTo 0 step step).forEach { points.add(Point(stop.toDouble(), it.toDouble())) }
        (stop - step downTo 0 step step).forEach { points.add(Point(it.toDouble(), .0)) }

        val poly = Polygon(points)
        poly.centroid.x shouldBeClose 49999999.75000187
        poly.centroid.y shouldBeClose 49999999.75001216
    }

    @Test
    @JsName("PolygonTest11")
    fun `polygonContains(polygon, point) returns the expected value for closed counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0), Point(.0, .0)))

        poly.contains(Point(.5, .5)) shouldBe true
        poly.contains(Point(1.5, .5)) shouldBe false
        poly.contains(Point(-.5, .5)) shouldBe false
        poly.contains(Point(.5, 1.5)) shouldBe false
        poly.contains(Point(.5, -.5)) shouldBe false
    }

    @Test
    @JsName("PolygonTest12")
    fun `polygonContains(polygon, point) returns the expected value for closed clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0), Point(.0, .0)))
        poly.contains(Point(.5, .5)) shouldBe true

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0), Point(1.0, 1.0)))
        poly2.contains(Point(1.5, 1.5)) shouldBe true
    }

    @Test
    @JsName("PolygonTest13")
    fun `polygonContains(polygon, point) returns the expected value for open counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0)))
        poly.contains(Point(.5, .5)) shouldBe true
    }

    @Test
    @JsName("PolygonTest14")
    fun `polygonContains(polygon, point) returns the expected value for open clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0)))
        poly.contains(Point(.5, .5)) shouldBe true

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0)))
        poly2.contains(Point(1.5, 1.5)) shouldBe true
    }

    @Test
    @JsName("PolygonTest15")
    fun `polygonHull(points) raise IllegalArgumentException if points has fewer than three elements`() {
        shouldThrow<IllegalArgumentException>{ polygonHull(listOf()) }
        shouldThrow<IllegalArgumentException>{ polygonHull(listOf(Point(1.0, .0))) }
        shouldThrow<IllegalArgumentException>{ polygonHull(listOf(Point(1.0, .0), Point(.0, 1.0))) }
    }

    @Test
    @JsName("PolygonTest16")
    fun `polygonHull(points) returns the convex hull of the specified points`() {
        val polygon = polygonHull(
            listOf(
                Point(200.0, 200.0),
                Point(760.0, 300.0),
                Point(500.0, 500.0)
            )
        )
        polygon.points[0] shouldBe Point(760.0, 300.0)
        polygon.points[1] shouldBe Point(200.0, 200.0)
        polygon.points[2] shouldBe Point(500.0, 500.0)

        val polygon2 = polygonHull(
            listOf(
                Point(200.0, 200.0),
                Point(760.0, 300.0),
                Point(500.0, 500.0),
                Point(400.0, 400.0)
            )
        )
        polygon2.points[0] shouldBe Point(760.0, 300.0)
        polygon2.points[1] shouldBe Point(200.0, 200.0)
        polygon2.points[2] shouldBe Point(500.0, 500.0)
    }

    @Test
    @JsName("PolygonTest17")
    fun `polygonHull(points) handles points with duplicate ordinates`() {
        val polygon = polygonHull(
            listOf(
                Point(-10.0, -10.0),
                Point(10.0, 10.0),
                Point(10.0, -10.0),
                Point(-10.0, 10.0)
            )
        )
        polygon.points[0] shouldBe Point(10.0, 10.0)
        polygon.points[1] shouldBe Point(10.0, -10.0)
        polygon.points[2] shouldBe Point(-10.0, -10.0)
        polygon.points[3] shouldBe Point(-10.0, 10.0)
    }

    @Test
    @JsName("PolygonTest18")
    fun `polygonHull(points) handles overlapping upper and lower hulls`() {
        val polygon = polygonHull(
            listOf(
                Point(.0, -10.0),
                Point(.0, 10.0),
                Point(.0, .0),
                Point(10.0, .0),
                Point(-10.0, .0)
            )
        )
        polygon.points[0] shouldBe Point(10.0, .0)
        polygon.points[1] shouldBe Point(.0, -10.0)
        polygon.points[2] shouldBe Point(-10.0, .0)
        polygon.points[3] shouldBe Point(.0, 10.0)
    }

    @Test
    @JsName("PolygonTest19")
    // Cases below taken from http://uva.onlinejudge.org/external/6/681.html
    fun `polygonHull(points) handles various non-trivial hulls`() {
        val polygon = polygonHull(
            listOf(
                Point(60.0, 20.0),
                Point(250.0, 140.0),
                Point(180.0, 170.0),
                Point(79.0, 140.0),
                Point(50.0, 60.0),
                Point(60.0, 20.0)
            )
        )
        polygon.points[0] shouldBe Point(250.0, 140.0)
        polygon.points[1] shouldBe Point(60.0, 20.0)
        polygon.points[2] shouldBe Point(50.0, 60.0)
        polygon.points[3] shouldBe Point(79.0, 140.0)
        polygon.points[4] shouldBe Point(180.0, 170.0)

        val polygon2 = polygonHull(
            listOf(
                Point(50.0, 60.0),
                Point(60.0, 20.0),
                Point(70.0, 45.0),
                Point(100.0, 70.0),
                Point(125.0, 90.0),
                Point(200.0, 113.0),
                Point(250.0, 140.0),
                Point(180.0, 170.0),
                Point(105.0, 140.0),
                Point(79.0, 140.0),
                Point(60.0, 85.0),
                Point(50.0, 60.0)
            )
        )
        polygon2.points[0] shouldBe Point(250.0, 140.0)
        polygon2.points[1] shouldBe Point(60.0, 20.0)
        polygon2.points[2] shouldBe Point(50.0, 60.0)
        polygon2.points[3] shouldBe Point(79.0, 140.0)
        polygon2.points[4] shouldBe Point(180.0, 170.0)

        val polygon3 = polygonHull(
            listOf(
                Point(30.0, 30.0),
                Point(50.0, 60.0),
                Point(60.0, 20.0),
                Point(70.0, 45.0),
                Point(86.0, 39.0),
                Point(112.0, 60.0),
                Point(200.0, 113.0),
                Point(250.0, 50.0),
                Point(300.0, 200.0),
                Point(130.0, 240.0),
                Point(76.0, 150.0),
                Point(47.0, 76.0),
                Point(36.0, 40.0),
                Point(33.0, 35.0),
                Point(30.0, 30.0)
            )
        )
        polygon3.points[0] shouldBe Point(300.0, 200.0)
        polygon3.points[1] shouldBe Point(250.0, 50.0)
        polygon3.points[2] shouldBe Point(60.0, 20.0)
        polygon3.points[3] shouldBe Point(30.0, 30.0)
        polygon3.points[4] shouldBe Point(47.0, 76.0)
        polygon3.points[5] shouldBe Point(76.0, 150.0)
        polygon3.points[6] shouldBe Point(130.0, 240.0)
    }

    @Test
    @JsName("PolygonTest20")
    fun `polygonLength(polygon) returns the expected value for closed counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0), Point(.0, .0)))

        poly.length shouldBeClose 4.0
    }

    @Test
    @JsName("PolygonTest21")
    fun `polygonLength(polygon) returns the expected value for closed clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0), Point(.0, .0)))
        poly.length shouldBeClose 4.0

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0), Point(1.0, 1.0)))
        poly2.length shouldBeClose (sqrt(20.0) + sqrt(2.0))
    }

    @Test
    @JsName("PolygonTest22")
    fun `polygonLength(polygon) returns the expected value for open counterclockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(.0, 1.0), Point(1.0, 1.0), Point(1.0, .0)))
        poly.length shouldBeClose 4.0
    }

    @Test
    @JsName("PolygonTest23")
    fun `polygonLength(polygon) returns the expected value for open clockwise polygons`() {
        val poly = Polygon(listOf(Point(.0, .0), Point(1.0, .0), Point(1.0, 1.0), Point(.0, 1.0)))
        poly.length shouldBeClose 4.0

        val poly2 = Polygon(listOf(Point(1.0, 1.0), Point(3.0, 2.0), Point(2.0, 3.0)))
        poly2.length shouldBeClose (sqrt(20.0) + sqrt(2.0))
    }
}
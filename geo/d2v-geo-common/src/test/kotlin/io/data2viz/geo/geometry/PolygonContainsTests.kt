package io.data2viz.geo.geometry

import io.data2viz.geo.geojson.path.GeoCircle
import io.data2viz.geojson.Polygon
import io.data2viz.math.PI
import io.data2viz.test.TestBase
import kotlin.test.Test

class PolygonContainsTests : TestBase() {


    private fun testCircleCoordinates(radius: Double): List<List<DoubleArray>> {
        val geoCircle = GeoCircle<Int>()
        geoCircle.radius = {
            radius
        }
        return convertToCoordinates(geoCircle.circle())
    }

    private fun convertToCoordinates(
        polygon: Polygon

    ): List<List<DoubleArray>> {
        val coordinates = mutableListOf<List<DoubleArray>>()

        polygon.coordinates.forEach { line ->
            val lineList = mutableListOf<DoubleArray>()
            coordinates.add(lineList)

            line.forEach { position ->
                val arr = DoubleArray(position.size)
                lineList.add(arr)
                position.forEachIndexed { index, point ->
                    arr[index] = point
                }
            }

        }
        return coordinates
    }

    fun testPolygonContains(polygon: List<List<DoubleArray>>, point: DoubleArray, exceptedResult: Boolean) {

        val transformedPolygon: MutableList<MutableList<DoubleArray>> = mutableListOf()
        polygon.forEach { row ->
            val mutableRow = mutableListOf<DoubleArray>()
            row.forEach { polygonPoint ->

                mutableRow.add(pointRadians(polygonPoint))
            }
            transformedPolygon.add(mutableRow)
        }
        val transformedPoint = pointRadians(point)

        var polygonContainsResult:Any = polygonContains(transformedPolygon, transformedPoint)

        // Workaround for test on JS platform
        polygonContainsResult = when (polygonContainsResult) {
            0, false -> false
            else -> true
        }

        polygonContainsResult shouldBe exceptedResult
    }

    private fun pointRadians(point: DoubleArray): DoubleArray =
        doubleArrayOf(point[0] * PI / 180.0, point[1] * PI / 180.0)


    @Test
    fun polygonContains_empty_point_returns_false() {

        testPolygonContains(listOf(), doubleArrayOf(0.0), false)
    }

    @Test
    fun polygonContains_simple_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(0.0, 0.0),
                doubleArrayOf(0.0, 1.0),
                doubleArrayOf(1.0, 1.0),
                doubleArrayOf(1.0, 0.0),
                doubleArrayOf(0.0, 0.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.1, 2.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.1, 0.1), true)
    }


    @Test
    fun polygonContains_smallCircle_point_returns_the_expected_value() {

        val polygon = testCircleCoordinates(60.0)

        testPolygonContains(polygon, doubleArrayOf(-180.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(1.0, 1.0), true)
    }


    @Test
    fun polygonContains_southPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(-60.0, -80.0),
                doubleArrayOf(60.0, -80.0),
                doubleArrayOf(180.0, -80.0),
                doubleArrayOf(-60.0, -80.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, -85.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), true)
    }


    @Test
    fun polygonContains_northPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(60.0, 80.0),
                doubleArrayOf(-60.0, 80.0),
                doubleArrayOf(-180.0, 80.0),
                doubleArrayOf(60.0, 80.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 85.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, 90.0), true)
        testPolygonContains(polygon, doubleArrayOf(-100.0, 90.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), false)
    }

    // See d3-geo issue #105
    @Test
    fun polygonContains_touchingPole_NorthPole_returns_true() {

        var polygon = listOf(
            listOf(
                doubleArrayOf(0.0, 30.0),
                doubleArrayOf(-120.0, 30.0),
                doubleArrayOf(0.0, 90.0),
                doubleArrayOf(0.0, 30.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 90.0), false)
        testPolygonContains(polygon, doubleArrayOf(-60.0, 90.0), false)
        testPolygonContains(polygon, doubleArrayOf(60.0, 90.0), false)

    }


    // See d3-geo issue #105
    @Test
    fun polygonContains_touchingPole_SouthPole_returns_true() {

        var polygon = listOf(
            listOf(
                doubleArrayOf(0.0, -30.0),
                doubleArrayOf(120.0, -30.0),
                doubleArrayOf(0.0, -90.0),
                doubleArrayOf(0.0, -30.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), false)
        testPolygonContains(polygon, doubleArrayOf(-60.0, -90.0), false)
        testPolygonContains(polygon, doubleArrayOf(60.0, -90.0), false)

    }


    @Test
    fun polygonContains_southHemispherePoly_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(0.0, 0.0),
                doubleArrayOf(10.0, -40.0),
                doubleArrayOf(-10.0, -40.0),
                doubleArrayOf(0.0, 0.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, -40.2), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, -40.5), false)
    }


    @Test
    fun polygonContains_largeNearOrigin_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(0.0, 0.0),
                doubleArrayOf(1.0, 0.0),
                doubleArrayOf(1.0, 1.0),
                doubleArrayOf(0.0, 1.0),
                doubleArrayOf(0.0, 0.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.1, 0.1), false)
        testPolygonContains(polygon, doubleArrayOf(2.0, 0.1), true)
    }

    @Test
    fun polygonContains_largeNearSouthPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(-60.0, 80.0),
                doubleArrayOf(60.0, 80.0),
                doubleArrayOf(180.0, 80.0),
                doubleArrayOf(-60.0, 80.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 85.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
    }

    @Test
    fun polygonContains_largeNearNorthPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(60.0, -80.0),
                doubleArrayOf(-60.0, -80.0),
                doubleArrayOf(-180.0, -80.0),
                doubleArrayOf(60.0, -80.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, -85.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
    }


    @Test
    fun polygonContains_largeCircle_point_returns_the_expected_value() {

        val polygon = testCircleCoordinates(120.0)

        testPolygonContains(polygon, doubleArrayOf(-180.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(-90.0, 0.0), true)
    }

    @Test
    fun polygonContains_largeNarrowStripHole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(-170.0, -1.0),
                doubleArrayOf(0.0, -1.0),
                doubleArrayOf(170.0, -1.0),
                doubleArrayOf(170.0, 1.0),
                doubleArrayOf(0.0, 1.0),
                doubleArrayOf(-170.0, 1.0),
                doubleArrayOf(-170.0, -1.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 20.0), true)
    }


    @Test
    fun polygonContains_largeNarrowEquatorialHole_point_returns_the_expected_value() {

        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(0.0, -90.0) }
        geoCircle.radius = { 90.0 - 0.01 }
        val ring0 = convertToCoordinates(geoCircle.circle())
        geoCircle.radius = { 90.0 + 0.01 }
        val ring1 = convertToCoordinates(geoCircle.circle()).reversed()

        val polygon: MutableList<List<DoubleArray>> = mutableListOf()
        polygon.addAll(ring0)
        polygon.addAll(ring1)

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), true)
    }

    @Test
    fun polygonContains_largeNarrowEquatorialStrip_point_returns_the_expected_value() {

        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(0.0, -90.0) }
        geoCircle.radius = { 90.0 + 0.01 }
        val ring0 = convertToCoordinates(geoCircle.circle())[0]
        geoCircle.radius = { 90.0 - 0.01 }
        val ring1 = convertToCoordinates(geoCircle.circle())[0].reversed()

        val polygon = listOf(ring0, ring1)

        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
    }


    @Test
    fun polygonContains_ringNearOrigin_point_returns_the_expected_value() {

        val ring0 =
            listOf(
                doubleArrayOf(0.0, 0.0),
                doubleArrayOf(0.0, 1.0),
                doubleArrayOf(1.0, 1.0),
                doubleArrayOf(1.0, 0.0),
                doubleArrayOf(0.0, 0.0)

            )

        val ring1 =
            listOf(
                doubleArrayOf(0.4, 0.4),
                doubleArrayOf(0.6, 0.4),
                doubleArrayOf(0.6, 0.6),
                doubleArrayOf(0.4, 0.6),
                doubleArrayOf(0.4, 0.4)

            )


        val polygon = listOf(ring0, ring1)


        testPolygonContains(polygon, doubleArrayOf(0.5, 0.5), false)
        testPolygonContains(polygon, doubleArrayOf(0.1, 0.5), true)
    }

    @Test
    fun polygonContains_ringEquatorial_point_returns_the_expected_value() {

        val ring0 =
            listOf(
                doubleArrayOf(0.0, -10.0),
                doubleArrayOf(-120.0, -10.0),
                doubleArrayOf(-120.0, 10.0),
                doubleArrayOf(0.0, -10.0)
            )

        val ring1 =
            listOf(
                doubleArrayOf(0.0, 10.0),
                doubleArrayOf(120.0, 10.0),
                doubleArrayOf(-120.0, 10.0),
                doubleArrayOf(0.0, 10.0)
            )


        val polygon = listOf(ring0, ring1)


        testPolygonContains(polygon, doubleArrayOf(0.0, 20.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
    }

    @Test
    fun polygonContains_ringExcludingBothPoles_point_returns_the_expected_value() {

        val ring0 =
            listOf(
                doubleArrayOf(10.0, 10.0),
                doubleArrayOf(-10.0, 10.0),
                doubleArrayOf(-10.0, -10.0),
                doubleArrayOf(10.0, -10.0),
                doubleArrayOf(10.0, 10.0)
            ).reversed()

        val ring1 =
            listOf(
                doubleArrayOf(170.0, 10.0),
                doubleArrayOf(170.0, -10.0),
                doubleArrayOf(-170.0, -10.0),
                doubleArrayOf(-170.0, 10.0),
                doubleArrayOf(170.0, 10.0)
            ).reversed()


        val polygon = listOf(ring0, ring1)


        testPolygonContains(polygon, doubleArrayOf(0.0, 90.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
    }

    @Test
    fun polygonContains_ringContainingBothPoles_point_returns_the_expected_value() {

        val ring0 =
            listOf(
                doubleArrayOf(10.0, 10.0),
                doubleArrayOf(-10.0, 10.0),
                doubleArrayOf(-10.0, -10.0),
                doubleArrayOf(10.0, -10.0),
                doubleArrayOf(10.0, 10.0)
            )

        val ring1 =
            listOf(
                doubleArrayOf(170.0, 10.0),
                doubleArrayOf(170.0, -10.0),
                doubleArrayOf(-170.0, -10.0),
                doubleArrayOf(-170.0, 10.0),
                doubleArrayOf(170.0, 10.0)
            )


        val polygon = listOf(ring0, ring1)


        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 20.0), true)
    }

    @Test
    fun polygonContains_ringContainingSouthPole_point_returns_the_expected_value() {

        val ring0 =
            listOf(
                doubleArrayOf(10.0, 10.0),
                doubleArrayOf(-10.0, 10.0),
                doubleArrayOf(-10.0, -10.0),
                doubleArrayOf(10.0, -10.0),
                doubleArrayOf(10.0, 10.0)
            )

        val ring1 =
            listOf(
                doubleArrayOf(0.0, 80.0),
                doubleArrayOf(120.0, 80.0),
                doubleArrayOf(-120.0, 80.0),
                doubleArrayOf(0.0, 80.0)
            )


        val polygon = listOf(ring0, ring1)


        testPolygonContains(polygon, doubleArrayOf(0.0, 90.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), true)
    }


    @Test
    fun polygonContains_ringContainingNorthPole_point_returns_the_expected_value() {

        val ring0 =
            listOf(
                doubleArrayOf(10.0, 10.0),
                doubleArrayOf(-10.0, 10.0),
                doubleArrayOf(-10.0, -10.0),
                doubleArrayOf(10.0, -10.0),
                doubleArrayOf(10.0, 10.0)
            ).reversed()

        val ring1 =
            listOf(
                doubleArrayOf(0.0, 80.0),
                doubleArrayOf(120.0, 80.0),
                doubleArrayOf(-120.0, 80.0),
                doubleArrayOf(0.0, 80.0)
            ).reversed()


        val polygon = listOf(ring0, ring1)


        testPolygonContains(polygon, doubleArrayOf(0.0, -90.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 90.0), true)
    }

    @Test
    fun polygonContains_selfIntersectingNearOrigin_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(0.0, 0.0),
                doubleArrayOf(1.0, 0.0),
                doubleArrayOf(1.0, 3.0),
                doubleArrayOf(3.0, 3.0),
                doubleArrayOf(3.0, 1.0),
                doubleArrayOf(0.0, 1.0),
                doubleArrayOf(0.0, 0.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(15.0, 0.5), false)
        testPolygonContains(polygon, doubleArrayOf(12.0, 2.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.5, 0.5), true)
        testPolygonContains(polygon, doubleArrayOf(2.0, 2.0), true)
    }

    @Test
    fun polygonContains_selfIntersectingNearSouthPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(-10.0, -80.0),
                doubleArrayOf(120.0, -80.0),
                doubleArrayOf(-120.0, -80.0),
                doubleArrayOf(10.0, -85.0),
                doubleArrayOf(10.0, -75.0),
                doubleArrayOf(-10.0, -75.0),
                doubleArrayOf(-10.0, -80.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, -76.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, -89.0), true)

    }

    @Test
    fun polygonContains_selfIntersectingNearNorthPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(-10.0, 80.0),
                doubleArrayOf(-10.0, 75.0),
                doubleArrayOf(10.0, 75.0),
                doubleArrayOf(10.0, 85.0),
                doubleArrayOf(-120.0, 80.0),
                doubleArrayOf(120.0, 80.0),
                doubleArrayOf(-10.0, 80.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 76.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, 89.0), true)

    }

    @Test
    fun polygonContains_hemisphereTouchingTheSouthPole_point_returns_the_expected_value() {

        val polygon = testCircleCoordinates(90.0)

        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)

    }


    @Test
    fun polygonContains_triangleTouchingTheSouthPole_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(180.0, -90.0),
                doubleArrayOf(-45.0, 0.0),
                doubleArrayOf(45.0, 0.0),
                doubleArrayOf(180.0, -90.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(-46.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 1.0), false)
        testPolygonContains(polygon, doubleArrayOf(-90.0, -80.0), false)
        testPolygonContains(polygon, doubleArrayOf(-44.0, 0.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, -30.0), true)
        testPolygonContains(polygon, doubleArrayOf(3.0, -80.0), true)


    }


    @Test
    fun polygonContains_triangleTouchingTheSouthPole2_point_returns_the_expected_value() {

        val polygon = listOf(
            listOf(
                doubleArrayOf(-45.0, 0.0),
                doubleArrayOf(45.0, 0.0),
                doubleArrayOf(180.0, -90.0),
                doubleArrayOf(-45.0, 0.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(-46.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 1.0), false)
        testPolygonContains(polygon, doubleArrayOf(-90.0, -80.0), false)
        testPolygonContains(polygon, doubleArrayOf(-44.0, 0.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, -30.0), true)
        testPolygonContains(polygon, doubleArrayOf(3.0, -80.0), true)


    }

    @Test
    fun polygonContains_triangleTouchingTheSouthPole3_point_returns_the_expected_value() {


        val polygon = listOf(
            listOf(
                doubleArrayOf(180.0, -90.0),
                doubleArrayOf(-135.0, 0.0),
                doubleArrayOf(135.0, 0.0),
                doubleArrayOf(180.0, -90.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(180.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(150.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(180.0, -30.0), false)
        testPolygonContains(polygon, doubleArrayOf(150.0, -80.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, 0.0), true)
        testPolygonContains(polygon, doubleArrayOf(180.0, 1.0), true)
        testPolygonContains(polygon, doubleArrayOf(-90.0, -80.0), true)


    }


    @Test
    fun polygonContains_triangleTouchingTheNorthPole_point_returns_the_expected_value() {


        val polygon = listOf(
            listOf(
                doubleArrayOf(180.0, 90.0),
                doubleArrayOf(45.0, 0.0),
                doubleArrayOf(-45.0, 0.0),
                doubleArrayOf(180.0, 90.0)
            )
        )

        testPolygonContains(polygon, doubleArrayOf(-90.0, 0.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, -1.0), false)
        testPolygonContains(polygon, doubleArrayOf(0.0, -80.0), false)
        testPolygonContains(polygon, doubleArrayOf(-90.0, 1.0), false)
        testPolygonContains(polygon, doubleArrayOf(-90.0, 80.0), false)
        testPolygonContains(polygon, doubleArrayOf(-44.0, 10.0), true)
        testPolygonContains(polygon, doubleArrayOf(0.0, 10.0), true)
        testPolygonContains(polygon, doubleArrayOf(30.0, 80.0), true)


    }

}

//var tape = require("tape"),
//rollup = require("rollup"),
//d3_geo = require("../");
//
//rollup.rollup({input: "src/polygonContains.js"})
//.then(bundle => bundle.generate({format: "iife", name: "_"}))
//.then(bundle => {
//    var contains = new Function(bundle.code + " return _;")();
//
//    function polygonContains(polygon, point) {
//        return contains(polygon.map(ringRadians), pointRadians(point));
//    }
//
//    tape("geoPolygonContains(empty, point) returns false", function(test) {
//        test.equal(polygonContains([], [0, 0]), 0);
//        test.end();
//    });
//
//    tape("geoPolygonContains(simple, point) returns the expected value", function(test) {
//        var polygon = [[[0, 0], [0, 1], [1, 1], [1, 0], [0, 0]]];
//        test.equal(polygonContains(polygon, [0.1, 2]), 0);
//        test.equal(polygonContains(polygon, [0.1, 0.1]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(smallCircle, point) returns the expected value", function(test) {
//        var polygon = d3_geo.geoCircle().radius(60)().coordinates;
//        test.equal(polygonContains(polygon, [-180, 0]), 0);
//        test.equal(polygonContains(polygon, [1, 1]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(southPole, point) returns the expected value", function(test) {
//        var polygon = [[[-60, -80], [60, -80], [180, -80], [-60, -80]]];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, -85]), 1);
//        test.equal(polygonContains(polygon, [0, -90]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(northPole, point) returns the expected value", function(test) {
//        var polygon = [[[60, 80], [-60, 80], [-180, 80], [60, 80]]];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, 85]), 1);
//        test.equal(polygonContains(polygon, [0, 90]), 1);
//        test.equal(polygonContains(polygon, [-100, 90]), 1);
//        test.equal(polygonContains(polygon, [0, -90]), 0);
//        test.end();
//    });
//
//    tape("geoPolygonContains(touchingPole, Pole) returns true (issue #105)", function(test) {
//        var polygon = [[[0, -30], [120, -30], [0, -90], [0, -30]]];
//        test.equal(polygonContains(polygon, [0, -90]), 0);
//        test.equal(polygonContains(polygon, [-60, -90]), 0);
//        test.equal(polygonContains(polygon, [60, -90]), 0);
//        polygon = [[[0, 30], [-120, 30], [0, 90], [0, 30]]];
//        test.equal(polygonContains(polygon, [0, 90]), 0);
//        test.equal(polygonContains(polygon, [-60, 90]), 0);
//        test.equal(polygonContains(polygon, [60, 90]), 0);
//        test.end();
//    });
//
//    tape("geoPolygonContains(southHemispherePoly) returns the expected value", function(test) {
//        var polygon = [[[0, 0], [10, -40], [-10, -40], [0, 0]]];
//        test.equal(polygonContains(polygon, [0,-40.2]), 1);
//        test.equal(polygonContains(polygon, [0,-40.5]), 0);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeNearOrigin, point) returns the expected value", function(test) {
//        var polygon = [[[0, 0], [1, 0], [1, 1], [0, 1], [0, 0]]];
//        test.equal(polygonContains(polygon, [0.1, 0.1]), 0);
//        test.equal(polygonContains(polygon, [2, 0.1]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeNearSouthPole, point) returns the expected value", function(test) {
//        var polygon = [[[-60, 80], [60, 80], [180, 80], [-60, 80]]];
//        test.equal(polygonContains(polygon, [0, 85]), 0);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeNearNorthPole, point) returns the expected value", function(test) {
//        var polygon = [[[60, -80], [-60, -80], [-180, -80], [60, -80]]];
//        test.equal(polygonContains(polygon, [0, -85]), 0);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeCircle, point) returns the expected value", function(test) {
//        var polygon = d3_geo.geoCircle().radius(120)().coordinates;
//        test.equal(polygonContains(polygon, [-180, 0]), 0);
//        test.equal(polygonContains(polygon, [-90, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeNarrowStripHole, point) returns the expected value", function(test) {
//        var polygon = [[[-170, -1], [0, -1], [170, -1], [170, 1], [0, 1], [-170, 1], [-170, -1]]];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, 20]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeNarrowEquatorialHole, point) returns the expected value", function(test) {
//        var circle = d3_geo.geoCircle().center([0, -90]),
//        ring0 = circle.radius(90 - 0.01)().coordinates[0],
//        ring1 = circle.radius(90 + 0.01)().coordinates[0].reverse(),
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, -90]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(largeNarrowEquatorialStrip, point) returns the expected value", function(test) {
//        var circle = d3_geo.geoCircle().center([0, -90]),
//        ring0 = circle.radius(90 + 0.01)().coordinates[0],
//        ring1 = circle.radius(90 - 0.01)().coordinates[0].reverse(),
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, -90]), 0);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(ringNearOrigin, point) returns the expected value", function(test) {
//        var ring0 = [[0, 0], [0, 1], [1, 1], [1, 0], [0, 0]],
//        ring1 = [[0.4, 0.4], [0.6, 0.4], [0.6, 0.6], [0.4, 0.6], [0.4, 0.4]],
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0.5, 0.5]), 0);
//        test.equal(polygonContains(polygon, [0.1, 0.5]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(ringEquatorial, point) returns the expected value", function(test) {
//        var ring0 = [[0, -10], [-120, -10], [120, -10], [0, -10]],
//        ring1 = [[0, 10], [120, 10], [-120, 10], [0, 10]],
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, 20]), 0);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(ringExcludingBothPoles, point) returns the expected value", function(test) {
//        var ring0 = [[10, 10], [-10, 10], [-10, -10], [10, -10], [10, 10]].reverse(),
//        ring1 = [[170, 10], [170, -10], [-170, -10], [-170, 10], [170, 10]].reverse(),
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, 90]), 0);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(ringContainingBothPoles, point) returns the expected value", function(test) {
//        var ring0 = [[10, 10], [-10, 10], [-10, -10], [10, -10], [10, 10]],
//        ring1 = [[170, 10], [170, -10], [-170, -10], [-170, 10], [170, 10]],
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, 20]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(ringContainingSouthPole, point) returns the expected value", function(test) {
//        var ring0 = [[10, 10], [-10, 10], [-10, -10], [10, -10], [10, 10]],
//        ring1 = [[0, 80], [120, 80], [-120, 80], [0, 80]],
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, 90]), 0);
//        test.equal(polygonContains(polygon, [0, -90]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(ringContainingNorthPole, point) returns the expected value", function(test) {
//        var ring0 = [[10, 10], [-10, 10], [-10, -10], [10, -10], [10, 10]].reverse(),
//        ring1 = [[0, 80], [120, 80], [-120, 80], [0, 80]].reverse(),
//        polygon = [ring0, ring1];
//        test.equal(polygonContains(polygon, [0, -90]), 0);
//        test.equal(polygonContains(polygon, [0, 90]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(selfIntersectingNearOrigin, point) returns the expected value", function(test) {
//        var polygon = [[[0, 0], [1, 0], [1, 3], [3, 3], [3, 1], [0, 1], [0, 0]]];
//        test.equal(polygonContains(polygon, [15, 0.5]), 0);
//        test.equal(polygonContains(polygon, [12, 2]), 0);
//        test.equal(polygonContains(polygon, [0.5, 0.5]), 1);
//        test.equal(polygonContains(polygon, [2, 2]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(selfIntersectingNearSouthPole, point) returns the expected value", function(test) {
//        var polygon = [[[-10, -80], [120, -80], [-120, -80], [10, -85], [10, -75], [-10, -75], [-10, -80]]];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, -76]), 1);
//        test.equal(polygonContains(polygon, [0, -89]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(selfIntersectingNearNorthPole, point) returns the expected value", function(test) {
//        var polygon = [[[-10, 80], [-10, 75], [10, 75], [10, 85], [-120, 80], [120, 80], [-10, 80]]];
//        test.equal(polygonContains(polygon, [0, 0]), 0);
//        test.equal(polygonContains(polygon, [0, 76]), 1);
//        test.equal(polygonContains(polygon, [0, 89]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(hemisphereTouchingTheSouthPole, point) returns the expected value", function(test) {
//        var polygon = d3_geo.geoCircle().radius(90)().coordinates;
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(triangleTouchingTheSouthPole, point) returns the expected value", function(test) {
//        var polygon = [[[180, -90], [-45, 0], [45, 0], [180, -90]]];
//        test.equal(polygonContains(polygon, [-46, 0]), 0);
//        test.equal(polygonContains(polygon, [0, 1]), 0);
//        test.equal(polygonContains(polygon, [-90, -80]), 0);
//        test.equal(polygonContains(polygon, [-44, 0]), 1);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.equal(polygonContains(polygon, [0, -30]), 1);
//        test.equal(polygonContains(polygon, [30, -80]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(triangleTouchingTheSouthPole2, point) returns the expected value", function(test) {
//        var polygon = [[[-45, 0], [45, 0], [180, -90], [-45, 0]]];
//        test.equal(polygonContains(polygon, [-46, 0]), 0);
//        test.equal(polygonContains(polygon, [0, 1]), 0);
//        test.equal(polygonContains(polygon, [-90, -80]), 0);
//        test.equal(polygonContains(polygon, [-44, 0]), 1);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.equal(polygonContains(polygon, [0, -30]), 1);
//        test.equal(polygonContains(polygon, [30, -80]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(triangleTouchingTheSouthPole3, point) returns the expected value", function(test) {
//        var polygon = [[[180, -90], [-135, 0], [135, 0], [180, -90]]];
//        test.equal(polygonContains(polygon, [180, 0]), 0);
//        test.equal(polygonContains(polygon, [150, 0]), 0);
//        test.equal(polygonContains(polygon, [180, -30]), 0);
//        test.equal(polygonContains(polygon, [150, -80]), 0);
//        test.equal(polygonContains(polygon, [0, 0]), 1);
//        test.equal(polygonContains(polygon, [180, 1]), 1);
//        test.equal(polygonContains(polygon, [-90, -80]), 1);
//        test.end();
//    });
//
//    tape("geoPolygonContains(triangleTouchingTheNorthPole, point) returns the expected value", function(test) {
//        var polygon = [[[180, 90], [45, 0], [-45, 0], [180, 90]]];
//        test.equal(polygonContains(polygon, [-90, 0]), 0);
//        test.equal(polygonContains(polygon, [0, -1]), 0);
//        test.equal(polygonContains(polygon, [0, -80]), 0);
//        test.equal(polygonContains(polygon, [-90, 1]), 0);
//        test.equal(polygonContains(polygon, [-90, 80]), 0);
//        test.equal(polygonContains(polygon, [-44, 10]), 1);
//        test.equal(polygonContains(polygon, [0, 10]), 1);
//        test.equal(polygonContains(polygon, [30, 80]), 1);
//        test.end();
//    });
//});
//
//function ringRadians(ring) {
//    return ring = ring.map(pointRadians), ring.pop(), ring;
//}
//
//function pointRadians(point) {
//    return [point[0] * Math.PI / 180, point[1] * Math.PI / 180];
//}
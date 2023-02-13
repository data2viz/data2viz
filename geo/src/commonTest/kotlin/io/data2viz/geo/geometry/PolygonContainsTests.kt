/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.geometry

import io.data2viz.geo.geojson.path.GeoCircle
import io.data2viz.geojson.Polygon
import io.data2viz.test.TestBase
import kotlin.math.PI
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



        polygonContainsResult shouldBeBoolean exceptedResult
    }

    private fun pointRadians(point: DoubleArray): DoubleArray =
        doubleArrayOf(point[0] * PI / 180.0, point[1] * PI / 180.0)


    @Test
    fun polygonContains_empty_point_returns_false() {

        testPolygonContains(listOf(), doubleArrayOf(0.0, 0.0), false)
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

infix fun <T> T.shouldBeBoolean(exceptedBooleanValue: Boolean): Unit  {
    // Workaround for test on JS platform
    val transformedActualValue = when (this) {
        null, 0, false, Double.NaN -> false
        else -> true
    }

    if(transformedActualValue != exceptedBooleanValue) {
        throw AssertionError("$this did not equal $exceptedBooleanValue")
    }

}

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

package io.data2viz.geo.geojson.path

import io.data2viz.geo.projection.pt
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class BoundsTests : TestBase() {

    @Test
    fun geobounds_feature() {
        val result = GeoBoundsStream()
            .result(Feature(MultiPoint(arrayOf(
                pt(-123.0, 39.0),
                pt(-122.0, 38.0)
            ))))
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_featureCollection() {
        val result = GeoBoundsStream().result(
            FeatureCollection(
                arrayOf(
                    Feature(Point(pt(-123.0, 39.0))),
                    Feature(Point(pt(-122.0, 38.0)))
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_GeometryCollection() {
        val result = GeoBoundsStream().result(
            GeometryCollection(
                arrayOf(
                    Point(pt(-123.0, 39.0)),
                    Point(pt(-122.0, 38.0))
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_LineString_simple() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(-123.0, 39.0),
                    pt(-122.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_LineString_symmetry() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(-30.0, -20.0),
                    pt(130.0, 40.0)
                )
            )
        )
        val result2 = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(130.0, 40.0),
                    pt(-30.0, -20.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(result2.x0, result2.y0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(result2.x1, result2.y1)
    }

    @Test
    fun geobounds_LineString_containing_coincident_points() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(-123.0, 39.0),
                    pt(-122.0, 38.0),
                    pt(-122.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_LineString_meridian() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(.0, 1.0),
                    pt(.0, 60.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(.0, 60.0)
    }

    @Test
    fun geobounds_LineString_equator() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(1.0, .0),
                    pt(60.0, .0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(60.0, .0)
    }

    @Test
    fun geobounds_LineString_containing_an_inflection_point_in_the_northern_hemisphere() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(-45.0, 60.0),
                    pt(45.0, 60.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-45.0, 60.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(45.0, 67.792345)
    }

    @Test
    fun geobounds_LineString_containing_an_inflection_point_in_the_southern_hemisphere() {
        val result = GeoBoundsStream().result(
            LineString(
                arrayOf(
                    pt(-45.0, -60.0),
                    pt(45.0, -60.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-45.0, -67.792345)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(45.0, -60.0)
    }

    @Test
    fun geobounds_MultiLineString() {
        val result = GeoBoundsStream().result(
            MultiLineString(
                arrayOf(
                    arrayOf(
                        pt(-123.0, 39.0),
                        pt(-122.0, 38.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_simple() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(-123.0, 39.0),
                    pt(-122.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_2_points_near_antimeridian() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(-179.0, 39.0),
                    pt(179.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(179.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_2_points_near_antimeridian_2_points_near_primary_meridian() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(-179.0, 39.0),
                    pt(179.0, 38.0),
                    pt(-1.0, .0),
                    pt(1.0, .0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-1.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_2_points_near_primary_meridian_2_points_near_antimeridian() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(-1.0, .0),
                    pt(1.0, .0),
                    pt(-179.0, 39.0),
                    pt(179.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-1.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_4_mixed_points_near_primary_meridian_and_antimeridian() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(-1.0, .0),
                    pt(-179.0, 39.0),
                    pt(1.0, .0),
                    pt(179.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-1.0, .0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_3_points_near_antimeridian() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(178.0, 38.0),
                    pt(179.0, 39.0),
                    pt(-179.0, 37.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(178.0, 37.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-179.0, 39.0)
    }

    @Test
    fun geobounds_MultiPoint_various_points_near_antimeridian() {
        val result = GeoBoundsStream().result(
            MultiPoint(
                arrayOf(
                    pt(-179.0, 39.0),
                    pt(-179.0, 38.0),
                    pt(178.0, 39.0),
                    pt(-178.0, 38.0)
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(178.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-178.0, 39.0)
    }

    @Test
    fun geobounds_MultiPolygon() {
        val result = GeoBoundsStream().result(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(-123.0, 39.0),
                            pt(-122.0, 39.0),
                            pt(-122.0, 38.0),
                            pt(-123.0, 39.0)
                        )
                    ),
                    arrayOf(
                        arrayOf(
                            pt(10.0, 20.0),
                            pt(20.0, 20.0),
                            pt(20.0, 10.0),
                            pt(10.0, 10.0),
                            pt(10.0, 20.0)
                        )
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 10.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(20.0, 39.001067)
    }

    @Test
    fun geobounds_Point() {
        val result = GeoBoundsStream().result(
            Point(
                pt(-123.0, 39.0)
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 39.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-123.0, 39.0)
    }

    @Test
    fun geobounds_Polygon_simple() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-123.0, 39.0),
                        pt(-122.0, 39.0),
                        pt(-122.0, 38.0),
                        pt(-123.0, 39.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-123.0, 38.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-122.0, 39.001067)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_small_counter_clockwise() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(.0, .0),
                        pt(10.0, .0),
                        pt(10.0, 10.0),
                        pt(.0, 10.0),
                        pt(.0, .0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-180.0, -90.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(180.0, 90.0)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_large_lat_lon_rectangle() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-170.0, 80.0),
                        pt(.0, 80.0),
                        pt(170.0, 80.0),
                        pt(170.0, -80.0),
                        pt(.0, -80.0),
                        pt(-170.0, -80.0),
                        pt(-170.0, 80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-170.0, -89.119552)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(170.0, 89.119552)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_south_pole() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(10.0, 80.0),
                        pt(170.0, 80.0),
                        pt(-170.0, 80.0),
                        pt(-10.0, 80.0),
                        pt(10.0, 80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-180.0, -90.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(180.0, 88.246216)
    }

    @Test
    fun geobounds_Polygon_larger_than_a_hemisphere_excluding_both_poles() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(10.0, 80.0),
                        pt(170.0, 80.0),
                        pt(-170.0, 80.0),
                        pt(-10.0, 80.0),
                        pt(-10.0, .0),
                        pt(-10.0, -80.0),
                        pt(-170.0, -80.0),
                        pt(170.0, -80.0),
                        pt(10.0, -80.0),
                        pt(10.0, .0),
                        pt(10.0, 80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(10.0, -88.246216)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-10.0, 88.246216)
    }

    @Test
    fun geobounds_Polygon_south_pole() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-60.0, -80.0),
                        pt(60.0, -80.0),
                        pt(180.0, -80.0),
                        pt(-60.0, -80.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-180.0, -90.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(180.0, -80.0)
    }

    @Test
    fun geobounds_Polygon_ring() {
        val result = GeoBoundsStream().result(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-60.0, -80.0),
                        pt(60.0, -80.0),
                        pt(180.0, -80.0),
                        pt(-60.0, -80.0)
                    ),
                    arrayOf(
                        pt(-60.0, -89.0),
                        pt(180.0, -89.0),
                        pt(60.0, -89.0),
                        pt(-60.0, -89.0)
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-180.0, -89.499961)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(180.0, -80.0)
    }

    @Test
    fun geobounds_nestedCollection() {
        val result = GeoBoundsStream().result(
            FeatureCollection(
                arrayOf(
                    Feature(
                        GeometryCollection(
                            arrayOf(
                                Point(
                                    pt(-120.0, 47.0)
                                ),
                                Point(
                                    pt(-119.0, 46.0)
                                )
                            )
                        )
                    )
                )
            )
        )
        doubleArrayOf(result.x0, result.y0) shouldBeClose doubleArrayOf(-120.0, 46.0)
        doubleArrayOf(result.x1, result.y1) shouldBeClose doubleArrayOf(-119.0, 47.0)
    }

    /*@Test
    fun geobounds_null_geometries() {
        val result = GeoBoundsStream().result(Feature(null))

        doubleArrayOf(result.x0, result.phi0) shouldBeClose doubleArrayOf(Double.NaN, Double.NaN)
        doubleArrayOf(result.x1, result.phi1) shouldBeClose doubleArrayOf(Double.NaN, Double.NaN)
    }*/
}

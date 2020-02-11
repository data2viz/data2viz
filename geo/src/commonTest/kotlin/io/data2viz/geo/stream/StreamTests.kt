/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.geo.stream

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.geojson.Sphere
import io.data2viz.geo.geojson.stream
import io.data2viz.geo.projection.pt
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.test.Test


class StreamTests : TestBase() {

    val emptyStream = object : Stream<GeoJsonPoint>() {}

    @Test
    fun geoStream_object_allows_empty_multi_geometries() {

        MultiPoint(arrayOf()).stream(emptyStream)
        MultiLineString(arrayOf()).stream(emptyStream)
        MultiPolygon(arrayOf()).stream(emptyStream)

    }


    @Test
    fun geoStream_Sphere_sphere() {

        var calls = 0
        Sphere().stream(object : Stream<GeoJsonPoint>() {

            override fun sphere() {
                calls++
            }
        })

        calls shouldBe 1
    }


    @Test
    fun geoStream_Point_point() {

        var calls = 0
        Point(pt(1.0, 2.0, 3.0)).stream(object : Stream<GeoJsonPoint>() {

            override fun point(point: GeoJsonPoint) {
                point.lon.rad shouldBeClose 1.0
                point.lat.rad shouldBeClose 2.0
                point.z!! shouldBeClose 3.0
                calls++
            }
        })

        calls shouldBe 1
    }

    @Test
    fun geoStream_MultiPoint_point() {

        var calls = 0
        var pointCalls = 0
        val coordinates = arrayOf(
            pt(1.0, 2.0, 3.0),
            pt(4.0, 5.0, 6.0)
        )
        MultiPoint(
            coordinates
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun point(point: GeoJsonPoint) {
                coordinates[pointCalls][0] shouldBeClose point.lon.rad
                coordinates[pointCalls][1] shouldBeClose point.lat.rad
                coordinates[pointCalls][2] shouldBeClose point.z!!
                pointCalls++
                calls++
                (calls in 1..2) shouldBe true
            }
        })

        calls shouldBe 2
    }


    @Test
    fun geoStream_LineString_lineStart_point_lineEnd() {

        var calls = 0
        var pointCalls = 0
        val coordinates = arrayOf(
            pt(1.0, 2.0, 3.0),
            pt(4.0, 5.0, 6.0)
        )
        LineString(
            coordinates
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun lineStart() {
                ++calls shouldBe 1

            }

            override fun lineEnd() {
                ++calls shouldBe 4
            }

            override fun point(point: GeoJsonPoint) {
                coordinates[pointCalls][0] shouldBeClose point.lon.rad
                coordinates[pointCalls][1] shouldBeClose point.lat.rad
                coordinates[pointCalls][2] shouldBeClose point.z!!
                pointCalls++
                calls++
                (calls in 2..3) shouldBe true
            }
        })

        calls shouldBe 4

    }


    @Test
    fun geoStream_MultiLineString_lineStart_point_lineEnd() {

        var calls = 0
        var pointCalls = 0
        val coordinates = arrayOf(
            arrayOf(
                pt(1.0, 2.0, 3.0),
                pt(4.0, 5.0, 6.0)
            ),
            arrayOf(
                pt(7.0, 8.0, 9.0),
                pt(10.0, 11.0, 12.0)
            )
        )
        MultiLineString(
            coordinates
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun lineStart() {
                (++calls == 1 || calls == 5) shouldBe true
            }

            override fun lineEnd() {
                (++calls == 4 || calls == 8) shouldBe true
            }

            override fun point(point: GeoJsonPoint) {

                val row = pointCalls / 2
                val index = pointCalls % 2

                coordinates[row][index][0] shouldBeClose point.lon.rad
                coordinates[row][index][1] shouldBeClose point.lat.rad
                coordinates[row][index][2] shouldBeClose point.z!!
                pointCalls++

                calls++
                (calls in 2..3 || calls in 6..7) shouldBe true
            }
        })

        calls shouldBe 8
    }


    @Test
    fun geoStream_Polygon_polygonStart_lineStart_point_lineEnd_polygonEnd() {

        var calls = 0
        var pointCalls = 0
        val coordinates = arrayOf(
            arrayOf(
                pt(1.0, 2.0, 3.0),
                pt(4.0, 5.0, 6.0),
                pt(1.0, 2.0, 3.0)
            ),
            arrayOf(
                pt(7.0, 8.0, 9.0),
                pt(10.0, 11.0, 12.0),
                pt(7.0, 8.0, 9.0)
            )
        )
        Polygon(
            coordinates
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun polygonStart() {
                (++calls == 1) shouldBe true
            }

            override fun polygonEnd() {
                (++calls == 10) shouldBe true
            }

            override fun lineStart() {
                (++calls == 2 || calls == 6) shouldBe true
            }

            override fun lineEnd() {
                (++calls == 5 || calls == 9) shouldBe true
            }

            override fun point(point: GeoJsonPoint) {

                val row = pointCalls / 2
                val index = pointCalls % 2

                coordinates[row][index][0] shouldBeClose point.lon.rad
                coordinates[row][index][1] shouldBeClose point.lat.rad
                coordinates[row][index][2] shouldBeClose point.z!!
                pointCalls++

                calls++
                (calls in 3..4 || calls in 7..8) shouldBe true
            }
        })

        calls shouldBe 10
    }


    @Test
    fun geoStream_MultiPolygon_polygonStart_lineStart_point_lineEnd_polygonEnd() {

        var calls = 0
        var pointCalls = 0
        val coordinates = arrayOf(
            arrayOf(
                arrayOf(
                    pt(1.0, 2.0, 3.0),
                    pt(4.0, 5.0, 6.0),
                    pt(1.0, 2.0, 3.0)
                )
            ),
            arrayOf(
                arrayOf(
                    pt(7.0, 8.0, 9.0),
                    pt(10.0, 11.0, 12.0),
                    pt(7.0, 8.0, 9.0)
                )
            )
        )
        MultiPolygon(
            coordinates
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun polygonStart() {
                (++calls == 1 || calls == 7) shouldBe true
            }

            override fun polygonEnd() {
                (++calls == 6 || calls == 12) shouldBe true
            }

            override fun lineStart() {
                (++calls == 2 || calls == 8) shouldBe true
            }

            override fun lineEnd() {
                (++calls == 5 || calls == 11) shouldBe true
            }

            override fun point(point:GeoJsonPoint) {

                val row = pointCalls / 2
                val index = pointCalls % 2

                coordinates[row][0][index][0] shouldBeClose point.lon.rad
                coordinates[row][0][index][1] shouldBeClose point.lat.rad
                coordinates[row][0][index][2] shouldBeClose point.z!!
                pointCalls++

                calls++
                (calls in 3..4 || calls in 9..10) shouldBe true
            }
        })

        calls shouldBe 12
    }


    @Test
    fun geoStream_Feature() {

        var calls = 0
        Feature(Point(pt(1.0, 2.0, 3.0))).stream(object : Stream<GeoJsonPoint>() {

            override fun point(point: GeoJsonPoint) {
                point.lon.rad shouldBeClose 1.0
                point.lat.rad shouldBeClose 2.0
                point.z!! shouldBeClose 3.0
                calls++
            }
        })

        calls shouldBe 1
    }

    @Test
    fun geoStream_FeatureCollection() {

        var calls = 0
        FeatureCollection(
            arrayOf(Feature(Point(pt(1.0, 2.0, 3.0))))
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun point(point: GeoJsonPoint) {
                point.lon.rad shouldBeClose 1.0
                point.lat.rad shouldBeClose 2.0
                point.z!! shouldBeClose 3.0
                calls++
            }
        })

        calls shouldBe 1
    }

    @Test
    fun geoStream_GeometryCollection() {

        var calls = 0
        GeometryCollection(
            arrayOf(Point(pt(1.0, 2.0, 3.0)))
        ).stream(object : Stream<GeoJsonPoint>() {

            override fun point(point: GeoJsonPoint) {
                point.lon.rad shouldBeClose 1.0
                point.lat.rad shouldBeClose 2.0
                point.z!! shouldBeClose 3.0
                calls++
            }
        })

        calls shouldBe 1
    }

}
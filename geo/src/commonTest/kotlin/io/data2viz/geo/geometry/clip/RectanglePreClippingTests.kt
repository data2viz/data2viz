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

package io.data2viz.geo.projection


import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.geometry.clip.RectangleClip
import io.data2viz.geo.geometry.clip.ExtentClip
import io.data2viz.geojson.LineString
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geojson.Polygon
import io.data2viz.geom.Extent
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.math.toRadians
import io.data2viz.test.TestBase
import kotlin.test.Test

class RectanglePreClippingTests : TestBase() {

    fun getProjection() = equirectangularProjection {
        translate(480.0, 350.0)
        precision = .0
    }

    val polygon = Polygon(
            arrayOf(
                arrayOf(
                    doubleArrayOf(-8.0, 15.0),
                    doubleArrayOf(-6.0, 15.0),
                    doubleArrayOf(-6.0, -15.0),
                    doubleArrayOf(-8.0, -15.0),
                    doubleArrayOf(-8.0, 15.0)
                )
            )
    )

    val line = LineString(
        arrayOf(
            doubleArrayOf(6.0, 15.0),
            doubleArrayOf(6.0, -15.0)
        )

    )

    val clipZonePolygon = Polygon(
            arrayOf(
                arrayOf(
                    doubleArrayOf(-10.0, 10.0),
                    doubleArrayOf(10.0, 10.0),
                    doubleArrayOf(10.0, -10.0),
                    doubleArrayOf(-10.0, -10.0),
                    doubleArrayOf(-10.0, 10.0)
                )
            )
    )


//    @Test
//    fun rectangle_clipping_east() {
//        val path = PathGeom()
//        val projection = getProjection()
////        geoPath(projection, path).project(clipZonePolygon)
//
//        projection.preClip = RectangleClip( //in radians
//            -10.0.toRadians(), -10.0.toRadians(),
//            10.0.toRadians(), 10.0.toRadians())
//
//        geoPath(projection, path).project(polygon)
//        geoPath(projection, path).project(line)
//        val svg = path.svgPath.round()
//        println(generateHtmlWithSvg(svg))
//        svg shouldBe "M464.016624,323.361040L464.016624,376.638960L458.688832,376.638960L458.688832,323.361040L464.016624,323.361040ZM495.983376,323.361040L495.983376,376.638960".round()
//    }

}
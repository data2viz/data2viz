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

package io.data2viz.geo.projection


import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.geometry.clip.RectangleClip
import io.data2viz.geo.geometry.clip.ExtentClip
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geom.Extent
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class RectanglePostClippingTests : TestBase() {

    fun getProjection() = equirectangularProjection {
        translate(480.0, 350.0)
        scale = 2000.0
        precision = .0
        center(10.0.deg, 5.0.deg)
    }

    val polygon = MultiPolygon(
        arrayOf(
            arrayOf(
                arrayOf(
                    doubleArrayOf(5.0, 5.0),
                    doubleArrayOf(2.50, 7.5),
                    doubleArrayOf(5.0, 10.0),
                    doubleArrayOf(10.0, 10.0),
                    doubleArrayOf(12.5, 7.5),
                    doubleArrayOf(10.0, 5.0),
                    doubleArrayOf(5.0, 5.0)
                )
            )
        )
    )

    @Test
    fun no_clipping() {
        val path = PathGeom()
        geoPath(getProjection(), path).project(polygon)

        path.svgPath.round() shouldBe "M305.46707480056705,350L218.20061220085057,262.7335374002835L305.46707480056705,175.46707480056705L480,175.46707480056705L567.2664625997165,262.7335374002835L480,350Z".round()
    }

    @Test
    fun rectangle_clipping_east() {
        val projection = getProjection()
        projection.postClip = RectangleClip(48.0, 50.0, 498.0, 500.0)

        val path = PathGeom()
        geoPath(projection, path).project(polygon)

        path.svgPath.round() shouldBe "M498,332L480,350L305.46707480056705,350L305.46707480056705,350L218.20061220085057,262.7335374002835L305.46707480056705,175.46707480056705L480,175.46707480056705L498,193.46707480056705L498,332Z".round()
    }

    @Test
    fun rectangle_clipping_north() {
        val projection = getProjection()
        projection.postClip = RectangleClip(200.0, 200.0, 700.0, 500.0)

        val path = PathGeom()
        geoPath(projection, path).project(polygon)

        path.svgPath.round() shouldBe "M504.53292519943295,200L567.2664625997165,262.7335374002835L480,350L305.46707480056705,350L305.46707480056705,350L218.20061220085057,262.7335374002835L280.9341496011341,200L504.53292519943295,200Z".round()
    }

    @Test
    fun rectangle_clipping_south() {
        val projection = getProjection()
        projection.postClip = RectangleClip(48.0, 50.0, 700.0, 300.0)

        val path = PathGeom()
        geoPath(projection, path).project(polygon)

        path.svgPath.round() shouldBe "M255.46707480056705,300L218.20061220085057,262.7335374002835L305.46707480056705,175.46707480056705L480,175.46707480056705L567.2664625997165,262.7335374002835L530,300L255.46707480056705,300Z".round()
    }

    @Test
    fun rectangle_clipping_west() {
        val projection = getProjection()
        projection.postClip = RectangleClip(250.0, 50.0, 700.0, 500.0)

        val path = PathGeom()
        geoPath(projection, path).project(polygon)

        path.svgPath.round() shouldBe "M250,230.9341496011341L305.46707480056705,175.46707480056705L480,175.46707480056705L567.2664625997165,262.7335374002835L480,350L305.46707480056705,350L305.46707480056705,350L250,294.53292519943295L250,230.9341496011341Z".round()
    }

    @Test
    fun rectangle_vs_extent() {
        val projection = getProjection()
        projection.postClip = RectangleClip(250.0, 50.0, 700.0, 500.0)
        val path1 = PathGeom()
        geoPath(projection, path1).project(polygon)

        projection.postClip = ExtentClip(Extent(250.0, 50.0, 700.0, 500.0))
        val path2 = PathGeom()
        geoPath(projection, path2).project(polygon)

        path1.svgPath shouldBe path2.svgPath
    }
}

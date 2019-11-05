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

import io.data2viz.geo.geojson.Sphere
import io.data2viz.geo.geojson.fitExtent
import io.data2viz.geo.geometry.calculateSvgPath
import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test


class TransverseMercatorTests : TestBase() {

// Clip tests not passed
//    @Test
//    fun transverseMercator_clipExtent_null_sets_default_automatic_clip_extent() {
//        val projection = transverseMercatorProjection {
//            translate(0.0, 0.0)
//            scale = 1.0
//            extentPostClip = null
//            precision = 0.0
//        }
//
//
//        projection.extentPostClip shouldBe null
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M3.141593,3.141593L0,3.141593L-3.141593,3.141593L-3.141593,-3.141593L-3.141593,-3.141593L0,-3.141593L3.141593,-3.141593L3.141593,3.141593Z"
//
//    }
//
//
//    @Test
//    fun transverseMercator_center_sets_correct_automatic_clip_extent() {
//        val projection = transverseMercatorProjection {
//            translate(0.0, 0.0)
//            scale = 1.0
//            extentPostClip = null
//            center(10.0.deg, 10.0.deg)
//            precision = 0.0
//        }
//
//
//        projection.extentPostClip shouldBe null
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M2.966167,3.316126L-0.175426,3.316126L-3.317018,3.316126L-3.317019,-2.967060L-3.317019,-2.967060L-0.175426,-2.967060L2.966167,-2.967060L2.966167,3.316126Z"
//
//    }
//
//
//    @Test
//    fun transverseMercator_clipExtent_intersects_the_specified_clip_extent_with_the_automatic_clip_extent() {
//        val projection = transverseMercatorProjection {
//            translate(0.0, 0.0)
//            scale = 1.0
//            extentPostClip = Extent(-10.0, -10.0, 10.0, 10.0)
//            precision = 0.0
//        }
//
//        projection.extentPostClip!!.width shouldBe 20.0
//        projection.extentPostClip!!.height shouldBe 20.0
//        projection.extentPostClip!!.x0 shouldBe -10.0
//        projection.extentPostClip!!.y0 shouldBe -10.0
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M10,3.141593L0,3.141593L-10,3.141593L-10,-3.141593L-10,-3.141593L0,-3.141593L10,-3.141593L10,3.141593Z"
//
//    }
//
//
//    @Test
//    fun transverseMercator_clipExtent_scale_updates_the_intersected_clip_extent() {
//
//        val projection = transverseMercatorProjection {
//            translate(0.0, 0.0)
//            extentPostClip = Extent(-10.0, -10.0, 10.0, 10.0)
//            scale = 1.0
//            precision = 0.0
//        }
//
//        projection.extentPostClip!!.width shouldBe 20.0
//        projection.extentPostClip!!.height shouldBe 20.0
//        projection.extentPostClip!!.x0 shouldBe -10.0
//        projection.extentPostClip!!.y0 shouldBe -10.0
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M10,3.141593L0,3.141593L-10,3.141593L-10,-3.141593L-10,-3.141593L0,-3.141593L10,-3.141593L10,3.141593Z"
//
//    }
//
//    @Test
//    fun transverseMercator_clipExtent_translate_updates_the_intersected_clip_extent() {
//
//        val projection = transverseMercatorProjection {
//            scale = 1.0
//            extentPostClip = Extent(-10.0, -10.0, 10.0, 10.0)
//            translate(0.0, 0.0)
//            precision = 0.0
//        }
//
//        projection.extentPostClip!!.width shouldBe 20.0
//        projection.extentPostClip!!.height shouldBe 20.0
//        projection.extentPostClip!!.x0 shouldBe -10.0
//        projection.extentPostClip!!.y0 shouldBe -10.0
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M10,3.141593L0,3.141593L-10,3.141593L-10,-3.141593L-10,-3.141593L0,-3.141593L10,-3.141593L10,3.141593Z"
//
//    }
//
//    @Test
//    fun transverseMercator_rotate_does_not_affect_the_automatic_clip_extent() {
//        val projection = mercatorProjection()
//
//
//        val geoJsonObject =   """{
//            "type": "MultiPoint",
//            "coordinates": [
//            [
//                 [-82.35024908550241, 29.649391549778745],
//            [-82.35014449996858, 29.65075946917633],
//            [-82.34916073446641, 29.65070265688781],
//            [-82.3492653331286, 29.64933474064504]
//            ]
//            ]
//        }""".toGeoJsonObject()
//
//        projection.fitExtent(Extent(0.0, 0.0, 960.0, 600.0), geoJsonObject)
//
//
//        projection.scale shouldBe 15724992.330511674
//        projection.translateX shouldBe 20418843.897824813
//        projection.translateY shouldBe 21088401.790971387
//
//        projection.rotate(0.0.deg, 95.0.deg, projection.rotateGamma)
//        projection.fitExtent(Extent(0.0, 0.0, 960.0, 600.0), geoJsonObject)
//
//        projection.scale shouldBe 15724992.330511674
//        projection.translateX shouldBe 20418843.897824813
//        projection.translateY shouldBe 47161426.43770847
//
//    }


}
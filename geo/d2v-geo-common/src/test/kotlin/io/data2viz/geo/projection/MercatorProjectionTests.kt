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

class MercatorProjectionTests : TestBase() {

    // Clip tests not passed
//
//    @Test
//    fun mercator_clip_extent_null_sets_default_automatic_clip_extent() {
//        val projection = mercatorProjection {
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
//        sphereSvgPath shouldBe "M3.141593,-3.141593L3.141593,0L3.141593,3.141593L3.141593,3.141593L-3.141593,3.141593L-3.141593,3.141593L-3.141593,0L-3.141593,-3.141593L-3.141593,-3.141593L3.141593,-3.141593Z"
//
//    }
//
//    @Test
//    fun mercator_center_center_sets_correct_automatic_clip_extent() {
//        val projection = mercatorProjection {
//            translate(0.0, 0.0)
//            scale = 1.0
//            center(10.0.deg, 10.0.deg)
//            extentPostClip = null
//            precision = 0.0
//        }
//
//
//        projection.extentPostClip shouldBe null
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M2.967060,-2.966167L2.967060,0.175426L2.967060,3.317018L2.967060,3.317018L-3.316126,3.317018L-3.316126,3.317019L-3.316126,0.175426L-3.316126,-2.966167L-3.316126,-2.966167L2.967060,-2.966167Z"
//
//    }
//
//    @Test
//    fun mercator_translate_scale_clipExtent_center_center_intersects_the_specified_clip_extent_with_the_automatic_clip_extent() {
//        val projection = mercatorProjection {
//            translate(0.0, 0.0)
//
//            scale = 1.0
//            // difference
//            extentPostClip = Extent(-10.0, -10.0, 10.0, 10.0)
//            precision = 0.0
//        }
//
//        projection.extentPostClip!!.width shouldBe 20.0
//        projection.extentPostClip!!.height shouldBe 20.0
//        projection.extentPostClip!!.x0 shouldBe -10.0
//        projection.extentPostClip!!.y0 shouldBe -10.0
//
//
//        val sphereSvgPath = calculateSvgPath(projection, Sphere())
//
//        sphereSvgPath shouldBe "M3.141593,-10L3.141593,0L3.141593,10L3.141593,10L-3.141593,10L-3.141593,10L-3.141593,0L-3.141593,-10L-3.141593,-10L3.141593,-10Z"
//
//    }
//
//    @Test
//    fun mercator_translate_clipExtent_scale_center_center_intersects_the_specified_clip_extent_with_the_automatic_clip_extent() {
//        val projection = mercatorProjection {
//            translate(0.0, 0.0)
//            extentPostClip = Extent(-10.0, -10.0, 10.0, 10.0)
//
//            // difference
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
//        sphereSvgPath shouldBe "M3.141593,-10L3.141593,0L3.141593,10L3.141593,10L-3.141593,10L-3.141593,10L-3.141593,0L-3.141593,-10L-3.141593,-10L3.141593,-10Z"
//
//    }
//
//    @Test
//    fun mercator_clipExtent_translate_scale_center_center_intersects_the_specified_clip_extent_with_the_automatic_clip_extent() {
//        val projection = mercatorProjection {
//            extentPostClip = Extent(-10.0, -10.0, 10.0, 10.0)
//            translate(0.0, 0.0)
//
//            // difference
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
//        sphereSvgPath shouldBe "M3.141593,-10L3.141593,0L3.141593,10L3.141593,10L-3.141593,10L-3.141593,10L-3.141593,0L-3.141593,-10L-3.141593,-10L3.141593,-10Z"
//
//    }
//
//
//    @Test
//    fun mercator_rotate_does_not_affect_the_automatic_clip_extent() {
//        val projection = mercatorProjection()
//
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
//        projection.scale shouldBe 20969742.365692537
//        projection.translateX shouldBe 30139734.76760269
//        projection.translateY shouldBe 11371473.949706702
//
//        projection.rotate(0.0.deg, 95.0.deg, projection.rotateGamma)
//        projection.fitExtent(Extent(0.0, 0.0, 960.0, 600.0), geoJsonObject)
//
//        projection.scale shouldBe 35781690.650920525
//        projection.translateX shouldBe 75115911.95344563
//        projection.translateY shouldBe 2586046.4116968135
//
//    }


    @Test
    fun mercator_various_projects_1() {
        val projection = mercatorProjection {
            translateX = .0
            translateY = .0
            rotate(20.0.deg, 10.0.deg, 30.0.deg)
        }

        checkProjectAndInvert(projection, 84.0, 59.0, 468.39738235470327, -301.9997264679594)
        checkProjectAndInvert(projection, -22.0, 16.0, -41.232469642834104, -58.132750186605435)
        checkProjectAndInvert(projection, 800.0, -800.0, 206.79103709446065, 151.98632483005113)
    }

    @Test
    fun mercator_various_projects_2() {
        val projection = mercatorProjection {
            translateX = 40.0
            translateY = 200.0
            rotate(5.0.deg, .0.deg, (-30.0).deg)
        }

        checkProjectAndInvert(projection, 84.0, 59.0, 278.67805496267147, 119.04407321930081)
        checkProjectAndInvert(projection, -22.0, 16.0, 22.511032184577658, 138.94899164036798)
        checkProjectAndInvert(projection, 800.0, -800.0, -193.4977306320785, 464.9972618113396)
    }

    @Test
    fun mercator_various_projects_3() {
        val projection = mercatorProjection {
            translateX = -100.0
            translateY = 20.0
            rotate((-15.0).deg, 20.0.deg, .0.deg)
            scale = 1.0
            precision = .0
        }

        checkProjectAndInvert(projection, 84.0, 59.0, -98.18516654577843, 18.672644484723165)
        checkProjectAndInvert(projection, -22.0, 16.0, -100.74508864271014, 19.421488829918328)
        checkProjectAndInvert(projection, 800.0, -800.0, -99.63002236034055, 21.473889277898014)
    }
}
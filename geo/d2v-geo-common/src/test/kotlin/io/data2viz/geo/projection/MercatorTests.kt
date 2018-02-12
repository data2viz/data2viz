package io.data2viz.geo.projection

import io.data2viz.geo.MultiPoint
import io.data2viz.geo.Point
import io.data2viz.geo.path.geoPath
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.test.Test

class MercatorTests : TestBase() {

    val util = ProjectionTests()

    @Test
    fun mercator_clip_extent_null_sets_default_automatic_clip_extent_LEGACY() {
        val projection = MercatorProjection()
        projection.translate = doubleArrayOf(.0, .0)
        projection.scale = 1.0
        projection.clipExtent = null
        projection.precision = .0

        projection.clipExtent shouldBe null
    }

//    @Test
//    fun mercatormultiPoint_projection() {
//        val projection = MercatorProjection()
//        projection.translate = doubleArrayOf(.0, .0)
//        projection.scale = 1.0
//        projection.clipExtent = null
//        projection.precision = .0
//
//        val geoPath = geoPath(projection, svgPath())
//        val path:SvgPath = geoPath.path(Point(doubleArrayOf(1.0, 2.0))) as SvgPath
//        path.path shouldBe "M0.017453292519943295,-0.03491367596925956m0,4.5a4.5,4.5 0 1,1 0,-9a4.5,4.5 0 1,1 0,9z"
//
//        // M4.517453292519943,0.03491367596925945A4.5,4.5,0,1,1,-4.482546707480057,0.03491367596925945A4.5,4.5,0,1,1,4.517453292519943,0.03491367596925945
//        // M0.017453292519943295,-0.03491367596925956m0,4.5a4.5,4.5 0 1,1 0,-9a4.5,4.5 0 1,1 0,9z
//
////        path.path shouldBe "M0.017453292519943295,-0.03491367596925956m0,4.5a4.5,4.5 0 1,1 0,-9a4.5,4.5 0 1,1 0,9zM0.06981317007977318,-0.08737743596203343m0,4.5a4.5,4.5 0 1,1 0,-9a4.5,4.5 0 1,1 0,9z"
//    }

    @Test
    fun mercator_various_projects_1() {
        val projection = mercator {
            translate = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(20.0, 10.0, 30.0)
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(468.39738235470327, -301.9997264679594))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-41.232469642834104, -58.132750186605435))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(206.79103709446065, 151.98632483005113))
    }

    @Test
    fun mercator_various_projects_2() {
        val projection = mercator {
            translate = doubleArrayOf(40.0, 200.0)
            rotate = doubleArrayOf(5.0, .0, -30.0)
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(278.67805496267147, 119.04407321930081))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(22.511032184577658, 138.94899164036798))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(-193.4977306320785, 464.9972618113396))
    }

    @Test
    fun mercator_various_projects_3() {
        val projection = mercator {
            translate = doubleArrayOf(-100.0, 20.0)
            rotate = doubleArrayOf(-15.0, 20.0, .0)
            scale = 1.0
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(-98.18516654577843, 18.672644484723165))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-100.74508864271014, 19.421488829918328))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(-99.63002236034055, 21.473889277898014))
    }
}
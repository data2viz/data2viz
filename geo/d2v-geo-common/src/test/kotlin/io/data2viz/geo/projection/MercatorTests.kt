package io.data2viz.geo.projection

import io.data2viz.geo.Point
import io.data2viz.geo.path.geoPath
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.test.Test

class MercatorTests : TestBase() {

    @Test
    fun mercator_clip_extent_null_sets_default_automatic_clip_extent_LEGACY() {
//        val projection = Mercator()
//        projection.translate = doubleArrayOf(.0, .0)
//        projection.scale = 1.0
//        projection.clipExtent = null
//        projection.precision = .0
//
//        projection.clipExtent shouldBe null
//        val geoPath = geoPath(projection, svgPath())
//        val path:SvgPath = geoPath.path(Point(doubleArrayOf(2.0, 2.0, .0))) as SvgPath
//        println(path.path)
        //test.pathEqual(d3.geoPath(projection)({type: "Sphere"}), "M3.141593,-3.141593L3.141593,0L3.141593,3.141593L3.141593,3.141593L-3.141593,3.141593L-3.141593,3.141593L-3.141593,0L-3.141593,-3.141593L-3.141593,-3.141593L3.141593,-3.141593Z");
    }
}
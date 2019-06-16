package io.data2viz.geo.geojson.path

import io.data2viz.geo.projection.inDelta
import io.data2viz.geo.projection.pt
import io.data2viz.geojson.Point
import io.data2viz.test.TestBase
import kotlin.test.Test

class DistanceTests : TestBase() {
    @Test
    fun geoDistance_a_b_computes_the_great_arc_distance_in_radians_between_the_two_points_a_and_b() {

        geoDistance(arrayOf(0.0, 0.0), arrayOf(0.0, 0.0)) shouldBe 0.0


        inDelta(geoDistance(
            arrayOf(118.0 + 24.0 / 60.0, 33.0 + 57.0 / 60.0),
            arrayOf(73.0 + 47.0 / 60.0, 40.0 + 38.0 / 60.0)
        ), 3973.0 / 6371.0, 0.5)

    }

    @Test
    fun geoDistance_a_b_correctly_computes_small_distances() {

        val geoDistance = geoDistance(arrayOf(0.0, 0.0), arrayOf(0.0, 1.0 / 1000000000000.0))
        if(geoDistance <= 0.0) {
            error("geoDistance $geoDistance should more than 0.0")
        }

    }
}
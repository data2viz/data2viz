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

package io.data2viz.geo.geojson.path

import io.data2viz.geo.projection.inDelta
import io.data2viz.geo.projection.pt
import io.data2viz.geojson.Point
import io.data2viz.test.TestBase
import kotlin.test.Test

class DistanceTests : TestBase() {
    @Test
    fun geoDistance_a_b_computes_the_great_arc_distance_in_radians_between_the_two_points_a_and_b() {

        geoDistance(doubleArrayOf(0.0, 0.0), doubleArrayOf(0.0, 0.0)) shouldBe 0.0


        inDelta(geoDistance(
            doubleArrayOf(118.0 + 24.0 / 60.0, 33.0 + 57.0 / 60.0),
            doubleArrayOf(73.0 + 47.0 / 60.0, 40.0 + 38.0 / 60.0)
        ), 3973.0 / 6371.0, 0.5)

    }

    @Test
    fun geoDistance_a_b_correctly_computes_small_distances() {

        val geoDistance = geoDistance(doubleArrayOf(0.0, 0.0), doubleArrayOf(0.0, 1.0 / 1000000000000.0))
        if(geoDistance <= 0.0) {
            error("geoDistance $geoDistance should more than 0.0")
        }

    }
}
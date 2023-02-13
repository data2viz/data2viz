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


import io.data2viz.geo.geojson.path.GeoCircle
import io.data2viz.test.TestBase
import kotlin.test.Test

class CircleTests : TestBase() {

    @Test
    fun geoCircle_generates_a_polygon() {
        val circle = GeoCircle<Int>().circle()
        val coords = circle.coordinates[0]

        coords[0] shouldBeClose doubleArrayOf(-78.690067, -90.0)
        coords[1] shouldBeClose doubleArrayOf(-90.0, -84.0)
        coords[2] shouldBeClose doubleArrayOf(-90.0, -78.0)
        coords[3] shouldBeClose doubleArrayOf(-90.0, -72.0)
        coords[4] shouldBeClose doubleArrayOf(-90.0, -66.0)
        coords[5] shouldBeClose doubleArrayOf(-90.0, -60.0)
        coords[6] shouldBeClose doubleArrayOf(-90.0, -54.0)
        coords[7] shouldBeClose doubleArrayOf(-90.0, -48.0)
        coords[8] shouldBeClose doubleArrayOf(-90.0, -42.0)
        coords[9] shouldBeClose doubleArrayOf(-90.0, -36.0)
        coords[10] shouldBeClose doubleArrayOf(-90.0, -30.0)
        coords[11] shouldBeClose doubleArrayOf(-90.0, -24.0)
        coords[12] shouldBeClose doubleArrayOf(-90.0, -18.0)
        coords[13] shouldBeClose doubleArrayOf(-90.0, -12.0)
        coords[14] shouldBeClose doubleArrayOf(-90.0, -6.0)
        coords[15] shouldBeClose doubleArrayOf(-90.0, 0.0)
        coords[16] shouldBeClose doubleArrayOf(-90.0, 6.0)
        coords[17] shouldBeClose doubleArrayOf(-90.0, 12.0)
        coords[18] shouldBeClose doubleArrayOf(-90.0, 18.0)
        coords[19] shouldBeClose doubleArrayOf(-90.0, 24.0)
        coords[20] shouldBeClose doubleArrayOf(-90.0, 30.0)
        coords[21] shouldBeClose doubleArrayOf(-90.0, 36.0)
        coords[22] shouldBeClose doubleArrayOf(-90.0, 42.0)
        coords[23] shouldBeClose doubleArrayOf(-90.0, 48.0)
        coords[24] shouldBeClose doubleArrayOf(-90.0, 54.0)
        coords[25] shouldBeClose doubleArrayOf(-90.0, 60.0)
        coords[26] shouldBeClose doubleArrayOf(-90.0, 66.0)
        coords[27] shouldBeClose doubleArrayOf(-90.0, 72.0)
        coords[28] shouldBeClose doubleArrayOf(-90.0, 78.0)
        coords[29] shouldBeClose doubleArrayOf(-90.0, 84.0)
        coords[30] shouldBeClose doubleArrayOf(-89.5966588, 90.0)
        coords[31] shouldBeClose doubleArrayOf(90.0, 84.0)
        coords[32] shouldBeClose doubleArrayOf(90.0, 78.0)
        coords[33] shouldBeClose doubleArrayOf(90.0, 72.0)
        coords[34] shouldBeClose doubleArrayOf(90.0, 66.0)
        coords[35] shouldBeClose doubleArrayOf(90.0, 60.0)
        coords[36] shouldBeClose doubleArrayOf(90.0, 54.0)
        coords[37] shouldBeClose doubleArrayOf(90.0, 48.0)
        coords[38] shouldBeClose doubleArrayOf(90.0, 42.0)
        coords[39] shouldBeClose doubleArrayOf(90.0, 36.0)
        coords[40] shouldBeClose doubleArrayOf(90.0, 30.0)
        coords[41] shouldBeClose doubleArrayOf(90.0, 24.0)
        coords[42] shouldBeClose doubleArrayOf(90.0, 18.0)
        coords[43] shouldBeClose doubleArrayOf(90.0, 12.0)
        coords[44] shouldBeClose doubleArrayOf(90.0, 6.0)
        coords[45] shouldBeClose doubleArrayOf(90.0, 0.0)
        coords[46] shouldBeClose doubleArrayOf(90.0, -6.0)
        coords[47] shouldBeClose doubleArrayOf(90.0, -12.0)
        coords[48] shouldBeClose doubleArrayOf(90.0, -18.0)
        coords[49] shouldBeClose doubleArrayOf(90.0, -24.0)
        coords[50] shouldBeClose doubleArrayOf(90.0, -30.0)
        coords[51] shouldBeClose doubleArrayOf(90.0, -36.0)
        coords[52] shouldBeClose doubleArrayOf(90.0, -42.0)
        coords[53] shouldBeClose doubleArrayOf(90.0, -48.0)
        coords[54] shouldBeClose doubleArrayOf(90.0, -54.0)
        coords[55] shouldBeClose doubleArrayOf(90.0, -60.0)
        coords[56] shouldBeClose doubleArrayOf(90.0, -66.0)
        coords[57] shouldBeClose doubleArrayOf(90.0, -72.0)
        coords[58] shouldBeClose doubleArrayOf(90.0, -78.0)
        coords[59] shouldBeClose doubleArrayOf(90.0, -84.0)
        coords[60] shouldBeClose doubleArrayOf(89.5697683, -90.0)
    }

    @Test
    fun geoCircle_center_0_90() {
        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(.0, 90.0) }

        val circle = geoCircle.circle()
        val coords = circle.coordinates[0]

        val map = (360 downTo 0 step 6).map { arrayOf(if (it >= 180) it.toDouble() - 360 else it.toDouble(), .0) }
        map.forEachIndexed { index, ref ->
            ref shouldBeClose coords[index].toTypedArray()
        }
    }

    @Test
    fun geoCircle_center_45_45() {
        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(45.0, 45.0) }
        geoCircle.radius = { .0 }

        val circle = geoCircle.circle()
        circle.coordinates[0][0] shouldBeClose doubleArrayOf(45.0, 45.0)
    }

    @Test
    fun geoCircle_first_and_last_points_are_coincident() {
        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(.0, .0) }
        geoCircle.radius = { .002 }
        geoCircle.precision = { 45.0 }

        val circle = geoCircle.circle()
        val coords = circle.coordinates[0]
        coords[0] shouldBeClose coords[coords.lastIndex]
    }
}

package io.data2viz.geo.projection


import io.data2viz.geo.geojson.path.GeoCircle
import io.data2viz.test.TestBase
import kotlin.test.Test

class CircleTests : TestBase() {

    @Test
    fun geoCircle_generates_a_polygon() {
        val circle = GeoCircle<Int>().circle()
        val coords = circle.coordinates[0]

        coords[0] shouldBeClose arrayOf(-78.690067, -90.0)
        coords[1] shouldBeClose arrayOf(-90.0, -84.0)
        coords[2] shouldBeClose arrayOf(-90.0, -78.0)
        coords[3] shouldBeClose arrayOf(-90.0, -72.0)
        coords[4] shouldBeClose arrayOf(-90.0, -66.0)
        coords[5] shouldBeClose arrayOf(-90.0, -60.0)
        coords[6] shouldBeClose arrayOf(-90.0, -54.0)
        coords[7] shouldBeClose arrayOf(-90.0, -48.0)
        coords[8] shouldBeClose arrayOf(-90.0, -42.0)
        coords[9] shouldBeClose arrayOf(-90.0, -36.0)
        coords[10] shouldBeClose arrayOf(-90.0, -30.0)
        coords[11] shouldBeClose arrayOf(-90.0, -24.0)
        coords[12] shouldBeClose arrayOf(-90.0, -18.0)
        coords[13] shouldBeClose arrayOf(-90.0, -12.0)
        coords[14] shouldBeClose arrayOf(-90.0, -6.0)
        coords[15] shouldBeClose arrayOf(-90.0, 0.0)
        coords[16] shouldBeClose arrayOf(-90.0, 6.0)
        coords[17] shouldBeClose arrayOf(-90.0, 12.0)
        coords[18] shouldBeClose arrayOf(-90.0, 18.0)
        coords[19] shouldBeClose arrayOf(-90.0, 24.0)
        coords[20] shouldBeClose arrayOf(-90.0, 30.0)
        coords[21] shouldBeClose arrayOf(-90.0, 36.0)
        coords[22] shouldBeClose arrayOf(-90.0, 42.0)
        coords[23] shouldBeClose arrayOf(-90.0, 48.0)
        coords[24] shouldBeClose arrayOf(-90.0, 54.0)
        coords[25] shouldBeClose arrayOf(-90.0, 60.0)
        coords[26] shouldBeClose arrayOf(-90.0, 66.0)
        coords[27] shouldBeClose arrayOf(-90.0, 72.0)
        coords[28] shouldBeClose arrayOf(-90.0, 78.0)
        coords[29] shouldBeClose arrayOf(-90.0, 84.0)
        coords[30] shouldBeClose arrayOf(-89.5966588, 90.0)
        coords[31] shouldBeClose arrayOf(90.0, 84.0)
        coords[32] shouldBeClose arrayOf(90.0, 78.0)
        coords[33] shouldBeClose arrayOf(90.0, 72.0)
        coords[34] shouldBeClose arrayOf(90.0, 66.0)
        coords[35] shouldBeClose arrayOf(90.0, 60.0)
        coords[36] shouldBeClose arrayOf(90.0, 54.0)
        coords[37] shouldBeClose arrayOf(90.0, 48.0)
        coords[38] shouldBeClose arrayOf(90.0, 42.0)
        coords[39] shouldBeClose arrayOf(90.0, 36.0)
        coords[40] shouldBeClose arrayOf(90.0, 30.0)
        coords[41] shouldBeClose arrayOf(90.0, 24.0)
        coords[42] shouldBeClose arrayOf(90.0, 18.0)
        coords[43] shouldBeClose arrayOf(90.0, 12.0)
        coords[44] shouldBeClose arrayOf(90.0, 6.0)
        coords[45] shouldBeClose arrayOf(90.0, 0.0)
        coords[46] shouldBeClose arrayOf(90.0, -6.0)
        coords[47] shouldBeClose arrayOf(90.0, -12.0)
        coords[48] shouldBeClose arrayOf(90.0, -18.0)
        coords[49] shouldBeClose arrayOf(90.0, -24.0)
        coords[50] shouldBeClose arrayOf(90.0, -30.0)
        coords[51] shouldBeClose arrayOf(90.0, -36.0)
        coords[52] shouldBeClose arrayOf(90.0, -42.0)
        coords[53] shouldBeClose arrayOf(90.0, -48.0)
        coords[54] shouldBeClose arrayOf(90.0, -54.0)
        coords[55] shouldBeClose arrayOf(90.0, -60.0)
        coords[56] shouldBeClose arrayOf(90.0, -66.0)
        coords[57] shouldBeClose arrayOf(90.0, -72.0)
        coords[58] shouldBeClose arrayOf(90.0, -78.0)
        coords[59] shouldBeClose arrayOf(90.0, -84.0)
        coords[60] shouldBeClose arrayOf(89.5697683, -90.0)
    }

    @Test
    fun geoCircle_center_0_90() {
        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(.0, 90.0) }

        val circle = geoCircle.circle()
        val coords = circle.coordinates[0]

        val map = (360 downTo 0 step 6).map { arrayOf(if (it >= 180) it.toDouble() - 360 else it.toDouble(), .0) }
        map.forEachIndexed { index, ref ->
            ref shouldBeClose coords[index]
        }
    }

    @Test
    fun geoCircle_center_45_45() {
        val geoCircle = GeoCircle<Int>()
        geoCircle.center = { doubleArrayOf(45.0, 45.0) }
        geoCircle.radius = { .0 }

        val circle = geoCircle.circle()
        circle.coordinates[0][0] shouldBeClose arrayOf(45.0, 45.0)
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
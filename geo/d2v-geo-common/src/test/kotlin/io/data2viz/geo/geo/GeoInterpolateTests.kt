package io.data2viz.geo.geo

import io.data2viz.geo.stream.geoInterpolate
import io.data2viz.test.TestBase
import kotlin.test.Test

class GeoInterpolateTests : TestBase() {

    @Test
    fun geoInterpolate_a_a_returns_a() {
        geoInterpolate(
        doubleArrayOf(140.63289, -29.95101),
        doubleArrayOf(140.63289, -29.95101)
    )(.5) shouldBeClose  doubleArrayOf(140.63289, -29.95101)
    }

    @Test
    fun geoInterpolate_a_b_returns_the_expected_values_when_a_and_b_lie_on_the_equator() {
        geoInterpolate(doubleArrayOf(10.0, .0), doubleArrayOf(20.0, .0))(.5) shouldBeClose doubleArrayOf(15.0, .0)
    }

    @Test
    fun geoInterpolate_a_b_returns_the_expected_values_when_a_and_b_lie_on_the_meridian() {
        geoInterpolate(doubleArrayOf(10.0, -20.0), doubleArrayOf(10.0, 40.0))(.5) shouldBeClose doubleArrayOf(
            10.0,
            10.0
        )
    }

    @Test
    fun geoInterpolate_a_b_various_tests() {
        geoInterpolate(doubleArrayOf(60.0, 30.0), doubleArrayOf(20.0, -30.0))(.8) shouldBeClose doubleArrayOf(
            28.7502586288682,
            -18.227569608526814
        )
        geoInterpolate(doubleArrayOf(60.0, 30.0), doubleArrayOf(20.0, -30.0))(.2) shouldBeClose doubleArrayOf(
            51.2497413711318,
            18.227569608526814
        )
        geoInterpolate(doubleArrayOf(-40.0, -30.0), doubleArrayOf(20.0, -30.0))(.8) shouldBeClose doubleArrayOf(
            8.311428845590223,
            -32.33006949315247
        )
        geoInterpolate(
        doubleArrayOf(-40.0, -30.0),
        doubleArrayOf(20.0, -30.0)
    )(.2) shouldBeClose doubleArrayOf(-28.31142884559023, -32.33006949315248)
    }
}
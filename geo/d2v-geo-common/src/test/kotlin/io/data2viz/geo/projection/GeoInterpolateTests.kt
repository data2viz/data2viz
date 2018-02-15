package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class GeoInterpolateTests : TestBase() {

    @Test
    fun geoInterpolate_a_a_returns_a_LEGACY() {
        geoInterpolate(pt(140.63289, -29.95101), pt(140.63289, -29.95101))(.5) shouldBe pt(140.63289, -29.95101)
    }

    @Test
    fun geoInterpolate_a_b_returns_the_expected_values_when_a_and_b_lie_on_the_equator_LEGACY() {
        geoInterpolate(pt(10.0, .0), pt(20.0, .0))(.5) shouldBeClose pt(15.0, .0)
    }

    @Test
    fun geoInterpolate_a_b_returns_the_expected_values_when_a_and_b_lie_on_the_meridian_LEGACY() {
        geoInterpolate(pt(10.0, -20.0), pt(10.0, 40.0))(.5) shouldBeClose pt(10.0, 10.0)
    }

    @Test
    fun geoInterpolate_a_b_various_tests() {
        geoInterpolate(pt(60.0, 30.0), pt(20.0, -30.0))(.8) shouldBeClose pt(28.7502586288682, -18.227569608526814)
        geoInterpolate(pt(60.0, 30.0), pt(20.0, -30.0))(.2) shouldBeClose pt(51.2497413711318, 18.227569608526814)
        geoInterpolate(pt(-40.0, -30.0), pt(20.0, -30.0))(.8) shouldBeClose pt(8.311428845590223, -32.33006949315247)
        geoInterpolate(pt(-40.0, -30.0), pt(20.0, -30.0))(.2) shouldBeClose pt(-28.31142884559023, -32.33006949315248)
    }
}
package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.rotation
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class RotationTests : TestBase() {

    @Test
    fun a_rotation_of_90_0_only_rotates_longitude() {
        val point = doubleArrayOf(.0, .0)
        val rotation = rotation(90.0.deg, .0.deg)
        rotation.projectLambda(point[0], point[1]) shouldBeClose 90.0
        rotation.projectPhi(point[0], point[1]) shouldBeClose .0
    }

    @Test
    fun a_rotation_of_90_0_wraps_around_when_crossing_the_antimeridian() {
        val point = doubleArrayOf(150.0, .0)
        val rotation = rotation(90.0.deg, .0.deg)
        rotation.projectLambda(point[0], point[1]) shouldBeClose -120.0
        rotation.projectPhi(point[0], point[1]) shouldBeClose .0
    }

    @Test
    fun a_rotation_of_minus_45_45_rotates_latitude_and_longitude() {
        val point = doubleArrayOf(.0, .0)
        val rotation = rotation((-45.0).deg, 45.0.deg)

        rotation.projectLambda(point[0], point[1]) shouldBeClose -54.73561
        rotation.projectPhi(point[0], point[1]) shouldBeClose 30.0
    }

    @Test
    fun a_rotation_of_minus_45_45_inverse_rotation_of_latitude_and_longitude() {

        val point = rotation((-45.0).deg, 45.0.deg).invert(-54.73561, 30.0)
        point[0] shouldBeClose .0
        point[1] shouldBeClose .0
    }
}


//tape("the identity rotation constrains longitudes to [-180°, 180°]", function(test) {
//    var rotate = d3.geoRotation([0, 0]);
//    test.equal(rotate([180,0])[0], 180);
//    test.equal(rotate([-180,0])[0], -180);
//    test.equal(rotate([360,0])[0], 0);
//    test.inDelta(rotate([2562,0])[0], 42, 1e-10);
//    test.inDelta(rotate([-2562,0])[0], -42, 1e-10);
//    test.end();
//});

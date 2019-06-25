package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.RotationProjector
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class RotationTests : TestBase() {

    @Test
    fun a_rotation_of_90_0_only_rotates_longitude() {
        val point = doubleArrayOf(.0, .0)
        val rotation = RotationProjector(90.0.deg, .0.deg)
        val projected = rotation.project(point[0], point[1])
        projected[0] shouldBeClose 90.0
        projected[1] shouldBeClose .0
    }

    @Test
    fun a_rotation_of_90_0_wraps_around_when_crossing_the_antimeridian() {
        val point = doubleArrayOf(150.0, .0)
        val rotation = RotationProjector(90.0.deg, .0.deg)
        val projected = rotation.project(point[0], point[1])
        projected[0] shouldBeClose -120.0
        projected[1] shouldBeClose .0
    }

    @Test
    fun a_rotation_of_minus_45_45_rotates_latitude_and_longitude() {
        val point = doubleArrayOf(.0, .0)
        val rotation = RotationProjector((-45.0).deg, 45.0.deg)
        val projected = rotation.project(point[0], point[1])
        projected[0] shouldBeClose -54.73561
        projected[1] shouldBeClose 30.0
    }

    @Test
    fun a_rotation_of_minus_45_45_inverse_rotation_of_latitude_and_longitude() {

        val point = RotationProjector((-45.0).deg, 45.0.deg).invert(-54.73561, 30.0)
        point[0] shouldBeClose .0
        point[1] shouldBeClose .0
    }

    @Test
    fun the_identity_rotation_constrains_longitudes_to_minus_180_180() {

        val rotate = RotationProjector(0.deg, 0.deg)

        rotate.project(180.0, 0.0)[0] shouldBe 180.0
        rotate.project(-180.0, 0.0)[0] shouldBe -180.0
        rotate.project(360.0, 0.0)[0] shouldBe 0.0
        inDelta(rotate.project(2562.0, 0.0)[0], 42.0, 1.0 / 10000000000)
        inDelta(rotate.project(-2562.0, 0.0)[0], -42.0, 1.0 / 10000000000)
    }
}
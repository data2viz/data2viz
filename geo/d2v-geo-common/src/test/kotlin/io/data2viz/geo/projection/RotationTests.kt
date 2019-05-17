package io.data2viz.geo.projection

import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class RotationTests : TestBase() {

    @Test
    fun a_rotation_of_90_0_only_rotates_longitude() {
        val rotation = rotation(arrayOf(90.0.deg, .0.deg)).project(.0, .0)
        rotation[0] shouldBeClose 90.0
        rotation[1] shouldBeClose .0
    }

    @Test
    fun a_rotation_of_90_0_wraps_around_when_crossing_the_antimeridian() {
        val rotation = rotation(arrayOf(90.0.deg, .0.deg)).project(150.0, .0)
        rotation[0] shouldBeClose -120.0
        rotation[1] shouldBeClose .0
    }

    @Test
    fun a_rotation_of_minus_45_45_rotates_latitude_and_longitude() {
        val rotation = rotation(arrayOf((-45.0).deg, 45.0.deg)).project(.0, .0)
        rotation[0] shouldBeClose -54.73561
        rotation[1] shouldBeClose 30.0
    }

    @Test
    fun a_rotation_of_minus_45_45_inverse_rotation_of_latitude_and_longitude() {
        val rotation = rotation(arrayOf((-45.0).deg, 45.0.deg)).invert(-54.73561, 30.0)
        rotation[0] shouldBeClose .0
        rotation[1] shouldBeClose .0
    }
}
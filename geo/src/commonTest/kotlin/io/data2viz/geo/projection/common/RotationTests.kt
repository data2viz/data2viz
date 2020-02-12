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

package io.data2viz.geo.projection

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.projection.common.RotationProjector
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class RotationTests : TestBase() {

    @Test
    fun a_rotation_of_90_0_only_rotates_longitude() {
        val rotation = RotationProjector(90.0.deg, .0.deg)
        val projected = rotation.project(GeoPoint())
        projected.lon.deg shouldBeClose 90.0
        projected.lat.deg shouldBeClose .0
    }

    @Test
    fun a_rotation_of_90_0_wraps_around_when_crossing_the_antimeridian() {
        val point = GeoPoint(150.deg, .0.deg)
        val rotation = RotationProjector(90.0.deg, .0.deg)
        val projected = rotation.project(point)
        projected.lon.deg shouldBeClose -120.0
        projected.lat.deg shouldBeClose .0
    }

    @Test
    fun a_rotation_of_minus_45_45_rotates_latitude_and_longitude() {
        val rotation = RotationProjector((-45.0).deg, 45.0.deg)
        val projected = rotation.project(GeoPoint())
        projected.lon.deg shouldBeClose -54.73561
        projected.lat.deg shouldBeClose 30.0
    }

    @Test
    fun a_rotation_of_minus_45_45_inverse_rotation_of_latitude_and_longitude() {

        val point = RotationProjector((-45.0).deg, 45.0.deg).invert( GeoPoint(-54.73561.deg, 30.0.deg))
        point.lat.deg shouldBeClose .0
        point.lon.deg shouldBeClose .0
    }

    @Test
    fun the_identity_rotation_constrains_longitudes_to_minus_180_180() {

        val rotate = RotationProjector(0.deg, 0.deg)
        rotate.project(GeoPoint(180.deg, 0.deg)).lon.deg shouldBe 180.0
        rotate.project(GeoPoint(-180.deg, 0.deg)).lon.deg shouldBe -180.0
        rotate.project(GeoPoint(360.deg, 0.deg)).lon.deg shouldBe 0.0

        inDelta(rotate.project(GeoPoint(2562.deg, 0.deg)).lon.deg, 42.0, 1.0 / 10000000000)
        inDelta(rotate.project(GeoPoint(-2562.deg, 0.deg)).lon.deg, -42.0, 1.0 / 10000000000)
    }
}
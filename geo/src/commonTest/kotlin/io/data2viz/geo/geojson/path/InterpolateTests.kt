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

import io.data2viz.geo.geojson.geoInterpolate
import io.data2viz.test.TestBase
import kotlin.test.Test

class InterpolateTests : TestBase() {

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

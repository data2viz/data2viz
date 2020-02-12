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
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class AlbersUsaTests : TestBase() {

    

    @Test
    fun point_invert_returns_the_expected_result() {
        val albersUsa = albersUSAProjection()

        // 0.1 precision taken from d3
        checkProjectAndInvert(albersUsa, -122.4194, 37.7749, 107.4, 214.1, 0.1) // San Francisco, CA
        checkProjectAndInvert(albersUsa, -74.0059, 40.7128, 794.6, 176.5, 0.1) // New York, NY
        checkProjectAndInvert(albersUsa, -95.9928, 36.1540, 488.8, 298.0, 0.1) // Tulsa, OK


        checkProject(albersUsa, -149.9003, 61.2181, 171.2, 446.9, 0.1) // Anchorage, AK
        checkProject(albersUsa, -157.8583, 21.3069, 298.5, 451.0, 0.1) // Honolulu, HI
        val projectedParis = albersUsa.project(GeoPoint(2.3522.deg, 48.8566.deg)) // Paris, France

        projectedParis.x shouldEqual Double.NaN
        projectedParis.y shouldEqual Double.NaN
    }
}

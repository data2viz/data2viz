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

import io.data2viz.geo.geometry.calculateSvgPath
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test


class RotateProjectionTests : TestBase() {


    @Test
    fun project_rotation__of_degenerate_polygon_should_not_break() {
        val projection = mercatorProjection {
            rotate((-134.300).deg, 25.776.deg, rotateGamma)
            scale = 750.0
            translate(0.0, 0.0)
        }


        val actualSvgPath = calculateSvgPath(
            projection, """{
            "type": "Polygon",
            "coordinates": [
            [
                [125.67351590459046, -14.17673705310531],
                [125.67351590459046, -14.173276873687367],
                [125.67351590459046, -14.173276873687367],
                [125.67351590459046, -14.169816694269425],
                [125.67351590459046, -14.17673705310531]
            ]
            ]
        }""".toGeoJsonObject())
        actualSvgPath shouldBe "M-111.644162,-149.157654L-111.647235,-149.203744L-111.647235,-149.203744L-111.650307,-149.249835Z"
    }

}
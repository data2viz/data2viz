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

import io.data2viz.geojson.Point
import io.data2viz.geojson.lat
import io.data2viz.geojson.lon
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.test.CurrentPlatform
import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class GeoJsonTests : TestBase() {

    @Test
    fun pointJson() {
        if (CurrentPlatform.isIOS) return
        val json = """{"type":"Point", "coordinates":[1.0, 2.0]}"""
        val p = json.toGeoJsonObject() as Point
        assertEquals(expected = 1.0, actual = p.coordinates.lon)
        assertEquals(expected = 2.0, actual = p.coordinates.lat)
    }

}

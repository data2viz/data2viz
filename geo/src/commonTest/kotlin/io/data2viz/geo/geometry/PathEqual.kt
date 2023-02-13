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

package io.data2viz.geo.geometry

import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.test.TestBase
import io.data2viz.test.matchers.Matcher
import io.data2viz.test.matchers.Matchers
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToLong

fun TestBase.calculateSvgPath(projection: Projection, sourceGeoJsonObject: GeoJsonObject, precision: Int = 6, pointRadius : Double? = null): String {
    val path = PathGeom()

    val geoPath = geoPath(projection, path)
    if(pointRadius != null) {
        geoPath.pointRadius = pointRadius
    }
    geoPath.project(sourceGeoJsonObject)

    return path.svgPath.round()
}

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

package io.data2viz.geo

import io.data2viz.geo.projection.*
import io.data2viz.geo.projection.common.Projection


object Geo {
    object Projections {
        fun Albers(init: Projection.() -> Unit = {})            = albersProjection(init)
        fun AlbersUSA(init: Projection.() -> Unit = {})         = albersUSAProjection(init)
        fun AzimuthalEqualArea(init: Projection.() -> Unit = {})         = azimuthalEqualAreaProjection(init)
        fun AzimuthalEquidistant(init: Projection.() -> Unit = {})         = azimuthalEquidistant(init)
//        fun Conic(init: ConicProjection.() -> Unit = {})         = conicProjection(init)
        fun EqualEarth(init: Projection.() -> Unit = {})        = equalEarthProjection(init)
        fun Equirectangular(init: Projection.() -> Unit = {})   = equirectangularProjection(init)
        fun Identity(init: Projection.() -> Unit = {})          = identityProjection(init)
        fun Mercator(init: Projection.() -> Unit = {})          = mercatorProjection(init)
        fun NaturalEarth(init: Projection.() -> Unit = {})      = naturalEarthProjection(init)
        fun Orthographic(init: Projection.() -> Unit = {})      = orthographicProjection(init)
        fun Stereographic(init: Projection.() -> Unit = {})     = stereographicProjection(init)
        fun TransverseMercator(init: Projection.() -> Unit = {})     = transverseMercatorProjection(init)
    }
}


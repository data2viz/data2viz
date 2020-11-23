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


public object Geo {
    public object Projections {
        public fun Albers(init: Projection.() -> Unit = {})                 : ConicProjection = albersProjection(init)
        public fun AlbersUSA(init: Projection.() -> Unit = {})              : AlbersUSAProjection = albersUSAProjection(init)
        public fun AzimuthalEqualArea(init: Projection.() -> Unit = {})     : Projection = azimuthalEqualAreaProjection(init)
        public fun AzimuthalEquidistant(init: Projection.() -> Unit = {})   : Projection = azimuthalEquidistant(init)
//      public   fun Conic(init: ConicProjection.() -> Unit = {})         = :Unit conicProjection(init)
        public fun EqualEarth(init: Projection.() -> Unit = {})             : Projection = equalEarthProjection(init)
        public fun Equirectangular(init: Projection.() -> Unit = {})        : Projection = equirectangularProjection(init)
        public fun Identity(init: Projection.() -> Unit = {})               : Projection = identityProjection(init)
        public fun Mercator(init: Projection.() -> Unit = {})               : MercatorProjection = mercatorProjection(init)
        public fun NaturalEarth(init: Projection.() -> Unit = {})           : Projection = naturalEarthProjection(init)
        public fun Orthographic(init: Projection.() -> Unit = {})           : Projection = orthographicProjection(init)
        public fun Stereographic(init: Projection.() -> Unit = {})          : Projection = stereographicProjection(init)
        public fun TransverseMercator(init: Projection.() -> Unit = {})     : TransverseMercatorProjection = transverseMercatorProjection(init)
    }
}


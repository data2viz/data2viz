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

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.deg
import io.data2viz.math.rad
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


fun gnomonicProjection(init: Projection.() -> Unit = {}) =
    projection(GnomonicProjector()) {
        anglePreClip = 60.0.deg
        scale = 144.049
        init()
    }

class GnomonicProjector : Projector<GeoJsonPoint, Point3D> {

    override fun project(point: GeoJsonPoint): Point3D {
        val cy = point.lat.cos
        val k = point.lon.cos * cy
        return Point3D(
            cy * point.lon.sin / k,
            point.lat.sin / k
        )
    }

    override fun invert(point: Point3D): GeoJsonPoint = azimuthalInvertPoint(::atan)(point)

}
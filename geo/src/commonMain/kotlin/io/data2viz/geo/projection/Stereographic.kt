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
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.deg
import io.data2viz.math.rad
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


/**
 * @see StereographicProjector
 */
fun stereographicProjection(init: Projection.() -> Unit = {}) =
    projection(StereographicProjector()) {
        scale = 250.0
        anglePreClip = (142.0).deg
        init()
    }


private fun doubleAtan(d: Double) = 2 * atan(d)

/**
 * The stereographic projection.
 */
class StereographicProjector : Projector<GeoJsonPoint, Point3D> {

    override fun project(point: GeoJsonPoint): Point3D {
        val cosPhi = point.lat.cos
        val k = 1 + point.lon.cos * cosPhi
        return Point3D(
            cosPhi * point.lon.sin / k,
            point.lat.sin/ k
        )
    }

    override fun invert(point: Point3D): GeoJsonPoint {
        val arr = azimuthalInvert(::doubleAtan)(point.x, point.y)
        return GeoJsonPoint(arr[0].rad, arr[1].rad)
    }


}
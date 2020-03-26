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
import io.data2viz.geo.Point3D
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.rad

fun equirectangularProjection(init: Projection.() -> Unit = {}) =
    projection(EquirectangularProjector) {
        scale = 152.63
        init()
    }

/**
 * Cylindrical projections projector the sphere onto a containing cylinder,
 * and then unroll the cylinder onto the plane.
 * Pseudocylindrical projections are a generalization of cylindrical projections.
 * The equirectangular (plate carrée) projection.
 */
internal object EquirectangularProjector : Projector<GeoPoint, Point3D> {
    override fun project(point: GeoPoint) = Point3D(point.lon.rad, point.lat.rad)
    override fun invert(point: Point3D) = GeoPoint(point.x.rad, point.y.rad)
}

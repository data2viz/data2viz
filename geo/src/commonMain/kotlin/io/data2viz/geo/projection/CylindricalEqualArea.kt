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
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.rad
import kotlin.math.asin
import kotlin.math.cos

/**
 * Simple cylindrical equal area
 * Used in [ConicEqualAreaBaseConditionalProjector]
 */
class CylindricalEqualAreaProjector() : Projector<GeoPoint, Point3D> {

    constructor(phi:Double) : this() {
        phi0 = phi
    }

    var phi0:Double = 0.0
    set(value) {
        field = value
        recalculate()
    }

    var cosPhi0: Double = 0.0

    private fun recalculate() {
        cosPhi0 = cos(phi0)
    }

    override fun project(point: GeoPoint) = Point3D(point.lon.rad * cosPhi0, point.lat.sin / cosPhi0)

    override fun invert(point: Point3D) = GeoPoint( (point.x / cosPhi0).rad, asin(point.y * cosPhi0).rad)

}

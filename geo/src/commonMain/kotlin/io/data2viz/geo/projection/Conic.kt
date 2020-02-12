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
import io.data2viz.geo.projection.common.ProjectorProjection
import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.rad

fun conicProjection(projection: ConicProjector, init: ConicProjection.() -> Unit = {}) =
    ConicProjection(projection).apply(init)


interface ConicProjector : Projector<GeoPoint, Point3D> {
    /**
     * Minimum parallel angle value in radians
     *
     * @see ConicProjection.parallelsMin
     */
    var phi0: Double
    /**
     * Maximum parallel angle value in radians
     *
     * @see ConicProjection.parallelsMax
     */
    var phi1: Double
}

/**
 * Conic projections projector the sphere onto a cone, and then unroll the cone onto the plane.
 * Conic projections have two standard parallels.
 */

class ConicProjection(val conicProjector: ConicProjector) : ProjectorProjection(conicProjector) {
    private var phi0: Double = 0.0
    private var phi1: Double = PI / 3.0


    /**
     * The two standard parallels that define the map layout in conic projections.
     *
     * Useful when you want to specify both min & max values with less calculations
     *
     * @see ConicProjector.phi0
     * @see ConicProjector.phi1
     * @see parallelsMin
     * @see parallelsMax
     */
    fun parallels(min: Angle, max: Angle) {
        parallelsMin = min
        parallelsMax = max
    }


    /**
     * Correspond to minimum parallel
     *
     * @see ConicProjector.phi0
     * @see parallels
     */
    var parallelsMin: Angle
        get() = phi0.rad
        set(value) {
            phi0 = value.rad
            conicProjector.phi0 = phi0
        }

    /**
     * Correspond to maximum parallel
     *
     * @see ConicProjector.phi1
     * @see parallels
     */
    var parallelsMax: Angle
        get() = phi1.rad
        set(value) {
            phi1 = value.rad
            conicProjector.phi1 = phi1
        }

}

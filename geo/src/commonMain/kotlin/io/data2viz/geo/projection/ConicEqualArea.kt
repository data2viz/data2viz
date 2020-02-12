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
import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import io.data2viz.math.rad
import kotlin.math.*

/**
 * The [ConicEqualAreaProjector]. See also conic.parallels.
 * For some parallels values use [CylindricalEqualAreaProjector]
 *
 * @see BaseConditionalProjector
 */
fun conicEqualAreaProjection(init: ConicProjection.() -> Unit = {}) =
    conicProjection(ConicEqualAreaBaseConditionalProjector()) {
        scale = 155.424
        center(0.0.deg, 33.6442.deg)
        init()
    }


internal class ConicEqualAreaBaseConditionalProjector(
    private val conicEqualAreaProjector: ConicEqualAreaProjector = ConicEqualAreaProjector(),
    private val cylindricalEqualAreaProjector: CylindricalEqualAreaProjector = CylindricalEqualAreaProjector(
        conicEqualAreaProjector.phi0
    )
) : ConicProjector, BaseConditionalProjector() {
    override var phi0: Double
        get() = conicEqualAreaProjector.phi0
        set(value) {
            conicEqualAreaProjector.phi0 = value
        }
    override var phi1: Double
        get() = conicEqualAreaProjector.phi1
        set(value) {
            conicEqualAreaProjector.phi1 = value
        }

    override val baseProjector: Projector<GeoPoint, Point3D>
        get() = cylindricalEqualAreaProjector

    override val nestedProjector: Projector<GeoPoint, Point3D>
        get() = conicEqualAreaProjector

    override val isNeedUseBaseProjector: Boolean
        get() = conicEqualAreaProjector.isPossibleToUseProjector
}


class ConicEqualAreaProjector : ConicProjector, Projector<GeoPoint, Point3D> {

    override var phi0: Double = 0.0
        set(value) {
            field = value
            recalculate()
        }
    override var phi1: Double = io.data2viz.math.PI / 3.0
        set(value) {
            field = value
            recalculate()
        }

    private var sy0 = sy0()
    private var n = n()
    private var c = c()
    private var r0 = r0()
    var isPossibleToUseProjector = isPossibleToUse()
        private set

    private fun recalculate() {
        sy0 = sy0()
        n = n()
        c = c()
        r0 = r0()
        isPossibleToUseProjector = isPossibleToUse()
    }

    private fun isPossibleToUse() = abs(n) < EPSILON

    private fun r0() = sqrt(c) / n

    private fun c() = 1 + sy0 * (2 * n - sy0)

    private fun n() = (sy0 + sin(phi1)) / 2

    private fun sy0() = sin(phi0)

    override fun invert(point: Point3D): GeoPoint {
        val r0y = r0y(point.y)
        return GeoPoint(
            (atan2(point.x, abs(r0y)) / n * sign(r0y)).rad,
            (asin((c - (point.x * point.x + r0y * r0y) * n * n) / (2 * n))).rad
        )
    }

    override fun project(point: GeoPoint): Point3D {
        val r = r(point.lat.rad)

        return Point3D(
            r * sin(point.lon.rad * n),
            r0 - r * cos(point.lon.rad * n)
        )
    }

    private fun r0y(phi: Double) = r0 - phi

    private fun r(phi: Double) = sqrt(c - 2 * n * sin(phi)) / n
}
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
import io.data2viz.geo.projection.common.BaseConditionalProjector
import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import io.data2viz.math.rad
import kotlin.math.*

/**
 * The [ConicConformalProjector].
 * The parallels default to [30°, 30°] resulting in flat top.
 * For some parallels values use [MercatorProjector]
 *
 * @see BaseConditionalProjector
 */

fun conicConformalProjection(init: ConicProjection.() -> Unit = {}) =
    conicProjection(ConicConformalBaseConditionalProjector()) {
        scale = 109.5
        parallels(30.0.deg, 30.0.deg)
        init()
    }

internal fun tany(y: Double): Double {
    return tan((HALFPI + y) / 2)
}

internal class ConicConformalBaseConditionalProjector(
    private val conicConformalProjector: ConicConformalProjector = ConicConformalProjector(),
    private val mercatorProjector: MercatorProjector = MercatorProjector()
) : ConicProjector, BaseConditionalProjector() {

    override var phi0: Double
        get() = conicConformalProjector.phi0
        set(value) {
            conicConformalProjector.phi0 = value
        }

    override var phi1: Double
        get() = conicConformalProjector.phi1
        set(value) {
            conicConformalProjector.phi1 = value
        }

    override val baseProjector: Projector<GeoJsonPoint, Point3D>
        get() = mercatorProjector

    override val nestedProjector: Projector<GeoJsonPoint, Point3D>
        get() = conicConformalProjector

    override val isNeedUseBaseProjector: Boolean
        get() = conicConformalProjector.isPossibleToUseProjector
}

class ConicConformalProjector : ConicProjector, Projector<GeoJsonPoint, Point3D> {


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

    private var cy0 = cy0()
    private var n = n()
    private var f = f()
    var isPossibleToUseProjector = isPossibleToUse()
        private set


    private fun recalculate() {

        cy0 = cy0()
        n = n()
        f = f()
        isPossibleToUseProjector = isPossibleToUse()
    }

    private fun isPossibleToUse() = (n == 0.0 || n == Double.NaN)

    private fun f() = cy0 * (tany(phi0).pow(n)) / n

    private fun n(): Double {
        return if (phi0.equals(phi1)) {
            sin(phi0)
        } else {
            log(cy0, cos(phi1)) / log(tany(phi1), tany(phi0))
        }
    }

    private fun cy0() = cos(phi0)

    override fun invert(point: Point3D): GeoJsonPoint {

        val fy = fy(point.y)
        val rInvert = rInvert(point.x, fy)
        return GeoJsonPoint(
            (atan2(point.x, abs(fy)) / n * sign(fy)).rad,
            (2 * atan((f / rInvert).pow(1 / n)) - HALFPI).rad
        )

    }

    private fun rInvert(x: Double, fy: Double) = sign(n) * sqrt(x * x + fy * fy)

    override fun project(point: GeoJsonPoint): Point3D {
        val convertedPhi = convertPhi(point.lat.rad)
        val r = r(convertedPhi)
        return Point3D(
            r * sin(n * point.lon.rad),
            f - r * cos(n * point.lon.rad)
        )
    }


    private fun fy(phi: Double) = f - phi

    private fun r(convertedPhi: Double) = f / tany(convertedPhi).pow(n)

    private fun convertPhi(phi: Double): Double {
        return if (f > 0) {
            if (phi < -HALFPI + EPSILON) {
                -HALFPI + EPSILON
            } else {
                phi
            }
        } else {
            if (phi > HALFPI - EPSILON) {
                HALFPI - EPSILON
            } else {
                phi
            }
        }
    }
}


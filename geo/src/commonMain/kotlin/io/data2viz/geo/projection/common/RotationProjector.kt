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

package io.data2viz.geo.projection.common

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.math.*
import kotlin.math.*
import kotlin.math.PI

/**
 * Create a rotation [Projector]
 *
 * See https://github.com/d3/d3-geo/blob/master/src/rotation.js
 * TODO Why gamma is nullable? By default all rotation could be 0.0.
 */
class RotationProjector(
    lambda: Angle = 0.deg,
    phi: Angle = 0.deg,
    gamma: Angle = 0.deg) : Projector<GeoJsonPoint, GeoJsonPoint> {

    val rotator =
        createRotateRadiansProjector(
            lambda.rad,
            phi.rad,
            gamma.rad
        )

    override fun project(point: GeoJsonPoint): GeoJsonPoint = rotator.project(point)

    override fun invert(point: GeoJsonPoint): GeoJsonPoint = rotator.invert(point)
}


internal object IdentityRotationProjector : Projector<GeoJsonPoint, GeoJsonPoint> {

    override fun project(point: GeoJsonPoint): GeoJsonPoint {
        return point.copy(
            lon = normalizeLon(point.lon)
        )
    }

    override fun invert(point: GeoJsonPoint): GeoJsonPoint {
        return point.copy(
            lon = normalizeLon(point.lon)
        )
    }
}

internal class RotationLambdaProjector(val deltaLambda: Double): Projector<GeoJsonPoint, GeoJsonPoint> {

    override fun project(point: GeoJsonPoint): GeoJsonPoint = point.copy(lon = normalizeLon(point.lon + deltaLambda.rad))
    override fun invert(point: GeoJsonPoint): GeoJsonPoint  = point.copy(lon = normalizeLon(point.lon - deltaLambda.rad))
}

/**
 * -PI < lon < PI
 */
private fun normalizeLon(lambda: Double) = when {
    lambda > PI -> lambda - TAU
    lambda < -PI -> lambda + TAU
    else -> lambda
}

private fun normalizeLon(lon: Angle) = when {
    lon > ANGLE_PI -> lon - ANGLE_TAU
    lon < -ANGLE_PI-> lon + ANGLE_TAU
    else -> lon
}


internal class RotationPhiGammaProjector(deltaPhi: Double, deltaGamma: Double) : Projector<GeoJsonPoint, GeoJsonPoint> {

    private val cosDeltaPhi = cos(deltaPhi)
    private val sinDeltaPhi = sin(deltaPhi)
    private val cosDeltaGamma = cos(deltaGamma)
    private val sinDeltaGamma = sin(deltaGamma)


    override fun project(point: GeoJsonPoint): GeoJsonPoint {
        val cosPhi = point.lat.cos
        val x = point.lon.cos * cosPhi
        val y = point.lon.sin * cosPhi
        val z = point.lat.sin
        val k = z * cosDeltaPhi + x * sinDeltaPhi

        return point.copy(
            lon = atan2(y * cosDeltaGamma - k * sinDeltaGamma, x * cosDeltaPhi - z * sinDeltaPhi).rad,
            lat = asin(k * cosDeltaGamma + y * sinDeltaGamma).rad
        )

    }

    override fun invert(point:GeoJsonPoint): GeoJsonPoint {
        val cosPhi = point.lat.cos
        val newX = point.lon.cos * cosPhi
        val newY = point.lon.sin * cosPhi
        val z = point.lat.sin
        val k = z * cosDeltaGamma - newY * sinDeltaGamma

        return point.copy(
            lon = atan2(newY * cosDeltaGamma + z * sinDeltaGamma, newX * cosDeltaPhi + k * sinDeltaPhi).rad,
            lat = asin(k * cosDeltaPhi - newX * sinDeltaPhi).rad
        )
    }
}

/**
 * Create or compose new Projector for given angles.
 *
 *
 * @see RotationLambdaProjector
 * @see RotationPhiGammaProjector
 */
internal fun createRotateRadiansProjector(
    deltaLambda: Double,
    deltaPhi: Double,
    deltaGamma: Double): Projector<GeoJsonPoint, GeoJsonPoint> {

    val newDeltaLambda = deltaLambda % TAU
    val atLeastOneSecondaryAngleIsZero = deltaPhi != .0 || deltaGamma != .0
    return when {
        newDeltaLambda != .0 -> {
            if (atLeastOneSecondaryAngleIsZero) {
                ComposedProjector(
                    RotationLambdaProjector(deltaLambda),
                    RotationPhiGammaProjector(deltaPhi, deltaGamma)
                )
            } else RotationLambdaProjector(deltaLambda)
        }
        atLeastOneSecondaryAngleIsZero -> RotationPhiGammaProjector(
            deltaPhi,
            deltaGamma
        )
        else -> IdentityRotationProjector
    }
}

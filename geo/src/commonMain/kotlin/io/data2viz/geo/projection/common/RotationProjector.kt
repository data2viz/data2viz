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
    gamma: Angle = 0.deg) : Projector {

    val rotator =
        createRotateRadiansProjector(
            lambda.rad,
            phi.rad,
            gamma.rad
        )

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val p = rotator.project(lambda.toRadians(), phi.toRadians())
        return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        val p = rotator.invert(x.toRadians(), y.toRadians())
        return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
    }
}


internal object IdentityRotationProjector : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        return doubleArrayOf(
            normalizeLongitude(lambda),
            phi
        )
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        return doubleArrayOf(
            normalizeLongitude(x),
            y
        )
    }
}

internal class RotationLambdaProjector(val deltaLambda: Double): Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        return doubleArrayOf(
            normalizeLongitude(lambda + deltaLambda),
            phi
        )
    }

    override fun invert(x: Double, y: Double): DoubleArray =
        doubleArrayOf(
            normalizeLongitude(x - deltaLambda),
            y
        )
}

/**
 * -PI < lon < PI
 */
private fun normalizeLongitude(lambda: Double) = when {
    lambda > PI -> lambda - TAU
    lambda < -PI -> lambda + TAU
    else -> lambda
}


internal class RotationPhiGammaProjector(deltaPhi: Double, deltaGamma: Double) : Projector {

    private val cosDeltaPhi = cos(deltaPhi)
    private val sinDeltaPhi = sin(deltaPhi)
    private val cosDeltaGamma = cos(deltaGamma)
    private val sinDeltaGamma = sin(deltaGamma)


    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cosPhi = cos(phi)
        val x = cos(lambda) * cosPhi
        val y = sin(lambda) * cosPhi
        val z = sin(phi)
        val k = z * cosDeltaPhi + x * sinDeltaPhi

        return doubleArrayOf(
            atan2(y * cosDeltaGamma - k * sinDeltaGamma, x * cosDeltaPhi - z * sinDeltaPhi),
            asin(k * cosDeltaGamma + y * sinDeltaGamma)
        )

    }

    override fun invert(x: Double, y: Double): DoubleArray {
        val cosPhi = cos(y)
        val newX = cos(x) * cosPhi
        val newY = sin(x) * cosPhi
        val z = sin(y)
        val k = z * cosDeltaGamma - newY * sinDeltaGamma
        return doubleArrayOf(
            atan2(newY * cosDeltaGamma + z * sinDeltaGamma, newX * cosDeltaPhi + k * sinDeltaPhi),
            asin(k * cosDeltaPhi - newX * sinDeltaPhi)
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
    deltaGamma: Double): Projector {

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

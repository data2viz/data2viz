package io.data2viz.geo.projection.common

import io.data2viz.math.Angle
import io.data2viz.math.TAU
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*

/**
 * TODO: docs
 */
class RotationProjector(lambda: Angle, phi: Angle, gamma: Angle? = null): Projector {

    val rotator =
        createRotateRadiansProjector(
            lambda.rad,
            phi.rad,
            gamma?.rad ?: 0.0
        )

    override fun projectLambda(lambda: Double, phi: Double): Double =
        rotator.projectLambda(lambda.toRadians(), phi.toRadians()).toDegrees()

    override fun projectPhi(lambda: Double, phi: Double): Double =
        rotator.projectPhi(lambda.toRadians(), phi.toRadians()).toDegrees()

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val p = rotator.invert(lambda.toRadians(), phi.toRadians())
        return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
    }
}


private fun identityProjection(x: Double, y: Double) = doubleArrayOf(
    identityProjectionX(x),
    identityProjectionY(y)
)


private fun identityProjectionX(x: Double) = when {
    x > PI -> x - TAU
    x < -PI -> x + TAU
    else -> x
}

private fun identityProjectionY(y: Double) = y

object IdentityRotationProjector: Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double =
        identityProjectionX(lambda)

    override fun projectPhi(lambda: Double, phi: Double): Double =
        identityProjectionY(phi)

    override fun invert(lambda: Double, phi: Double) = identityProjection(lambda, phi)
}


class RotationLambdaProjector(val deltaLambda: Double) : Projector {

    override fun projectLambda(lambda: Double, phi: Double): Double =
        identityProjectionX(lambda + deltaLambda)

    override fun projectPhi(lambda: Double, phi: Double): Double =
        identityProjectionY(phi)

    override fun invert(lambda: Double, phi: Double): DoubleArray =
        identityProjection(lambda - deltaLambda, phi)
}

class RotationPhiGammaProjector(deltaPhi: Double, deltaGamma: Double) : Projector {

    override fun projectLambda(lambda: Double, phi: Double): Double {
        val cosPhi = cos(phi)
        val x = cos(lambda) * cosPhi
        val y = sin(lambda) * cosPhi
        val z = sin(phi)
        val k = z * cosDeltaPhi + x * sinDeltaPhi

        return atan2(y * cosDeltaGamma - k * sinDeltaGamma, x * cosDeltaPhi - z * sinDeltaPhi)
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val cosPhi = cos(phi)
        val x = cos(lambda) * cosPhi
        val y = sin(lambda) * cosPhi
        val z = sin(phi)
        val k = z * cosDeltaPhi + x * sinDeltaPhi

        return asin(k * cosDeltaGamma + y * sinDeltaGamma)
    }

    private val cosDeltaPhi = cos(deltaPhi)
    private val sinDeltaPhi = sin(deltaPhi)
    private val cosDeltaGamma = cos(deltaGamma)
    private val sinDeltaGamma = sin(deltaGamma)


    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val cosPhi = cos(phi)
        val newX = cos(lambda) * cosPhi
        val newY = sin(lambda) * cosPhi
        val z = sin(phi)
        val k = z * cosDeltaGamma - newY * sinDeltaGamma
        return doubleArrayOf(
            atan2(newY * cosDeltaGamma + z * sinDeltaGamma, newX * cosDeltaPhi + k * sinDeltaPhi),
            asin(k * cosDeltaPhi - newX * sinDeltaPhi)
        )
    }
}

internal fun createRotateRadiansProjector(deltaLambda: Double, deltaPhi: Double, deltaGamma: Double): Projector {
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


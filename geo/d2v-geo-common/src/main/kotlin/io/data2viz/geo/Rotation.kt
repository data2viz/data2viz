package io.data2viz.geo

import io.data2viz.math.Angle
import io.data2viz.math.TAU
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*



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

private fun rotationIdentity(): Projector = object : Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double = identityProjectionX(lambda)
    override fun projectPhi(lambda: Double, phi: Double): Double = identityProjectionY(phi)

//    override fun project(lambda: Double, phi: Double) = identityProjection(lambda, phi)
    override fun invert(lambda: Double, phi: Double) = identityProjection(lambda, phi)
}

class RotationLambda(val deltaLambda: Double) : Projector {

    override fun projectLambda(lambda: Double, phi: Double): Double =
        identityProjectionX(lambda + deltaLambda)
    override fun projectPhi(lambda: Double, phi: Double): Double = identityProjectionY(phi)

//    override fun project(lambda: Double, phi: Double): DoubleArray =
//        identityProjection(lambda + deltaLambda, phi)
    override fun invert(lambda: Double, phi: Double): DoubleArray = identityProjection(lambda - deltaLambda, phi)
}

class RotationPhiGamma(deltaPhi: Double, deltaGamma: Double) : Projector {

    override fun projectLambda(lambda: Double, phi: Double): Double {
        val cosPhi = cos(phi)
        val x = cos(lambda) * cosPhi
        val y = sin(lambda) * cosPhi
        val z = sin(phi)
        val k = z * cosDeltaPhi + x * sinDeltaPhi

        return atan2(y * cosDeltaGamma - k * sinDeltaGamma, x * cosDeltaPhi - z * sinDeltaPhi)
    }
    override fun projectPhi(lambda: Double, phi: Double): Double  {
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

//    override fun project(lambda: Double, phi: Double): DoubleArray {
//        val cosPhi = cos(phi)
//        val x = cos(lambda) * cosPhi
//        val y = sin(lambda) * cosPhi
//        val z = sin(phi)
//        val k = z * cosDeltaPhi + x * sinDeltaPhi
//        return doubleArrayOf(
//            atan2(y * cosDeltaGamma - k * sinDeltaGamma, x * cosDeltaPhi - z * sinDeltaPhi),
//            asin(k * cosDeltaGamma + y * sinDeltaGamma)
//        )
//    }

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

internal fun rotateRadians(deltaLambda: Double, deltaPhi: Double, deltaGamma: Double): Projector {
    val newDeltaLambda = deltaLambda % TAU
    return if (newDeltaLambda != .0) {
        if (deltaPhi != .0 || deltaGamma != .0) {
            compose(
                RotationLambda(deltaLambda),
                RotationPhiGamma(deltaPhi, deltaGamma)
            ) as Projector
        } else RotationLambda(deltaLambda)
    } else if (deltaPhi != .0 || deltaGamma != .0) RotationPhiGamma(deltaPhi, deltaGamma)
    else rotationIdentity()
}

internal fun rotation(rotate: Array<Angle>): Projector {
    val rotator =
        rotateRadians(
            rotate[0].rad,
            rotate[1].rad,
            if (rotate.size > 2) rotate[2].rad else 0.0
        )

    return object : Projector {

        override fun projectLambda(lambda: Double, phi: Double): Double =
            rotator.projectLambda(lambda.toRadians(), phi.toRadians()).toDegrees()

        override fun projectPhi(lambda: Double, phi: Double): Double =
            rotator.projectPhi(lambda.toRadians(), phi.toRadians()).toDegrees()

//        override fun project(lambda: Double, phi: Double): DoubleArray {
//            val p = rotator.project(lambda.toRadians(), phi.toRadians())
//            return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
//        }

        override fun invert(lambda: Double, phi: Double): DoubleArray {
            val p = rotator.invert(lambda.toRadians(), phi.toRadians())
            return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
        }
    }
}
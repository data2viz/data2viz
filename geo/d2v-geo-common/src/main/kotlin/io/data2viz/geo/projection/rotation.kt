package io.data2viz.geo.projection

import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*

private fun identity(x: Double, y: Double) = when {
    x > PI -> doubleArrayOf(x - (2 * PI), y)
    x < -PI -> doubleArrayOf(x + (2 * PI), y)
    else -> doubleArrayOf(x, y)
}

private fun rotationIdentity() = object : ProjectableInvertable {
    override fun project(lambda: Double, phi: Double) = identity(lambda, phi)
    override fun invert(x: Double, y: Double) = identity(x, y)
}

fun forwardRotationLambda(deltaLambda: Double): (Double, Double) -> DoubleArray {
    return { lambda: Double, phi: Double ->
        val l = lambda + deltaLambda
        identity(l, phi)
    }
}

class RotationLambda(deltaLambda: Double) : ProjectableInvertable {
    private val apply = forwardRotationLambda(deltaLambda)
    private val invert = forwardRotationLambda(-deltaLambda)

    override fun project(lambda: Double, phi: Double): DoubleArray = apply(lambda, phi)
    override fun invert(x: Double, y: Double): DoubleArray = invert.invoke(x, y)
}

class RotationPhiGamma(deltaPhi: Double, deltaGamma: Double) : ProjectableInvertable {
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

fun rotateRadians(deltaLambda: Double, deltaPhi: Double, deltaGamma: Double): Projectable {
    val newDeltaLambda = deltaLambda % (2 * PI)
    return if (newDeltaLambda != .0) {
        if (deltaPhi != .0 || deltaGamma != .0) compose(
            RotationLambda(deltaLambda),
            RotationPhiGamma(deltaPhi, deltaGamma)
        )
        else RotationLambda(deltaLambda)
    } else if (deltaPhi != .0 || deltaGamma != .0) RotationPhiGamma(deltaPhi, deltaGamma)
    else rotationIdentity()
}

fun rotation(rotate: DoubleArray): ProjectableInvertable {
    val rotator =
        rotateRadians(
            rotate[0].toRadians(),
            rotate[1].toRadians(),
            if (rotate.size > 2) rotate[2].toRadians() else 0.0
        ) as ProjectableInvertable

    return object : ProjectableInvertable {
        override fun project(lambda: Double, phi: Double): DoubleArray {
            val p = rotator.project(lambda.toRadians(), phi.toRadians())
            return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
        }

        override fun invert(x: Double, y: Double): DoubleArray {
            val p = rotator.invert(x.toRadians(), y.toRadians())
            return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
        }
    }
}
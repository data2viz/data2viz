package io.data2viz.geo.projection

import io.data2viz.geo.geometry.limitedAsin
import io.data2viz.geo.projection.common.Projector
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun azimuthalInvert(angle: (Double) -> Double) = { lambda: Double, phi: Double ->

    val z = z(lambda, phi)
    val c = angle(z)
    val sc = sc(c)
    val cc = cc(c)
    doubleArrayOf(
        internalInvertLambda(lambda, sc, z, cc),
        internalInvertPhi(z, phi, sc)
    )

}

fun azimuthalInvertLambda(angle: (Double) -> Double) = { lambda: Double, phi: Double ->

    val z = z(lambda, phi)
    val c = angle(z)
    val sc = sc(c)
    val cc = cc(c)
        internalInvertLambda(lambda, sc, z, cc)
}

fun azimuthalInvertPhi(angle: (Double) -> Double) = { lambda: Double, phi: Double ->

    val z = z(lambda, phi)
    val c = angle(z)
    val sc = sc(c)
        internalInvertPhi(z, phi, sc)
}


private fun internalProjectLambda(k: Double, cy: Double, lambda: Double) = k * cy * sin(lambda)
private fun internalProjectPhi(k: Double, phi: Double) = k * sin(phi)


private fun internalInvertLambda(
    lambda: Double,
    sc: Double,
    z: Double,
    cc: Double
) = atan2(lambda * sc, z * cc)

private fun internalInvertPhi(z: Double, phi: Double, sc: Double) =
    (if (z != .0) phi * sc / z else z).limitedAsin

private fun sc(c: Double) = sin(c)

private fun z(lambda: Double, phi: Double) = sqrt(lambda * lambda + phi * phi)

private fun cx(lambda: Double) = cos(lambda)
private fun cy(phi: Double) = cos(phi)

private fun cc(c: Double) = cos(c)


/**
 * Azimuthal projections project the sphere directly onto a plane
 */
open class AzimuthalProjector(val scale: (Double) -> Double, val angle: (Double) -> Double) :
    Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double {

        val cx = cx(lambda)
        val cy = cy(phi)
        val k = scaleCxCy(cx, cy)

        return internalProjectLambda(k, cy, lambda)
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {

        val cx = cx(lambda)
        val cy = cy(phi)
        val k = scaleCxCy(cx, cy)

        return internalProjectPhi(k, phi)
    }

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cx = cx(lambda)
        val cy = cy(phi)
        val k = scaleCxCy(cx, cy)
        return doubleArrayOf(internalProjectLambda(k, cy, lambda), internalProjectPhi(k, phi))
    }


    override fun invertLambda(lambda: Double, phi: Double): Double {
        val z = z(lambda, phi)
        val c = c(z)
        val sc = sc(c)
        val cc = cc(c)

        return internalInvertLambda(lambda, sc, z, cc)
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        val z = z(lambda, phi)
        val c = c(z)
        val sc = sc(c)
        return internalInvertPhi(z, phi, sc)
    }


    override fun invert(lambda: Double, phi: Double): DoubleArray {
        val z = z(lambda, phi)
        val c = c(z)
        val sc = sc(c)
        val cc = cc(c)
        return doubleArrayOf(
            internalInvertLambda(lambda, sc, z, cc),
            internalInvertPhi(z, phi, sc)
        )
    }

    private fun c(z: Double) = angle(z)

    private fun scaleCxCy(cx: Double, cy: Double) = scale(cx * cy)
}
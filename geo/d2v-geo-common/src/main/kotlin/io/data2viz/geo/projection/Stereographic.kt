package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


fun stereographicProjection() = stereographicProjection {}

fun stereographicProjection(init: Projection.() -> Unit) =
    projection(StereographicProjector()) {
        scale = 250.0
        clipAngle = 142.0
        init()
    }

private fun doubleAtan(d: Double) = 2 * atan(d)

class StereographicProjector : Projector {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cy = cy(phi)
        val k = k(lambda, cy);
        return doubleArrayOf(internalProjectLambda(cy, lambda, k), internalProjectPhi(phi, k))
    }


    override fun invertLambda(lambda: Double, phi: Double): Double
            = azimuthalInvertLambda(::doubleAtan)(lambda, phi)

    override fun invertPhi(lambda: Double, phi: Double): Double
            = azimuthalInvertPhi(::doubleAtan)(lambda, phi)

    override fun invert(lambda: Double, phi: Double): DoubleArray
            = azimuthalInvert(::doubleAtan)(lambda, phi)


    override fun projectLambda(lambda: Double, phi: Double): Double {
        val cy = cy(phi)
        val k = k(lambda, cy);
        return internalProjectLambda(cy, lambda, k)
    }

    private fun internalProjectLambda(cy: Double, lambda: Double, k: Double) = cy * sin(lambda) / k

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val cy = cy(phi)
        val k = k(lambda, cy);
        return internalProjectPhi(phi, k)
    }

    private fun internalProjectPhi(phi: Double, k: Double) = sin(phi) / k

    private fun k(lambda: Double, cy: Double) = 1 + cos(lambda) * cy

    private fun cy(phi: Double) = cos(phi)
}
package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.*
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


fun gnomonicProjection() = gnomonicProjection {}

fun gnomonicProjection(init: Projection.() -> Unit) =
    projection(GnomonicProjector()) {
        clipAngle = 60.0
        scale = 144.049
        init()
    }

class GnomonicProjector : Projector {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cy = cos(phi)
        val k = cos(lambda) * cy;
        return doubleArrayOf(cy * sin(lambda) / k, sin(phi) / k)
    }
    override fun invert(lambda: Double, phi: Double): DoubleArray = azimuthalInvert(::atan)(lambda, phi)

    override fun projectLambda(lambda: Double, phi: Double): Double {
        val cy = cos(phi)
        val k = cos(lambda) * cy;
        return cy * sin(lambda) / k
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val cy = cos(phi)
        val k = cos(lambda) * cy;
        return sin(phi) / k
    }
}
package io.data2viz.geo.projection

import io.data2viz.geo.*
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


fun gnomonicProjection() = gnomonicProjection {}

fun gnomonicProjection(init: Projection.() -> Unit) = projection(GnomonicProjector()) {
    clipAngle = 60.0
    scale = 144.049
    init()
}

class GnomonicProjector : Projectable, Invertable {
    override fun project(x: Double, y: Double): DoubleArray {
        val cy = cos(y)
        val k = cos(x) * cy;
        return doubleArrayOf(cy * sin(x) / k, sin(y) / k)
    }
    override fun invert(x: Double, y: Double): DoubleArray = azimuthalInvert(::atan)(x, y)

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
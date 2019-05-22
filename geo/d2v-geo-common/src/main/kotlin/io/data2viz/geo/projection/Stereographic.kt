package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.*
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

class StereographicProjector : Projector {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cy = cos(phi)
        val k = 1 + cos(lambda) * cy;
        return doubleArrayOf(cy * sin(lambda) / k, sin(phi) / k)
    }


    override fun invert(lambda: Double, phi: Double): DoubleArray = azimuthalInvert {
        2 * atan(it)
    }(lambda, phi)

    override fun projectLambda(lambda: Double, phi: Double): Double {
        val cy = cos(phi)
        val k = 1 + cos(lambda) * cy;
        return cy * sin(lambda) / k
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val cy = cos(phi)
        val k = 1 + cos(lambda) * cy;
        return sin(phi) / k
    }
}
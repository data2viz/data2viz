package io.data2viz.geo.projection

import io.data2viz.geo.*
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

class StereographicProjector : Projectable, Invertable {
//    override fun project(x: Double, y: Double): DoubleArray {
//        var cy = cos(y)
//        val k = 1 + cos(x) * cy;
//        return doubleArrayOf(cy * sin(x) / k, sin(y) / k)
//    }


    override fun invert(x: Double, y: Double): DoubleArray = azimuthalInvert {
        2 * atan(it)
    }(x, y)

    override fun projectLambda(lambda: Double, phi: Double): Double {
        var cy = cos(phi)
        val k = 1 + cos(lambda) * cy;
        return cy * sin(lambda) / k
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        var cy = cos(phi)
        val k = 1 + cos(lambda) * cy;
        return sin(phi) / k
    }
}
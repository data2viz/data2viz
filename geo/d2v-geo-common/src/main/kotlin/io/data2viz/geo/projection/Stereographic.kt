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

    override fun projectLambda(x: Double, y: Double): Double {
        var cy = cos(y)
        val k = 1 + cos(x) * cy;
        return cy * sin(x) / k
    }

    override fun projectPhi(x: Double, y: Double): Double {
        var cy = cos(y)
        val k = 1 + cos(x) * cy;
        return sin(y) / k
    }
}
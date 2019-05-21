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
//    override fun project(x: Double, y: Double): DoubleArray {
//        val cy = cos(y)
//        val k = cos(x) * cy;
//        return doubleArrayOf(cy * sin(x) / k, sin(y) / k)
//    }
    override fun invert(x: Double, y: Double): DoubleArray = azimuthalInvert(::atan)(x, y)

    override fun projectLambda(x: Double, y: Double): Double {
        val cy = cos(y)
        val k = cos(x) * cy;
        return cy * sin(x) / k
    }

    override fun projectPhi(x: Double, y: Double): Double {
        val cy = cos(y)
        val k = cos(x) * cy;
        return sin(y) / k
    }
}
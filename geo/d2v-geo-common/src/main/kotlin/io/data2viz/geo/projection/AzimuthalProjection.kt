package io.data2viz.geo.projection

import io.data2viz.geo.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun azimuthalInvert(angle: (Double) -> Double) = { x: Double, y: Double ->
    val z = sqrt(x * x + y * y)
    val c = angle(z)
    val sc = sin(c)
    val cc = cos(c)
    doubleArrayOf(atan2(x * sc, z * cc), (if (z == 0.0) z else y * sc / z).asin)
}

open class Azimuthal(val scale: (Double) -> Double, val angle: (Double) -> Double) : ProjectableInvertable {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cx = cos(lambda)
        val cy = cos(phi)
        val k = scale(cx * cy)
        return doubleArrayOf(k * cy * sin(lambda), k * sin(phi))
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        val z = sqrt(x * x + y * y)
        val c = angle(z)
        val sc = sin(c)
        val cc = cos(c)
        return doubleArrayOf(atan2(x * sc, z * cc), (if (z != .0) y * sc / z else z).asin)
    }
}
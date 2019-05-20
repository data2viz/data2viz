package io.data2viz.geo.geo

import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*

class GeoInterpolate(
    val distance: Double,
    private val k: Double,
    private val kx0: Double,
    private val kx1: Double,
    private val ky0: Double,
    private val ky1: Double,
    private val sy0: Double,
    private val sy1: Double,
    private val x0: Double,
    private val y0: Double
) {
    val interpolate = if (distance != .0) fun(t: Double): DoubleArray {
        val td = t * distance
        val B = sin(td) / k
        val A = sin(distance - td) / k
        val x = A * kx0 + B * kx1
        val y = A * ky0 + B * ky1
        val z = A * sy0 + B * sy1
        return doubleArrayOf(atan2(y, x).toDegrees(), atan2(z, sqrt(x * x + y * y)).toDegrees())
    } else fun(_: Double): DoubleArray {
        return doubleArrayOf(x0.toDegrees(), y0.toDegrees())
    }

    operator fun invoke(t: Double): DoubleArray {
        return interpolate(t)
    }
}

fun haversin(x: Double): Double {
    val y = sin(x / 2)
    return y * y
}

fun geoInterpolate(a: DoubleArray, b: DoubleArray): GeoInterpolate {
    val x0 = a[0].toRadians()
    val y0 = a[1].toRadians()
    val x1 = b[0].toRadians()
    val y1 = b[1].toRadians()
    val cy0 = cos(y0)
    val sy0 = sin(y0)
    val cy1 = cos(y1)
    val sy1 = sin(y1)
    val kx0 = cy0 * cos(x0)
    val ky0 = cy0 * sin(x0)
    val kx1 = cy1 * cos(x1)
    val ky1 = cy1 * sin(x1)
    val d = 2 * asin(sqrt(haversin(y1 - y0) + cy0 * cy1 * haversin(x1 - x0)))
    val k = sin(d)

    return GeoInterpolate(d, k, kx0, kx1, ky0, ky1, sy0, sy1, x0, y0)
}

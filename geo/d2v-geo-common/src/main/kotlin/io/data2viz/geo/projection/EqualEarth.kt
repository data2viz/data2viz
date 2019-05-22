package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.math.EPSILON2
import kotlin.math.*

val A1 = 1.340264
val A2 = -0.081106
val A3 = 0.000893
val A4 = 0.003796
val M = sqrt(3.0) / 2
val iterations = 12

fun equalEarthProjection() = equalEarthProjection {}

fun equalEarthProjection(init: Projection.() -> Unit) =
    projection(EqualEarthProjector()) {
        scale = 177.158
        init()
    }

class EqualEarthProjector : Projectable, Invertable {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        var l = asin(M * sin(phi))
        var l2 = l * l
        var l6 = l2 * l2 * l2
        return doubleArrayOf(
            lambda * cos(l) / (M * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2))),
            l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2))
        )
    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        var l = phi
        var l2 = l * l
        var l6 = l2 * l2 * l2
        for (i in 0 until iterations) {
            val fy = l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2)) - phi;
            val fpy = A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2);
            val delta = fy / fpy
            l2 = l * l
            l6 = l2 * l2 * l2
            l -= delta;
            if (abs(delta) < EPSILON2) break;
        }
        return doubleArrayOf(
            M * lambda * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)) / cos(l),
            asin(sin(l) / M)
        )
    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        var l = asin(M * sin(phi))
        var l2 = l * l
        var l6 = l2 * l2 * l2
        return lambda * cos(l) / (M * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)))
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        var l = asin(M * sin(phi))
        var l2 = l * l
        var l6 = l2 * l2 * l2
        return l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2))
    }
}
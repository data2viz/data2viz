package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
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

/**
 * The Equal Earth projection, by Bojan Šavrič et al., 2018.
 */
class EqualEarthProjector : Projector {
    override fun project(lambda: Double, phi: Double): DoubleArray {
        var l = l(phi)
        var l2 = l2(l)
        var l6 = l6(l2)
        return doubleArrayOf(
            internalProjectLambda(lambda, l, l2, l6),
            internalProjectPhi(l, l2, l6)
        )
    }

    override fun invertLambda(lambda: Double, phi: Double): Double {
        var l = phi
        var l2 = l2(l)
        var l6 = l6(l2)
        for (i in 0 until iterations) {
            val fy = l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2)) - phi;
            val fpy = A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2);
            val delta = fy / fpy
            l2 = l * l
            l6 = l2 * l2 * l2
            l -= delta;
            if (abs(delta) < EPSILON2) break;
        }

        return internalInvertLambda(lambda, l2, l6, l)


    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        var l = phi
        var l2 = l2(l)
        var l6 = l6(l2)
        for (i in 0 until iterations) {
            val fy = l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2)) - phi;
            val fpy = A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2);
            val delta = fy / fpy
            l2 = l * l
            l6 = l2 * l2 * l2
            l -= delta;
            if (abs(delta) < EPSILON2) break;
        }
        return internalInvertPhi(l)

    }

    override fun invert(lambda: Double, phi: Double): DoubleArray {
        var l = phi
        var l2 = l2(l)
        var l6 = l6(l2)
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
            internalInvertLambda(lambda, l2, l6, l),
            internalInvertPhi(l)
        )
    }

    private fun internalInvertPhi(l: Double) = asin(sin(l) / M)

    private fun internalInvertLambda(
        lambda: Double,
        l2: Double,
        l6: Double,
        l: Double
    ) = M * lambda * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)) / cos(l)

    override fun projectLambda(lambda: Double, phi: Double): Double {
        var l = l(phi)
        var l2 = l2(l)
        var l6 = l2 * l2 * l2
        return internalProjectLambda(lambda, l, l2, l6)
    }

    private fun internalProjectLambda(
        lambda: Double,
        l: Double,
        l2: Double,
        l6: Double
    ) = lambda * cos(l) / (M * (A1 + 3 * A2 * l2 + l6 * (7 * A3 + 9 * A4 * l2)))

    override fun projectPhi(lambda: Double, phi: Double): Double {
        var l = l(phi)
        var l2 = l * l
        var l6 = l6(l2)
        return internalProjectPhi(l, l2, l6)
    }

    private fun internalProjectPhi(l: Double, l2: Double, l6: Double) =
        l * (A1 + A2 * l2 + l6 * (A3 + A4 * l2))

    private fun l6(l2: Double) = l2 * l2 * l2

    private fun l2(l: Double) = l * l

    private fun l(phi: Double) = asin(M * sin(phi))
}
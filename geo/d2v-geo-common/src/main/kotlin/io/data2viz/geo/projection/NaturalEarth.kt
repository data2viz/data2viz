package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.math.EPSILON
import kotlin.math.abs


fun naturalEarthProjection() = naturalEarthProjection {}

fun naturalEarthProjection(init: Projection.() -> Unit) =
    projection(NaturalEarthProjection()) {
        scale = 175.295
        init()
    }

class NaturalEarthProjection : Projectable, Invertable {
//    override fun project(lambda: Double, phi: Double): DoubleArray {
//        val phi2 = phi * phi
//        val phi4 = phi2 * phi2;
//        return doubleArrayOf(
//            lambda * (0.8707 - 0.131979 * phi2 + phi4 * (-0.013791 + phi4 * (0.003971 * phi2 - 0.001529 * phi4))),
//            phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4)))
//        )
//    }

    override fun invert(x: Double, y: Double): DoubleArray {
        var phi = y
        var i = 25
        var delta:Double
        do {
            var phi2 = phi * phi
            var phi4 = phi2 * phi2;
            delta =
                (phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4))) - y) /
                        (1.007226 + phi2 * (0.015085 * 3 + phi4 * (-0.044475 * 7 + 0.028874 * 9 * phi2 - 0.005916 * 11 * phi4)))
            phi -= delta
        } while (abs(delta) > EPSILON && --i > 0);
        val phi2 = phi * phi
        return doubleArrayOf(
            x / (0.8707 + (phi2) * (-0.131979 + phi2 * (-0.013791 + phi2 * phi2 * phi2 * (0.003971 - 0.001529 * phi2)))),
            phi
        )
    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        val phi2 = phi * phi
        val phi4 = phi2 * phi2;

        return lambda * (0.8707 - 0.131979 * phi2 + phi4 * (-0.013791 + phi4 * (0.003971 * phi2 - 0.001529 * phi4)))
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        val phi2 = phi * phi
        val phi4 = phi2 * phi2;
        return phi * (1.007226 + phi2 * (0.015085 + phi4 * (-0.044475 + 0.028874 * phi2 - 0.005916 * phi4)))

    }
}
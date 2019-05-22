package io.data2viz.geo.projection

import io.data2viz.geo.ProjectableInvertable
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin


class CylindricalEqualAreaProjector() : ProjectableInvertable {
    override fun invert(lambda: Double, phi: Double): DoubleArray {
        return doubleArrayOf(lambda / cosPhi0, asin(phi * cosPhi0))
    }

    constructor(phi:Double) : this() {
        phi0 = phi
    }

    var phi0:Double = 0.0
    set(value) {
        field = value
        recalculate()
    }

    var cosPhi0: Double = 0.0

    private fun recalculate() {
        cosPhi0 = cos(phi0);
    }

    override fun projectLambda(lambda: Double, phi: Double): Double {
        return lambda * cosPhi0
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        return sin(phi) / cosPhi0
    }

}

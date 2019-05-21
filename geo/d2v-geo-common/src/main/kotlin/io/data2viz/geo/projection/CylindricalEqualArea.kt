package io.data2viz.geo.projection

import io.data2viz.geo.Invertable
import io.data2viz.geo.Projectable
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin


class CylindricalEqualAreaProjector() : Projectable, Invertable {
    override fun invert(x: Double, y: Double): DoubleArray {
        return doubleArrayOf(x / cosPhi0, asin(y * cosPhi0))
    }

    constructor(phi:Double) : this() {
        phi0 = phi
    }

    var phi0:Double = 0.0
    set(value) {
        field = value
        cosPhi0 = cos(phi0);
    }

    var cosPhi0 = cos(phi0);

    override fun projectLambda(lambda: Double, phi: Double): Double {
        return lambda * cosPhi0
    }

    override fun projectPhi(lambda: Double, phi: Double): Double {
        return sin(phi) / cosPhi0
    }

}

package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projector
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

/**
 * Simple cylindrical equal area
 * Used in [ConicEqualAreaBaseConditionalProjector]
 */
class CylindricalEqualAreaProjector() : Projector {

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

    override fun invertLambda(lambda: Double, phi: Double): Double {
        return lambda / cosPhi0
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        return  asin(phi * cosPhi0)
    }



}

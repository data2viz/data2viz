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
        cosPhi0 = cos(phi0)
    }

    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda * cosPhi0, sin(phi) / cosPhi0)

    override fun invert(lambda: Double, phi: Double) = doubleArrayOf( lambda / cosPhi0, asin(phi * cosPhi0))

}

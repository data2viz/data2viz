package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection

fun equirectangularProjection() = equirectangularProjection {}
fun equirectangularProjection(init: Projection.() -> Unit) =
    projection(EquirectangularProjector()) {
        scale = 152.63
        init()
    }

class EquirectangularProjector : Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = phi

    override fun invert(lambda: Double, phi: Double) = doubleArrayOf(lambda, phi)
}
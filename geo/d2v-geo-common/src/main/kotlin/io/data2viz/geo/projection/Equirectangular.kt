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

/**
 * Cylindrical projections project the sphere onto a containing cylinder, and then unroll the cylinder onto the plane. Pseudocylindrical projections are a generalization of cylindrical projections.
 * The equirectangular (plate carrée) projection.
 */
class EquirectangularProjector : Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = phi

    override fun invertLambda(lambda: Double, phi: Double): Double = lambda
    override fun invertPhi(lambda: Double, phi: Double): Double = phi

}
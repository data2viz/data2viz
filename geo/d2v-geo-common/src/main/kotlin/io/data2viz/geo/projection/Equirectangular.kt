package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection

fun equirectangularProjection(init: Projection.() -> Unit = {}) =
    projection(EquirectangularProjector()) {
        scale = 152.63
        init()
    }

/**
 * Cylindrical projections projector the sphere onto a containing cylinder,
 * and then unroll the cylinder onto the plane.
 * Pseudocylindrical projections are a generalization of cylindrical projections.
 * The equirectangular (plate carrée) projection.
 */
internal class EquirectangularProjector : Projector {
    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda, phi)
    override fun invert(x: Double, y: Double) = doubleArrayOf(x, y)
}
package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.noPostClip
import io.data2viz.geo.geometry.clip.noPreClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.PI


/**
 * Projections without any transformations and clipping
 */
fun identityProjection(init: Projection.() -> Unit = {}) =
    projection(IdentityProjection()) {
        preClip = noPreClip
        postClip = noPostClip
        scale = 180 / PI
        init()
    }

internal class IdentityProjection : Projector {
    override fun invert(x: Double, y: Double): DoubleArray = doubleArrayOf(x, y)
    override fun project(lambda: Double, phi: Double): DoubleArray = doubleArrayOf(lambda, phi)
}
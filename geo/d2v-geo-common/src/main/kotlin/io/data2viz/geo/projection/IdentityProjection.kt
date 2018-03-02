package io.data2viz.geo.projection

import io.data2viz.math.PI

fun identityProjection() = projection(IdentityProjection()) {
    preClip = { it }
    postClip = { it }
    scale = 180 / PI
}

class IdentityProjection : ProjectableInvertable {
    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda, phi)
    override fun invert(x: Double, y: Double) = doubleArrayOf(x, y)
}
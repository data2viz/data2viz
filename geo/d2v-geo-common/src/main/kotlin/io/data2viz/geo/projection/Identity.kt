package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.math.PI


fun identityProjection() = identityProjection {}

fun identityProjection(init: Projection.() -> Unit) = projection(IdentityProjection()) {
    preClip = { it }
    postClip = { it }
    scale = 180 / PI
    init()
}

class IdentityProjection : Projectable, Invertable {
//    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda, phi)
    override fun invert(x: Double, y: Double) = doubleArrayOf(x, y)
    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = phi
}
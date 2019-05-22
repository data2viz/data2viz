package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.*
import io.data2viz.math.PI


fun identityProjection() = identityProjection {}

fun identityProjection(init: Projection.() -> Unit) =
    projection(IdentityProjection()) {
        preClip = { it }
        postClip = { it }
        scale = 180 / PI
        init()
    }

class IdentityProjection : Projector {

    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = phi

    override fun invertLambda(lambda: Double, phi: Double): Double = lambda
    override fun invertPhi(lambda: Double, phi: Double): Double = phi

}
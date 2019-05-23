package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

fun orthographicProjection() = orthographicProjection {}

fun orthographicProjection(init: ProjectorProjection.() -> Unit) =
    projection(OrthographicProjector()) {
        scale = 249.5
        anglePreClip = (90 + EPSILON).deg
        init()
    }

/**
 * The orthographic projection.
 */
class OrthographicProjector : Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double = cos(phi) * sin(lambda)

    override fun projectPhi(lambda: Double, phi: Double): Double = sin(phi)


    override fun invertLambda(lambda: Double, phi: Double): Double {
        return azimuthalInvertLambda(::asin)(lambda, phi)
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        return azimuthalInvertPhi(::asin)(lambda, phi)
    }

    override fun invert(lambda: Double, phi: Double) = azimuthalInvert(::asin)(lambda, phi)
}
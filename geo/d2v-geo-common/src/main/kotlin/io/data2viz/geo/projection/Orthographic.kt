package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Invertable
import io.data2viz.geo.projection.common.ProjectableProjection
import io.data2viz.geo.projection.common.Projectable
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.EPSILON
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

fun orthographicProjection() = orthographicProjection {}

fun orthographicProjection(init: ProjectableProjection.() -> Unit) =
    projection(OrthographicProjector()) {
        scale = 249.5
        clipAngle = 90 + EPSILON
        init()
    }

class OrthographicProjector : Projectable,
    Invertable {
    override fun projectLambda(lambda: Double, phi: Double): Double = cos(phi) * sin(lambda)

    override fun projectPhi(lambda: Double, phi: Double): Double = sin(phi)

    private val invertFunction = azimuthalInvert(::asin)

//    override fun project(lambda: Double, phi: Double) = doubleArrayOf(cos(phi) * sin(lambda), sin(phi))
    override fun invert(lambda: Double, phi: Double) = invertFunction(lambda, phi)
}
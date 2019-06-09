package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin


fun orthographicProjection(init: Projection.() -> Unit = {}) =
    projection(OrthographicProjector()) {
        scale = 249.5
        anglePreClip = (90 + EPSILON).deg
        init()
    }


internal class OrthographicProjector : Projector {

    override fun project(lambda: Double, phi: Double) = doubleArrayOf(cos(phi) * sin(lambda), sin(phi))
    override fun invert(x: Double, y: Double) = azimuthalInvert(::asin)(x, y)

}
package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


fun gnomonicProjection(init: Projection.() -> Unit = {}) =
    projection(GnomonicProjector()) {
        anglePreClip = 60.0.deg
        scale = 144.049
        init()
    }

class GnomonicProjector : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cy = cos(phi)
        val k = cos(lambda) * cy
        return doubleArrayOf(
            cy * sin(lambda) / k,
            sin(phi) / k
        )
    }

    override fun invert(x: Double, y: Double): DoubleArray = azimuthalInvert(::atan)(x, y)

}
package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.projection.common.Projector
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


/**
 * @see StereographicProjector
 */
fun stereographicProjection(init: Projection.() -> Unit = {}) =
    projection(StereographicProjector()) {
        scale = 250.0
        anglePreClip = (142.0).deg
        init()
    }


private fun doubleAtan(d: Double) = 2 * atan(d)

/**
 * The stereographic projection.
 */
class StereographicProjector : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val cy = cos(phi)
        val k = 1 + cos(lambda) * cy
        return doubleArrayOf(
            cy * sin(lambda) / k,
            sin(phi) / k
        )
    }

    override fun invert(x: Double, y: Double): DoubleArray
            = azimuthalInvert(::doubleAtan)(x, y)


}
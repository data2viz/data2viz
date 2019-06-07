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
        val cy = cy(phi)
        val k = k(lambda, cy)
        return doubleArrayOf(
            internalProjectLambda(cy, lambda, k),
            internalProjectPhi(phi, k)
        )
    }


    override fun invert(lambda: Double, phi: Double): DoubleArray = azimuthalInvert(::atan)(lambda, phi)

    private fun internalProjectLambda(cy: Double, lambda: Double, k: Double) = cy * sin(lambda) / k


    private fun internalProjectPhi(phi: Double, k: Double) = sin(phi) / k

    private fun k(lambda: Double, cy: Double) = cos(lambda) * cy

    private fun cy(phi: Double) = cos(phi)
}
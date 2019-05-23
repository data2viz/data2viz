package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.ExtentPostClip
import io.data2viz.geo.geometry.clip.StreamPostClip
import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geo.projection.common.*
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import io.data2viz.math.TAU
import kotlin.math.*

fun mercatorProjection() = mercatorProjection {}


fun mercatorProjection(init: Projection.() -> Unit) = MercatorProjection(MercatorProjector()).apply {
    scale = 961 / TAU
}.apply(init)

class MercatorProjector : Projector {
    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = ln(tan((HALFPI + phi) / 2))

    override fun invertLambda(lambda: Double, phi: Double): Double {
        return lambda
    }

    override fun invertPhi(lambda: Double, phi: Double): Double {
        return 2 * atan(exp(phi)) - HALFPI
    }

}

/**
 * The spherical Mercator projection. Defines a default projection.clipExtent such that the world is projected to a square, clipped to approximately ±85° latitude.
 */
open class MercatorProjection(projector: Projector = MercatorProjector()) : ProjectorProjection(projector) {

    override var scale: Double
        get() = super.scale
        set(value) {
            super.scale = value
            reclip()
        }

    override var translateX: Double
        get() = super.translateX
        set(value) {
            super.translateX = value
            reclip()
        }
    override var translateY: Double
        get() = super.translateY
        set(value) {
            super.translateY = value
            reclip()
        }

    override fun translate(x: Double, y: Double) {
        super.translate(x, y)
        reclip()
    }

    override var centerLat: Angle
        get() = super.centerLat
        set(value) {
            super.centerLat = value
            reclip()
        }
    override var centerLon: Angle
        get() = super.centerLon
        set(value) {
            super.centerLon = value
            reclip()
        }

    override fun center(lat: Angle, lon: Angle) {
        super.center(lat, lon)
        reclip()
    }

    // TODO check tests still some issues with extentPostClip. Don't properly clip bottom border.
    // TODO Implement different extentPostClip to pass null tests
    private fun reclip() {
        val k = PI * scale
        val invert = RotationProjector(rotateLambda, rotatePhi, rotateGamma).invert(.0, .0)

        val lambda = invert[0]
        val phi = invert[1]
        val t0 = projectLambda(lambda, phi)
        val t1 = projectPhi(lambda, phi)

        this.extentPostClip = when {
            extentPostClip == null -> Extent(t0 - k, t1 - k, k * 2, k * 2)
            projection is MercatorProjector -> Extent(
                max(t0 - k, extentPostClip!!.x0),
                extentPostClip!!.y0,
                max(0.0, min(k * 2, extentPostClip!!.width)),
                extentPostClip!!.height
            )
            else -> Extent(
                extentPostClip!!.x0,
                max(t1 - k, extentPostClip!!.y0),
                extentPostClip!!.width,
                min(k * 2, extentPostClip!!.height)
            )
        }
    }
}
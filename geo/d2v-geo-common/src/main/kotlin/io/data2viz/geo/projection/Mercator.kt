package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.ExtentClip
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


// TODO: we should use MercatorProjection in this case instaed of default projection without clipping.
//  Currently MercatorProjection have clipping issues in this case
fun mercatorProjection(init: Projection.() -> Unit) = projection(MercatorProjector()){}.apply {
//fun mercatorProjection(init: Projection.() -> Unit) = MercatorProjection(MercatorProjector()).apply {
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

open class MercatorProjection(projector: Projector = MercatorProjector()) : ProjectableProjection(projector) {

    private var innerExtent: Extent? = null

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

    override var postClip: StreamPostClip
        get() = super.postClip
        set(value) {
            if(value is ExtentClip) {
                innerExtent = value.extent
            } else {
                super.postClip = value
                innerExtent = null
            }

        }
//    override var extentPostClip: Extent?
//        get() = innerExtent
//        set(value) {
//            innerExtent = value
//        }

    // TODO check tests still some issues with extentPostClip
    private fun reclip() {
        val k = PI * scale
        val invert = rotation(rotateLambda, rotatePhi, rotateGamma).invert(.0, .0)

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
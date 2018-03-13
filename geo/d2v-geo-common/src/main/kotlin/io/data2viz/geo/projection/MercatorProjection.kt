package io.data2viz.geo.projection

import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import io.data2viz.math.TAU
import kotlin.math.*

fun mercatorProjection(init: Projection.() -> Unit) = projection(MercatorProjector()) {
    scale = 961 / TAU
    init()
}

class MercatorProjector : ProjectableInvertable {
    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda, ln(tan((HALFPI + phi) / 2)))
    override fun invert(x: Double, y: Double) = doubleArrayOf(x, 2 * atan(exp(y)) - HALFPI)
}

class MercatorProjection : MutableProjection(MercatorProjector()) {

    private var reclippingInProgress = false

    override var scale: Double
        get() = super.scale
        set(value) {
            super.scale = value
            reclip()
        }

    override var translate: DoubleArray
        get() = super.translate
        set(value) {
            super.translate = value
            reclip()
        }

    override var center: DoubleArray
        get() = super.center
        set(value) {
            super.center = value
            reclip()
        }

    override var clipExtent: Extent?
        get() = super.clipExtent
        set(value) {
            super.clipExtent = value
            if (!reclippingInProgress) reclip()
        }

    private fun reclip() {
        reclippingInProgress = true

        val k = PI * scale
        val invert = rotation(rotate).invert(.0, .0)
        val t = project(invert[0], invert[1])

        clipExtent = when {
            clipExtent == null -> Extent(t[0] - k, t[1] - k, k * 2, k * 2)
            projection is MercatorProjector -> Extent(
                max(t[0] - k, clipExtent!!.x0),
                clipExtent!!.y0,
                max(0.0, min(k * 2, clipExtent!!.width)),
                clipExtent!!.height
            )
            else -> Extent(
                clipExtent!!.x0,
                max(t[1] - k, clipExtent!!.y0),
                clipExtent!!.width,
                min(k * 2, clipExtent!!.height)
            )
        }

        reclippingInProgress = false
    }
}
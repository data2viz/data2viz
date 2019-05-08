package io.data2viz.geo.projection

import io.data2viz.geom.Extent
import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import io.data2viz.math.TAU
import kotlin.math.*

fun mercatorProjection(init: Projection.() -> Unit) = projection(MercatorProjector()) {
    scale = 961 / TAU
    init()
}

class MercatorProjector : ProjectableInvertable {
    override fun projectLambda(lambda: Double, phi: Double): Double = lambda

    override fun projectPhi(lambda: Double, phi: Double): Double = ln(tan((HALFPI + phi) / 2))

    override fun project(lambda: Double, phi: Double) = doubleArrayOf(lambda, ln(tan((HALFPI + phi) / 2)))
    override fun invert(x: Double, y: Double) = doubleArrayOf(x, 2 * atan(exp(y)) - HALFPI)
}

class MercatorProjection : MutableProjection(MercatorProjector()) {

    private var innerExtent: Extent? = null

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
        get() = innerExtent
        set(value) {
            innerExtent = value
        }

    // TODO check tests still some issues with clipExtent
    private fun reclip() {
        val k = PI * scale
        val invert = rotation(rotate).invert(.0, .0)

        val lambda = invert[0]
        val phi = invert[1]
        val t0 = projectLambda(lambda, phi)
        val t1 = projectPhi(lambda, phi)

        super.clipExtent = when {
            clipExtent == null -> Extent(t0 - k, t1 - k, k * 2, k * 2)
            projection is MercatorProjector -> Extent(
                max(t0 - k, clipExtent!!.x0),
                clipExtent!!.y0,
                max(0.0, min(k * 2, clipExtent!!.width)),
                clipExtent!!.height
            )
            else -> Extent(
                clipExtent!!.x0,
                max(t1 - k, clipExtent!!.y0),
                clipExtent!!.width,
                min(k * 2, clipExtent!!.height)
            )
        }
    }
}
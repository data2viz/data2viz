package io.data2viz.geo.projection

import io.data2viz.geo.ModifiedStream
import io.data2viz.geo.clip.clipRectangle
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.HALFPI
import io.data2viz.math.TAU
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*

class MercatorRaw

fun mercatorProjection() = mercatorProjection {}
fun mercatorProjection(init: Projection.() -> Unit) = projection(MercatorProjection()) {
    scale = 961.0 / TAU
    init()
}

class MercatorProjection : Projection {

    private var projectResample: (Stream) -> Stream = resampleNone(this)
    private lateinit var projectRotate: Projectable

    private var k = 150.0
    override var scale: Double = 150.0
        set(value) {
            field = value
            reclip()
        }

    private var x = 480.0
    private var y = 250.0
    override var translate: DoubleArray = doubleArrayOf(480.0, 250.0)
        set(value) {
            field = value
            reclip()
        }

    private var dx = 0.0
    private var dy = 0.0
    private var lambda = .0
    private var phi = .0
    override var center: DoubleArray = doubleArrayOf(.0, .0)
        set(value) {
            field = value
            reclip()
        }

    override var rotate: DoubleArray
        get() = doubleArrayOf(deltaLambda.toDegrees(), deltaPhi.toDegrees(), deltaGamma.toDegrees())
        set(value) {
            deltaLambda = (value[0] % 360).toRadians()
            deltaPhi = (value[1] % 360).toRadians()
            deltaGamma = if (value.size > 2) (value[2] % 360).toRadians() else 0.0
            recenter()
        }

    override var precision: Double
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    val noClip: (Stream) -> Stream = { it }
    override var preClip: (Stream) -> Stream
        get() = noClip
        set(value) {}

    override var postClip: (Stream) -> Stream
        get() = noClip
        set(value) {}

    override var clipAngle: Double
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var clipExtent: Extent? = null
        set(value) {
            field = value
            if (value != null) {
                postClip = io.data2viz.geo.clip.clipExtent(value)
            } else {
                postClip = noClip
            }
        }

    override fun project(lambda: Double, phi: Double): DoubleArray {
        return doubleArrayOf(lambda, ln(tan((HALFPI + phi) / 2)))
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        return doubleArrayOf(x, 2 * atan(exp(y)) - HALFPI)
    }

    override fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.fitExtent(this, extent, geo)
    }

    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.fitWidth(this, width, geo)
    }

    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.fitHeight(this, height, geo)
    }

    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.fitSize(this, width, height, geo)
    }

    private var deltaLambda = .0
    private var deltaPhi = .0
    private var deltaGamma = .0
    private lateinit var rotator: Projectable

    protected var cache: Stream? = null
    protected var cacheStream: Stream? = null

    override fun stream(stream: Stream): Stream {
        if (cacheStream == null) {
            recenter()
            cacheStream = transformRadians(transformRotate(rotator)(preClip(projectResample(postClip(stream)))))
        }
        return cacheStream!!
    }

    val transformRadians: (stream: Stream) -> ModifiedStream = { stream: Stream ->
        object : ModifiedStream(stream) {
            override fun point(x: Double, y: Double, z: Double) =
                stream.point(x.toRadians(), y.toRadians(), z.toRadians())
        }
    }

    fun transformRotate(rotate: Projectable): (stream: Stream) -> ModifiedStream = { stream: Stream ->
        object : ModifiedStream(stream) {
            override fun point(x: Double, y: Double, z: Double) {
                val r = rotate.project(x, y)
                stream.point(r[0], r[1], 0.0)
            }
        }
    }

    fun recenter(): Projection {
        rotator = rotateRadians(deltaLambda, deltaPhi, deltaGamma)
        projectRotate = compose(rotator, this) /// project ??!,?
        val center = project(lambda, phi)
        dx = x - center[0] * k
        dy = y + center[1] * k
        return this
//        return reset()
    }

    private fun reclip() {
        // TODO (mercator only ?)
    }

}
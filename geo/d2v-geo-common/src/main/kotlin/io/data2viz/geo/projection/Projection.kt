package io.data2viz.geo.projection


import io.data2viz.geo.ModifiedStream
import io.data2viz.geo.clip.clipAntimeridian
import io.data2viz.geo.clip.clipCircle
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.sqrt

interface Stream {
    fun point(x: Double, y: Double, z: Double) {}
    fun lineStart() {}
    fun lineEnd() {}
    fun polygonStart() {}
    fun polygonEnd() {}
    fun sphere() {}
}

interface Projectable {
    fun project(lambda: Double, phi: Double): DoubleArray
    fun projectLambda(lambda: Double, phi: Double): Double
    fun projectPhi(lambda: Double, phi: Double): Double
}

interface Invertable {
    fun invert(x: Double, y: Double): DoubleArray
}

interface ProjectableInvertable : Projectable, Invertable

interface Projection : ProjectableInvertable {
    var scale: Double
    var translate: DoubleArray
    var center: Array<Angle>
    var precision: Double
    var rotate: Array<Angle>

    var preClip: (Stream) -> Stream
    var postClip: (Stream) -> Stream
    var clipAngle: Double
    var clipExtent: Extent?

    fun stream(stream: Stream): Stream

    fun recenter()

    fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection
    fun fitWidth(width: Double, geo: GeoJsonObject): Projection
    fun fitHeight(height: Double, geo: GeoJsonObject): Projection
    fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection
}

fun compose(a: Projectable, b: Projectable): Projectable {
    if (a is Invertable && b is Invertable) {
        return object : ProjectableInvertable {
            override fun projectLambda(lambda: Double, phi: Double): Double {
                val aX = a.projectLambda(lambda, phi)
                val aY = a.projectPhi(lambda, phi)
                return b.projectLambda(aX, aY)
            }
            override fun projectPhi(lambda: Double, phi: Double): Double {
                val aX = a.projectLambda(lambda, phi)
                val aY = a.projectPhi(lambda, phi)
                return b.projectPhi(aX, aY)
            }

            override fun project(lambda: Double, phi: Double): DoubleArray {
                val p = a.project(lambda, phi)
                return b.project(p[0], p[1])
            }

            override fun invert(x: Double, y: Double): DoubleArray {
                val p = b.invert(x, y)
                return a.invert(p[0], p[1])
            }
        }
    } else {
        return object : Projectable {
            override fun projectLambda(lambda: Double, phi: Double): Double {
                val aX = a.projectLambda(lambda, phi)
                val aY = a.projectPhi(lambda, phi)
                return b.projectLambda(aX, aY)
            }
            override fun projectPhi(lambda: Double, phi: Double): Double {
                val aX = a.projectLambda(lambda, phi)
                val aY = a.projectPhi(lambda, phi)
                return b.projectPhi(aX, aY)
            }
            override fun project(lambda: Double, phi: Double): DoubleArray {
                val p = a.project(lambda, phi)
                return b.project(p[0], p[1])
            }
        }
    }
}

class TransformRadians(stream: Stream) : ModifiedStream(stream) {
    override fun point(x: Double, y: Double, z: Double) = stream.point(x.toRadians(), y.toRadians(), z.toRadians())
}

fun projection(projection: Projectable, init: MutableProjection.() -> Unit) = MutableProjection(projection).apply(init)


open class MutableProjection(val projection: Projectable) : Projection {


    protected var cache: Stream? = null
    protected var cacheStream: Stream? = null

    // TODO Change
    protected fun getCachedStream(stream: Stream): Stream? =
        if (cache != null && cacheStream == stream) cache else null

    // TODO Change
    protected fun cache(stream1: Stream, stream2: Stream) {
        cache = stream2
        cacheStream = stream1
    }

    private val clipAntimeridian: (Stream) -> Stream = clipAntimeridian()
    val noClip: (Stream) -> Stream = { it }

    override var preClip: (Stream) -> Stream = clipAntimeridian
        set(value) {
            field = value
        }

    override var postClip: (Stream) -> Stream = noClip
        set(value) {
            field = value
        }

    // TODO : manage angles-range (ex. -180..-90 & 90..180) to permit see-through ?
    private var theta: Double = Double.NaN
    override var clipAngle: Double
        get() = theta
        set(value) {
            if (value.isNaN()) {
                theta = Double.NaN
                preClip = clipAntimeridian()
            } else {
                theta = value.toRadians()
                preClip = clipCircle(theta)
            }
        }

    override var clipExtent: Extent? = null
        set(value) {
            field = value
            if (value != null) {
                postClip = io.data2viz.geo.clip.clipExtent(value)
            } else {
                postClip = noClip
            }
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

    // Scale
    private var k = 150.0

    override var scale: Double
        get() = k
        set(value) {
            k = value
            recenter()
        }

    // Translate
    private var x = 480.0
    private var y = 250.0
    override var translate: DoubleArray
        get() = doubleArrayOf(x, y)
        set(value) {
            x = value[0]
            y = value[1]
            recenter()
        }

    // Center
    private var dx = 0.0
    private var dy = 0.0
    private var lambda = 0.0
    private var phi = 0.0
    override var center
        get() = arrayOf(lambda.deg, phi.deg)
        set(value) {
            lambda = value[0].rad
            phi = value[1].rad
            recenter()
        }

    // Rotate
    private var deltaLambda = 0.0
    private var deltaPhi = 0.0
    private var deltaGamma = 0.0
    private lateinit var rotator: Projectable


    override var rotate: Array<Angle>
        get() = arrayOf(deltaLambda.deg, deltaPhi.deg, deltaGamma.deg)
        set(value) {
            deltaLambda = value[0].rad
            deltaPhi = value[1].rad
            deltaGamma = if (value.size > 2) value[2].rad else 0.0
            recenter()
        }

    private lateinit var projectRotate: Projectable

    private val projectTransform: Projectable = object : Projectable {

        override fun projectLambda(lambda: Double, phi: Double): Double
                = projection.projectLambda(lambda, phi) * k + dx

        override fun projectPhi(lambda: Double, phi: Double): Double
                = dy - projection.projectPhi(lambda, phi) * k

        override fun project(lambda: Double, phi: Double): DoubleArray {
            val p = projection.project(lambda, phi)
            return doubleArrayOf(p[0] * k + dx, dy - p[1] * k)
        }
    }

    // Precision
    private var delta2 = 0.5
    private var projectResample = resample(projectTransform, delta2)
    override var precision: Double
        get() = sqrt(delta2)
        set(value) {
            delta2 = value * value
            projectResample = resample(projectTransform, delta2)
            reset()
        }

    private val transformRadians: (stream: Stream) -> ModifiedStream = { stream: Stream ->
        object : ModifiedStream(stream) {
            override fun point(x: Double, y: Double, z: Double) =
                stream.point(x.toRadians(), y.toRadians(), z.toRadians())
        }
    }

    private fun transformRotate(rotate: Projectable): (stream: Stream) -> ModifiedStream = { stream: Stream ->
        object : ModifiedStream(stream) {
            override fun point(x: Double, y: Double, z: Double) {
                stream.point(rotate.projectLambda(x, y), rotate.projectPhi(x, y), 0.0)
            }
        }
    }

    override fun projectLambda(lambda: Double, phi: Double): Double
            = projection.projectLambda(lambda.toRadians(), phi.toRadians()) * k + dx

    override fun projectPhi(lambda: Double, phi: Double): Double
            = dy - projection.projectPhi(lambda.toRadians(), phi.toRadians()) * k

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val p = projectRotate.project(lambda.toRadians(), phi.toRadians())
        return doubleArrayOf(p[0] * k + dx, dy - p[1] * k)
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        require(projectRotate is Invertable, { "This projection is not invertable." })

        val p = (projectRotate as Invertable).invert((x - dx) / k, (dy - y) / k)
        return doubleArrayOf(p[0].toDegrees(), p[1].toDegrees())
    }

    override fun stream(stream: Stream): Stream {
        var cachedStream = getCachedStream(stream)
        if (cachedStream == null) {
            cachedStream = transformRadians(transformRotate(rotator)(preClip(projectResample(postClip(stream)))))
            cache(cachedStream, cachedStream)
        }
        return cachedStream
    }

    override fun recenter() {
        rotator = rotateRadians(deltaLambda, deltaPhi, deltaGamma)
        projectRotate = compose(rotator, projection)

        dx = x - (projection.projectLambda(lambda, phi)* k)
        dy = y + (projection.projectPhi(lambda, phi) * k)
    }

    fun reset() {
        cache = null
        cacheStream = null
    }
}

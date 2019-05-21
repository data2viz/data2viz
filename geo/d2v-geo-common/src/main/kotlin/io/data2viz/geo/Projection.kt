package io.data2viz.geo.projection


import io.data2viz.geo.DelegateStreamAdapter
import io.data2viz.geo.Stream
import io.data2viz.geo.clip.clipAntimeridian
import io.data2viz.geo.clip.clipCircle
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.*
import kotlin.math.sqrt


/**
 * Project a single Geo point (lon, lat)
 */
interface Projectable {

    /**
     * Project a geo point
     */
    fun project(lambda: Double, phi: Double): DoubleArray

    /**
     * Default implementation of a longitude projection (can be overrided)
     */
    fun projectLambda(lambda: Double, phi: Double): Double = project(lambda, phi)[0]

    /**
     * Default implementation of a latitude projection (can be overrided)
     */
    fun projectPhi(lambda: Double, phi: Double): Double = project(lambda, phi)[1]
}


/**
 * Todo document
 */
interface Invertable {
    fun invert(x: Double, y: Double): DoubleArray
}

/**
 * Todo do we need this interface? Why just not keep the two inherited interfaces.
 */
interface ProjectableInvertable : Projectable, Invertable


/**
 * Todo document
 */
interface Projection : ProjectableInvertable {


    /**
     * The scale factor corresponds linearly to the distance between projected points;
     * however, absolute scale factors are not equivalent across projections.
     */
    var scale: Double

    /**
     * The translation offset determines the pixel coordinates of the projection’s center.
     * The default translation offset places ⟨0°,0°⟩ at the center of a 960×500 area.
     */
    var translate: DoubleArray

    /**
     * a two-element array of longitude and latitude in degrees
     */
    var center: Array<Angle>

    /**
     * The threshold for the projection’s adaptive resampling pixels.
     * This value corresponds to the Douglas–Peucker distance.
     * Defaults to √0.5 ≅ 0.70710…
     */
    var precision: Double

    /**
     * The projection’s three-axis spherical rotation to
     * the specified angles, which must be a two- or three-element array of numbers [lambda, phi, gamma]
     * specifying the rotation angles in degrees about each spherical axis
     * (these correspond to yaw, pitch and roll).
     */
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

/**
 * Todo document
 */
fun compose(a: Projectable, b: Projectable): Projectable =
    if (a is Invertable && b is Invertable) {
        object : ProjectableInvertable {
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
        object : Projectable {
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

class TransformRadians(stream: Stream) : DelegateStreamAdapter(stream) {
    override fun point(x: Double, y: Double, z: Double) = delegate.point(x.toRadians(), y.toRadians(), z.toRadians())
}

fun projection(projection: Projectable, init: MutableProjection.() -> Unit) = MutableProjection(projection).apply(init)


/**
 * todo What is it?
 */
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

    override var postClip: (Stream) -> Stream = noClip

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
        get() = arrayOf(lambda.rad, phi.rad)
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
        get() = arrayOf(deltaLambda.rad, deltaPhi.rad, deltaGamma.rad)
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

    private val transformRadians: (stream: Stream) -> DelegateStreamAdapter = { stream: Stream ->
        object : DelegateStreamAdapter(stream) {
            override fun point(x: Double, y: Double, z: Double) =
                stream.point(x.toRadians(), y.toRadians(), z.toRadians())
        }
    }

    private fun transformRotate(rotate: Projectable): (stream: Stream) -> DelegateStreamAdapter = { stream: Stream ->
        object : DelegateStreamAdapter(stream) {
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
            cachedStream = fullCycleStream(stream)
            cache(cachedStream, cachedStream)
        }
        return cachedStream
    }

    private fun fullCycleStream(stream: Stream) =
        transformRadians(transformRotate(rotator)(preClip(projectResample(postClip(stream)))))

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

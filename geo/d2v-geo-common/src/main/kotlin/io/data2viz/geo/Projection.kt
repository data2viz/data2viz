package io.data2viz.geo


import io.data2viz.geo.clip.clipAntimeridian
import io.data2viz.geo.clip.clipCircle
import io.data2viz.geo.projection.resample
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.rad
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.sqrt

/**
 * Project a single Geo point (lon, lat)
 */
interface Projectable {

    /**
     * Project a geo point
     * By default uses projectLambda & projectPhi but can be overriden for better performance
     */
    fun project(lambda: Double, phi: Double) = doubleArrayOf(
        projectLambda(lambda, phi),
        projectPhi(lambda, phi)
    )

    /**
     * Default implementation of a longitude projection (can be overrided)
     */
    fun projectLambda(lambda: Double, phi: Double): Double

    /**
     * Default implementation of a latitude projection (can be overrided)
     */
    fun projectPhi(lambda: Double, phi: Double): Double
}

/**
 * Todo document
 */
interface Invertable {
    //    fun invert(point: DoubleArray): DoubleArray
    fun invert(x: Double, y: Double): DoubleArray
}



/**
 * Todo document
 */
interface Projection : Projectable, Invertable {
    /**
     * The scale factor corresponds linearly to the distance between projected points;
     * however, absolute scale factors are not equivalent across projections.
     */
    var scale: Double

    /**
     *
     * TODO: enhance docs
     * The translation offset determines the pixel coordinates of the projection’s center.
     * The default translation offset places ⟨0°,0°⟩ at the center of a 960×500 area.
     */
    var x: Double
    var y: Double

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

    fun translate(x: Double, y: Double)


    fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection
    fun fitWidth(width: Double, geo: GeoJsonObject): Projection
    fun fitHeight(height: Double, geo: GeoJsonObject): Projection
    fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection
}

/**
 * Todo document
 */
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

            override fun invert(x: Double, y: Double): DoubleArray {
                val p = b.invert(x, y)
                return a.invert(p[0], p[1])
            }
        }
    } else {
        return object : Projectable {

//            override fun project(point: DoubleArray) {
//                point[0] = a.projectLambda(point[0], point[1])
//                point[1] = a.projectPhi(point[0], point[1])
//                b.project(point)
//            }

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

        }
    }
}

class TransformRadians(stream: Stream) : DelegateStreamAdapter(stream) {
    override fun point(x: Double, y: Double, z: Double) = delegate.point(x.toRadians(), y.toRadians(), z.toRadians())
}

fun projection(projection: Projectable, init: MutableProjection.() -> Unit) = MutableProjection(
    projection
).apply(init)


/**
 * todo What is it?
 */
abstract class BaseProjection() : Projection {
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
        return fitExtent(this, extent, geo)
    }

    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection {
        return fitWidth(this, width, geo)
    }

    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection {
        return fitHeight(this, height, geo)
    }

    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection {
        return fitSize(this, width, height, geo)
    }

    // Scale
    protected var k = 150.0

    override var scale: Double
        get() = k
        set(value) {
            k = value
            recenter()
        }

    private var _x = 480.0
    private var _y = 250.0

    // Translate
    override var x
        get () = _x
        set(value) {
            _x = value
            recenter()
        }
    override var y
        get () = _y
        set(value) {
            _y = value
            recenter()
        }


    override fun translate(x: Double, y: Double) {
        _x = x;
        _y = y;
        recenter()
    }

    // Center
    protected var dx = 0.0
    protected var dy = 0.0
    protected var lambda = 0.0
    protected var phi = 0.0
    override var center
        get() = arrayOf(lambda.rad, phi.rad)
        set(value) {
            lambda = value[0].rad
            phi = value[1].rad
            recenter()
        }

    // Rotate
    protected var deltaLambda = 0.0
    protected var deltaPhi = 0.0
    protected var deltaGamma = 0.0
    protected lateinit var rotator: Projectable


    override var rotate: Array<Angle>
        get() = arrayOf(deltaLambda.rad, deltaPhi.rad, deltaGamma.rad)
        set(value) {
            deltaLambda = value[0].rad
            deltaPhi = value[1].rad
            deltaGamma = if (value.size > 2) value[2].rad else 0.0
            recenter()
        }

    protected lateinit var projectRotate: Projectable

    protected val projectTransform: Projectable = createProjectTransform()

    abstract fun createProjectTransform(): Projectable

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

    override abstract fun projectLambda(lambda: Double, phi: Double): Double;

    override abstract fun projectPhi(lambda: Double, phi: Double): Double;


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

    open protected fun fullCycleStream(stream: Stream): Stream {
        // TODO: hack should be removed
        projectResample = resample(projectTransform, delta2)
        return transformRadians(transformRotate(rotator)(preClip(projectResample(postClip(stream)))))
    }

    abstract override fun recenter()

    fun reset() {
        cache = null
        cacheStream = null
    }
}

open class MutableProjection(val projection: Projectable) : BaseProjection() {
    override fun createProjectTransform(): Projectable {
        return object : Projectable {
            private fun internalProjectLambda(lambda: Double) =
                lambda * k + dx

            private fun internalProjectPhi(phi: Double) =
                dy - phi * k

            override fun projectLambda(lambda: Double, phi: Double): Double =
                internalProjectLambda(projection.projectLambda(lambda, phi))

            override fun projectPhi(lambda: Double, phi: Double): Double =
                internalProjectPhi(projection.projectPhi(lambda, phi))


//        override fun project(point: DoubleArray) {
//            projection.project(point)
//            point[0] = internalProjectLambda(point[0])
//            point[1] = internalProjectPhi(point[1])
//        }

        }
    }


    override fun projectLambda(lambda: Double, phi: Double): Double =
        projectTransform.projectLambda(lambda.toRadians(), phi.toRadians())

    override fun projectPhi(lambda: Double, phi: Double): Double =
        projectTransform.projectPhi(lambda.toRadians(), phi.toRadians())

    override fun recenter() {
        rotator = rotateRadians(deltaLambda, deltaPhi, deltaGamma)
        projectRotate = compose(rotator, projection)

        dx = x - (projection.projectLambda(lambda, phi) * k)
        dy = y + (projection.projectPhi(lambda, phi) * k)
    }

}

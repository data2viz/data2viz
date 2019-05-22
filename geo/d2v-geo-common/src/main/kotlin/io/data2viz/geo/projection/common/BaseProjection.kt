package io.data2viz.geo.projection.common

import io.data2viz.geo.geometry.clip.clipAntimeridian
import io.data2viz.geo.geometry.clip.clipCircle
import io.data2viz.geo.stream.DelegateStreamAdapter
import io.data2viz.geo.stream.Stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.rad
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.sqrt


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
                postClip = io.data2viz.geo.geometry.clip.clipExtent(value)
            } else {
                postClip = noClip
            }
        }

    override fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.geojson.fitExtent(this, extent, geo)
    }

    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.geojson.fitWidth(this, width, geo)
    }

    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.geojson.fitHeight(this, height, geo)
    }

    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection {
        return io.data2viz.geo.geojson.fitSize(this, width, height, geo)
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


    override fun invert(lambda: Double, phi: Double): DoubleArray {
        require(projectRotate is Invertable, { "This projection is not invertable." })

        val p = (projectRotate as Invertable).invert((lambda - dx) / k, (dy - phi) / k)
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



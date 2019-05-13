package io.data2viz.geo.projection

import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.tan

class MultiplexStream(val streams: Collection<Stream>) : Stream {
    override fun point(x: Double, y: Double, z: Double) = streams.forEach {
        it.point(x, y, z)
    }

    override fun lineStart() = streams.forEach {
        it.lineStart()
    }

    override fun lineEnd() = streams.forEach {
        it.lineEnd()
    }

    override fun polygonStart() = streams.forEach {
        it.polygonStart()
    }

    override fun polygonEnd() = streams.forEach {
        it.polygonEnd()
    }

    override fun sphere() = streams.forEach {
        it.sphere()
    }
}

fun alberUSAProjection() = alberUSAProjection {

}

fun alberUSAProjection(init: Projection.() -> Unit) = AlberUSAProjection().also {
    it.scale = 1070.0
}.also(init)

//// A composite projection for the United States, configured by default for
//// 960×500. The projection also works quite well at 960×600 if you change the
//// scale to 1285 and adjust the translate accordingly. The set of standard
//// parallels for each region comes from USGS, which is published here:
//// http://egsc.usgs.gov/isb/pubs/MapProjections/projections.html#albers
class AlberUSAProjection() : Projection {


    val lower48 = albersProjection()
    val alaska = conicEqualAreaProjection {
        rotate = arrayOf(154.0.deg, 0.0.deg)
        center = arrayOf((-2.0).deg, 58.5.deg)
        parallels = arrayOf(55.0.deg, 65.0.deg)
    }
    val hawaii = conicEqualAreaProjection {
        rotate = arrayOf(157.0.deg, 0.0.deg)
        center = arrayOf((-3.0).deg, 19.9.deg)
        parallels = arrayOf(8.0.deg, 18.0.deg)

    }

    var translateX = 0.0
    var translateY = 0.0

    val pointStream = object : Stream {

        override fun point(x: Double, y: Double, z: Double) {
            point = doubleArrayOf(x, y)
        }
    }

    var point = doubleArrayOf(0.0, 0.0)
    var pointLower48 = lower48.stream(pointStream)
    var pointHawaii = hawaii.stream(pointStream)
    var pointAlaska = alaska.stream(pointStream)


    override fun project(lambda: Double, phi: Double): DoubleArray {

        return lower48.project(lambda, phi)
    }

    override fun projectLambda(lambda: Double, phi: Double): Double = project(lambda, phi)[0]

    override fun projectPhi(lambda: Double, phi: Double): Double = project(lambda, phi)[1]

    override fun invert(x: Double, y: Double): DoubleArray {
        val k = lower48.scale
        val t = lower48.translate
        val newX = (x - t[0]) / k
        val newY = (y - t[1]) / k

        val projection = when {
            newY >= 0.120 && newY < 0.234 && newX >= -0.425 && newX < -0.214 -> alaska
            newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115 -> hawaii
            else -> lower48
        }

        return projection.invert(x, y)
    }

    override var center: Array<Angle>
        get() = lower48.center
        set(value) {
            lower48.center = value
            hawaii.center = value
            alaska.center = value
        }
    override var rotate: Array<Angle>
        get() = lower48.rotate
        set(value) {
            lower48.rotate = value
            hawaii.rotate = value
            alaska.rotate = value
        }
    override var preClip: (Stream) -> Stream
        get() = lower48.preClip
        set(value) {
            lower48.preClip = value
            hawaii.preClip = value
            alaska.preClip = value
        }
    override var postClip: (Stream) -> Stream
        get() = lower48.postClip
        set(value) {
            lower48.postClip = value
            hawaii.postClip = value
            alaska.postClip = value
        }
    override var clipAngle: Double
        get() = lower48.clipAngle
        set(value) {
            lower48.clipAngle = value
            hawaii.clipAngle = value
            alaska.clipAngle = value
        }
    override var clipExtent: Extent?
        get() = lower48.clipExtent
        set(value) {
            lower48.clipExtent = value
            hawaii.clipExtent = value
            alaska.clipExtent = value
        }

    override fun recenter() {
        lower48.recenter()
        hawaii.recenter()
        alaska.recenter()
    }

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


    //    lower48 = albers(), lower48Point,
//    alaska = conicEqualArea().rotate([154, 0]).center([-2, 58.5]).parallels([55, 65]), alaskaPoint, // EPSG:3338
//    hawaii = conicEqualArea().rotate([157, 0]).center([-3, 19.9]).parallels([8, 18]), hawaiiPoint, // ESRI:102007
//    point, pointStream = {point: function(x, y) { point = [x, y]; }};

    override var translate: DoubleArray
        get() = lower48.translate
        set(value) {
            var k = lower48.scale

            translateX += value[0]
            translateY += value[1]

            var x = translateX
            var y = translateY;
            lower48.translate = value
            lower48.clipExtent = Extent(x - 0.455 * k, y - 0.238 * k, x + 0.455 * k, y + 0.238 * k)

            pointLower48 = lower48.stream(pointStream)

            alaska.translate = doubleArrayOf(x - 0.307 * k, y + 0.201 * k)
            alaska.clipExtent = Extent(
                x - 0.425 * k + EPSILON,
                y + 0.120 * k + EPSILON,
                x - 0.214 * k - EPSILON,
                y + 0.234 * k - EPSILON
            )

            pointAlaska = alaska.stream(pointStream)

            hawaii.translate = doubleArrayOf(x - 0.205 * k, y + 0.212 * k)
            hawaii.clipExtent = Extent(
                x - 0.214 * k + EPSILON,
                y + 0.166 * k + EPSILON,
                x - 0.115 * k - EPSILON,
                y + 0.234 * k - EPSILON
            )

            pointHawaii = hawaii.stream(pointStream)

            reset()
        }


    override var scale: Double
        get() = lower48.scale
        set(value) {
            lower48.scale = value
            alaska.scale = value * 0.35
            hawaii.scale = value
            reset()
        }

    override var precision: Double
        get() = lower48.precision
        set(value) {
            lower48.precision = value
            alaska.precision = value * 0.35
            hawaii.precision = value
            reset()
        }


//    private fun fullCycleStream(stream: Stream) =
//        MultiplexStream(lower48.stream())


    override fun stream(stream: Stream): Stream {
        var cachedStream = getCachedStream(stream)
        if (cachedStream == null) {
            cachedStream = fullCycleStream(stream)
            cache(cachedStream, cachedStream)
        }
        return cachedStream
    }

    fun fullCycleStream(stream: Stream): Stream {

        return MultiplexStream(
            listOf(
                lower48.stream(stream),
                alaska.stream(stream),
                hawaii.stream(stream)
            )
        )


//        return if (cache != null && cacheStream == stream) {
//            cache
//        } else {
//
//        }
//        return super.stream(stream)
        //        return cache && cacheStream === stream ? cache : cache = multiplex([lower48.stream(cacheStream = stream), alaska.stream(stream), hawaii.stream(stream)]);
    }

    override fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitExtent(lower48, extent, geo)


    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitWidth(lower48, width, geo)


    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitHeight(lower48, height, geo)


    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitSize(lower48, width, height, geo)


    fun reset() {
        cache = null
        cacheStream = null
    }

}


//import {epsilon} from "../math";
//import albers from "./albers";
//import conicEqualArea from "./conicEqualArea";
//import {fitExtent, fitSize, fitWidth, fitHeight} from "./fit";
//
//// The projections must have mutually exclusive clip regions on the sphere,
//// as this will avoid emitting interleaving lines and polygons.
//function multiplex(streams) {
//    var n = streams.length;
//    return {
//        point: function(x, y) { var i = -1; while (++i < n) streams[i].point(x, y); },
//        sphere: function() { var i = -1; while (++i < n) streams[i].sphere(); },
//        lineStart: function() { var i = -1; while (++i < n) streams[i].lineStart(); },
//        lineEnd: function() { var i = -1; while (++i < n) streams[i].lineEnd(); },
//        polygonStart: function() { var i = -1; while (++i < n) streams[i].polygonStart(); },
//        polygonEnd: function() { var i = -1; while (++i < n) streams[i].polygonEnd(); }
//    };
//}
//

//export default function() {
//    var cache,
//    cacheStream,

//
//
//
//    albersUsa.stream = function(stream) {

//    };
//
//    albersUsa.precision = function(_) {

//    };
//
//    albersUsa.scale = function(_) {

//    };
//
//    albersUsa.translate = function(_) {
//
//    };
//

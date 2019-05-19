package io.data2viz.geo.projection

import io.data2viz.geo.BaseProjection
import io.data2viz.geo.Projectable
import io.data2viz.geo.Projection
import io.data2viz.geo.Stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.EPSILON
import io.data2viz.math.deg

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

fun albersUSAProjection() = albersUSAProjection {

}

fun albersUSAProjection(init: Projection.() -> Unit) = AlbersUSAProjection().also {
    it.scale = 1070.0
}.also(init)


/**
 * A composite projection for the United States, configured by default for
 * 960×500. The projection also works quite well at 960×600 if you change the
 * scale to 1285 and adjust the translate accordingly. The set of standard
 * parallels for each region comes from USGS, which is published here:
 * http://egsc.usgs.gov/isb/pubs/MapProjections/projections.html#albers
 */
class AlbersUSAProjection() : BaseProjection() {
    override val projectTransform = object : Projectable {
        override fun project(lambda: Double, phi: Double): DoubleArray =
            project(lambda, phi)

    }


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


    override fun project(x: Double, y: Double): DoubleArray {

        val k = lower48.scale

        val newX = (x - lower48.x) / k
        val newY = (y - lower48.y) / k

        val projection = when {
            newY >= 0.120 && newY < 0.234 && newX >= -0.425 && newX < -0.214 -> alaska
            newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115 -> hawaii
            else -> lower48
        }

        return projection.project(x, y)
    }

    override fun projectLambda(lambda: Double, phi: Double): Double = project(lambda, phi)[0]

    override fun projectPhi(lambda: Double, phi: Double): Double = project(lambda, phi)[1]

    override fun invert(x: Double, y: Double): DoubleArray {
        val k = lower48.scale

        val newX = (x - lower48.x) / k
        val newY = (y - lower48.y) / k

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

    override var x: Double
        get() = super.x
        set(value) {
            super.x = value
            translateX += value
            translateNestedProjections()

        }

    private fun translateNestedProjections() {
        var k = lower48.scale

        var x = translateX
        var y = translateY;
        lower48.translate(x, y)
        lower48.clipExtent = Extent(x - 0.455 * k, y - 0.238 * k, x + 0.455 * k, y + 0.238 * k)

        pointLower48 = lower48.stream(pointStream)

        alaska.translate(x - 0.307 * k, y + 0.201 * k)
        alaska.clipExtent = Extent(
            x - 0.425 * k + EPSILON,
            y + 0.120 * k + EPSILON,
            x - 0.214 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )

        pointAlaska = alaska.stream(pointStream)

        hawaii.translate(x - 0.205 * k, y + 0.212 * k)
        hawaii.clipExtent = Extent(
            x - 0.214 * k + EPSILON,
            y + 0.166 * k + EPSILON,
            x - 0.115 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )

        pointHawaii = hawaii.stream(pointStream)

        reset()
    }

    override var y: Double
        get() = super.y
        set(value) {
            super.y = value
            translateY += value
            translateNestedProjections()
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

    override fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitExtent(lower48, extent, geo)


    override fun fitWidth(width: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitWidth(lower48, width, geo)


    override fun fitHeight(height: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitHeight(lower48, height, geo)


    override fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection =
        io.data2viz.geo.fitSize(lower48, width, height, geo)


}
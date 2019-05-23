package io.data2viz.geo.geojson.path

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.noop
import io.data2viz.geo.geojson.noop2
import io.data2viz.geo.geojson.stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.LineString
import io.data2viz.geojson.Position
import io.data2viz.math.toRadians
import io.data2viz.geo.geometry.path.MeasureStream
import kotlin.math.*

/**
 * Returns the great-arc distance in radians between the two points from and to.
 * Each point must be specified as a two-element array [longitude, latitude] in degrees.
 * This is the spherical equivalent of [MeasureStream] given a LineString of two points.
 */
fun geoDistance(from: Position, to: Position): Double {
    val line = LineString(arrayOf(from, to))
    return GeoLengthStream().result(line)
}



/**
 * Returns the great-arc length of the specified GeoJSON object in radians.
 * For polygons, returns the perimeter of the exterior ring plus that of any interior rings.
 * This is the spherical equivalent of path.measure.
 */
fun geoLength(geo: GeoJsonObject): Double
        = GeoLengthStream().result(geo)

/**
 * Returns the great-arc length of the specified GeoJSON object in radians.
 * For polygons, returns the perimeter of the exterior ring plus that of any interior rings.
 * This is the spherical equivalent of [MeasureStream]
 */
class GeoLengthStream : Stream {

    // TODO refactor function references :: to objects like in ProjectorResambleStream.
//  Function references have poor performance due to GC & memory allocation


    private var lengthSum = .0
    private var lambda0 = Double.NaN
    private var cosPhi0 = Double.NaN
    private var sinPhi0 = Double.NaN

    private var currentPoint: (Double, Double) -> Unit = noop2
    private var currentLineEnd: () -> Unit = noop

    fun result(geo: GeoJsonObject): Double {
        lengthSum = .0
        geo.stream(this)
        return lengthSum
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
    override fun lineStart() {
        currentPoint = ::lengthPointFirst
        currentLineEnd = ::lengthLineEnd
    }

    override fun lineEnd() = currentLineEnd()

    private fun lengthPointFirst(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        lambda0 = lambda
        sinPhi0 = sin(phi)
        cosPhi0 = cos(phi)
        currentPoint = ::lengthPoint
    }

    private fun lengthLineEnd() {
        currentPoint = noop2
        currentLineEnd = noop
    }

    private fun lengthPoint(x: Double, y: Double) {
        val lambda = x.toRadians()
        val phi = y.toRadians()
        val sinPhi = sin(phi)
        val cosPhi = cos(phi)
        val delta = abs(lambda - lambda0)
        val cosDelta = cos(delta)
        val sinDelta = sin(delta)
        val a = cosPhi * sinDelta
        val b = cosPhi0 * sinPhi - sinPhi0 * cosPhi * cosDelta
        val c = sinPhi0 * sinPhi + cosPhi0 * cosPhi * cosDelta
        lengthSum += atan2(sqrt(a * a + b * b), c)
        lambda0 = lambda
        sinPhi0 = sinPhi
        cosPhi0 = cosPhi
    }
}
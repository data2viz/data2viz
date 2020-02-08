/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.geojson.path

import io.data2viz.geo.StreamPoint
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
class GeoLengthStream : Stream() {

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

    override fun point(point: StreamPoint) = currentPoint(point.x, point.y)
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
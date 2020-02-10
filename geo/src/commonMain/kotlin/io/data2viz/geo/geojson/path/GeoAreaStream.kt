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

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.noop
import io.data2viz.geo.geojson.stream
import io.data2viz.geojson.GeoJsonObject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import io.data2viz.geo.geometry.path.AreaStream
import io.data2viz.math.*

fun geoArea(geo: GeoJsonObject) = GeoAreaStream().result(geo)


private val local_noop: (GeoJsonPoint) -> Unit = { _ -> }


/**
 * Stream and returns via [result] the spherical area of the specified GeoJSON object in steradians.
 * This is the spherical equivalent of [AreaStream]
 */
class GeoAreaStream : Stream<GeoJsonPoint>() {

    // TODO refactor function references :: to objects like in ProjectorResambleStream.
    //  Function references have poor performance due to GC & memory allocation

    private var areaSum = .0
    internal var areaRingSum = .0

    private var point00 = GeoJsonPoint(0.deg, 0.deg, .0)
    private var point0 = GeoJsonPoint(0.deg, 0.deg, .0)

    private var lambda00 = 0.rad
    private var phi00 = 0.rad

    private var lon0 = 0.rad
    private var phi0 = 0.rad

    private var cosPhi0 = Double.NaN
    private var sinPhi0 = Double.NaN

    private var currentPoint: (GeoJsonPoint) -> Unit = local_noop
    private var currentLineStart: () -> Unit = noop
    private var currentLineEnd: () -> Unit = noop

    fun result(geo: GeoJsonObject): Double {
        areaSum = .0
        geo.stream(this)
        return areaSum * 2
    }

    override fun point(point:GeoJsonPoint) = currentPoint(point)
    override fun lineStart() = currentLineStart()
    override fun lineEnd() = currentLineEnd()

    override fun polygonStart() {
        areaRingSum = .0
        currentLineStart = ::areaRingStart
        currentLineEnd = ::areaRingEnd
    }

    override fun polygonEnd() {
        currentLineStart = noop
        currentLineEnd = noop
        currentPoint = local_noop
        areaSum += areaRingSum + if (areaRingSum < 0) TAU else .0
    }

    override fun sphere() {
        areaSum += TAU
    }

    private fun areaRingStart() {
        currentPoint = ::areaPointFirst
    }

    private fun areaPointFirst(point: GeoJsonPoint) {
        currentPoint = ::areaPoint
//        lambda00 = x
//        phi00 = y
        point00 = point
        point0 = point
        lon0 = point.lon
        phi0 = point.lat
        val phi = point.lat / 2 + ANGLE_QUARTERPI
        cosPhi0 = phi.cos
        sinPhi0 = phi.sin
    }

    private fun areaPoint(point: GeoJsonPoint) {
        val lon = point.lon
        val phi = point.lat / 2.0 + ANGLE_QUARTERPI // half the angular distance from south pole

        // Spherical excess E for a spherical triangle with vertices: south pole,
        // previous point, current point.  Uses a formula derived from Cagnoli’s
        // theorem.  See Todhunter, Spherical Trig. (1871), Sec. 103, Eq. (2).
        val dLon = lon - lon0
        val signLon = if (dLon >= ANGLE_ZERO) 1.0 else -1.0
        val adLambda = signLon * dLon
        val cosPhi = phi.cos
        val sinPhi = phi.sin
        val k = sinPhi0 * sinPhi
        val u = cosPhi0 * cosPhi + k * adLambda.cos
        val v = k * signLon * adLambda.sin
        areaRingSum += atan2(v, u)

        // Advance the previous points.
        lon0 = lon
        cosPhi0 = cosPhi
        sinPhi0 = sinPhi
    }

    private fun areaRingEnd() {
        areaPoint(point00)
    }
}
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

package io.data2viz.geo.geometry.path

import io.data2viz.geo.Point3D
import io.data2viz.geo.geojson.noop
import io.data2viz.geo.geojson.noop2
import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.path.GeoAreaStream
import kotlin.math.abs



/**
 * Stream and returns via [area] the area of the specified GeoJSON object in cartesian.
 * This is the cartesian equivalent of [GeoAreaStream]
 */
internal class AreaStream : Stream<Point3D>() {

    // TODO : check for use of D3 "adder"

    private var areaSum = .0
    private var areaRingSum = .0
    private var x00 = Double.NaN
    private var y00 = Double.NaN
    private var x0 = Double.NaN
    private var y0 = Double.NaN

    private var currentPoint: (Double, Double) -> Unit = noop2
    private var currentLineStart: () -> Unit = noop
    private var currentLineEnd: () -> Unit = noop

    fun area(): Double {
        val a = areaSum / 2.0
        areaSum = .0
        return a
    }

    override fun point(point: Point3D) = currentPoint(point.x, point.y)
    override fun lineStart() = currentLineStart()
    override fun lineEnd() = currentLineEnd()
    override fun polygonStart() {
        currentLineStart = ::areaRingStart
        currentLineEnd = ::areaRingEnd
    }

    override fun polygonEnd() {
        currentLineStart = noop
        currentLineEnd = noop
        currentPoint = noop2
        areaSum += abs(areaRingSum)
        areaRingSum = .0
    }

    private fun areaRingStart() {
        currentPoint = ::areaPointFirst
    }

    private fun areaPointFirst(x: Double, y: Double) {
        currentPoint = ::areaPoint
        x00 = x
        x0 = x
        y00 = y
        y0 = y
    }

    private fun areaPoint(x: Double, y: Double) {
        areaRingSum += y0 * x - x0 * y
        x0 = x
        y0 = y
    }

    private fun areaRingEnd() {
        areaPoint(x00, y00)
    }
}
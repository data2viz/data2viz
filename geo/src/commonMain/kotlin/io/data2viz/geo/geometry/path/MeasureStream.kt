/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

import io.data2viz.geo.geojson.noop2
import io.data2viz.geo.stream.Stream
import kotlin.math.sqrt
import io.data2viz.geo.geojson.path.GeoLengthStream



/**
 * Returns the great-arc length of the specified object in radians.
 * For polygons, returns the perimeter of the exterior ring plus that of any interior rings.
 * This is the cartesian equivalent of [GeoLengthStream]
 */
internal class MeasureStream : Stream {

    // TODO : check for use of D3 "adder"
    // TODO refactor function references :: to objects like in ProjectorResambleStream.
    //  Function references have poor performance due to GC & memory allocation

    private var lengthSum = .0
    private var lengthRing = false
    private var x00 = Double.NaN
    private var y00 = Double.NaN
    private var x0 = Double.NaN
    private var y0 = Double.NaN

    private var currentPoint: (Double, Double) -> Unit = noop2

    fun result(): Double {
        val result = lengthSum
        lengthSum = .0
        return result
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
    override fun lineStart() {
        currentPoint = ::lengthPointFirst
    }

    override fun lineEnd() {
        if (lengthRing) lengthPoint(x00, y00)
        currentPoint = noop2
    }

    override fun polygonStart() {
        lengthRing = true
    }

    override fun polygonEnd() {
        lengthRing = false
    }

    private fun lengthPointFirst(x: Double, y: Double) {
        currentPoint = ::lengthPoint
        x0 = x
        x00 = x
        y0 = y
        y00 = y
    }

    private fun lengthPoint(x: Double, y: Double) {
        x0 -= x
        y0 -= y
        lengthSum += sqrt(x0 * x0 + y0 * y0)
        x0 = x
        y0 = y
    }
}

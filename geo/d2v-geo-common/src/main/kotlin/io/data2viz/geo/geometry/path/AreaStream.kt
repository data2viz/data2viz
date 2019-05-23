package io.data2viz.geo.geometry.path

import io.data2viz.geo.geojson.noop
import io.data2viz.geo.geojson.noop2
import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geojson.path.GeoAreaStream
import kotlin.math.abs



/**
 * Stream and returns via [result] the spherical area of the specified GeoJSON object in cartesian.
 * This is the cartesian equivalent of [GeoAreaStream]
 */
internal class AreaStream : Stream {

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

    fun result(): Double {
        val a = areaSum / 2.0
        areaSum = .0
        return a
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
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
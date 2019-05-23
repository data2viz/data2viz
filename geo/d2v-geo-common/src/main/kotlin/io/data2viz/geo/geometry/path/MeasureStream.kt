package io.data2viz.geo.geometry.path

import io.data2viz.geo.geojson.noop2
import io.data2viz.geo.stream.Stream
import kotlin.math.sqrt

// TODO : check for use of D3 "adder"
// TODO refactor function references :: to objects like in ProjectorResambleStream.
//  Function references have poor performance due to GC & memory allocation
/**
 * TODO: docs
 */
internal class MeasureStream : Stream {

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
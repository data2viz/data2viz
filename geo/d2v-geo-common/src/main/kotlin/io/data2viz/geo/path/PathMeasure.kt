package io.data2viz.geo.path

import io.data2viz.geo.projection.Stream
import kotlin.math.sqrt

class PathMeasure : Stream {

    private var lengthSum = .0
    private var lengthRing = false
    private var x00 = Double.NaN
    private var y00 = Double.NaN
    private var x0 = Double.NaN
    private var y0 = Double.NaN

    private val noop3: (Double, Double, Double) -> Unit = { x, y, z -> }
    private var currentPoint: (Double, Double, Double) -> Unit = noop3

    fun result(): Double {
        val a = lengthSum
        lengthSum = .0
        return a
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y, z)
    override fun lineStart() {
        currentPoint = ::lengthPointFirst
    }
    override fun lineEnd() {
        if (lengthRing) lengthPoint(x00, y00, .0)
        currentPoint = noop3
    }
    override fun polygonStart() {
        lengthRing = true
    }
    override fun polygonEnd() {
        lengthRing = false
    }

    private fun lengthPointFirst(x: Double, y: Double, z: Double) {
        currentPoint = ::lengthPoint
        x0 = x
        x00 = x
        y0 = y
        y00 = y
    }

    private fun lengthPoint(x: Double, y: Double, z: Double) {
        x0 -= x
        y0 -= y
        lengthSum += sqrt(x0 * x0 + y0 * y0)
        x0 = x
        y0 = y
    }
}
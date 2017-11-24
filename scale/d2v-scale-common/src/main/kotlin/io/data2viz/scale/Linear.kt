package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.core.tickStep
import io.data2viz.interpolate.interpolateHsl
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.ceil
import kotlin.math.floor

open class LinearScale<R>(interpolateRange: (R, R) -> (Double) -> R,
                          uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                          rangeComparator: Comparator<R>? = null)
    : ContinuousScale<R>(::interpolateNumber, ::uninterpolateNumber, interpolateRange, uninterpolateRange, rangeComparator) {

    fun domain(vararg d: Double) {
        domain = d.toMutableList()
    }
    fun range(vararg r: R) {
        range = r.toMutableList()
    }

    fun nice(count: Int = 10): LinearScale<R> {
        val i = domain.size - 1
        val n = count
        val start = domain.first()
        val stop = domain.last()
        var step = tickStep(start, stop, n)

        if(step > 0) {
            step = tickStep(floor(start / step) * step, ceil(stop / step) * step, n)
            domain[0] = floor(start / step) * step
            domain[1] = ceil(stop / step) * step
        }

        return this
    }
}


val doubleComparator = naturalOrder<Double>()

fun linearScaleDouble(): LinearScale<Double> {
    return LinearScale<Double>(::interpolateNumber, ::uninterpolateNumber, doubleComparator)
}

fun linearScaleHSL(): LinearScale<HSL> = LinearScale<HSL>(::interpolateHsl)
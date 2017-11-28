package io.data2viz.scale

import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.interpolateRound
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.pow

/**
 * Power scales are similar to linear scales, except an exponential transform is applied to the input domain
 * value before the output range value is computed.
 * Each range value y can be expressed as a function of the domain value x: y = mx^k + b, where k is the exponent value.
 * Power scales also support negative domain values, in which case the input value and the resulting output
 * value are multiplied by -1.
 */
open class PowerScale<R>(exponent: Double = 1.0, interpolateRange: (R, R) -> (Double) -> R,
                          uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                          rangeComparator: Comparator<R>? = null)
    : LinearScale<R>(interpolateRange, uninterpolateRange, rangeComparator) {

    var exponent: Double = exponent
        set(value) {
            field = value
            rescale()
        }

    override fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double {
        val dFrom = raise(from, exponent)
        val dTo = raise(to, exponent) - dFrom

        return if (dTo == .0 || dTo == Double.NaN) { t -> dTo }
        else { t -> (raise(t, exponent) - dFrom) / dTo }
    }

    override fun interpolateDomain(from: Double, to: Double): (Double) -> Double {
        val ra = raise(from, exponent)
        val rb = raise(to, exponent) - ra
        return { t -> raise(ra + rb * t, 1.0 / exponent) }
    }

    private fun raise(x: Double, exponent: Double): Double = when {
        x < 0.0 -> -x.pow(exponent)
        else -> x.pow(exponent)
    }
}

fun powerScale(exponent:Double = 1.0): PowerScale<Double> {
    return PowerScale<Double>(exponent, ::interpolateNumber, ::uninterpolateNumber, naturalOrder<Double>())
}

fun powerScaleRound(exponent:Double = 1.0): PowerScale<Double> {
    return PowerScale<Double>(exponent, ::interpolateRound, ::uninterpolateNumber, naturalOrder<Double>())
}
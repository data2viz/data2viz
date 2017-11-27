package io.data2viz.scale

import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.interpolateRound
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.pow

open class PowerScale<R>(exponent: Double = 1.0, interpolateRange: (R, R) -> (Double) -> R,
                          uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                          rangeComparator: Comparator<R>? = null)
    : LinearScale<R>(interpolateRange, uninterpolateRange, rangeComparator) {

    var exponent: Double = exponent
        set(value) {
            field = value

            // force recompute
            domain = this.domain
        }

    override fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double {
        val da = raise(from, exponent)
        val db = raise(to, exponent) - da

        return if (db == .0 || db == Double.NaN) { { db } }
        else { t -> (raise(t, exponent) - da) / db }
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
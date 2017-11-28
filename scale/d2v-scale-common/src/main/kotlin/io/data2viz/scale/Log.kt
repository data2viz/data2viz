package io.data2viz.scale

import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.interpolateRound
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.*

/**
 * Log scales are similar to linear scales, except a logarithmic transform is applied to the input domain value
 * before the output range value is computed.
 * The mapping to the range value y can be expressed as a function of the domain value x: y = m log(x) + b.
 */
open class LogScale<R>(var base: Double = 10.0, interpolateRange: (R, R) -> (Double) -> R,
                       uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                       rangeComparator: Comparator<R>? = null)
    : ContinuousScale<R>(interpolateRange, uninterpolateRange, rangeComparator) {

    /**
     * As log(0) = -∞, a log scale domain must be strictly-positive or strictly-negative;
     * the domain must not include or cross zero. A log scale with a positive domain has a well-defined
     * behavior for positive values, and a log scale with a negative domain has a well-defined behavior for
     * negative values. (For a negative domain, input and output values are implicitly multiplied by -1.)
     * The behavior of the scale is undefined if you pass a negative value to a log scale with a positive
     * domain or vice versa.
     */
    override var domain: MutableList<Double> = arrayListOf(1.0, 10.0)
        set(value) {
            if (domain.contains(.0)) throw IllegalArgumentException("The domain interval must not contain 0, as log(0) = -∞.")
            val totalPositives = domain.filter { it > 0}.size
            val totalNegatives = domain.filter { it > 0}.size
            if ((totalPositives > 0 && totalPositives < domain.size)
                    || (totalNegatives > 0 && totalNegatives < domain.size))
                throw IllegalArgumentException("The domain interval must contain only positive or negative elements.")

            // copy the value (no binding intended)
            field = value.toMutableList()
            rescale()
        }

    override fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double {
        val diff = ln(to / from)
        return if (diff != .0 && diff != Double.NaN) { t -> ln(t / from) / diff }
        else { t -> diff }
    }

    override fun interpolateDomain(from: Double, to: Double): (Double) -> Double {
        return if (from < 0) { t -> -(-to.pow(t) * -from.pow(1 - t)) }
        else { t -> to.pow(t) * from.pow(1 - t) }
    }

    private fun nice(domain: List<Double>, floor: (Double) -> Double, ceil: (Double) -> Double): MutableList<Double> {
        val reversed = domain.last() < domain.first()
        val first = if(reversed) domain.size - 1 else 0
        val last  = if(reversed) 0 else domain.size - 1

        val newDomain = domain.toMutableList()
        newDomain[first] = floor(domain[first])
        newDomain[last] = ceil(domain[last])
        return newDomain
    }

    fun nice() {
        domain = nice(domain, { x -> base.pow(floor(log(x, base))) }, { x -> base.pow(ceil(log(x, base))) })
    }

    override fun ticks(count: Int): List<Double> {
        var domainStart = domain.first()
        var domainEnd = domain.last()
        val domainReversed = domainEnd < domainStart

        if (domainReversed) {
            domainStart = domain.last()
            domainEnd = domain.first()
        }

        var i = log(domainStart, base)
        var j = log(domainEnd, base)
        var tickList = arrayListOf<Double>()

        val test = !((base % 1 == .0) || (base % 1 == Double.NaN))

        if (test && (j - i < count)) {
            i = round(i) - 1
            j = round(j) + 1
            if (domainStart > 0) {
                while (i < j) {
                    val p = base.pow(i)
                    for (k in 1 until base.toInt()) {
                        val t = p * k
                        if (t < domainStart) continue
                        if (t > domainEnd) break
                        tickList.add(t)
                    }
                    ++i
                }
            } else {
                while (i < j) {
                    val p = base.pow(i)
                    for (k in (base - 1.0).toInt() until 0) {
                        val t = p * k
                        if (t < domainStart) continue
                        if (t > domainEnd) break
                        tickList.add(t)
                    }
                    ++i
                }
            }
        } else {
            tickList = (io.data2viz.core.ticks(i, j, min((j - i).toInt(), count)) as List<Double>).map({ base.pow(it) }) as ArrayList<Double>
        }

        return if (domainReversed) tickList.reversed() else tickList
    }
}

fun logScale(base: Double = 10.0): LogScale<Double> {
    return LogScale<Double>(base, ::interpolateNumber, ::uninterpolateNumber, naturalOrder<Double>())
}

fun logScaleRound(base: Double = 10.0): LogScale<Double> {
    return LogScale<Double>(base, ::interpolateRound, ::uninterpolateNumber, naturalOrder<Double>())
}
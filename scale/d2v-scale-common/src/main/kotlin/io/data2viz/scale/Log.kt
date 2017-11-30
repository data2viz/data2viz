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
    : ContinuousScaleImpl<R>(interpolateRange, uninterpolateRange, rangeComparator), NiceableScale<Double, R> {

    /**
     * As log(0) = -∞, a log scale domain must be strictly-positive or strictly-negative;
     * the domain must not include or cross zero. A log scale with a positive domain has a well-defined
     * behavior for positive values, and a log scale with a negative domain has a well-defined behavior for
     * negative values. (For a negative domain, input and output values are implicitly multiplied by -1.)
     * The behavior of the scale is undefined if you pass a negative value to a log scale with a positive
     * domain or vice versa.
     */
    override var domain: List<Double>
        get() = super.domain
        set(value) {
            if (value.contains(.0)) throw IllegalArgumentException("The domain interval must not contain 0, as log(0) = -∞.")
            val totalPositives = value.filter { it > 0}.size
            val totalNegatives = value.filter { it > 0}.size
            if ((totalPositives > 0 && totalPositives < value.size)
                    || (totalNegatives > 0 && totalNegatives < value.size))
                throw IllegalArgumentException("The domain interval must contain only positive or negative elements.")

            // copy the value (no binding intended)
            super.domain = value
        }

    override fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double {
        val diff = ln(to / from)
        return if (diff != .0 && diff != Double.NaN) { t -> ln(t / from) / diff }
        else { _ -> diff }
    }

    override fun interpolateDomain(from: Double, to: Double): (Double) -> Double {
        return if (from < 0) { t -> -(-to.pow(t) * -from.pow(1 - t)) }
        else { t -> to.pow(t) * from.pow(1 - t) }
    }

    private fun nice(values: List<Double>, floor: (Double) -> Double, ceil: (Double) -> Double): List<Double> {
        val reversed = values.last() < values.first()
        val first = if(reversed) values.size - 1 else 0
        val last  = if(reversed) 0 else values.size - 1

        val newDomain = values.toMutableList()
        newDomain[first] = floor(values[first])
        newDomain[last] = ceil(values[last])
        return newDomain
    }

    init {
        _domain.clear()
        _domain.addAll(arrayListOf(1.0, 10.0))
    }

    override fun nice(count: Int) {
        domain = nice(domain, { x -> base.pow(floor(log(x, base))) }, { x -> base.pow(ceil(log(x, base))) })
    }

    override fun ticks(count: Int): List<Double> {
        var domainStart = _domain.first()
        var domainEnd = _domain.last()
        val domainReversed = domainEnd < domainStart

        if (domainReversed) {
            domainStart = _domain.last()
            domainEnd = _domain.first()
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
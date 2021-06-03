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

package io.data2viz.scale

import io.data2viz.interpolate.Interpolator
import io.data2viz.interpolate.UnInterpolator
import io.data2viz.math.Percent
import io.data2viz.math.pct
import io.data2viz.math.ticks
import kotlin.math.*

/**
 * Log scales are similar to linear scales, except a logarithmic transform is applied to the input domain value
 * before the output range value is computed.
 * The mapping to the range value y can be expressed as a function of the domain value x: y = m log(x) + b.
 */
public open class LogScale
    constructor(base: Double = 10.0, interpolateRange: (Double, Double) -> Interpolator<Double>,
                       uninterpolateRange: ((Double, Double) -> UnInterpolator<Double>)? = null,
                       rangeComparator: Comparator<Double>? = null)
    : LinearScale<Double>(interpolateRange, uninterpolateRange, rangeComparator) {

    // this log function can accept negative values if needed
    private lateinit var innerLog: (Double) -> Double

    // when having negative values for the log, this pow translate them correctly
    private lateinit var innerPow: (Double) -> Double

    public var base: Double = base
        set(value) {
            field = value
            rescale()
        }

    /**
     * As log(0) = -∞, a log scale domain must be strictly-positive or strictly-negative;
     * the domain must not include or cross zero. A log scale with a positive domain has a well-defined
     * behavior for positive values, and a log scale with a negative domain has a well-defined behavior for
     * negative values. (For a negative domain, input and output values are implicitly multiplied by -1.)
     * The behavior of the scale is undefined if you pass a negative value to a log scale with a positive
     * domain or vice versa.
     */
    override var domain: List<Double>
        get() = _domain
        set(value) {
            val containPositive = value.first() > .0
            value.forEach {
                if (it == .0) throw IllegalArgumentException("The domain interval must not contain 0, as log(0) = -∞.")
                if ((containPositive && it < .0) || (!containPositive && it > .0))
                    throw IllegalArgumentException("The domain interval must contain only positive or negative elements.")
            }

            // copy the value (no binding intended)
            _domain.clear()
            _domain.addAll(value)
            rescale()
        }

    override fun rescale() {
        super.rescale()
        if (_domain.first() < 0) {
            innerLog = { -log(-it, base) }
            innerPow = { -(base.pow(-it)) }
        } else {
            innerLog = { log(it, base) }
            innerPow = { base.pow(it) }
        }
    }

    override fun uninterpolateDomain(from: Double, to: Double): UnInterpolator<Double> {
        val diff = ln(to / from)
        return if (diff != .0 && !diff.isNaN()) { t -> Percent(ln(t / from) / diff) }
        else { _ -> 0.pct }
    }

    override fun interpolateDomain(from: Double, to: Double): Interpolator<Double> {
        return if (from < 0) { t -> -(-to.pow(t.value) * -from.pow(1 - t.value)) }
        else { t -> to.pow(t.value) * from.pow(1 - t.value) }
    }

    private fun niceLogScale(values: List<Double>, floor: (Double) -> Double, ceil: (Double) -> Double): List<Double> {
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
        _domain.addAll(listOf(1.0, 10.0))
        _range.clear()
        _range.addAll(listOf(.0, 1.0))
    }

    override fun copy(): ContinuousScale<Double, Double> = LogScale(base, interpolateRange, uninterpolateRange, rangeComparator).also {
        it.domain = domain
        it.range = range
        it.clamp = clamp
    }

    override fun nice(count: Int) {
        domain = niceLogScale(domain, { x -> innerPow(floor(innerLog(x))) }, { x -> innerPow(ceil(innerLog(x))) })
    }

    override fun ticks(count: Int): List<Double> {
        var domainStart = _domain.first()
        var domainEnd = _domain.last()

        val domainReversed = domainEnd < domainStart
        if (domainReversed) {
            domainStart = _domain.last()
            domainEnd = _domain.first()
        }

        var logStart = innerLog(domainStart)
        var logEnd = innerLog(domainEnd)
        var tickList = mutableListOf<Double>()

        val integerBase = ((base % 1.0) == .0 || (base % 1.0).isNaN())
        if (integerBase && (logEnd - logStart < count)) {
            logStart = floor(logStart)
            logEnd = ceil(logEnd)
            if (domainStart > 0) {
                while (logStart <= logEnd) {
                    val p = innerPow(logStart)
                    for (k in 1 until base.toInt()) {
                        val t = p * k
                        if (t < domainStart) continue
                        if (t > domainEnd) break
                        tickList.add(t)
                    }
                    ++logStart
                }
            } else {
                while (logStart <= logEnd) {
                    val p = innerPow(logStart)
                    for (k in (base - 1).toInt() downTo 1) {
                        val t = p * k
                        if (t < domainStart) continue
                        if (t > domainEnd) break
                        tickList.add(t)
                    }
                    ++logStart
                }
            }
            if (tickList.size * 2 < count)
                tickList = ticks(domainStart, domainEnd, count).toMutableList()
        } else {
            tickList = (ticks(logStart, logEnd, min((logEnd - logStart).toInt(), count)).map { innerPow(it) }).toMutableList()
        }

        return if (domainReversed) tickList.reversed() else tickList
    }
}

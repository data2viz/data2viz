/*
 * Copyright (c) 2018-2020. data2viz sàrl.
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
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.uninterpolateNumber
import io.data2viz.math.tickStep
import kotlin.math.ceil
import kotlin.math.floor

public open class LinearScale<R>
    constructor(
        interpolateRange: (R, R) -> Interpolator<R>,
        uninterpolateRange: ((R, R) -> UnInterpolator<R>)? = null,
        rangeComparator: Comparator<R>? = null) :

    ContinuousScale<Double, R>(interpolateRange, uninterpolateRange, rangeComparator),
    Tickable<Double>,
    NiceableScale<Double> {

    public val comparator: Comparator<Double> = naturalOrder<Double>()

    override fun interpolateDomain(from: Double, to: Double): Interpolator<Double> =
        interpolateNumber(from, to)
    override fun uninterpolateDomain(from: Double, to: Double): UnInterpolator<Double> =
        uninterpolateNumber(from, to)
    override fun domainComparator(): Comparator<Double> = comparator

    @Deprecated("Convert the domainValue to Double before calling the scale.")
    public operator fun invoke(domainValue: Int): R {
        return this(domainValue.toDouble())
    }

    init {
        _domain.clear()
        _domain.addAll(listOf(.0, 1.0))
    }

    /**
     * Extends the domain so that it starts and ends on nice round values.
     * This method typically modifies the scale’s domain, and may only extend the bounds to the nearest round value.
     * An optional tick count argument allows greater control over the step size used to extend the bounds,
     * guaranteeing that the returned ticks will exactly cover the domain. Nicing is useful if the domain is computed
     * from data, say using extent, and may be irregular. For example, for a domain of [0.201479…, 0.996679…],
     * a nice domain might be [0.2, 1.0]. If the domain has more than two values, nicing the domain only affects
     * the first and last value.
     *
     * Nicing a scale only modifies the current domain; it does not automatically nice domains that are
     * subsequently set using continuous.domain. You must re-nice the scale after setting the new domain, if desired.
     */
    override fun nice(count: Int) {
        val last = _domain.size - 1
        var step = tickStep(_domain[0], _domain[last], count)
        val start = floor(_domain[0] / step) * step
        val stop = ceil(_domain[last] / step) * step

        if (step != .0) {
            step = tickStep(start, stop, count)
            _domain[0] = floor(start / step) * step
            _domain[last] = ceil(stop / step) * step
            rescale()
        }
    }

    override fun ticks(count: Int): List<Double> {
        return io.data2viz.math.ticks(_domain.first(), _domain.last(), count)
    }

    override fun copy(): ContinuousScale<Double, R> =
         LinearScale(
            interpolateRange,
            uninterpolateRange,
            rangeComparator
        ).also {
            it.domain = domain
            it.range = range
            it.clamp = clamp

        }

}
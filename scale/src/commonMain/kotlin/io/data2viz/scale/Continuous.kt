/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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
import io.data2viz.math.*
import kotlin.math.min


// uninterpolate  [value A .. value B] --> [0% .. 100%]
// interpolate [0% .. 100%] --> [value A .. value B]


/**
 * Continuous scales map a continuous, quantitative input domain to a continuous output range.
 *
 * If the range is also numeric, the mapping may be inverted. TODO so it's not invertable by default -> should not implement Invertable
 *
 * A continuous scale is not constructed directly; instead, try a linear, power, log,
 * identity, time or sequential color scale.
 */
public abstract class ContinuousScale<D, R>(
    public val interpolateRange: (R, R) -> Interpolator<R>,
    public val uninterpolateRange: ((R, R) -> UnInterpolator<R>)? = null,
    public val rangeComparator: Comparator<R>? = null) :
        ContinuousDomain<D>,
        ContinuousRangeScale<D, R>,
        ClampableScale,
        InvertableScale<D, R> {

    private var rangeToDomain: ((R) -> D)? = null
    private var domainToRange: ((D) -> R)? = null

    protected val _domain: MutableList<D> = mutableListOf()
    protected val _range: MutableList<R> = mutableListOf()

    override var clamp: Boolean = false
        set(value) {
            field = value
            rescale()
        }

    // copy the value (no binding intended)
    override var domain: List<D>
        get() = _domain.toList()
        set(value) {
            _domain.clear()
            _domain.addAll(value)
            rescale()
        }

    // copy the value (no binding intended)
    override var range: List<R>
        get() = _range.toList()
        set(value) {
            _range.clear()
            _range.addAll(value)
            rescale()
        }

    public abstract fun interpolateDomain(from: D, to: D): Interpolator<D>
    public abstract fun uninterpolateDomain(from: D, to: D): UnInterpolator<D>
    public abstract fun domainComparator(): Comparator<D>


    override operator fun invoke(domainValue: D): R {
        if (domainToRange == null) {
            check(_domain.size == _range.size) { "Domains (in) and Ranges (out) must have the same size." }
            val uninterpolateFunc = if (clamp) uninterpolateClamp(::uninterpolateDomain) else ::uninterpolateDomain
            domainToRange =
                    if (_domain.size > 2) polymap(uninterpolateFunc)
                    else bimap(uninterpolateFunc)
        }

        return domainToRange?.invoke(domainValue) ?: throw IllegalStateException()
    }

    // TODO : wrong : clamping is done on interpolateRange function...
    override fun invert(rangeValue: R): D {
        checkNotNull(uninterpolateRange) { "No de-interpolation function for range has been found for this scale. Invert operation is impossible." }

        if (rangeToDomain == null) {
            check(_domain.size == _range.size) { "Domains (in) and Ranges (out) must have the same size." }
            val interpolateFunc = if (clamp) interpolateClamp(::interpolateDomain) else ::interpolateDomain
            rangeToDomain =
                    if (_domain.size > 2 || _range.size > 2) polymapInvert(interpolateFunc, uninterpolateRange!!)
                    else bimapInvert(interpolateFunc, uninterpolateRange!!)
        }

        return rangeToDomain?.invoke(rangeValue) ?: throw IllegalStateException()
    }

    protected open fun rescale() {
        rangeToDomain = null
        domainToRange = null
    }

    private fun uninterpolateClamp(uninterpolateFunction: (D, D) -> UnInterpolator<D>): (D, D) -> UnInterpolator<D> {
        return fun(a: D, b: D): UnInterpolator<D> {
            val d = uninterpolateFunction(a, b)
            return fun(value: D) = d(value).coerceToDefault()
        }
    }

    private fun interpolateClamp(interpolateFunction: (D, D) -> Interpolator<D>): (D, D) -> Interpolator<D> {
        return fun(a: D, b: D): Interpolator<D> {
            val r = interpolateFunction(a, b)
            return fun(value: Percent) = r(value.coerceToDefault())
        }
    }

    private fun bimap(deinterpolateDomain: (D, D) -> UnInterpolator<D>): (D) -> R {

        val d0 = _domain[0]
        val d1 = _domain[1]
        val r0 = _range[0]
        val r1 = _range[1]

        val r: Interpolator<R>
        val d: UnInterpolator<D>

        if (domainComparator().compare(d1, d0) < 0) {
            d = deinterpolateDomain(d1, d0)
            r = interpolateRange(r1, r0)
        } else {
            d = deinterpolateDomain(d0, d1)
            r = interpolateRange(r0, r1)
        }

        return { x: D -> r(d(x)) }
    }

    private fun bimapInvert(reinterpolateDomain: (D, D) -> Interpolator<D>,
                            deinterpolateRange: (R, R) -> UnInterpolator<R>): (R) -> D {

        checkNotNull(rangeComparator) { "No RangeComparator has been found for this scale. Invert operation is impossible." }

        val d0 = _domain[0]
        val d1 = _domain[1]
        val r0 = _range[0]
        val r1 = _range[1]

        val r: UnInterpolator<R>
        val d: Interpolator<D>

        if (rangeComparator.compare(r1, r0) < 0) {
            d = reinterpolateDomain(d1, d0)
            r = deinterpolateRange(r1, r0)
        } else {
            d = reinterpolateDomain(d0, d1)
            r = deinterpolateRange(r0, r1)
        }

        return { x: R -> d(r(x)) }
    }

    private fun polymap(uninterpolateDomain: (D, D) -> UnInterpolator<D>): (D) -> R {

        val d0 = _domain.first()
        val d1 = _domain.last()
        val domainReversed = domainComparator().compare(d1, d0) < 0
        val domainValues = if (domainReversed) _domain.reversed() else _domain
        val rangeValues = if (domainReversed) _range.reversed() else _range

        val size = min(_domain.size, _range.size) - 1
        val domainInterpolators = Array(size) { uninterpolateDomain(domainValues[it], domainValues[it + 1]) }
        val rangeInterpolators = Array(size) { interpolateRange(rangeValues[it], rangeValues[it + 1]) }

        return { x ->
            val index = bisectRight(_domain, x, domainComparator(), 1, size) - 1
            rangeInterpolators[index](domainInterpolators[index](x))
        }
    }

    private fun polymapInvert(interpolateDomain: (D, D) -> Interpolator<D>,
                              uninterpolateRange: (R, R) -> UnInterpolator<R>): (R) -> D {

        // TODO <R> instanceOf Comparable ??
        checkNotNull(rangeComparator) { "No RangeComparator has been found for this scale. Invert operation is impossible." }

        val r0 = _range.first()
        val r1 = _range.last()
        val rangeReversed = rangeComparator.compare(r1, r0) < 0
        val domainValues = if (rangeReversed) _domain.reversed() else _domain
        val rangeValues = if (rangeReversed) _range.reversed() else _range

        val size = min(_domain.size, _range.size) - 1
        val domainInterpolators = Array(size) { interpolateDomain(domainValues[it], domainValues[it + 1]) }
        val rangeInterpolators = Array(size) { uninterpolateRange(rangeValues[it], rangeValues[it + 1]) }

        return { y ->
            val index = bisectRight(rangeValues, y, rangeComparator, 1, size) - 1
            domainInterpolators[index](rangeInterpolators[index](y))
        }
    }
}


// TODO move to array module
/**
 * Returns the insertion point for x in array to maintain sorted order.
 * The arguments lo and hi may be used to specify a subset of the array which should be considered;
 * by default the entire array is used. If x is already present in array, the insertion point will be
 * after (to the right of) any existing entries of x in array.
 * The returned insertion point i partitions the array into two halves so that all v <= x for v in array.slice(lo, i)
 * for the left side and all v > x for v in array.slice(i, hi) for the right side.
 */
public fun <T> bisectRight(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
    var lo = low
    var hi = high
    while (lo < hi) {
        val mid = (lo + hi) / 2
        if (comparator.compare(list[mid], x) > 0)
            hi = mid
        else
            lo = mid + 1
    }
    return lo
}

public fun <T> bisectLeft(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
    var lo = low
    var hi = high
    while (lo < hi) {
        val mid = (lo + hi) / 2
        if (comparator.compare(list[mid], x) < 0)
            lo = mid + 1
        else
            hi = mid
    }
    return lo
}

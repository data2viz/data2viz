package io.data2viz.scale

import io.data2viz.core.tickStep
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min


// uninterpolate  [value A .. value B] --> [0 .. 1]
// interpolate [0 .. 1] --> [value A .. value B]

// TODO RGB continuous scale, HCL ...
/**
 * Continuous scales map a continuous, quantitative input domain to a continuous output range.
 * 
 * If the range is also numeric, the mapping may be inverted. TODO so it's not invertable by default -> should not implement Invertable
 * 
 * A continuous scale is not constructed directly; instead, try a linear, power, log,
 * identity, time or sequential color scale.
 */
open class ContinuousScale<R>(
        val interpolateRange: (R, R) -> (Double) -> R,
        val uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
        val rangeComparator: Comparator<R>? = null) :
        ContinuousDomain<Double>,
        ContinuousRange<R>,
        ClampableScale,
        InvertableScale<Double, R>,
        NiceableScale<Double>,
        Tickable<Double> {

    private var rangeToDouble: ((R) -> Double)? = null
    private var doubleToRange: ((Double) -> R)? = null
    

    protected val _domain: MutableList<Double> = arrayListOf(.0, 1.0)
    protected val _range: MutableList<R> = arrayListOf()

    override var clamp: Boolean = false
        set(value) {
            field = value
            rescale()
        }

    // copy the value (no binding intended)
    override var domain: List<Double>
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

    /**
     * Extends the domain so that it starts and ends on nice round values.
     * This method typically modifies the scale’s domain, and may only extend the bounds to the nearest round value.
     * An optional tick count argument allows greater control over the step size used to extend the bounds,
     * guaranteeing that the returned ticks will exactly cover the domain. Nicing is useful if the domain is computed
     * from data, say using extent, and may be irregular. For example, for a domain of [0.201479…, 0.996679…],
     * a nice domain might be [0.2, 1.0]. If the domain has more than two values, nicing the domain only affects
     * the first and last value. See also d3-array’s tickStep.
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



    open fun interpolateDomain(from: Double, to: Double): (Double) -> Double = interpolateNumber(from, to)
    open fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double = uninterpolateNumber(from, to)


    override operator fun invoke(domainValue: Double): R {
        if (doubleToRange == null) {
            check(_domain.size == _range.size, { "Domains (in) and Ranges (out) must have the same size." })
            val uninterpolateFunc = if (clamp) uninterpolateClamp(::uninterpolateDomain) else ::uninterpolateDomain
            doubleToRange =
                    if (_domain.size > 2) polymap(uninterpolateFunc)
                    else bimap(uninterpolateFunc)
        }

        return doubleToRange?.invoke(domainValue) ?: throw IllegalStateException()
    }

    // TODO : wrong : clamping is done on interpolateRange function...
    override fun invert(rangeValue: R): Double {
        checkNotNull(uninterpolateRange, { "No de-interpolation function for range has been found for this scale. Invert operation is impossible." })

        if (rangeToDouble == null) {
            check(_domain.size == _range.size, { "Domains (in) and Ranges (out) must have the same size." })
            val interpolateFunc = if (clamp) interpolateClamp(::interpolateDomain) else ::interpolateDomain
            rangeToDouble =
                    if (_domain.size > 2 || _range.size > 2) polymapInvert(interpolateFunc, uninterpolateRange!!)
                    else bimapInvert(interpolateFunc, uninterpolateRange!!)
        }

        return rangeToDouble?.invoke(rangeValue) ?: throw IllegalStateException()
    }

    override fun ticks(count: Int): List<Double> {
        return io.data2viz.core.ticks(_domain.first(), _domain.last(), count)
    }

    protected fun rescale() {
        rangeToDouble = null
        doubleToRange = null
    }

    private fun uninterpolateClamp(uninterpolateFunction: (Double, Double) -> (Double) -> Double): (Double, Double) -> (Double) -> Double {
        return fun(a: Double, b: Double): (Double) -> Double {
            val d = uninterpolateFunction(a, b)
            return fun(value: Double): Double {
                return when {
                    (value <= a) -> .0
                    (value >= b) -> 1.0
                    else -> d(value)
                }
            }
        }
    }

    private fun interpolateClamp(interpolateFunction: (Double, Double) -> (Double) -> Double): (Double, Double) -> (Double) -> Double {
        return fun(a: Double, b: Double): (Double) -> Double {
            val r = interpolateFunction(a, b)
            return fun(value: Double): Double = when {
                (value <= 0.0) -> a
                (value >= 1.0) -> b
                else -> r(value)
            }
        }
    }

    private fun bimap(deinterpolateDomain: (Double, Double) -> (Double) -> Double): (Double) -> R {

        val d0 = _domain[0]
        val d1 = _domain[1]
        val r0 = _range[0]
        val r1 = _range[1]

        val r: (Double) -> R
        val d: (Double) -> Double

        if (d1 < d0) {
            d = deinterpolateDomain(d1, d0)
            r = interpolateRange(r1, r0)
        } else {
            d = deinterpolateDomain(d0, d1)
            r = interpolateRange(r0, r1)
        }

        return { x: Double -> r(d(x)) }
    }

    private fun bimapInvert(reinterpolateDomain: (Double, Double) -> (Double) -> Double,
                            deinterpolateRange: (R, R) -> (R) -> Double): (R) -> Double {

        checkNotNull(rangeComparator, { "No RangeComparator has been found for this scale. Invert operation is impossible." })

        val d0 = _domain[0]
        val d1 = _domain[1]
        val r0 = _range[0]
        val r1 = _range[1]

        val r: (R) -> Double
        val d: (Double) -> Double

        if (rangeComparator!!.compare(r1, r0) < 0) {
            d = reinterpolateDomain(d1, d0)
            r = deinterpolateRange(r1, r0)
        } else {
            d = reinterpolateDomain(d0, d1)
            r = deinterpolateRange(r0, r1)
        }

        return { x: R -> d(r(x)) }
    }

    private fun polymap(uninterpolateDomain: (Double, Double) -> (Double) -> Double): (Double) -> R {

        val d0 = _domain.first()
        val d1 = _domain.last()
        val domainReversed = d1 < d0
        val domainValues = if (domainReversed) _domain.reversed() else _domain
        val rangeValues = if (domainReversed) _range.reversed() else _range

        val size = min(_domain.size, _range.size) - 1
        val domainInterpolators = Array(size, { uninterpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { interpolateRange(rangeValues[it], rangeValues[it + 1]) })

        return { x ->
            val index = bisect<Double>(_domain, x, naturalOrder<Double>(), 1, size) - 1
            rangeInterpolators[index](domainInterpolators[index](x))
        }
    }

    private fun polymapInvert(interpolateDomain: (Double, Double) -> (Double) -> Double,
                              uninterpolateRange: (R, R) -> (R) -> Double): (R) -> Double {

        // TODO <R> instanceOf Comparable ??
        checkNotNull(rangeComparator, { "No RangeComparator has been found for this scale. Invert operation is impossible." })

        val r0 = _range.first()
        val r1 = _range.last()
        val rangeReversed = rangeComparator!!.compare(r1, r0) < 0
        val domainValues = if (rangeReversed) _domain.reversed() else _domain
        val rangeValues = if (rangeReversed) _range.reversed() else _range

        val size = min(_domain.size, _range.size) - 1
        val domainInterpolators = Array(size, { interpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { uninterpolateRange(rangeValues[it], rangeValues[it + 1]) })

        return { y ->
            val index = bisect<R>(rangeValues, y, rangeComparator, 1, size) - 1
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
fun <T> bisect(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
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

fun <T> bisectLeft(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
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

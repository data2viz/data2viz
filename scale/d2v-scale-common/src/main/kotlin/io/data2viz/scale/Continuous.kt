package io.data2viz.scale

import kotlin.math.min

// TODO move to array module
private fun <T> bisect(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
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

val doubleComparator = naturalOrder<Double>()

// uninterpolate  [value A .. value B] --> [0 .. 1]
// interpolate [0 .. 1] --> [value A .. value B]

// TODO RGB continuous scale, HCL ...
/**
 * Continuous scales map a continuous, quantitative input domain to a continuous output range.
 * If the range is also numeric, the mapping may be inverted.
 * A continuous scale is not constructed directly; instead, try a linear, power, log,
 * identity, time or sequential color scale.
 */
abstract class ContinuousScaleImpl<R>(
        val interpolateRange: (R, R) -> (Double) -> R,
        val uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
        val rangeComparator: Comparator<R>? = null) :
        ContinuousScale<Double, R>,
        ClampableScale<Double, R>,
        InvertableScale<Double, R>,
        TickableScale<Double, R> {

    var input: ((R) -> Double)? = null
    var output: ((Double) -> R)? = null

    abstract fun interpolateDomain(from: Double, to: Double): (Double) -> Double
    abstract fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double

    // TODO : keep or not ?
    fun domain(vararg d: Double) {
        domain = d.toMutableList()
    }

    // TODO : keep or not ?
    fun range(vararg r: R) {
        range = r.toMutableList()
    }

    override var clamp: Boolean = false
        set(value) {
            field = value
            rescale()
        }

    // copy the value (no binding intended)
    override var domain: MutableList<Double> = arrayListOf(.0, 1.0)
        get() = field.toMutableList()
        set(value) {
            field = value.toMutableList()
            rescale()
        }

    // copy the value (no binding intended)
    override var range: MutableList<R> = arrayListOf()
        get() = field.toMutableList()
        set(value) {
            field = value.toMutableList()
            rescale()
        }

    override operator fun invoke(domainValue: Double): R {
        if (output == null) {
            check(domain.size == range.size, { "Domains (in) and Ranges (out) must have the same size." })
            val uninterpolateFunc = if (clamp) uninterpolateClamp(::uninterpolateDomain) else ::uninterpolateDomain
            output =
                    if (domain.size > 2 || range.size > 2) polymap(uninterpolateFunc, interpolateRange)
                    else bimap(uninterpolateFunc, interpolateRange)
        }

        return output?.invoke(domainValue) ?: throw IllegalStateException()
    }

    // TODO : wrong : clamping is done on interpolateRange function...
    override fun invert(rangeValue: R): Double {
        checkNotNull(uninterpolateRange, { "No de-interpolation function for range has been found for this scale. Invert operation is impossible." })

        if (input == null) {
            check(domain.size == range.size, { "Domains (in) and Ranges (out) must have the same size." })
            val interpolateFunc = if (clamp) interpolateClamp(::interpolateDomain) else ::interpolateDomain
            input =
                    if (domain.size > 2 || range.size > 2) polymapInvert(interpolateFunc, uninterpolateRange!!)
                    else bimapInvert(interpolateFunc, uninterpolateRange!!)
        }

        return input?.invoke(rangeValue) ?: throw IllegalStateException()
    }

    override fun ticks(count: Int): List<Double> {
        return io.data2viz.core.ticks(domain.first(), domain.last(), count) as List<Double>
    }

    protected open fun rescale() {
        input = null
        output = null
    }

    private fun uninterpolateClamp(uninterpolateFunction: (Double, Double) -> (Double) -> Double): (Double, Double) -> (Double) -> Double {
        return fun(a: Double, b: Double): (Double) -> Double {
            val d = uninterpolateFunction(a, b)
            return fun(domain: Double): Double {
                return when {
                    (domain <= a) -> .0
                    (domain >= b) -> 1.0
                    else -> d(domain)
                }
            }
        }
    }

    private fun interpolateClamp(interpolateFunction: (Double, Double) -> (Double) -> Double): (Double, Double) -> (Double) -> Double {
        return fun(a: Double, b: Double): (Double) -> Double {
            val r = interpolateFunction(a, b)
            return fun(t: Double): Double = when {
                t <= 0.0 -> a
                t >= 1.0 -> b
                else -> r(t)
            }
        }
    }

    private fun bimap(deinterpolateDomain: (Double, Double) -> (Double) -> Double,
                      reinterpolateRange: (R, R) -> (Double) -> R): (Double) -> R {

        val d0 = domain[0]
        val d1 = domain[1]
        val r0 = range[0]
        val r1 = range[1]

        val r: (Double) -> R
        val d: (Double) -> Double

        if (d1 < d0) {
            d = deinterpolateDomain(d1, d0)
            r = reinterpolateRange(r1, r0)
        } else {
            d = deinterpolateDomain(d0, d1)
            r = reinterpolateRange(r0, r1)
        }

        return { x: Double -> r(d(x)) }
    }

    private fun bimapInvert(reinterpolateDomain: (Double, Double) -> (Double) -> Double,
                            deinterpolateRange: (R, R) -> (R) -> Double): (R) -> Double {

        checkNotNull(rangeComparator, { "No RangeComparator has been found for this scale. Invert operation is impossible." })

        val d0 = domain[0]
        val d1 = domain[1]
        val r0 = range[0]
        val r1 = range[1]

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

    private fun polymap(uninterpolateDomain: (Double, Double) -> (Double) -> Double,
                        interpolateRange: (R, R) -> (Double) -> R): (Double) -> R {

        val d0 = domain.first()
        val d1 = domain.last()
        val domainReversed = d1 < d0
        val domainValues = if (domainReversed) domain.reversed() else domain
        val rangeValues = if (domainReversed) range.reversed() else range

        val size = min(domain.size, range.size) - 1
        val domainInterpolators = Array(size, { uninterpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { interpolateRange(rangeValues[it], rangeValues[it + 1]) })

        return { x ->
            val index = bisect<Double>(domain, x, naturalOrder<Double>(), 1, size) - 1
            rangeInterpolators[index](domainInterpolators[index](x))
        }
    }

    private fun polymapInvert(interpolateDomain: (Double, Double) -> (Double) -> Double,
                              uninterpolateRange: (R, R) -> (R) -> Double): (R) -> Double {

        // TODO <R> instanceOf Comparable ??
        checkNotNull(rangeComparator, { "No RangeComparator has been found for this scale. Invert operation is impossible." })

        val r0 = range.first()
        val r1 = range.last()
        val rangeReversed = rangeComparator!!.compare(r1, r0) < 0
        val domainValues = if (rangeReversed) domain.reversed() else domain
        val rangeValues = if (rangeReversed) range.reversed() else range

        val size = min(domain.size, range.size) - 1
        val domainInterpolators = Array(size, { interpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { uninterpolateRange(rangeValues[it], rangeValues[it + 1]) })

        return { y ->
            val index = bisect<R>(rangeValues, y, rangeComparator, 1, size) - 1
            domainInterpolators[index](rangeInterpolators[index](y))
        }
    }
}
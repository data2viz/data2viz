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


abstract class ContinuousScale<R>(
        val interpolateDomain: (Double, Double) -> ((Double) -> Double),
        val uninterpolateDomain: (Double, Double) -> ((Double) -> Double),
        val interpolateRange: (R, R) -> (Double) -> R,
        val uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
        val rangeComparator: Comparator<R>? = null) : Scale<Double, R> {

    var input: ((R) -> Double)? = null
    var output: ((Double) -> R)? = null

    var clamp: Boolean = false
        set(value) {
            field = value
            rescale()
        }

    override var domain: MutableList<Double> = arrayListOf()
        set(value) {
            field = value
            rescale()
        }

    override var range: MutableList<R> = arrayListOf()
        set(value) {
            field = value
            rescale()
        }

    override operator fun invoke(domainValue: Double): R {
        if (output == null) {
            val uninterpolateFunc = if (clamp) uninterpolateClamp(uninterpolateDomain) else uninterpolateDomain
            output =
                    if (domain.size > 2 || range.size > 2) polymap(uninterpolateFunc, interpolateRange)
                    else bimap(uninterpolateFunc, interpolateRange)
        }

        return output?.invoke(domainValue) ?: throw IllegalStateException()
    }

    fun invert(rangeValue: R): Double {
        checkNotNull(uninterpolateRange, { "No de-interpolation function for range has been found for this scale. Invert operation is impossible" })

        if (input == null) {
            val interpolateFunc = if (clamp) uninterpolateClamp(interpolateDomain) else interpolateDomain
            input =
                    if (domain.size > 2 || range.size > 2) polymapInvert(interpolateFunc, uninterpolateRange!!)
                    else bimapInvert(interpolateFunc, uninterpolateRange!!)
        }

        return input?.invoke(rangeValue) ?: throw IllegalStateException()
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

    private fun rescale() {
        input = null
        output = null
    }

    private fun bimap(deinterpolateDomain: (Double, Double) -> (Double) -> Double,
                      reinterpolateDomain: (R, R) -> (Double) -> R): (Double) -> R {

        val d0 = domain[0]
        val d1 = domain[1]
        val r0 = range[0]
        val r1 = range[1]

        val r: (Double) -> R
        val d: (Double) -> Double

        if (d1 < d0) {
            d = deinterpolateDomain(d1, d0)
            r = reinterpolateDomain(r1, r0)
        } else {
            d = deinterpolateDomain(d0, d1)
            r = reinterpolateDomain(r0, r1)
        }

        return { x: Double -> r(d(x)) }
    }

    private fun bimapInvert(deinterpolate: (Double, Double) -> (Double) -> Double,
                            reinterpolate: (R, R) -> (R) -> Double): (R) -> Double {

        val d0 = domain.first()
        val d1 = domain.last()
        val r0 = range[0]
        val r1 = range[1]

        val r: (R) -> Double
        val d: (Double) -> Double

        if (d1 < d0) {
            d = deinterpolate(d1, d0)
            r = reinterpolate(r1, r0)
        } else {
            d = deinterpolate(d0, d1)
            r = reinterpolate(r0, r1)
        }

        return { x: R -> d(r(x)) }
    }

    private fun polymap(deinterpolateDomain: (Double, Double) -> (Double) -> Double,
                        reinterpolateDomain: (R, R) -> (Double) -> R): (Double) -> R {

        val d0 = domain.first()
        val d1 = domain.last()
        val domainValues = if (d1 < d0) domain.reversed() else domain
        val rangeValues = if (d1 < d0) range.reversed() else range

        val size = min(domain.size, range.size) - 1
        val domainInterpolators = Array(size, { deinterpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { reinterpolateDomain(rangeValues[it], rangeValues[it + 1]) })

        return { x ->
            val idx = bisect<Double>(domain, x, naturalOrder<Double>(), 1, size) - 1
            rangeInterpolators[idx](domainInterpolators[idx](x))
        }
    }

    private fun polymapInvert(deinterpolateDomain: (Double, Double) -> (Double) -> Double,
                              reinterpolateDomain: (R, R) -> (R) -> Double): (R) -> Double {

        // TODO <R> instanceOf Comparable ??
        checkNotNull(rangeComparator, { "No RangeComparator has been found for this scale. Invert operation is impossible" })

        val d0 = domain.first()
        val d1 = domain.last()
        val domainValues = if (d1 < d0) domain.reversed() else domain
        val rangeValues = if (d1 < d0) range.reversed() else range

        val size = min(domain.size, range.size) - 1
        val domainInterpolators = Array(size, { deinterpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { reinterpolateDomain(rangeValues[it], rangeValues[it + 1]) })

        return { y ->
            val idx = bisect<R>(rangeValues, y, rangeComparator!!, 1, size) - 1
            domainInterpolators[idx](rangeInterpolators[idx](y))
        }
    }

    override fun ticks(count: Int): List<Double> {
        return io.data2viz.core.ticks(domain.first(), domain.last(), count)
    }
}
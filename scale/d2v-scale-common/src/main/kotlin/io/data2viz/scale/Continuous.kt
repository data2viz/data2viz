package io.data2viz.scale

import kotlin.math.min

// TODO move to array module
private fun <T> bisectRight(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
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

private fun <T> bisect(list: List<T>, x: T, comparator: Comparator<T>, low: Int = 0, high: Int = list.size): Int {
    return if (comparator.compare(list.first(), list.last()) < 0) bisectRight(list, x, comparator, low, high)
    else bisectLeft(list, x, comparator, low, high)
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

    override var domain: MutableList<Double> = arrayListOf(.0, 1.0)
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
            val interpolateFunc = if (clamp) interpolateClamp(interpolateDomain) else interpolateDomain
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

        val d0 = domain.first()
        val d1 = domain.last()
        val r0 = range[0]
        val r1 = range[1]

        val r: (R) -> Double
        val d: (Double) -> Double

        if (d1 < d0) {
            d = reinterpolateDomain(d1, d0)
            r = deinterpolateRange(r1, r0)
        } else {
            d = reinterpolateDomain(d0, d1)
            r = deinterpolateRange(r0, r1)
        }

        return { x: R -> d(r(x)) }
    }

    private fun polymap(deinterpolateDomain: (Double, Double) -> (Double) -> Double,
                        reinterpolateRange: (R, R) -> (Double) -> R): (Double) -> R {

        val d0 = domain.first()
        val d1 = domain.last()
        val domainReversed = d1 < d0
        val domainValues = if (domainReversed) domain.reversed() else domain
        val rangeValues = if (domainReversed) range.reversed() else range

        val size = min(domain.size, range.size) - 1
        val domainInterpolators = Array(size, { deinterpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { reinterpolateRange(rangeValues[it], rangeValues[it + 1]) })

        return { x ->
            val index = bisect<Double>(domain, x, naturalOrder<Double>(), 1, size) - 1
            rangeInterpolators[index](domainInterpolators[index](x))
        }
    }

    private fun polymapInvert(reinterpolateDomain: (Double, Double) -> (Double) -> Double,
                              deinterpolateRange: (R, R) -> (R) -> Double): (R) -> Double {

        // TODO <R> instanceOf Comparable ??
        checkNotNull(rangeComparator, { "No RangeComparator has been found for this scale. Invert operation is impossible" })

        val d0 = domain.first()
        val d1 = domain.last()
        val domainReversed = d1 < d0
        val domainValues = if (domainReversed) domain.reversed() else domain
        val rangeValues = if (domainReversed) range.reversed() else range

        val size = min(domain.size, range.size) - 1
        val domainInterpolators = Array(size, { reinterpolateDomain(domainValues[it], domainValues[it + 1]) })
        val rangeInterpolators = Array(size, { deinterpolateRange(rangeValues[it], rangeValues[it + 1]) })

        return { y ->
            val index = bisect<R>(rangeValues, y, rangeComparator!!, 1, size) - 1
            println(bisect<R>(rangeValues, y, rangeComparator, 1, size))
            println(index)
            println(rangeValues)
            domainInterpolators[index](rangeInterpolators[index](y))
        }
    }

    override fun ticks(count: Int): List<Double> {
        return io.data2viz.core.ticks(domain.first(), domain.last(), count) as List<Double>
    }
}
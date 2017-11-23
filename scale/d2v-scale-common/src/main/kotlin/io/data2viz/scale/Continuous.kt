package io.data2viz.scale

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


abstract class ContinuousScale<D, R>(
        val uninterpolateDomain: (D, D) -> (D) -> Double,
        val interpolateRange: (R, R) -> (Double) -> R,
        val interpolateDomain: ((D, D) -> (Double) -> D)? = null,
        val uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
        val domainComparator: Comparator<D>? = null,
        val rangeComparator: Comparator<R>? = null) : Scale<D, R> {

    override val domainsToRanges: MutableList<DomainToRange<D, R>> = ArrayList(2)

    var clamp: Boolean = false
        set(value) {
            field = value
            input = null
            output = null
        }

    var piecewiseOutput: ((List<DomainToRange<D, R>>,
                           (D, D) -> (D) -> Double,
                           (R, R) -> (Double) -> R) -> (D) -> R)? = null
    var output: ((D) -> R)? = null

    var piecewiseInput: ((List<DomainToRange<D, R>>,
                          (D, D) -> (Double) -> D,
                          (R, R) -> (R) -> Double) -> (R) -> D)? = null
    var input: ((R) -> D)? = null

    // TODO manage with Comparator / Comparable ?
    protected fun uninterpolateClamp(uninterpolateFunction: (D, D) -> (D) -> Double): (D, D) -> (D) -> Double {
        return fun(a: D, b: D): (D) -> Double {
            val d = uninterpolateFunction(a, b)
            return fun(domain: D): Double {
                return when {
                    d(domain) <= d(a) -> 0.0
                    d(domain) >= d(b) -> 1.0
                    else -> d(domain)
                }
            }
        }
    }

    // TODO manage with Comparator / Comparable ?
    protected fun interpolateClamp(interpolateFunction: (D, D) -> (Double) -> D): (D, D) -> (Double) -> D {
        return fun(a: D, b: D): (Double) -> D {
            val r = interpolateFunction(a, b)
            return fun(t: Double): D = when {
                t <= 0.0 -> a
                t >= 1.0 -> b
                else -> r(t)
            }
        }
    }

    open fun domainsToRanges(d: List<DomainToRange<D, R>>): ContinuousScale<D, R> {
        domainsToRanges.clear()
        domainsToRanges.addAll(d)
        rescale()
        return this
    }

    override operator fun invoke(domain: D): R {
        if (output == null) {
            output = piecewiseOutput?.invoke(
                    domainsToRanges,
                    if (clamp) uninterpolateClamp(uninterpolateDomain) else uninterpolateDomain,
                    interpolateRange
            )
        }

        return output?.invoke(domain) ?: throw IllegalStateException()
    }

    fun invert(range: R): D {
        if (uninterpolateRange == null || interpolateDomain == null)
            throw IllegalStateException()

        if (input == null) {
            input = piecewiseInput?.invoke(
                    domainsToRanges,
                    if (clamp) interpolateClamp(interpolateDomain) else interpolateDomain,
                    uninterpolateRange)
        }

        return input?.invoke(range) ?: throw IllegalStateException()
    }

    protected open fun rescale() {
        piecewiseOutput = if (domainsToRanges.size > 2) this::polymap else this::bimap
        piecewiseInput = if (domainsToRanges.size > 2) this::polymapInvert else this::bimapInvert
        input = null
        output = null
    }

    private fun bimap(domainstoRanges: List<DomainToRange<D, R>>,
                      deinterpolate: (D, D) -> (D) -> Double,
                      reinterpolate: (R, R) -> (Double) -> R): (D) -> R {

        val d0 = domainstoRanges[0].domain
        val d1 = domainstoRanges[1].domain
        val r0 = domainstoRanges[0].range
        val r1 = domainstoRanges[1].range

        val r: (Double) -> R
        val d: (D) -> Double

        val dom = uninterpolateDomain(d0, d1)
        if (dom(d1) < dom(d0)) {
            d = deinterpolate(d1, d0)
            r = reinterpolate(r1, r0)
        } else {
            d = deinterpolate(d0, d1)
            r = reinterpolate(r0, r1)
        }

        return { x: D -> r(d(x)) }
    }

    private fun bimapInvert(domainstoRanges: List<DomainToRange<D, R>>,
                            deinterpolate: (D, D) -> (Double) -> D,
                            reinterpolate: (R, R) -> (R) -> Double): (R) -> D {

        val d0 = domainstoRanges[0].domain
        val d1 = domainstoRanges[1].domain
        val r0 = domainstoRanges[0].range
        val r1 = domainstoRanges[1].range

        val r: (R) -> Double
        val d: (Double) -> D

        val dom = uninterpolateDomain(d0, d1)
        if (dom(d1) < dom(d0)) {
            d = deinterpolate(d1, d0)
            r = reinterpolate(r1, r0)
        } else {
            d = deinterpolate(d0, d1)
            r = reinterpolate(r0, r1)
        }

        return { x: R -> d(r(x)) }
    }

    private fun polymap(domainstoRanges: List<DomainToRange<D, R>>,
                        deinterpolateDomain: (D, D) -> (D) -> Double,
                        reinterpolateDomain: (R, R) -> (Double) -> R): (D) -> R {

        if (domainComparator == null) throw IllegalStateException()

        val d0 = domainstoRanges.first().domain
        val d1 = domainstoRanges.last().domain
        val dom = uninterpolateDomain(d0, d1)
        val values = if (dom(d1) < dom(d0)) domainstoRanges.reversed() else domainstoRanges
        val domains = values.map { it.domain }

        val size = domainstoRanges.size - 1
        val domainInterpolators = Array(size, { deinterpolateDomain(values[it].domain, values[it + 1].domain) })
        val rangeInterpolators = Array(size, { reinterpolateDomain(values[it].range, values[it + 1].range) })

        return { x ->
            val idx = bisect<D>(domains, x, domainComparator, 1, size) - 1
            rangeInterpolators[idx](domainInterpolators[idx](x))
        }
    }

    private fun polymapInvert(domainstoRanges: List<DomainToRange<D, R>>,
                              deinterpolateDomain: (D, D) -> (Double) -> D,
                              reinterpolateDomain: (R, R) -> (R) -> Double): (R) -> D {

        if (rangeComparator == null) throw IllegalStateException()

        val d0 = domainstoRanges.first().domain
        val d1 = domainstoRanges.last().domain
        val dom = uninterpolateDomain(d0, d1)
        val values = if (dom(d1) < dom(d0)) domainstoRanges.reversed() else domainstoRanges
        val ranges = values.map { it.range }

        val size = domainstoRanges.size - 1
        val domainInterpolators = Array(size, { deinterpolateDomain(values[it].domain, values[it + 1].domain) })
        val rangeInterpolators = Array(size, { reinterpolateDomain(values[it].range, values[it + 1].range) })

        return { y ->
            val idx = bisect(ranges, y, rangeComparator, 1, size) - 1
            domainInterpolators[idx](rangeInterpolators[idx](y))
        }
    }
}
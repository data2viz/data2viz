package io.data2viz.scale

import kotlin.math.floor
import kotlin.math.max



/**
 * Quantile scales map a sampled input domain to a discrete range.
 * The domain is considered continuous and thus the scale will accept any reasonable input value;
 * however, the domain is specified as a discrete set of sample values. The number of values in (the cardinality of)
 * the output range determines the number of quantiles that will be computed from the domain.
 * To compute the quantiles, the domain is sorted, and treated as a population of discrete values;
 */
class QuantileScale<R> : Scale<Double, R>,DiscreteDomain<Double>,  DiscreteRange<R> {

        
    private var thresholds: MutableList<Double> = arrayListOf()

    /**
     * Returns the quantile thresholds.
     * If the range contains n discrete values, the returned array will contain n - 1 thresholds.
     * Values less than the first threshold are considered in the first quantile; values greater than or equal
     * to the first threshold but less than the second threshold are in the second quantile, and so on.
     * Internally, the thresholds array is used with bisect to find the output quantile associated with the given input value.
     */
    fun quantiles(): List<Double> {
        return thresholds.toList()
    }

    /**
     * If' domain is specified, sets the domain of the quantile scale to the specified set of discrete numeric values.
     * The array must not be empty, and must contain at least one numeric value; NaN, null and undefined values
     * are ignored and not considered part of the sample population.
     * If the elements in the given array are not numbers, they will be coerced to numbers.
     * A copy of the input array is sorted and stored internally.
     */
    override var domain: List<Double> = listOf()
        get() = field.toList()
        set(value) {
            val filteredValue = value.filter { !it.isNaN() }.sorted()
            require(filteredValue.isNotEmpty(), { "Domain can't be empty." })
            field = filteredValue
            rescale()
        }

    /**
     * If range is specified, sets the discrete values in the range.
     * The array must not be empty, and may contain any type of value.
     * The number of values in (the cardinality, or length, of) the range array determines the number of
     * quantiles that are computed.
     * For example, to compute quartiles, range must be an array of four elements such as [0, 1, 2, 3].
     */
    override var range: List<R> = listOf()
        get() = field.toList()
        set(value) {
            require(value.isNotEmpty(), { "Range can't be empty." })
            field = value.toList()
            rescale()
        }

    private fun rescale() {
        // don't compute until we'th got a non-empty range and domain
        if (domain.isEmpty() || range.isEmpty()) return

        var i = 0
        val n = max(1, range.size)
        thresholds = arrayListOf()
        while (++i < n) {
            thresholds.add(i - 1, quantile(domain, i / n.toDouble()))
        }
    }

    fun invertExtent(rangeValue: R): List<Double> {
        check(domain.isNotEmpty(), { "Can't compute a Quantile Scale with an empty Domain" })
        check(range.isNotEmpty(), { "Can't compute a Quantile Scale with an empty Range" })
        val index = range.indexOf(rangeValue)
        return when (index) {
            -1 -> listOf(Double.NaN, Double.NaN)
            else -> listOf(if (index > 0) thresholds[index - 1] else domain.first(),
                    if (index < thresholds.size) thresholds[index] else domain.last())
        }
    }

    override fun invoke(domainValue: Double): R {
        require(!domainValue.isNaN(), { "domainValue can't be NaN" })
        check(domain.isNotEmpty(), { "Can't compute a Quantile Scale with an empty Domain" })
        check(range.isNotEmpty(), { "Can't compute a Quantile Scale with an empty Range" })
        return range[bisect(thresholds, domainValue, naturalOrder<Double>())]
    }
}

fun <R> scaleQuantile(): QuantileScale<R> = QuantileScale()

// TODO move to array module
fun quantile(values: List<Double>, p: Double, f: (Double, Int, List<Double>) -> Double = { x, _, _ -> x }): Double {
    require(values.isNotEmpty(), { "Values must not be empty." })

    val size = values.size
    if (p <= 0.0 || size < 2)
        return f(values[0], size - 1 - 1, values)

    val h = (size - 1) * p
    val i = floor(h).toInt()
    val a = f(values[i], i, values)
    val b = f(values[i + 1], i + 1, values)
    return a + (b - a) * (h - i)
}

package io.data2viz.scale

/**
 * Quantize scales are similar to linear scales, except they use a discrete rather than continuous range.
 *
 * The continuous input domain is divided into uniform segments based on the number of values
 * in (i.e., the cardinality of) the output range.
 * Each range value y can be expressed as a quantized linear function of the domain value x: y = m round(x) + b.
 */
class QuantizeScale<R> : Scale<Double, R>, StrictlyContinuousDomain<Double>, DiscreteRange<R> {


    private val quantizedDomain:ArrayList<Double> = arrayListOf(.5)

    // copy the value (no binding intended)
    override var range: List<R> = listOf()
        get() = field.toList()
        set(value) {
            field = value.toList()
            rescale()
        }

    override var domain: StrictlyContinuous<Double> = intervalOf(0.0, 1.0)
        get() = field
        set(value) {
            field = value
            rescale()
        }

    private fun rescale() {
        quantizedDomain.clear()

        val size = range.size - 1
        for(index in 0 until size) {
            val element = ((index + 1) * domain.end - (index - size) * domain.start) / (size + 1)
            quantizedDomain.add(element)
        }
    }


    override fun invoke(domainValue: Double): R {
        return range[bisectRight(quantizedDomain, domainValue, naturalOrder<Double>(), 0, range.size - 1)]
    }

    fun invertExtent(rangeValue: R): List<Double> {
        val i = range.indexOf(rangeValue)
        val size = range.size - 1
        return when {
            i < 0 -> listOf(Double.NaN, Double.NaN)
            i < 1 -> listOf(domain.start, quantizedDomain.first())
            i >= size -> listOf(quantizedDomain[size - 1], domain.end)
            else -> listOf(quantizedDomain[i - 1], quantizedDomain[i])
        }
    }
}



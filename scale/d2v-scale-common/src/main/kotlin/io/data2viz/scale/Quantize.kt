package io.data2viz.scale

import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.uninterpolateNumber

/**
 * Quantize scales are similar to linear scales, except they use a discrete rather than continuous range.
 * The continuous input domain is divided into uniform segments based on the number of values
 * in (i.e., the cardinality of) the output range.
 * Each range value y can be expressed as a quantized linear function of the domain value x: y = m round(x) + b.
 */
open class QuantizeScale<R>() : ContinuousScale<Double, R> {

    private var domainStart = .0
    private var domainEnd = 1.0
    private var givenDomain = arrayListOf(domainStart, domainEnd)
    private var quantizedDomain:MutableList<Double> = arrayListOf(.5)

    override fun domain(vararg d: Double) {
        domain = d.toMutableList()
    }

    override fun range(vararg r: R) {
        range = r.toMutableList()
    }

    // copy the value (no binding intended)
    override var range: MutableList<R> = arrayListOf()
        get() = field.toMutableList()
        set(value) {
            field = value.toMutableList()
            rescale()
        }

    // copy the value (no binding intended)
    override var domain: MutableList<Double>
        get() = givenDomain
        set(value) {
            if (value.size != 2) throw IllegalArgumentException("Quantize Scale can only accept a domain with 2 values.")
            domainStart = domain.first()
            domainEnd = domain.last()
            givenDomain = arrayListOf(domainStart, domainEnd)
            rescale()
        }

    fun rescale() {
        quantizedDomain = arrayListOf()

        val size = range.size - 1
        for(index in 0 until size) {
            val element = ((index + 1) * domainEnd - (index - size) * domainStart) / (size + 1)
            quantizedDomain.add(element)
        }
    }

    override fun invoke(domainValue: Double): R {
        val bisect = bisect(quantizedDomain, domainValue, doubleComparator, 0, range.size - 1)
        return range[bisect]
    }

    fun invertExtent(y: R): List<Double> {
        val i = range.indexOf(y)
        val size = range.size - 1
        return when {
            i < 0 -> listOf(Double.NaN, Double.NaN)
            i < 1 -> listOf(domainStart, quantizedDomain.first())
            i >= size -> listOf(quantizedDomain[size - 1], domainEnd)
            else -> listOf(quantizedDomain[i - 1], quantizedDomain[i])
        }
    }
}

fun <R> quantizeScale(): QuantizeScale<R> = QuantizeScale<R>()

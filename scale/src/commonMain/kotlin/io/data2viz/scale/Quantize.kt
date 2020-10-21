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

/**
 * Quantize scales are similar to linear scales, except they use a discrete rather than continuous range.
 *
 * The continuous input domain is divided into uniform segments based on the number of values
 * in (i.e., the cardinality of) the output range.
 * Each range value y can be expressed as a quantized linear function of the domain value x: y = m round(x) + b.
 */
public class QuantizeScale<R> internal constructor() : Scale<Double, R>, StrictlyContinuousDomain<Double>, DiscreteRange<R> {


    private val quantizedDomain:MutableList<Double> = mutableListOf(.5)

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
        return range[bisectRight(quantizedDomain, domainValue, naturalOrder(), 0, range.size - 1)]
    }

    public fun invertExtent(rangeValue: R): List<Double> {
        val i = range.indexOf(rangeValue)
        val size = range.size - 1
        return when {
            i < 0 -> listOf(Double.NaN, Double.NaN)
            i < 1 -> listOf(domain.start, quantizedDomain.first())
            i >= size -> listOf(quantizedDomain[size - 1], domain.end)
            else -> listOf(quantizedDomain[i - 1], quantizedDomain[i])
        }
    }

    override fun copy(): QuantizeScale<R> {
        return QuantizeScale<R>().also{
            it.domain = domain
            it.range = range
            it.rescale()
        }
    }
}



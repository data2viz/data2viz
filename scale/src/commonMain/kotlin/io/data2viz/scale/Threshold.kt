/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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
 * Threshold scales are similar to quantize scales, except they allow you to map arbitrary subsets of the
 * domain to discrete values in the range.
 * The input domain is still continuous, and divided into slices based on a set of threshold values.
 */
public class ThresholdScale<R> internal constructor() :

    Scale<Double, R>,
    DiscreteRange<R>,
    DiscreteDomain<Double> {

    public var _domain: List<Double> = listOf(.5)
    public var _range: List<R> = listOf()

    /**
     * Sets the scale’s range to the specified array of values.
     * If the number of values in the scale’s domain is N, the number of values in the scale’s range must be N+1.
     */
    override var range: List<R>
        get() = _range.toList()
        set(value) {
            _range = value.toList()
        }

    /**
     * Sets the scale’s domain to the specified array of values.
     * The values must be in sorted ascending order, or the behavior of the scale is undefined.
     * The values are typically numbers, but any naturally ordered values (such as strings) will work; a threshold
     * scale can be used to encode any type that is ordered.
     * If the number of values in the scale’s range is N+1, the number of values in the scale’s domain must be N.
     */
    override var domain: List<Double>
        get() = _domain.toList()
        set(value) {
            require(value.sorted() == value, { "The domain must be sorted in ascending order." })
            _domain = value.toList()
        }

    public operator fun invoke(domainValue: Int): R {
        return this(domainValue.toDouble())
    }

    /**
     * Given a value in the input domain, returns the corresponding value in the output range.
     */
    override fun invoke(domainValue: Double): R {
        check(_range.size == _domain.size + 1,
            { "The range size (actual: ${_range.size}) must be 1 more than the domain size (actual: ${_domain.size})." })
        return _range[bisectRight(_domain, domainValue, naturalOrder<Double>(), 0, _domain.size)]
    }

    /**
     * Returns the extent of values in the domain [x0, x1] for the corresponding value in the range,
     * representing the inverse mapping from range to domain.
     * This method is useful for interaction, say to determine the value in the domain that corresponds to the
     * pixel location under the mouse.
     */
    public fun invertExtent(rangeValue: R): List<Double> {
        check(_range.size == _domain.size + 1,
            { "The range size (actual: ${_range.size}) must be 1 more than the domain size (actual: ${_domain.size})." })
        val i = _range.indexOf(rangeValue)
        val size = _range.size - 1
        return when {
            i < 0 || i > size -> listOf(Double.NaN, Double.NaN)
            i == 0 -> listOf(Double.NaN, _domain.first())
            i == size -> listOf(domain.last(), Double.NaN)
            else -> listOf(_domain[i - 1], _domain[i])
        }
    }

    override fun copy(): Scale<Double, R> {
        return ThresholdScale<R>().also {
            it.domain = domain
            it.range = range
        }
    }
}



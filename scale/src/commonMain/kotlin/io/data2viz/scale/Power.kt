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

import io.data2viz.interpolate.*
import io.data2viz.math.*
import kotlin.math.pow

/**
 * Power scales are similar to linear scales, except an exponential transform is applied to the input domain
 * value before the output range value is computed.
 * Each range value y can be expressed as a function of the domain value x: y = mx^k + b, where k is the exponent value.
 * Power scales also support negative domain values, in which case the input value and the resulting output
 * value are multiplied by -1.
 */
public class PowerScale<R>
    internal constructor(exponent: Double = 1.0, interpolateRange: (R, R) -> Interpolator<R>,
                    uninterpolateRange: ((R, R) -> UnInterpolator<R>)? = null,
                    rangeComparator: Comparator<R>? = null)
    : LinearScale<R>(interpolateRange, uninterpolateRange, rangeComparator) {

    public var exponent: Double = exponent
        set(value) {
            field = value
            rescale()
        }

    override fun copy(): ContinuousScale<Double, R> =
        PowerScale(exponent, interpolateRange, uninterpolateRange, rangeComparator).also {
            it.domain = domain
            it.range = range
            it.clamp = clamp
    }

    override fun uninterpolateDomain(from: Double, to: Double): UnInterpolator<Double> {
        val dFrom = raise(from, exponent)
        val dTo = raise(to, exponent) - dFrom

        return if (dTo == .0 || dTo.isNaN()) { _ -> 0.pct }
        else { t -> Percent((raise(t, exponent) - dFrom) / dTo) }
    }

    override fun interpolateDomain(from: Double, to: Double): Interpolator<Double> {
        val ra = raise(from, exponent)
        val rb = raise(to, exponent) - ra
        return { t -> raise(ra + rb * t.value, 1.0 / exponent) }
    }

    private fun raise(x: Double, exponent: Double): Double {
        val pow = x.pow(exponent)
        return if (x < 0.0) -pow else pow
    }
}


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

import io.data2viz.interpolate.Interpolator
import io.data2viz.interpolate.uninterpolateNumber


/**
 * Sequential scales are similar to continuous scales in that they map a continuous numeric input domain to a
 * continuous output range. However, unlike continuous scales, the output range of a sequential scale is fixed
 * by its interpolator and not configurable.
 *
 * These scales do not expose invert, range, rangeRound and interpolate methods.
 */
class SequentialScale<R>
internal constructor(var interpolator: Interpolator<R>) : Tickable<Double>, ClampableScale,
    StrictlyContinuousDomain<Double> {

    override var domain: StrictlyContinuous<Double> = intervalOf(0.0, 1.0)

    override var clamp: Boolean = false

    operator fun invoke(domainValue: Int): R {
        return this(domainValue.toDouble())
    }

    operator fun invoke(domainValue: Double): R {
        var uninterpolatedDomain = uninterpolateNumber(domain.start, domain.end)(domainValue)
        if (clamp) uninterpolatedDomain = uninterpolatedDomain.coerceToDefault()
        return interpolator(uninterpolatedDomain)
    }

    override fun ticks(count: Int) = io.data2viz.math.ticks(domain.start, domain.end, count)
}


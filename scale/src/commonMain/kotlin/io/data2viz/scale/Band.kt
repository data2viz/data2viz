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

import io.data2viz.math.Percent
import io.data2viz.math.pct
import kotlin.math.floor
import kotlin.math.max


public abstract class BandedScale<D>(private val indexableDomain: IndexableDomain<D> = IndexableDomain()) :
    Scale<D, Double>,
    DiscreteDomain<D> by indexableDomain,
    Tickable<D>,
    StrictlyContinuousRange<D, Double> {

    private val unknown = Double.NaN

    protected var _paddingInner: Double = 0.0
    protected var _paddingOuter: Double = 0.0

    public abstract var padding: Percent

    override var domain: List<D>
        get() = indexableDomain._domain
        set(value) {
            indexableDomain.domain = value
            rescale()
        }

    override var range: StrictlyContinuous<Double> = intervalOf(0.0, 1.0)
        get() = field
        set(value) {
            field = value
            rescale()
        }

    public var round: Boolean = false
        set(value) {
            field = value
            rescale()
        }

    /**
     * Sets the band alignment to the specified value which must be in the range [0%, 100%].
     * [align] specifies how the outer padding in the scale’s range is distributed.
     * The default align = 50% centers the bands within the range, with equal outer padding on both sides.
     */
    public var align: Percent = 50.pct
        set(value) {
            field = value.coerceToDefault()
            rescale()
        }

    public var step: Double = 1.0
        private set

    public var bandwidth: Double = 1.0
        private set

    private var ordinalRange: MutableList<Double> = mutableListOf()

    override operator fun invoke(domainValue: D): Double {
        val i: Int = indexableDomain.index[domainValue] ?: return unknown
        return if (ordinalRange.isEmpty()) unknown else ordinalRange[i]
    }

    override fun ticks(count: Int): List<D> = domain

    protected fun rescale() {
        val n = indexableDomain._domain.size
        val reverse = range.end < range.start
        var start = if (reverse) range.end else range.start
        val stop = if (reverse) range.start else range.end
        step = (stop - start) / max(1.0, n - _paddingInner + _paddingOuter * 2)
        if (round)
            step = floor(step)

        start += (stop - start - step * (n - _paddingInner)) * align.value
        bandwidth = step * (1 - _paddingInner)
        if (round) {
            start = kotlin.math.round(start)
            bandwidth = kotlin.math.round(bandwidth)
        }

        val values: Array<Double> = Array(n, { start + step * it })
        if (reverse) values.reverse()
        ordinalRange.clear()
        ordinalRange.addAll(values)
    }
}

/**
 * Represents domain as band (for barchart for example).
 *
 * BandScale.invoke(domain) returns the coordinate of the start of each band.
 *
 *
 * [padding]
 *
 * [paddingInner] representents the size between
 *
 * Band scales are like ordinal scales except the output range is continuous and numeric.
 * Discrete output values are automatically computed by the scale by dividing the continuous range into uniform bands.
 * Band scales are typically used for bar charts with an ordinal or categorical dimension.
 * The unknown value of a band scale is always NaN: they do not allow implicit domain construction.
 */
public class BandScale<D> internal constructor() : BandedScale<D>() {

    override var padding: Percent
        get() = Percent(_paddingInner)
        set(value) {
            _paddingInner = value.value
            _paddingOuter = value.value
            rescale()
        }

    /**
     * Sets the inner padding to the specified value which must be in the range [0%, 100%].
     * Returns the current inner padding which defaults to 0%.
     * The inner padding determines the ratio of the range that is reserved for blank space before each band.
     */
    public var paddingInner: Percent
        get() = Percent(_paddingInner)
        set(value) {
            _paddingInner = value.coerceToDefault().value
            rescale()
        }

    /**
     * Sets the outer padding to the specified value which must be in the range [0%, 100%].
     * Returns the current outer padding which defaults to 0%.
     * The outer padding determines the ratio of the range that is reserved for blank space before the first
     * band and after the last band.
     */
    public var paddingOuter: Percent
        get() = Percent(_paddingOuter)
        set(value) {
            _paddingOuter = value.coerceToDefault().value
            rescale()
        }

    override fun copy(): BandScale<D> {
        return BandScale<D>().also {
            it.align        = align
            it.domain        = domain
            it.range        = range
            it.round        = round
            it.padding      = padding
            it.paddingInner = paddingInner
            it.paddingOuter = paddingOuter
            it.rescale()
        }
    }
}

/**
 * Point scales are a variant of band scales with the bandwidth fixed to zero.
 * Point scales are typically used for scatterplots with an ordinal or categorical dimension.
 * The unknown value of a point scale is always NaN: they do not allow implicit domain construction.
 */
public class PointScale<D> : BandedScale<D>() {

    /**
     * Sets the outer padding to the specified value which must be in the range [0%, 100%].
     * Returns the current outer padding which defaults to 0%.
     * The outer padding determines the ratio of the range that is reserved for blank space before the first
     * point and after the last point.
     */
    override var padding: Percent
        get() = Percent(_paddingOuter)
        set(value) {
            _paddingOuter = value.coerceToDefault().value
            rescale()
        }

    init {
        _paddingInner = 1.0
        rescale()
    }

    override fun copy(): PointScale<D> {
        return PointScale<D>().also {
            it.align        = align
            it.domain        = domain
            it.range        = range
            it.round        = round
            it.padding      = padding
            it.rescale()
        }
    }
}


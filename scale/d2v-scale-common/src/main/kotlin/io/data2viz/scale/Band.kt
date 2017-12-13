package io.data2viz.scale

import kotlin.math.floor
import kotlin.math.max


/**
 * Retu
 */
abstract class BandedScale<D>(private val indexableDomain: IndexableDomain<D> = IndexableDomain()) : 
        Scale<D,Double>, 
        DiscreteDomain<D> by indexableDomain, 
        Tickable<D>, 
        StrictlyContinuousRange<D, Double> {

    private val unknown = Double.NaN

    protected var _paddingInner: Double = 0.0
    protected var _paddingOuter: Double = 0.0

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

    var round: Boolean = false
        set(value) {
            field = value
            rescale()
        }

    var align: Double = 0.5
        set(value) {
            field = value.coerceIn(.0 .. 1.0)
            rescale()
        }

    var step: Double = 1.0
        private set

    var bandwidth: Double = 1.0
        private set

    private var ordinalRange: MutableList<Double> = ArrayList()

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

        start += (stop - start - step * (n - _paddingInner)) * align
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
class BandScale<D> : BandedScale<D>() {

    var padding: Double
        get() = _paddingInner
        set(value) {
            _paddingInner = value
            _paddingOuter = value
            rescale()
        }

    var paddingInner
        get() = _paddingInner
        set(value) {
            _paddingInner = value.coerceIn(.0 .. 1.0)
            rescale()
        }

    var paddingOuter
        get() = _paddingOuter
        set(value) {
            _paddingOuter = value.coerceIn(.0 .. 1.0)
            rescale()
        }
}

/**
 * Point scales are a variant of band scales with the bandwidth fixed to zero.
 * Point scales are typically used for scatterplots with an ordinal or categorical dimension.
 * The unknown value of a point scale is always NaN: they do not allow implicit domain construction.
 */
class PointScale<D> : BandedScale<D>() {

    /**
     * Sets the outer padding to the specified value which must be in the range [0, 1].
     * Returns the current outer padding which defaults to 0.
     * The outer padding determines the ratio of the range that is reserved for blank space before the first
     * point and after the last point. Equivalent to band.paddingOuter.
     */
    var padding:Double
        get() = _paddingOuter
        set(value) {
            _paddingOuter = value
            rescale()
        }

    init {
        _paddingInner = 1.0
        rescale()
    }
}


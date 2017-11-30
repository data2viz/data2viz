package io.data2viz.scale

import kotlin.math.floor
import kotlin.math.max

abstract class BandedScale<D> : DiscreteScale<D, Double>() {

    private val unknown = Double.NaN

    protected var _paddingInner: Double = 0.0
    protected var _paddingOuter: Double = 0.0

    override var domain: List<D>
        get() = super.domain
        set(value) {
            super.domain = value
            rescale()
        }

    override var range: List<Double>
        get() = super.range
        set(value) {
            super.range = value
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

    init {
        _range.clear()
        _range.addAll(arrayListOf(.0, 1.0))
    }

    override operator fun invoke(domainValue: D): Double {
        val i: Int = index[domainValue] ?: return unknown
        return if (ordinalRange.isEmpty()) unknown else ordinalRange[i]
    }

    override fun ticks(count: Int): List<D> = domain

    protected fun rescale() {
        val n = _domain.size
        if (_range.isEmpty())
            return

        val reverse = _range.last() < _range.first()
        var start = if (reverse) _range.last() else _range.first()
        val stop = if (reverse) _range.first() else _range.last()
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

fun <D> bandScale() = BandScale<D>()
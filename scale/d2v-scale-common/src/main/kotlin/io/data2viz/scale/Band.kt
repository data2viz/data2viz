package io.data2viz.scale

import kotlin.math.floor
import kotlin.math.max

/**
 * Band scales are like ordinal scales except the output range is continuous and numeric.
 * Discrete output values are automatically computed by the scale by dividing the continuous range into uniform bands.
 * Band scales are typically used for bar charts with an ordinal or categorical dimension.
 * The unknown value of a band scale is effectively undefined: they do not allow implicit domain construction.
 */
open class BandScale<D> : OrdinalScale<D, Double>() {

    /*val Double.unknown:Double
        get() = Double.NaN*/

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

    // TODO : avoid double rescale()
    var padding: Double = 0.0
        set(value) {
            paddingInner = value
            paddingOuter = value
        }

    var paddingInner: Double = 0.0
        set(value) {
            field = value.coerceIn(.0 .. 1.0)
            rescale()
        }

    var paddingOuter: Double = 0.0
        set(value) {
            field = value.coerceIn(.0 .. 1.0)
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
        range = listOf(.0, 1.0)
        unknown = Double.NaN
    }

    override operator fun invoke(domainValue: D): Double {
        /*if (!index.containsKey(domainValue)) {
            innerDomain.add(domainValue)
            index.put(domainValue, innerDomain.size - 1)
        }*/

        val i: Int = index[domainValue] ?: return unknown!!
        return if (ordinalRange.isEmpty()) unknown!! else ordinalRange[i]
    }

    override fun ticks(count: Int): List<D> = domain

    private fun rescale() {
        val n = innerDomain.size
        if (range.isEmpty())
            return

        val reverse = range.last() < range.first()
        var start = if (reverse) range.last() else range.first()
        val stop = if (reverse) range.first() else range.last()
        step = (stop - start) / max(1.0, n - paddingInner + paddingOuter * 2)
        if (round)
            step = floor(step)

        start += (stop - start - step * (n - paddingInner)) * align
        bandwidth = step * (1 - paddingInner)
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

fun <D> bandScale() = BandScale<D>()
package io.data2viz.scale

/**
 * Sequential scales are similar to continuous scales in that they map a continuous, numeric input domain to a
 * continuous output range. However, unlike continuous scales, the output range of a sequential scale is fixed
 * by its interpolator and not configurable.
 * These scales do not expose invert, range, rangeRound and interpolate methods.
 */
open class SequentialScale<R>(var interpolator: ((Double) -> R)?) : TickableScale<Double, R>, ClampableScale<Double, R> {

    override fun domain(vararg d: Double) {
        domain = d.toMutableList()
    }

    override var domain: MutableList<Double> = arrayListOf(.0, 1.0)
        get() = field.toMutableList()
        set(value) {
            if (value.size != 2) throw IllegalArgumentException("Sequential Scale can only accept a domain with 2 values.")
            field = value.toMutableList()
        }

    override var clamp: Boolean = false
        set(value) {
            field = value
        }

    override fun invoke(domainValue: Double): R {
        requireNotNull(interpolator)
        var uninterpolatedDomain = (domainValue - domain.first()) / (domain.last() - domain.first())
        if(clamp) uninterpolatedDomain = uninterpolatedDomain.coerceIn(0.0, 1.0)
        return interpolator!!(uninterpolatedDomain)
    }

    override fun ticks(count: Int) = io.data2viz.core.ticks(domain.first(), domain.last(), count) as List<Double>
}

fun sequentialScale(interpolator: (Double) -> Double) = SequentialScale<Double>(interpolator)
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

    operator fun invoke(domainValue: Double): R {
        var uninterpolatedDomain = uninterpolateNumber(domain.start, domain.end)(domainValue)
        if (clamp) uninterpolatedDomain = uninterpolatedDomain.coerceToDefault()
        return interpolator(uninterpolatedDomain)
    }

    override fun ticks(count: Int) = io.data2viz.math.ticks(domain.start, domain.end, count)
}


package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.core.tickStep
import io.data2viz.interpolate.interpolateHsl
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.interpolateRound
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Constructs a new continuous scale with the unit domain [0, 1], the unit range [0, 1], the default interpolator
 * and clamping disabled.
 * Linear scales are a good default choice for continuous quantitative data because they preserve proportional
 * differences. Each range value y can be expressed as a function of the domain value x: y = mx + b.
 */
open class LinearScale<R>(interpolateRange: (R, R) -> (Double) -> R,
                          uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                          rangeComparator: Comparator<R>? = null)

    : ContinuousScaleImpl<R>( interpolateRange, uninterpolateRange, rangeComparator), NiceableScale<Double, R> {

    override fun interpolateDomain(from: Double, to: Double): (Double) -> Double = interpolateNumber(from, to)
    override fun uninterpolateDomain(from: Double, to: Double): (Double) -> Double = uninterpolateNumber(from, to)

    /**
     * Extends the domain so that it starts and ends on nice round values.
     * This method typically modifies the scale’s domain, and may only extend the bounds to the nearest round value.
     * An optional tick count argument allows greater control over the step size used to extend the bounds,
     * guaranteeing that the returned ticks will exactly cover the domain. Nicing is useful if the domain is computed
     * from data, say using extent, and may be irregular. For example, for a domain of [0.201479…, 0.996679…],
     * a nice domain might be [0.2, 1.0]. If the domain has more than two values, nicing the domain only affects
     * the first and last value. See also d3-array’s tickStep.
     *
     * Nicing a scale only modifies the current domain; it does not automatically nice domains that are
     * subsequently set using continuous.domain. You must re-nice the scale after setting the new domain, if desired.
     */
    override fun nice(count:Int) {

        // since domain getter returns a copy we need to reset the whole value
        val newDomain = domain

        val last = domain.size - 1
        var step = tickStep(domain[0], domain[last], count)
        val start = floor(domain[0] / step) * step
        val stop = ceil(domain[last] / step) * step

        if (step != .0) {
            step = tickStep(start, stop, count)
            newDomain[0] = floor(start / step) * step
            newDomain[last] = ceil(stop / step) * step
            domain = newDomain
        }
    }
}

fun linearScale(): LinearScale<Double> {
    return LinearScale<Double>(::interpolateNumber, ::uninterpolateNumber, doubleComparator)
}

fun linearScaleRound(): LinearScale<Double> {
    return LinearScale(::interpolateRound, ::uninterpolateNumber, doubleComparator)
}

fun linearScaleHSL(): LinearScale<HSL> = LinearScale<HSL>(::interpolateHsl)
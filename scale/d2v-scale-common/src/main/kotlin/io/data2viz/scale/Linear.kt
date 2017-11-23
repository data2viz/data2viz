package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.interpolate.interpolateHsl
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.uninterpolateNumber

open class LinearScale<D, R>(uninterpolateDomain: (D, D) -> (D) -> Double,
                             interpolateRange: (R, R) -> (Double) -> R,
                             interpolateDomain: ((D, D) -> (Double) -> D)? = null,
                             uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                             domainComparator: Comparator<D>? = null,
                             rangeComparator: Comparator<R>? = null)
    : ContinuousScale<D, R>(uninterpolateDomain, interpolateRange, interpolateDomain, uninterpolateRange, domainComparator, rangeComparator) {

    fun domainsToRanges(vararg d:DomainToRange<D, R>) = domainsToRanges(d.toList())
    override fun domainsToRanges(d: List<DomainToRange<D, R>>)= super.domainsToRanges(d) as  LinearScale<D, R>

    fun nice(count: Int = 10): LinearScale<D, R> {
        val i = domainsToRanges.size - 1
        val n = count
        val start = domainsToRanges.first()
        val stop = domainsToRanges.last()
        /*var step = tickStep(start, stop, n)

        if(step > 0) {
            step = tickStep(Math.floor(start / step) * step, Math.ceil(stop / step) * step, n)
            domain[0] = Math.floor(start / step) * step
            domain[i] = Math.ceil(stop / step) * step
        }*/

        return this
    }

//    override fun ticks(count: Int) = io.data2viz.core.ticks(domainsToRanges.first().domain, domainsToRanges.last().domain, count)
}


fun linearScaleDouble() : LinearScale<Double, Double> {
    val comparator = naturalOrder<Double>()
    return LinearScale<Double, Double>(::uninterpolateNumber, ::interpolateNumber, ::interpolateNumber, ::uninterpolateNumber, comparator, comparator)
}

fun linearScaleHSL() : LinearScale<Double, HSL> = LinearScale<Double, HSL>(::uninterpolateNumber, ::interpolateHsl)
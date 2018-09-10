package io.data2viz.interpolate

import io.data2viz.color.RgbColor
import io.data2viz.core.Point
import kotlin.math.floor

// scale ----------------
data class DomainToViz<out A, out B>(
        val domain: A,
        val viz: B
)

infix fun <A, B> A.linkedTo(that: B): DomainToViz<A, B> = DomainToViz(this, that)

class NumberToColor(start: DomainToViz<Number, RgbColor>, end: DomainToViz<Number, RgbColor>){
    val domainToNormalized = uninterpolateNumber(start.domain, end.domain)
    val normalizedToColor = interpolateRgb(start.viz, end.viz)
    val numberToColor = {number:Number ->  normalizedToColor(domainToNormalized(number as Double)) }
}

// TODO remove
class scale {

    object linear {

        // TODO pointToPoint ? (not plural)
        fun pointsToPoints(start: DomainToViz<Point, Point>, end: DomainToViz<Point, Point>) =
                { pt: Point ->
                    Point(
                            numberToNumber(start.domain.x linkedTo start.viz.x, end.domain.x linkedTo end.viz.x)(pt.x).toDouble(),
                            numberToNumber(start.domain.y linkedTo start.viz.y, end.domain.y linkedTo end.viz.y)(pt.y).toDouble())
                }

        fun numberToNumber(start: DomainToViz<Number, Number>, end: DomainToViz<Number, Number>): (Number) -> Number =
                { domain: Number ->
                    interpolateNumber(start.viz, end.viz).invoke(
                            uninterpolateNumber(start.domain, end.domain).invoke(domain as Double))
                }

//        fun numberToNumber(start: DomainToViz<Double, Double>, end: DomainToViz<Double, Double>): (Double) -> Double =
//                { domain: Double ->
//                    interpolateNumber(start.viz, end.viz).invoke(
//                            uninterpolateNumber(start.domain as Number, end.domain).invoke(domain).toDouble()) as Double
//                }

        fun numberToColor(start: DomainToViz<Number, RgbColor>, end: DomainToViz<Number, RgbColor>): (Number) -> RgbColor = NumberToColor(start, end).numberToColor

        // TODO maybe think of a function accepting a list of domainToViz
        /*fun numberToColor(domainsToViz: List<DomainToViz<Number, Color>>): (Number) -> Color =
                { domain: Number ->
                    val sortedDomainsToViz = domainsToViz.sortedBy { it.domain.toDouble() }
                    val interpolationFunction = uninterpolateNumber(sortedDomainsToViz.first().domain, sortedDomainsToViz.last().domain)
                    val interpolator = interpolateRgb(sortedDomainsToViz.map { it.viz })
                    interpolator(interpolationFunction(domain))
                }*/

    }
    object ordinal {
        fun <T> bands(domain: Collection<T>, initBandScale: BandScale<T>.() -> Unit) = BandScale(domain).apply(initBandScale)
    }
}


/**
 * Generic BandScale. The type is given by the domain. It can be a key built from
 * the domain.
 */
class BandScale<T>(var domain: Collection<T>) {

    data class Padding(var inner: Double, var outer: Double)


    var start: Number = 0.0
        set(value) {
            field = value
            rescale()
        }

    var stop: Number = 1.0
        set(value) {
            field = value
            rescale()
        }

    var padding: Padding = Padding(.0, .0)
        set(value) {
            field = value
            rescale()
        }

    /**
     * Simplifies the set of paddingInner and paddingOuter to the same value
     */
    fun padding(inner: Double = .0, outer: Double = inner) = Padding(inner, outer)


//    var align = 0.5

    var round = false

    var bandwidth: Double = 0.0
        private set

    var step = .0
        private set


    init {
        rescale()
    }


    fun rescale() {
        val n = domain.size
        step = (stop.toDouble() - start.toDouble()) / (n - padding.inner + padding.outer * 2).coerceAtLeast(1.0)
        if (round) step = floor(step)
        bandwidth = step * (1.toDouble() - padding.inner)
    }

    operator fun invoke(key: T): Number {
        val i = domain.indexOf(key)
        return padding.outer + i * step
    }

}

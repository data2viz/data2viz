package io.data2viz.interpolate

import io.data2viz.core.Point

// scale ----------------
data class DomainToViz<out A, out B>(
        val domain: A,
        val viz: B
)

infix fun <A, B> A.linkedTo(that: B): DomainToViz<A, B> = DomainToViz(this, that)

class scale {

    object linear {

        fun pointsToPoints(start: DomainToViz<Point, Point>, end: DomainToViz<Point, Point>) =
                { pt: Point ->
                    Point(
                            numberToNumber(start.domain.x linkedTo start.viz.x, end.domain.x linkedTo end.viz.x)(pt.x),
                            numberToNumber(start.domain.y linkedTo start.viz.y, end.domain.y linkedTo end.viz.y)(pt.y))
                }

        fun numberToNumber(start: DomainToViz<Number, Number>, end: DomainToViz<Number, Number>): (Number) -> Number =
                { domain: Number -> interpolateNumber(start.viz, end.viz).invoke(
                        uninterpolate(start.domain, end.domain).invoke(domain).toDouble()) }

    }
}

package io.data2viz.interpolate

import io.data2viz.core.Point

// scale ----------------
data class DomainToViz<out A, out B>(
        val domain: A,
        val viz: B
)

infix fun <A, B> A.to(that: B): DomainToViz<A, B> = DomainToViz(this, that)

class scale {

    object linear {

        fun pointsToPoints(start: DomainToViz<Point, Point>, end: DomainToViz<Point, Point>) =
                { pt: Point ->
                    Point(
                            doublesToDoubles(start.domain.x to start.viz.x, end.domain.x to end.viz.x)(pt.x),
                            doublesToDoubles(start.domain.y to start.viz.y, end.domain.y to end.viz.y)(pt.y))
                }

        fun doublesToDoubles(start: DomainToViz<Double, Double>, end: DomainToViz<Double, Double>) =
                { domain: Double -> domain * (end.viz - start.viz) / (end.domain - start.domain) + start.viz }

    }
}

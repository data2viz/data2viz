package io.data2viz.scale

import io.data2viz.time.Date
import io.data2viz.time.date

/**
 * Time scales are a variant of linear scales that have a temporal domain: domain values are dates rather than numbers,
 * and invert returns a date.
 * Time scales implement ticks based on calendar intervals, taking the pain out of generating axes for temporal domains.
 */
class TimeScale<R>(interpolateRange: (R, R) -> (Double) -> R,
                   uninterpolateRange: ((R, R) -> (R) -> Double)? = null,
                   rangeComparator: Comparator<R>? = null)
    : ContinuousScale<Date, R>(interpolateRange, uninterpolateRange, rangeComparator),
        NiceableScale<Date>,
        Tickable<Date> {

    val comparator = Comparator<Date> { a, b -> if (a.millisecondsBetween(b) > 0) 1 else if (a.millisecondsBetween(b) < 0) -1 else 0 }

    init {
        _domain.clear()
        _domain.addAll(listOf(date(2000,1,1), date(2000,1,2)))
    }

    override fun uninterpolateDomain(from: Date, to: Date): (Date) -> Double {
        return { date ->
            if (from.millisecondsBetween(to) != 0L)
                ((from.millisecondsBetween(date)) / (from.millisecondsBetween(to))).toDouble()
            else .0
        }
    }

    override fun interpolateDomain(from: Date, to: Date): (Double) -> Date {
        val diff = from.millisecondsBetween(to)
        return { double ->
            val date: Date = date(from)
            val milliseconds = double.toLong() * diff
            date.plusMilliseconds(milliseconds)
            date
        }
    }

    override fun domainComparator():Comparator<Date> {
        return comparator
    }

    /**
     * Extends the domain so that it starts and ends on nice round values. This method typically modifies
     * the scale’s domain, and may only extend the bounds to the nearest round value. See continuous.nice for more.
     *
     * An optional tick count argument allows greater control over the step size used to extend the bounds,
     * guaranteeing that the returned ticks will exactly cover the domain.
     *
     * Alternatively, a time interval may be specified to explicitly set the ticks.
     * If an interval is specified, an optional step may also be specified to skip some ticks.
     * For example, time.nice(d3.timeSecond, 10) will extend the domain to an even ten seconds (0, 10, 20, etc.).
     * See time.ticks and interval.every for further detail.
     *
     * Nicing is useful if the domain is computed from data, say using extent, and may be irregular.
     * For example, for a domain of [2009-07-13T00:02, 2009-07-13T23:48],
     * the nice domain is [2009-07-13, 2009-07-14].
     * If the domain has more than two values, nicing the domain only affects the first and last value.
     */
    override fun nice(count: Int) {
        // TODO not implemented
    }

    /*
    scale.nice = function(interval, step) {
    var d = domain();
    return (interval = tickInterval(interval, d[0], d[d.length - 1], step))
        ? domain(nice(d, interval))
        : scale;
  };
     */

    override fun ticks(count: Int): List<Date> {
        // TODO not implemented
        return listOf()
    }


}


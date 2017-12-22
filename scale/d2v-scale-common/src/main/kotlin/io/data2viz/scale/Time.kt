package io.data2viz.scale

import io.data2viz.core.tickStep
import io.data2viz.time.*

private data class TickInterval(
        val interval: Interval,
        val step: Int,
        val duration: Long
)

private val tickIntervals = listOf(
        TickInterval(timeSecond, 1, durationSecond),
        TickInterval(timeSecond, 5, 5 * durationSecond),
        TickInterval(timeSecond, 15, 15 * durationSecond),
        TickInterval(timeSecond, 30, 30 * durationSecond),
        TickInterval(timeMinute, 1, durationMinute),
        TickInterval(timeMinute, 5, 5 * durationMinute),
        TickInterval(timeMinute, 15, 15 * durationMinute),
        TickInterval(timeMinute, 30, 30 * durationMinute),
        TickInterval(timeHour, 1, durationHour),
        TickInterval(timeHour, 3, 3 * durationHour),
        TickInterval(timeHour, 6, 6 * durationHour),
        TickInterval(timeHour, 12, 12 * durationHour),
        TickInterval(timeDay, 1, durationDay),
        TickInterval(timeDay, 2, 2 * durationDay),
        TickInterval(timeSunday, 1, durationWeek),
        TickInterval(timeMonth, 1, durationMonth),
        TickInterval(timeMonth, 3, 3 * durationMonth),
        TickInterval(timeYear, 1, durationYear)
)

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
        _domain.addAll(listOf(date(2000, 1, 1), date(2000, 1, 2)))
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

    override fun domainComparator(): Comparator<Date> {
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
        val start = _domain.first()
        val end = _domain.last()
        val interval: Interval = tickInterval(count, start, end)
        niceDomain(end, start, interval)
        rescale()
    }

    private fun tickInterval(count: Int, start: Date, end: Date): Interval {
        val target = start.millisecondsBetween(end) / count
        val intervalIndex = bisectRight(tickIntervals.map { it.duration }, target, naturalOrder())
        val step: Int?
        var interval: Interval = timeYear
        if (intervalIndex == tickIntervals.size) {
            step = tickStep(start.getTime() / durationYear, end.getTime() / durationYear, count).toInt()
        } else if (intervalIndex > 0) {
            val l = target.toDouble() / tickIntervals[intervalIndex - 1].duration
            val l1 = tickIntervals[intervalIndex].duration / target.toDouble()
            val tickInterval = tickIntervals[if (l < l1) intervalIndex - 1 else intervalIndex]
            step = tickInterval.step
            interval = tickInterval.interval
        } else {
            step = tickStep(start.getTime(), end.getTime(), count).toInt()
            interval = timeMillisecond
        }
        if (step > 0) interval = interval.every(step)
        return interval
    }

    private fun niceDomain(end: Date, start: Date, interval: Interval) {
        var first = 0
        var last = _domain.size - 1

        if (end.isBefore(start)) {
            first = domain.size - 1
            last = 0
        }

        val x0 = _domain[first]
        val x1 = _domain[last]

        _domain[first] = interval.floor(x0)
        _domain[last] = interval.ceil(x1)
    }

    /**
     * Returns representative dates from the scale’s domain.
     * The returned tick values are uniformly-spaced (mostly), have sensible values (such as every day at midnight),
     * and are guaranteed to be within the extent of the domain.
     * Ticks are often used to display reference lines, or tick marks, in conjunction with the visualized data.
     *
     * An optional count may be specified to affect how many ticks are generated.
     * If count is not specified, it defaults to 10. The specified count is only a hint; the scale may return more
     * or fewer values depending on the domain.
     *
     * The following time intervals are considered for automatic ticks:
     * 1-, 5-, 15- and 30-second.
     * 1-, 5-, 15- and 30-minute.
     * 1-, 3-, 6- and 12-hour.
     * 1- and 2-day.
     * 1-week.
     * 1- and 3-month.
     * 1-year.
     */
    override fun ticks(count: Int): List<Date> {
        var first = 0
        var last = _domain.size - 1

        var start = _domain[first]
        var end = _domain[last]

        if (start.millisecondsBetween(end) == 0L) return listOf()

        val reversed = end.isBefore(start)
        if (reversed) {
            first = _domain.size - 1
            last = 0
            start = _domain[first]
            end = _domain[last]
        }

        val endPlus = date(end)
        endPlus.plusMilliseconds(1)

        val ticks = tickInterval(count, start, end).range(start, endPlus)

        return if (reversed) ticks.reversed() else ticks
    }
}


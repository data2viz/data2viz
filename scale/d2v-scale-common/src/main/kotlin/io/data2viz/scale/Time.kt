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
        val target = start.millisecondsBetween(end) / count
        val intervalIndex = bisectRight(tickIntervals.map { it.duration }, target, naturalOrder())
        if (intervalIndex == tickIntervals.size) {
            val step = tickStep(start.getTime() / durationYear, end.getTime() / durationYear, count)
            // TODO : mange step with interval.every(step) !!
            niceDomain(end, start, timeYear)
        } else if (intervalIndex > 0) {
            val tickInterval = tickIntervals[if ((target / tickIntervals[intervalIndex - 1].duration) < tickIntervals[intervalIndex].duration / target) intervalIndex - 1 else intervalIndex]
            val step = tickInterval.step
            // TODO : mange step with interval.every(step) !!
            niceDomain(end, start, tickInterval.interval)
        }/* else {
            val step = tickStep(start.getTime(), end.getTime(), count)
            val interval = timeMillisecond
        }*/
        rescale()
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

    /*
    var target = Math.abs(stop - start) / interval,
          i = bisector(function(i) { return i[2]; }).right(tickIntervals, target);
      if (i === tickIntervals.length) {
        step = tickStep(start / durationYear, stop / durationYear, interval);
        interval = year;
      } else if (i) {
        i = tickIntervals[target / tickIntervals[i - 1][2] < tickIntervals[i][2] / target ? i - 1 : i];
        step = i[1];
        interval = i[0];
      } else {
        step = tickStep(start, stop, interval);
        interval = millisecond;
      }
     */

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


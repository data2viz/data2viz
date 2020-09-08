/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.scale

import io.data2viz.interpolate.Interpolator
import io.data2viz.interpolate.UnInterpolator
import io.data2viz.math.Percent
import io.data2viz.math.tickStep
import io.data2viz.time.*
import kotlinx.datetime.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

val dateComparator = Comparator<LocalDateTime> { a, b -> a.compareTo(b) }

private data class TickInterval(
    val interval: Interval,
    val step: Int,
    val duration: Long
)

val durationSecond = 1000L
val durationMinute = 60000L
val durationHour = 3600000L
val durationDay = 86400000L
val durationWeek = 604800000L        // (day * 7)
val durationMonth = 2592000000L      // (day * 30)
val durationYear = 31536000000L      // (day * 365)

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
//@ExperimentalTime
class TimeScale<R> internal constructor(
    interpolateRange: (R, R) -> Interpolator<R>,
    uninterpolateRange: ((R, R) -> UnInterpolator<R>)? = null,
    rangeComparator: Comparator<R>? = null
)
    : ContinuousScale<LocalDateTime, R>(interpolateRange, uninterpolateRange, rangeComparator),
        NiceableScale<LocalDateTime> ,
        Tickable<LocalDateTime> {

    init {
        _domain.clear()
        _domain.addAll(listOf(LocalDateTime(2000, 1, 1, 0, 0, 0, 0), LocalDateTime(2000, 1, 2, 0, 0, 0, 0)))
    }

    override fun uninterpolateDomain(from: LocalDateTime, to: LocalDateTime): UnInterpolator<LocalDateTime> {
        val range = to - from
        return { date -> if (range == ZERO) Percent(.0) else Percent((date - from) / range) }
    }

    override fun interpolateDomain(from: LocalDateTime, to: LocalDateTime): Interpolator<LocalDateTime> {
        val range = to - from
        val fromInstant = from.toInstant(defaultTZ)
        return { percent -> (fromInstant + (range * percent.value)).toLocalDateTime(defaultTZ) }
    }

    override fun domainComparator(): Comparator<LocalDateTime> = dateComparator

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
        val interval = tickInterval(count, start, end)
        niceDomain(end, start, interval)
        rescale()
    }

    private fun tickInterval(count: Int, start: LocalDateTime, end: LocalDateTime): Interval {
        val diff: Duration = end - start
        val targetDuration = diff / count
        val intervalIndex = bisectRight(tickIntervals.map { it.duration }, targetDuration.toLong(DurationUnit.MILLISECONDS), naturalOrder())
        val step: Int?
        var interval: Interval = timeYear
        if (intervalIndex == tickIntervals.size) {
            step = tickStep(start.toInstant(defaultTZ).toEpochMilliseconds().toDouble() / durationYear, end.toInstant(defaultTZ).toEpochMilliseconds().toDouble() / durationYear, count).toInt()
        } else if (intervalIndex > 0) {
            val l = targetDuration.toDouble(DurationUnit.MILLISECONDS) / tickIntervals[intervalIndex - 1].duration
            val l1 = tickIntervals[intervalIndex].duration / targetDuration.toDouble(DurationUnit.MILLISECONDS)
            val tickInterval = tickIntervals[if (l < l1) intervalIndex - 1 else intervalIndex]
            step = tickInterval.step
            interval = tickInterval.interval
        } else {
            step = tickStep(start.toInstant(defaultTZ).toEpochMilliseconds().toDouble(), end.toInstant(defaultTZ).toEpochMilliseconds().toDouble(), count).toInt()
            interval = timeMillisecond
        }
        if (step > 0) interval = interval.every(step)
        return interval
    }

    private fun niceDomain(end: LocalDateTime, start: LocalDateTime, interval: Interval) {
        var first = 0
        var last = _domain.size - 1

        if (end < start) {
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
    override fun ticks(count: Int): List<LocalDateTime> {
        var first = 0
        var last = _domain.size - 1

        var start = _domain[first]
        var end = _domain[last]

        if ((end - start) == ZERO) return listOf()

        val reversed = end < start
        if (reversed) {
            first = _domain.size - 1
            last = 0
            start = _domain[first]
            end = _domain[last]
        }

        val endPlus = end + DateTimeUnit.MILLISECOND.duration

        val tickInterval = tickInterval(count, start, end)
        val ticks = tickInterval.range(start, endPlus)

        return if (reversed) ticks.reversed() else ticks
    }

    /**
     * Returns every an array of dates representing every interval boundary after or equal to start (inclusive)
     * and before stop (exclusive).
     * If step is specified > 1 then every stepth boundary will be returned; for example, for the d2v.timeDay
     * interval a step of 2 will return every other day.
     * The first date in the returned array is the earliest boundary after or equal to start; subsequent dates are
     * offset by step intervals and floored.
     * Thus, two overlapping ranges may be consistent.
     */
//    fun range(start: Date, stop: Date, step: Long = 1): List<Date> {
//        val range = arrayListOf<Date>()
//        var current = ceil(start)
//        if (step > 0) {
//            while (current.isBefore(stop)) {
//                range.add(current)
//                current = floori(offseti(Date(current), step))
//            }
//        }
//        return range.toList()
//    }
}


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

package io.data2viz.time

import kotlinx.datetime.*

val defaultTZ: TimeZone = TimeZone.UTC

operator fun LocalDateTime.minus(other: LocalDateTime): DateTimePeriod = this.toInstant(defaultTZ).periodUntil(other.toInstant(defaultTZ), defaultTZ)
operator fun LocalDateTime.plus(period: DateTimePeriod): LocalDateTime = this.toInstant(defaultTZ).plus(period, defaultTZ).toLocalDateTime(defaultTZ)

/**
 * Constructs a new custom interval given the specified floor and offset functions and an optional count function.
 *
 * The floor function takes a single date as an argument and rounds it down to the nearest interval boundary.
 *
 * The offset function takes a date and an integer step as arguments and advances the specified date by
 * the specified number of boundaries; the step may be positive, negative or zero.
 *
 * The optional count function takes a start date and an end date, already floored to the current interval, and
 * returns the number of boundaries between the start (exclusive) and end (inclusive).
 * If a count function is not specified, the returned interval does not allow interval.count or interval.every methods.
 * Note: due to an internal optimization, the specified count function must not invoke interval.count on other time intervals.
 *
 * The optional field function takes a date, already floored to the current interval, and returns the field value
 * of the specified date, corresponding to the number of boundaries between this date (exclusive) and the latest
 * previous parent boundary.
 * For example, for the timeDay interval, this returns the number of days since the start of the month.
 * If a field function is not specified, it defaults to counting the number of interval boundaries since the UNIX
 * epoch of January 1, 1970 UTC. The field function defines the behavior of interval.every.
 *
 * [floor] Returns a new date representing the latest interval boundary date before or equal to date.
 * For example, d2v.timeDay.floor(date) typically returns 12:00 AM local time on the given date.
 * This method is idempotent: if the specified date is already floored to the current interval, a new date
 * with an identical time is returned.
 * Furthermore, the returned date is the minimum expressible value of the associated interval,
 * such that interval.floor(interval.floor(date) - 1) returns the preceeding interval boundary date.
 *
 * [offset] Returns a new date equal to date plus step intervals.
 * If step is not specified it defaults to 1.
 * If step is negative, then the returned date will be before the specified date;
 * if step is zero, then a copy of the specified date is returned.
 * This method does not round the specified date to the interval.
 * For example, if date is today at 5:34 PM, then d2v.timeDay.offset(date, 1) returns 5:34 PM tomorrow
 * (even if daylight saving changes!).
 *
 */
open class Interval(val floor: (LocalDateTime) -> LocalDateTime,
                    val offset: (LocalDateTime, Int) -> LocalDateTime,
                    val count: ((LocalDateTime, LocalDateTime) -> Int)? = null,
                    private val field: ((LocalDateTime) -> Int)? = null) {

    /**
     * Returns a new date representing the earliest interval boundary date after or equal to date.
     * For example, d2v.timeDay.ceil(date) typically returns 12:00 AM local time on the date following the given date.
     * This method is idempotent: if the specified date is already ceilinged to the current interval,
     * a new date with an identical time is returned.
     * Furthermore, the returned date is the maximum expressible value of the associated interval,
     * such that interval.ceil(interval.ceil(date) + 1) returns the following interval boundary date.
     */
    fun ceil(date: LocalDateTime): LocalDateTime {
        var newDate = date + DateTimePeriod(0, 0, 0, 0, 0, 0, -1_000_000)
        newDate = floor(newDate)
        newDate = offset(newDate, 1)
        newDate = floor(newDate)
        return newDate
    }

    /**
     * Returns a new date representing the closest interval boundary date to date.
     * For example, d2v.timeDay.round(date) typically returns 12:00 AM local time on the given date if it
     * is on or before noon, and 12:00 AM of the following day if it is after noon.
     * This method is idempotent: if the specified date is already rounded to the current interval,
     * a new date with an identical time is returned.
     */
    fun round(date: LocalDateTime): LocalDateTime {
        val d0 = floor(date)
        val d1 = ceil(date)
        val dateInstant = date.toInstant(defaultTZ)
        val millisecondsBetween1 = d0.toInstant(defaultTZ).until(dateInstant, DateTimeUnit.MILLISECOND, defaultTZ)
        val millisecondsBetween2 = dateInstant.until(d1.toInstant(defaultTZ), DateTimeUnit.MILLISECOND, defaultTZ)
        return if (millisecondsBetween1 < millisecondsBetween2) d0 else d1
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
    fun range(start: LocalDateTime, stop: LocalDateTime, step: Int = 1): List<LocalDateTime> {
        val range = mutableListOf<LocalDateTime>()
        var current = ceil(start)
        if (step > 0) {
            while (current < stop) {
                range.add(current)
                current = floor(offset(current, step))
            }
        }
        return range.toList()
    }

    /**
     * Returns a new interval that is a filtered subset of this interval using the specified test function.
     * The test function is passed a date and should return true if and only if the specified date should be
     * considered part of the interval.
     * The returned filtered interval does not support interval.count.
     * See also interval.every.
     */
    fun filter(test: (LocalDateTime) -> Boolean): Interval {
        return Interval(
                fun(date: LocalDateTime): LocalDateTime {
                    var newDate = floor(date)
                    while (!test(newDate)) {
                        newDate = floor(newDate + DateTimePeriod(0, 0, 0, 0, 0, 0, -1_000_000))
                    }
                    return newDate
                },
                fun(date: LocalDateTime, step: Int): LocalDateTime {
                    var newStep = step - 1
                    var newDate = date
                    while (newStep >= 0) {
                        newStep--
                        newDate = offset(date, 1)
                        while (!test(newDate)) {
                            newDate = offset(newDate, 1)
                        }
                    }
                    return newDate
                }
        )
    }

    /**
     * Returns a filtered view of this interval representing every stepth date.
     * The meaning of step is dependent on this interval’s parent interval as defined by the field function.
     * For example, timeMinute.every(15) returns an interval representing every fifteen minutes,
     * starting on the hour: :00, :15, :30, :45, etc.
     * Note that for some intervals, the resulting dates may not be uniformly-spaced; timeDay’s parent interval is
     * timeMonth, and thus the interval number resets at the start of each month.
     * If step is not valid, raise exception. If step is one, returns this interval.
     */
    fun every(step: Int): Interval {
        checkNotNull(count, { "The given Count function must not be null." })
        require(step > 0, { " The given Step parameter must be greater than zero." })
        if (step == 1) return this
        return if (field != null) {
            filter { d -> field.invoke(d) % step == 0; }
        } else {
            filter { d -> count(LocalDateTime(1970, 1, 1, 0, 0), d) % step == 0; }
        }
    }

    /**
     * Returns the number of interval boundaries after start (exclusive) and before or equal to end (inclusive).
     * Note that this behavior is slightly different than interval.range because its purpose is to return the
     * zero-based number of the specified end date relative to the specified start date.
     */
    fun count(start: LocalDateTime, stop: LocalDateTime): Int {
        checkNotNull(count, { "The given Count function must not be null." })
        val from = floor(start)
        val to = floor(stop)
        return count.invoke(from, to)
    }
}
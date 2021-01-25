package io.data2viz.scale.intervals

import kotlinx.datetime.*
import kotlin.time.*

internal class Day : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime(d.year, d.monthNumber, d.dayOfMonth, 0, 0, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + step.days,
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.until(end, DateTimeUnit.DAY, this),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).dayOfMonth - 1
)
internal class Hour : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime (d.year, d.monthNumber, d.dayOfMonth, d.hour, 0, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + step.hours,
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.until(end, DateTimeUnit.HOUR, this),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).hour
)
internal class Millisecond : Interval(
    floor = fun TimeZone.(date: Instant): Instant = date,
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + step.milliseconds,
    count = fun TimeZone.(start: Instant, end: Instant): Long = (end - start).toLongMilliseconds(),
    field = fun TimeZone.(date: Instant): Int = date.nanosecondsOfSecond / 1_000_000
)
internal class Minute : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime(d.year, d.monthNumber, d.dayOfMonth, d.hour, d.minute, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + step.minutes,
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.until(end, DateTimeUnit.MINUTE, this),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).minute
)
internal class Month : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime(d.year, d.monthNumber, 1, 0, 0, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant =
        (date.toLocalDateTime(this) + DateTimePeriod(0, step)).toInstant(this),
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.monthsUntil(end, this).toLong(),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).monthNumber - 1
)
internal class Second : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime(d.year, d.monthNumber, d.dayOfMonth, d.hour, d.minute, d.second, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + step.seconds,
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.until(end, DateTimeUnit.SECOND, this),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).second
)
internal class Weekday(day: Int) : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        val weekFlooredDay = d.dayOfMonth - (d.dayOfWeek.ordinal + 7 - day) % 7
        return if (weekFlooredDay < 1)
            (LocalDateTime(d.year, d.month, 1, 0, 0) + DateTimePeriod(0, 0, weekFlooredDay - 1)).toInstant(this)
        else
            LocalDateTime(d.year, d.month, weekFlooredDay, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant = date + (step*7).days,
    count = fun TimeZone.(start: Instant, end: Instant): Long = (end - start).inDays.toLong() / 7
)
internal class Year : Interval(
    floor = fun TimeZone.(date: Instant): Instant {
        val d = date.toLocalDateTime(this)
        return LocalDateTime(d.year, 1, 1, 0, 0, 0, 0).toInstant(this)
    },
    offset = fun TimeZone.(date: Instant, step: Int): Instant =
        (date.toLocalDateTime(this) + DateTimePeriod(step)).toInstant(this),
    count = fun TimeZone.(start: Instant, end: Instant): Long = start.yearsUntil(end, this).toLong(),
    field = fun TimeZone.(date: Instant): Int = date.toLocalDateTime(this).year
)

internal object Intervals {

    internal val timeYear: Year = Year()

    /**
     * Monday-based weeks (e.g., February 6, 2012 at 12:00 AM).
     */
    internal val timeMonday: Weekday = Weekday(0)

    /**
     * Tuesday-based weeks (e.g., February 7, 2012 at 12:00 AM).
     */

    internal val timeTuesday: Weekday = Weekday(1)

    /**
     * Wednesday-based weeks (e.g., February 8, 2012 at 12:00 AM).
     */
    internal val timeWednesday: Weekday = Weekday(2)

    /**
     * Thursday-based weeks (e.g., February 9, 2012 at 12:00 AM).
     */
    internal val timeThursday: Weekday = Weekday(3)

    /**
     * Friday-based weeks (e.g., February 10, 2012 at 12:00 AM).
     */
    internal val timeFriday: Weekday = Weekday(4)

    /**
     * Saturday-based weeks (e.g., February 11, 2012 at 12:00 AM).
     */
    internal val timeSaturday: Weekday = Weekday(5)


    /**
     * Sunday-based weeks (e.g., February 5, 2012 at 12:00 AM).
     */
    internal val timeSunday: Weekday = Weekday(6)
    internal val timeSecond: Second = Second()
    internal val timeMonth: Month = Month()
    internal val timeMinute: Minute = Minute()
    internal val timeMillisecond: Millisecond = Millisecond()
    internal val timeHour: Hour = Hour()
    internal val timeDay: Day = Day()
}



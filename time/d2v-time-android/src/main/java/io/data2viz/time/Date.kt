package io.data2viz.time

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit


//val utc = ZoneOffset.UTC
val UNIX_TIME = date(1970)
val milliToNano = 1000000


actual class Date {

    private var date: LocalDateTime

    actual constructor() {
        date = LocalDateTime.now()
    }

    private constructor(localDate: LocalDateTime) {
        date = localDate
    }

    actual constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
        date = LocalDateTime.of(year, month, day, hour, minute, second, millisecond * milliToNano)
    }

    actual constructor(date: Date) {
        this.date = date.date
    }

    actual override fun toString():String = date.toString()

    actual fun minusMilliseconds(milliseconds: Int): Date {
        return Date(date.minus(milliseconds.toLong(), ChronoUnit.MILLIS))
    }

    actual fun isBefore(otherDate: Date): Boolean = date.isBefore(otherDate.date)

    actual fun millisecondsBetween(otherDate: Date): Long = durationBetween(otherDate).toMillis()
    actual fun daysBetween(otherDate: Date): Long = durationBetween(otherDate).toDays()
    actual fun hoursBetween(otherDate: Date): Long = durationBetween(otherDate).toHours()

    private fun durationBetween(otherDate: Date): Duration {
        return Duration.between(date, otherDate.date)
    }

    actual fun getTimezoneOffset(): Int = 0

    actual fun plusMilliseconds(milliseconds: Long) {
        date = date.plus(milliseconds, ChronoUnit.MILLIS)
    }
//    actual fun plusSeconds(seconds:Long) { date = date.plusSeconds(seconds) }
//    actual fun plusMinutes(minutes:Long) { date = date.plusMinutes(minutes) }
    actual fun plusHours(hours:Long) { date = date.plusHours(hours) }
    actual fun plusDays(days:Long) { date = date.plusDays(days) }
    actual fun plusMonths(months:Long) { date = date.plusMonths(months) }
    actual fun plusYears(years:Long) { date = date.plusYears(years) }

    actual fun setMillisecond(millisecond: Int) {
        date = date.withNano(millisecond * milliToNano)
    }

    actual fun setSecond(second: Int) {
        date = date.withSecond(second)
    }

    actual fun setMinute(minute: Int) {
        date = date.withMinute(minute)
    }

    actual fun setHour(hour: Int) {
        date = date.withHour(hour)
    }

    actual fun setDayOfMonth(day: Int) {
        date = date.withDayOfMonth(day)
    }

    actual fun setMonth(month: Int) {
        date = date.withMonth(month)
    }

    actual fun setFullYear(year: Int) {
        date = date.withYear(year)
    }

    actual fun millisecond(): Int = date.nano / milliToNano
    actual fun second(): Int = date.second
    actual fun minute(): Int = date.minute
    actual fun hour(): Int = date.hour
    actual fun dayOfWeek(): Int = date.dayOfWeek.value
    actual fun dayOfMonth(): Int = date.dayOfMonth
    actual fun dayOfYear(): Int = date.dayOfYear
    actual fun month(): Int = date.monthValue
    actual fun year(): Int = date.year

    actual fun getTime():Double = UNIX_TIME.millisecondsBetween(this).toDouble()
}

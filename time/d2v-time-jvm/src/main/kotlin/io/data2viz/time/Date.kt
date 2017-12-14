package io.data2viz.time

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

val utc = ZoneOffset.UTC

actual fun currentYear(): Int = LocalDateTime.now().year
actual fun currentMonth(): Int = LocalDateTime.now().monthValue
actual fun currentDay(): Int = LocalDateTime.now().dayOfMonth
actual fun currentHour(): Int = LocalDateTime.now().hour
actual fun currentMinute(): Int = LocalDateTime.now().minute
actual fun currentSecond(): Int = LocalDateTime.now().second

actual class Date {

    private var date: LocalDateTime = LocalDateTime.now()

    actual public constructor() {
        date = LocalDateTime.now()
    }

    private constructor(localDate: LocalDateTime) {
        date = localDate
    }

    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
        date = LocalDateTime.of(year, month, day, hour, minute, second, millisecond * 1000)
    }

    actual public constructor(date: Date) {
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

//    actual fun plusSeconds(seconds:Long) { date = date.plusSeconds(seconds) }
//    actual fun plusMinutes(minutes:Long) { date = date.plusMinutes(minutes) }
    actual fun plusHours(hours:Long) { date = date.plusHours(hours) }
    actual fun plusDays(days:Long) { date = date.plusDays(days) }
//    actual fun plusMonths(months:Long) { date = date.plusMonths(months) }
    actual fun plusYears(years:Long) { date = date.plusYears(years) }

    actual fun setMillisecond(millisecond: Int) {
        date = date.withNano(millisecond * 1000)
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

    actual fun millisecond(): Int = date.nano / 1000
    actual fun second(): Int = date.second
    actual fun minute(): Int = date.minute
    actual fun hour(): Int = date.hour
    actual fun dayOfWeek(): Int = date.dayOfWeek.value
    actual fun dayOfMonth(): Int = date.dayOfMonth
    actual fun dayOfYear(): Int = date.dayOfYear
    actual fun month(): Int = date.monthValue
    actual fun year(): Int = date.year


    /*actual public fun getTime(): Long = date.toInstant(utc).toEpochMilli()
    actual public fun getUTCHours(): Int = date.hour

    actual public fun setUTCMinutes(minutes: Int, seconds: Int): Long {
        date = date.withMinute(minutes).withSecond(seconds)
        return getTime()
    }

    actual public fun setUTCHours(hours: Int, minutes: Int?, seconds: Int?): Long {
        date = date.withHour(hours)
        if (minutes != null) {
            date = date.withMinute(minutes)
        }
        if (seconds != null) {
            date = date.withSecond(seconds)
        }
        return getTime()
    }*/
}

//actual class Date actual constructor() {

//    actual public constructor(milliseconds: Number) : this() {
//        innerTime = milliseconds.toLong()
//    }
//    actual public constructor(dateString: String)
//    actual public constructor(year: Int, month: Int)
//    actual public constructor(year: Int, month: Int, day: Int)
//    actual public constructor(year: Int, month: Int, day: Int, hour: Int)
//    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int)
//    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int)
//    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Number)

//    actual public fun getDate(): Long {
//        return innerTime
//    }
//    actual public fun getDay(): Int
//    actual public fun getFullYear(): Int
//    actual public fun getHours(): Int
//    actual public fun getMilliseconds(): Int
//    actual public fun getMinutes(): Int
//    actual public fun getMonth(): Int
//    actual public fun getSeconds(): Int
//    actual public fun getTime(): Double
//    actual public fun getTimezoneOffset(): Int
//    actual public fun getUTCDate(): Int
//    actual public fun getUTCDay(): Int
//    actual public fun getUTCFullYear(): Int
//    actual public fun getUTCHours(): Int
//    actual public fun getUTCMilliseconds(): Int
//    actual public fun getUTCMinutes(): Int
//    actual public fun getUTCMonth(): Int
//    actual public fun getUTCSeconds(): Int
//    actual public fun toDateString(): String
//    actual public fun toISOString(): String

//    public fun toJSON(): Json
//    public fun toLocaleDateString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String
//    public fun toLocaleDateString(locales: String, options: LocaleOptions = definedExternally): String
//    public fun toLocaleString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String
//    public fun toLocaleString(locales: String, options: LocaleOptions = definedExternally): String
//    public fun toLocaleTimeString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String
//    public fun toLocaleTimeString(locales: String, options: LocaleOptions = definedExternally): String

//    actual public fun toTimeString(): String
//    actual public fun toUTCString(): String

/*actual public companion object {
    actual public fun now(): Double
    actual public fun parse(dateString: String): Double
    actual public fun UTC(year: Int, month: Int): Double
    actual public fun UTC(year: Int, month: Int, day: Int): Double
    actual public fun UTC(year: Int, month: Int, day: Int, hour: Int): Double
    actual public fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int): Double
    actual public fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Double
    actual public fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Number): Double
}*/

/*actual public interface LocaleOptions {
    actual public var localeMatcher: String?
    actual public var timeZone: String?
    actual public var hour12: Boolean?
    actual public var formatMatcher: String?
    actual public var weekday: String?
    actual public var era: String?
    actual public var year: String?
    actual public var month: String?
    actual public var day: String?
    actual public var hour: String?
    actual public var minute: String?
    actual public var second: String?
    actual public var timeZoneName: String?
}*/
//}

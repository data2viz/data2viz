package io.data2viz.time

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

val utc = ZoneOffset.UTC

actual class Date {

    var date: LocalDateTime = LocalDateTime.ofInstant(Instant.now(), utc)

    actual public constructor() { date = LocalDateTime.ofInstant(Instant.now(), utc) }
    actual public constructor(milliseconds: Long) { date = LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), utc) }
    actual public constructor(year: Int, month: Int)
    actual public constructor(year: Int, month: Int, day: Int)
    actual public constructor(year: Int, month: Int, day: Int, hour: Int)
    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int)
    actual public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
        date = LocalDateTime.of(year, month, day, hour, minute, second, millisecond * 1000)
    }

    actual public fun getTime(): Long = date.toInstant(utc).toEpochMilli()
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
    }
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

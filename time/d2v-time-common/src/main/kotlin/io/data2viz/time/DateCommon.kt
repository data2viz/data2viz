package io.data2viz.time

val durationSecond = 1000L
val durationMinute = 60000L
val durationHour = 3600000L
val durationDay = 86400000L
val durationWeek = 604800000L        // (day * 7)
val durationMonth = 2592000000L      // (day * 30)
val durationYear = 31536000000L      // (day * 365)

fun date(
        year: Int = 0,
        month: Int = 1,
        day: Int = 1,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0,
        millisecond: Int = 0): Date {
    return Date(year, month, day, hour, minute, second, millisecond)
}

fun date(date: Date): Date {
    return Date(date.year(), date.month(), date.dayOfMonth(), date.hour(), date.minute(), date.second(), date.millisecond())
}

fun date(): Date {
    return Date(currentYear(), currentMonth(), currentDay(), currentHour(), currentMinute(), currentSecond(), 0)
}

fun currentYear(): Int = Date().year()
fun currentMonth():Int = Date().month() 
fun currentDay():Int = Date().dayOfMonth() 
fun currentHour():Int = Date().hour() 
fun currentMinute():Int = Date().minute() 
fun currentSecond():Int = Date().second() 

/**
 * A date-time without a time-zone in the ISO-8601 calendar system,
 * such as {@code 2007-12-03T10:15:30}.
 */
expect class Date {

    constructor()
    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int)
    constructor(date: Date)

    override fun toString(): String

    fun isBefore(otherDate: Date): Boolean
    fun millisecondsBetween(otherDate: Date): Long
    fun daysBetween(otherDate: Date): Long
    fun hoursBetween(otherDate: Date): Long

    fun getTimezoneOffset(): Int

    fun plusMilliseconds(milliseconds: Long)
    //    fun plusSeconds(seconds:Long)
//    fun plusMinutes(minutes:Long)
    fun plusHours(hours: Long)

    fun plusDays(days: Long)
    fun plusMonths(months: Long)
    fun plusYears(years: Long)

    fun minusMilliseconds(milliseconds: Int): Date

    fun setMillisecond(millisecond: Int)
    fun setSecond(second: Int)
    fun setMinute(minute: Int)
    fun setHour(hour: Int)
    fun setDayOfMonth(day: Int)
    fun setMonth(month: Int)
    fun setFullYear(year: Int)

    fun millisecond(): Int
    fun second(): Int
    fun minute(): Int
    fun hour(): Int
    fun dayOfWeek(): Int
    fun dayOfMonth(): Int
    fun dayOfYear(): Int
    fun month(): Int
    fun year(): Int

    /**
     * Returns milliseconds from 1970-1-1 00:00
     */
    fun getTime(): Double

//    operator fun minus(otherDate:Date): Date

//    fun getTime(): Long

//    fun getUTCHours(): Int

//    fun setUTCMinutes(minutes:Int, seconds:Int): Long
//    fun setUTCHours(hours:Int, minutes:Int?, seconds:Int?): Long
}

operator fun Date.compareTo(other: Date): Int {
    val time = getTime()
    val otherTime = other.getTime()

    return when {
        time < otherTime -> -1
        time > otherTime -> 1
        else -> 0
    }
}

/*expect class Date {

    public constructor()
    public constructor(milliseconds: Long)
    public constructor(year: Int, month: Int)
    public constructor(year: Int, month: Int, day: Int)
    public constructor(year: Int, month: Int, day: Int, hour: Int)
    public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int)
    public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int)

    public fun getTime(): Long
    public fun getUTCHours(): Int

    public fun setUTCMinutes(minutes:Int, seconds:Int): Long
    public fun setUTCHours(hours:Int, minutes:Int?, seconds:Int?): Long
}*/

//expect class Date() {

//    public constructor(milliseconds: Number)
//    public constructor(dateString: String)
//    public constructor(year: Int, month: Int)
//    public constructor(year: Int, month: Int, day: Int)
//    public constructor(year: Int, month: Int, day: Int, hour: Int)
//    public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int)
//    public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int)
//    public constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Number)

//    public fun getDate(): Long
/*public fun getDay(): Int
public fun getFullYear(): Int
public fun getHours(): Int
public fun getMilliseconds(): Int
public fun getMinutes(): Int
public fun getMonth(): Int
public fun getSeconds(): Int
public fun getTime(): Double
public fun getTimezoneOffset(): Int
public fun getUTCDate(): Int
public fun getUTCDay(): Int
public fun getUTCFullYear(): Int
public fun getUTCHours(): Int
public fun getUTCMilliseconds(): Int
public fun getUTCMinutes(): Int
public fun getUTCMonth(): Int
public fun getUTCSeconds(): Int
public fun toDateString(): String
public fun toISOString(): String*/

/*public fun toJSON(): Json
public fun toLocaleDateString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String
public fun toLocaleDateString(locales: String, options: LocaleOptions = definedExternally): String
public fun toLocaleString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String
public fun toLocaleString(locales: String, options: LocaleOptions = definedExternally): String
public fun toLocaleTimeString(locales: Array<String> = definedExternally, options: LocaleOptions = definedExternally): String
public fun toLocaleTimeString(locales: String, options: LocaleOptions = definedExternally): String*/

//    public fun toTimeString(): String
//    public fun toUTCString(): String

/*public companion object {
    public fun now(): Double
    public fun parse(dateString: String): Double
    public fun UTC(year: Int, month: Int): Double
    public fun UTC(year: Int, month: Int, day: Int): Double
    public fun UTC(year: Int, month: Int, day: Int, hour: Int): Double
    public fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int): Double
    public fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Double
    public fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Number): Double
}*/

/*public interface LocaleOptions {
    public var localeMatcher: String?
    public var timeZone: String?
    public var hour12: Boolean?
    public var formatMatcher: String?
    public var weekday: String?
    public var era: String?
    public var year: String?
    public var month: String?
    public var day: String?
    public var hour: String?
    public var minute: String?
    public var second: String?
    public var timeZoneName: String?
}*/
//}
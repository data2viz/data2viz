package io.data2viz.time

typealias JsDate = io.data2viz.time.js.Date

val durationSecond = 1000
val durationMinute = 60000
val durationHour = 3600000
val durationDay = 86400000
val durationWeek = 604800000

actual fun currentYear(): Int = JsDate().getFullYear()
actual fun currentMonth(): Int = JsDate().getMonth() + 1
actual fun currentDay(): Int = JsDate().getDay()
actual fun currentHour(): Int = JsDate().getHours()
actual fun currentMinute(): Int = JsDate().getMinutes()
actual fun currentSecond(): Int = JsDate().getSeconds()

actual class Date {

    private var date: JsDate = JsDate()

    actual constructor() {
        date = JsDate()
    }

    actual constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
        date = JsDate(year, month - 1, day, hour, minute, second, millisecond)
    }

    private constructor(date: JsDate) {
        this.date = JsDate(date.getTime())
    }

    actual constructor(date: Date) {
        this.date = JsDate(date.date.getTime())
    }

    actual override fun toString(): String = date.toString()

    actual fun minusMilliseconds(milliseconds: Int): Date {
        return Date(JsDate(date.getTime() - 1))
    }

    actual fun isBefore(otherDate: Date): Boolean {
        return date.getTime() < otherDate.date.getTime()
    }

    actual fun millisecondsBetween(otherDate: Date): Long {
        return (otherDate.date.getTime() - date.getTime()).toLong()
    }

    actual fun daysBetween(otherDate: Date): Long {
        return (millisecondsBetween(otherDate) - ((otherDate.getTimezoneOffset() - date.getTimezoneOffset()) * durationMinute)) / durationDay
    }

    actual fun hoursBetween(otherDate: Date): Long {
        return (millisecondsBetween(otherDate) - ((otherDate.getTimezoneOffset() - date.getTimezoneOffset()) * durationMinute)) / durationHour
    }

    actual fun getTimezoneOffset(): Int = date.getTimezoneOffset()

    //    actual fun plusSeconds(seconds:Long)
//    actual fun plusMinutes(minutes:Long)
    actual fun plusHours(hours: Long) {
        date = JsDate(date.getTime() + (hours * durationHour))
    }

    actual fun plusDays(days: Long) {
        date = JsDate(date.getTime() + (days * durationDay))
    }
//    actual fun plusMonths(months:Long)
    actual fun plusYears(years:Long) {
        date.setFullYear(date.getFullYear() + years.toInt())
    }

    actual fun setMillisecond(millisecond: Int) {
        date.setMilliseconds(millisecond)
    }

    actual fun setSecond(second: Int) {
        date.setSeconds(second)
    }

    actual fun setMinute(minute: Int) {
        date.setMinutes(minute)
    }

    actual fun setHour(hour: Int) {
        date.setHours(hour)
    }
    actual fun setDayOfMonth(day:Int) {
        date.setDate(day)
    }
    actual fun setMonth(month:Int) {
        date.setMonth(month - 1)
    }
    actual fun setFullYear(year:Int) {
        date.setFullYear(year)
    }

    actual fun millisecond(): Int = date.getMilliseconds()
    actual fun second(): Int = date.getSeconds()
    actual fun minute(): Int = date.getMinutes()
    actual fun hour(): Int = date.getHours()
    actual fun dayOfWeek(): Int = date.getDay()
    actual fun dayOfMonth(): Int = date.getDate()
    actual fun dayOfYear(): Int = 1 + timeDay.count(timeYear.floor(this), this)
    actual fun month(): Int = date.getMonth() + 1
    actual fun year(): Int = date.getFullYear()

//    actual operator fun minus(otherDate:Date): Date

//    actual public fun getTime(): Long
//    actual public fun getUTCHours(): Int

//    actual public fun setUTCMinutes(minutes:Int, seconds:Int): Long
//    actual public fun setUTCHours(hours:Int, minutes:Int?, seconds:Int?): Long
}

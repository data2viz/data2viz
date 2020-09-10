package io.data2viz.time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

@Deprecated("Use kotlinx.datetime.LocalDateTime instead")
typealias Date = LocalDateTime

@Deprecated("Use kotlinx.datetime.LocalDateTime instead")
fun date(year: Int = 0, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0, millisecond: Int = 0): Date = Date(year, month, day, hour, minute, second, millisecond * 1_000_000)

@Deprecated("Use kotlinx.datetime.LocalDateTime instead")
fun date(date: Date): Date = Date(date.year, date.month, date.dayOfMonth, date.hour, date.minute, date.second, date.nanosecond / 1_000_000)

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone instead")
fun date(): Date = Clock.System.now().toLocalDateTime(defaultTZ)

@Deprecated("Use kotlinx.datetime.LocalDateTime.nanosecond instead")
fun Date.millisecond(): Int = nanosecond / 1_000_000

@Deprecated("Use kotlinx.datetime.LocalDateTime.second instead")
fun Date.second(): Int = second

@Deprecated("Use kotlinx.datetime.LocalDateTime.minute instead")
fun Date.minute(): Int = minute

@Deprecated("Use kotlinx.datetime.LocalDateTime.hour instead")
fun Date.hour(): Int = hour

@Deprecated("Use kotlinx.datetime.LocalDateTime.dayOfWeek instead")
fun Date.dayOfWeek(): Int = dayOfWeek.ordinal

@Deprecated("Use kotlinx.datetime.LocalDateTime.dayOfMonth instead")
fun Date.dayOfMonth(): Int = dayOfMonth

@Deprecated("Use kotlinx.datetime.LocalDateTime.dayOfYear instead")
fun Date.dayOfYear(): Int = dayOfYear

@Deprecated("Use kotlinx.datetime.LocalDateTime.monthNumber instead")
fun Date.month(): Int = monthNumber

@Deprecated("Use kotlinx.datetime.LocalDateTime.year instead")
fun Date.year(): Int = year



//fun currentYear(): Int = Date().year()
//fun currentMonth():Int = Date().month()
//fun currentDay():Int = Date().dayOfMonth()
//fun currentHour():Int = Date().hour()
//fun currentMinute():Int = Date().minute()
//fun currentSecond():Int = Date().second()
//
///**
// * A date-time without a time-zone in the ISO-8601 calendar system,
// * such as {@code 2007-12-03T10:15:30}.
// */
//expect class Date : Comparable<Date> {
//
//    constructor()
//    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int)
//    constructor(date: Date)
//
////    override fun toString(): String
////
////    fun isBefore(otherDate: Date): Boolean
////    fun millisecondsBetween(otherDate: Date): Long
////    fun daysBetween(otherDate: Date): Long
////    fun hoursBetween(otherDate: Date): Long
////
////    fun getTimezoneOffset(): Int
////
////    fun plusMilliseconds(milliseconds: Long)
////    //    fun plusSeconds(seconds:Long)
//////    fun plusMinutes(minutes:Long)
////    fun plusHours(hours: Long)
////
////    fun plusDays(days: Long)
////    fun plusMonths(months: Long)
////    fun plusYears(years: Long)
////
////    fun minusMilliseconds(milliseconds: Int): Date
////
////    fun setMillisecond(millisecond: Int)
////    fun setSecond(second: Int)
////    fun setMinute(minute: Int)
////    fun setHour(hour: Int)
////    fun setDayOfMonth(day: Int)
////    fun setMonth(month: Int)
////    fun setFullYear(year: Int)
////
////    fun millisecond(): Int
////    fun second(): Int
////    fun minute(): Int
////    fun hour(): Int
////    fun dayOfWeek(): Int
////    fun dayOfMonth(): Int
////    fun dayOfYear(): Int
////    fun month(): Int
////    fun year(): Int
////
////    /**
////     * Returns milliseconds from 1970-1-1 00:00
////     */
////    fun getTime(): Double
package io.data2viz.time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

@Deprecated("Use kotlinx.datetime.LocalDateTime instead")
public typealias Date = LocalDateTime

@Deprecated("Use kotlinx.datetime.LocalDateTime instead")
public fun date(year: Int = 0, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0, millisecond: Int = 0): Date = Date(year, month, day, hour, minute, second, millisecond * 1_000_000)

@Deprecated("Use kotlinx.datetime.LocalDateTime instead")
public fun date(date: Date): Date = Date(date.year, date.month, date.dayOfMonth, date.hour, date.minute, date.second, date.nanosecond / 1_000_000)

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone instead")
public fun date(): Date = Clock.System.now().toLocalDateTime(defaultTZ)

@Deprecated("Use kotlinx.datetime.LocalDateTime.nanosecond instead")
public fun Date.millisecond(): Int = nanosecond / 1_000_000

@Deprecated("Use kotlinx.datetime.LocalDateTime.second instead")
public fun Date.second(): Int = second

@Deprecated("Use kotlinx.datetime.LocalDateTime.minute instead")
public fun Date.minute(): Int = minute

@Deprecated("Use kotlinx.datetime.LocalDateTime.hour instead")
public fun Date.hour(): Int = hour

@Deprecated("Use kotlinx.datetime.LocalDateTime.dayOfWeek instead")
public fun Date.dayOfWeek(): Int = dayOfWeek.ordinal

@Deprecated("Use kotlinx.datetime.LocalDateTime.dayOfMonth instead")
public fun Date.dayOfMonth(): Int = dayOfMonth

@Deprecated("Use kotlinx.datetime.LocalDateTime.dayOfYear instead")
public fun Date.dayOfYear(): Int = dayOfYear

@Deprecated("Use kotlinx.datetime.LocalDateTime.monthNumber instead")
public fun Date.month(): Int = monthNumber

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.year property instead")
public fun Date.year(): Int = year

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.year property instead")
public fun currentYear(): Int = date().year

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.monthNumber property instead")
public fun currentMonth():Int = date().monthNumber

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.dayOfMonth property instead")
public fun currentDay():Int = date().dayOfMonth

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.hour property instead")
public fun currentHour():Int = date().hour

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.minute property instead")
public fun currentMinute():Int = date().minute

@Deprecated("Use kotlinx.datetime.Clock with your TimeZone and LocalDateTime.second property instead")
public fun currentSecond():Int = date().second

@Deprecated("Use the Comparable kotlinx.datetime.LocalDateTime class")
public fun Date.isBefore(otherDate: Date): Boolean = this < otherDate

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
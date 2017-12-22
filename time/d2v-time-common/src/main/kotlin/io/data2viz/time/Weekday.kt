package io.data2viz.time

class Weekday(day: Int) : Interval(
        fun(date: Date): Date {
            val dayofMonth = (date.dayOfMonth() - (date.dayOfWeek() + 7 - day) % 7) + 1
            if (dayofMonth >= 1) {
                date.setDayOfMonth(dayofMonth)
            } else {
                date.plusDays(dayofMonth.toLong() - 2)
            }
            date.setHour(0)
            date.setMinute(0)
            date.setSecond(0)
            date.setMillisecond(0)
            return date
        },
        fun(date: Date, step: Long): Date {
            date.plusDays(7 * step)
            return date
        },
        fun(start: Date, end: Date): Int {
            return start.daysBetween(end).toInt() / 7
        }
)

// TODO TESTS seems to be a bug as timeSunday returns "mondays"

// The value follows the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
val timeMonday = Weekday(1)
val timeTuesday = Weekday(2)
val timeWednesday = Weekday(3)
val timeThursday = Weekday(4)
val timeFriday = Weekday(5)
val timeSaturday = Weekday(6)
val timeSunday = Weekday(7)

package io.data2viz.time

class Day : Interval(
        fun (date:Date): Date {
            date.setHour(0)
            date.setMinute(0)
            date.setSecond(0)
            date.setMillisecond(0)
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusDays(step)
            return date
        },
        fun (start:Date, end:Date): Int {
            return start.daysBetween(end).toInt()
        },
        fun (date:Date): Int {
            return date.dayOfMonth() - 1
        }
)

val timeDay = Day()
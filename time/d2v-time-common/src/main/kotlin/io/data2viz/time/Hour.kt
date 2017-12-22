package io.data2viz.time

class Hour : Interval(
        fun (date:Date): Date {
            date.setMinute(0)
            date.setSecond(0)
            date.setMillisecond(0)
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusHours(step)
            return date
        },
        fun (start:Date, end:Date): Int {
            return start.hoursBetween(end).toInt()
        },
        fun (date:Date): Int {
            return date.hour()
        }
)

val timeHour = Hour()
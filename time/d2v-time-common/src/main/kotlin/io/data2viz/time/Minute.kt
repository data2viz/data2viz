package io.data2viz.time

class Minute : Interval(
        fun (date:Date): Date {
            date.setSecond(0)
            date.setMillisecond(0)
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusMilliseconds(step * durationMinute)
            return date
        },
        fun (start:Date, end:Date): Int {
            return (start.millisecondsBetween(end) / durationMinute).toInt()
        },
        fun (date:Date): Int {
            return date.minute()
        }
)

val timeMinute = Minute()
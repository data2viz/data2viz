package io.data2viz.time

class Second : Interval(
        fun (date:Date): Date {
            date.setMillisecond(0)
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusMilliseconds(step * durationSecond)
            return date
        },
        fun (start:Date, end:Date): Int {
            return (start.millisecondsBetween(end) / durationSecond).toInt()
        },
        fun (date:Date): Int {
            return date.second()
        }
)

val timeSecond = Second()
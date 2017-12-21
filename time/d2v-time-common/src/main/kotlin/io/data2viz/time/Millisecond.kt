package io.data2viz.time

class Millisecond : Interval(
        fun (date:Date): Date {
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusMilliseconds(step)
            return date
        },
        fun (start:Date, end:Date): Int {
            return (start.millisecondsBetween(end)).toInt()
        },
        fun (date:Date): Date {
            // TODO implement
            return date
        }
)

val timeMillisecond = Millisecond()
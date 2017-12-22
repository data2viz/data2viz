package io.data2viz.time

class Year : Interval(
        fun (date:Date): Date {
            date.setMonth(1)
            date.setDayOfMonth(1)
            date.setHour(0)
            date.setMinute(0)
            date.setSecond(0)
            date.setMillisecond(0)
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusYears(step)
            return date
        },
        fun (start:Date, end:Date): Int {
            return end.year() - start.year()
        },
        fun (date:Date): Int {
            return date.year()
        }
)


val timeYear = Year()
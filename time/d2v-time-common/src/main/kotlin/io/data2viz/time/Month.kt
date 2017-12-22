package io.data2viz.time

class Month : Interval(
        fun (date:Date): Date {
            date.setDayOfMonth(1)
            date.setHour(0)
            date.setMinute(0)
            date.setSecond(0)
            date.setMillisecond(0)
            return date
        },
        fun (date:Date, step:Long): Date {
            date.plusMonths(step)
            return date
        },
        fun (start:Date, end:Date): Int {
            return (end.year() - start.year()) * 12 + (end.month() - start.month())
        },
        fun (date:Date): Int {
            return date.month() - 1
        }
)


val timeMonth = Month()
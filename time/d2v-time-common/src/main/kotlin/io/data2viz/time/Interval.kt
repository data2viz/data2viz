package io.data2viz.time

//var t0 = Date()
//var t1 = Date()

class Interval(val floori: (Date) -> Date, val offseti: (Date, Int) -> Date, val count: Int? = null, val field: Int? = null) {

    /**
     * Alias for interval.floor. For example, d2v.timeYear(date) and d2v.timeYear.floor(date) are equivalent.
     */
    fun interval(date: Date): Date {
        return floori(date)
    }

    /**
     * Returns a new date representing the latest interval boundary date before or equal to date.
     * For example, d2v.timeDay.floor(date) typically returns 12:00 AM local time on the given date.
     * This method is idempotent: if the specified date is already floored to the current interval, a new date
     * with an identical time is returned.
     * Furthermore, the returned date is the minimum expressible value of the associated interval,
     * such that interval.floor(interval.floor(date) - 1) returns the preceeding interval boundary date.
     */
    fun floor(date: Date): Date = interval(date)

    /**
     * Returns a new date representing the earliest interval boundary date after or equal to date.
     * For example, d2v.timeDay.ceil(date) typically returns 12:00 AM local time on the date following the given date.
     * This method is idempotent: if the specified date is already ceilinged to the current interval,
     * a new date with an identical time is returned.
     * Furthermore, the returned date is the maximum expressible value of the associated interval,
     * such that interval.ceil(interval.ceil(date) + 1) returns the following interval boundary date.
     */
    fun ceil(date: Date): Date {
        var newDate = Date(date.getTime() - 1)
        newDate = floori(newDate)
        newDate = offseti(newDate, 1)
        newDate = floori(newDate)
        return newDate
    }

    /**
     * Returns a new date representing the closest interval boundary date to date.
     * For example, d2v.timeDay.round(date) typically returns 12:00 AM local time on the given date if it
     * is on or before noon, and 12:00 AM of the following day if it is after noon.
     * This method is idempotent: if the specified date is already rounded to the current interval,
     * a new date with an identical time is returned.
     */
    fun round(date: Date): Date {
        val d0 = interval(date)
        val d1 = ceil(date)
        return if ((date.getTime() - d0.getTime()) < (d1.getTime() - date.getTime())) d0 else d1
    }

    /**
     * Returns a new date equal to date plus step intervals.
     * If step is not specified it defaults to 1.
     * If step is negative, then the returned date will be before the specified date;
     * if step is zero, then a copy of the specified date is returned.
     * This method does not round the specified date to the interval.
     * For example, if date is today at 5:34 PM, then d2v.timeDay.offset(date, 1) returns 5:34 PM tomorrow
     * (even if daylight saving changes!).
     */
    fun offset(date: Date, step: Int = 1): Date {
        return offseti(Date(date.getTime()), step)
    }

    /**
     * Returns every an array of dates representing every interval boundary after or equal to start (inclusive)
     * and before stop (exclusive).
     * If step is specified > 1 then every stepth boundary will be returned; for example, for the d2v.timeDay
     * interval a step of 2 will return every other day.
     * The first date in the returned array is the earliest boundary after or equal to start; subsequent dates are
     * offset by step intervals and floored.
     * Thus, two overlapping ranges may be consistent.
     */
    fun range(start: Date, stop: Date, step: Int = 1): ArrayList<Date> {
        val range = arrayListOf<Date>()
        var current = ceil(start)
        if (step > 0) {
            while (current.getTime() < stop.getTime()) {
                current = floori(offseti(current, step))
                range.add(current)
            }
        }
        return range
    }

    /**
     * Returns a new interval that is a filtered subset of this interval using the specified test function.
     * The test function is passed a date and should return true if and only if the specified date should be
     * considered part of the interval.
     * The returned filtered interval does not support interval.count. See also interval.every.
     */
    /*fun filter(test:(Date)->Boolean) {
        Interval()
    }*/
}
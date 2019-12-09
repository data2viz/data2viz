/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.time

typealias JsDate = io.data2viz.time.js.Date

actual class Date {

    private var date: JsDate

    actual constructor() {
        date = JsDate()
    }

    actual constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
        if (year in 0..99) {
            date = JsDate(-1, month - 1, day, hour, minute, second, millisecond)
            date.setFullYear(year)
        } else date = JsDate(year, month - 1, day, hour, minute, second, millisecond)
    }

    private constructor(date: JsDate) {
        this.date = JsDate(date.getTime())
    }

    actual constructor(date: Date) {
        this.date = JsDate(date.date.getTime())
    }

    actual override fun toString(): String = date.toString()

    actual fun minusMilliseconds(milliseconds: Int): Date {
        return Date(JsDate(date.getTime() - 1))
    }

    actual fun isBefore(otherDate: Date): Boolean {
        return date.getTime() < otherDate.date.getTime()
    }

    actual fun millisecondsBetween(otherDate: Date): Long {
        return (otherDate.date.getTime() - date.getTime()).toLong()
    }

    actual fun daysBetween(otherDate: Date): Long {
        return (millisecondsBetween(otherDate) - ((otherDate.getTimezoneOffset() - date.getTimezoneOffset()) * durationMinute)) / durationDay
    }

    actual fun hoursBetween(otherDate: Date): Long {
        return (millisecondsBetween(otherDate) - ((otherDate.getTimezoneOffset() - date.getTimezoneOffset()) * durationMinute)) / durationHour
    }

    actual fun getTimezoneOffset(): Int = date.getTimezoneOffset()

    actual fun plusMilliseconds(milliseconds: Long) {
        date = JsDate(date.getTime() + milliseconds)
    }

    //    actual fun plusSeconds(seconds:Long)
//    actual fun plusMinutes(minutes:Long)
    actual fun plusHours(hours: Long) {
        date = JsDate(date.getTime() + (hours * durationHour))
    }

    actual fun plusDays(days: Long) {
        date = JsDate(date.getTime() + (days * durationDay))
    }

    actual fun plusMonths(months: Long) {
        val m = ((date.getMonth() + months) % 12).toInt()
        val y = ((date.getMonth() + months) / 12).toInt()
        date.setFullYear(date.getFullYear() + y)
        date.setMonth(m)
    }

    actual fun plusYears(years: Long) {
        date.setFullYear(date.getFullYear() + years.toInt())
    }

    actual fun setMillisecond(millisecond: Int) {
        date.setMilliseconds(millisecond)
    }

    actual fun setSecond(second: Int) {
        date.setSeconds(second)
    }

    actual fun setMinute(minute: Int) {
        date.setMinutes(minute)
    }

    actual fun setHour(hour: Int) {
        date.setHours(hour)
    }

    actual fun setDayOfMonth(day: Int) {
        date.setDate(day)
    }

    actual fun setMonth(month: Int) {
        date.setMonth(month - 1)
    }

    actual fun setFullYear(year: Int) {
        date.setFullYear(year)
    }

    actual fun millisecond(): Int = date.getMilliseconds()
    actual fun second(): Int = date.getSeconds()
    actual fun minute(): Int = date.getMinutes()
    actual fun hour(): Int = date.getHours()
    actual fun dayOfWeek(): Int = date.getDay()
    actual fun dayOfMonth(): Int = date.getDate()
    actual fun dayOfYear(): Int = 1 + timeDay.count(timeYear.floor(this), this)
    actual fun month(): Int = date.getMonth() + 1
    actual fun year(): Int = date.getFullYear()

    actual fun getTime(): Double = date.getTime()

//    actual operator fun minus(otherDate:Date): Date

//    actual public fun getTime(): Long
//    actual public fun getUTCHours(): Int

//    actual public fun setUTCMinutes(minutes:Int, seconds:Int): Long
//    actual public fun setUTCHours(hours:Int, minutes:Int?, seconds:Int?): Long
}

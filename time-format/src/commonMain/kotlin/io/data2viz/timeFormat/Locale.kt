/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.timeFormat

import kotlinx.datetime.*
import kotlin.math.abs
import kotlin.time.Duration.Companion.days

public data class ParseDate(
        var year: Int? = null,
        var month: Int? = null,
        var day: Int? = null,
        var hour: Int? = null,
        var minute: Int? = null,
        var second: Int? = null,
        var millisecond: Int? = null,
        var period: Int? = null,
        var weekDay: Int? = null,
        var weekNumberMonday: Int? = null,
        var weekNumberSunday: Int? = null,
        var zone: Int? = null
)

private fun date(d: ParseDate): Instant {
    var date = LocalDateTime(d.year ?: 0, d.month ?: 1, 1, d.hour ?: 0, d.minute ?: 0, d.second ?: 0, d.millisecond ?: 0).toInstant(TimeZone.UTC)

    // add days (cause day value may be a number of days <= 0 or > 31)
//    if (d.day != null) {
//        date = if (d.day!! > 1) date + (DateTimeUnit.HOUR * 24 * (d.day!! - 1)).duration
//        else if (d.day!! <= 0) date - (DateTimeUnit.HOUR * 24 * -(d.day!! - 1)).duration
//        else date
//    }
    if (d.day != null) {
        date += (d.day!! - 1).days
    }

    return date
}

/*var utcFormats = {
    "a": formatUTCShortWeekday,
    "A": formatUTCWeekday,
    "b": formatUTCShortMonth,
    "B": formatUTCMonth,
    "c": null,
    "d": formatUTCDayOfMonth,
    "e": formatUTCDayOfMonth,
    "H": formatUTCHour24,
    "I": formatUTCHour12,
    "j": formatUTCDayOfYear,
    "L": formatUTCMilliseconds,
    "m": formatUTCMonthNumber,
    "M": formatUTCMinutes,
    "p": formatUTCPeriod,
    "S": formatUTCSeconds,
    "U": formatUTCWeekNumberSunday,
    "w": formatUTCWeekdayNumber,
    "W": formatUTCWeekNumberMonday,
    "x": null,
    "X": null,
    "y": formatUTCYear,
    "Y": formatUTCFullYear,
    "Z": formatUTCZone,
    "%": formatLiteralPercent
}*/

public val defaultLocale: Locale = Locale()

public fun autoFormat(): TimeZone.(Instant) -> String = defaultLocale.autoFormat()

public fun format(specifier: String): (Instant) -> String = defaultLocale.format(specifier)

public fun parse(specifier: String): (String) -> Instant? = defaultLocale.parse(specifier)

public class Locale(timeLocale: TimeLocale = Locales.defaultTimeLocale()) {
    public val locale_dateTime: String = timeLocale.dateTime
    public val locale_date: String = timeLocale.date
    public val locale_time: String = timeLocale.time
    public val locale_periods: List<String> = timeLocale.periods
    public val locale_weekdays: List<String> = timeLocale.days
    public val locale_shortWeekdays: List<String> = timeLocale.shortDays
    public val locale_months: List<String> = timeLocale.months
    public val locale_shortMonths: List<String> = timeLocale.shortMonths
    public val periodRe: Regex = formatRe(locale_periods)
    public val periodLookup: Map<String, Int> = formatLookup(locale_periods)
    public val weekdayRe: Regex = formatRe(locale_weekdays)
    public val weekdayLookup: Map<String, Int> = formatLookup(locale_weekdays)
    public val shortWeekdayRe: Regex = formatRe(locale_shortWeekdays)
    public val shortWeekdayLookup: Map<String, Int> = formatLookup(locale_shortWeekdays)
    public val monthRe: Regex = formatRe(locale_months)
    public val monthLookup: Map<String, Int> = formatLookup(locale_months)
    public val shortMonthRe: Regex = formatRe(locale_shortMonths)
    public val shortMonthLookup: Map<String, Int> = formatLookup(locale_shortMonths)
    public val formats: MutableMap<Char, ((Instant, String) -> String)?> = mutableMapOf(
            Pair('a', ::formatShortWeekday),
            Pair('A', ::formatWeekday),
            Pair('b', ::formatShortMonth),
            Pair('B', ::formatMonth),
            Pair('c', null),
            Pair('d', ::formatDayOfMonth),
            Pair('e', ::formatDayOfMonth),
            Pair('H', ::formatHour24),
            Pair('I', ::formatHour12),
            Pair('j', ::formatDayOfYear),
            Pair('L', ::formatMilliseconds),
            Pair('m', ::formatMonthNumber),
            Pair('M', ::formatMinutes),
            Pair('p', ::formatPeriod),
            Pair('S', ::formatSeconds),
//            Pair('U', ::formatWeekNumberSunday),
            Pair('w', ::formatWeekdayNumber),
//            Pair('W', ::formatWeekNumberMonday),
            Pair('x', null),
            Pair('X', null),
            Pair('y', ::formatYear),
            Pair('Y', ::formatFullYear),
            Pair('Z', ::formatZone),
            Pair('%', ::formatLiteralPercent)
    )

    public val parses: MutableMap<Char, ((ParseDate, String, Int) -> Int)?> = mutableMapOf<Char, ((ParseDate, String, Int) -> Int)?>(
            Pair('a', ::parseShortWeekday),
            Pair('A', ::parseWeekday),
            Pair('b', ::parseShortMonth),
            Pair('B', ::parseMonth),
            Pair('c', ::parseLocaleDateTime),
            Pair('d', ::parseDayOfMonth),
            Pair('e', ::parseDayOfMonth),
            Pair('H', ::parseHour24),
            Pair('I', ::parseHour24),
            Pair('j', ::parseDayOfYear),
            Pair('L', ::parseMilliseconds),
            Pair('m', ::parseMonthNumber),
            Pair('M', ::parseMinutes),
            Pair('p', ::parsePeriod),
            Pair('S', ::parseSeconds),
            Pair('U', ::parseWeekNumberSunday),
            Pair('w', ::parseWeekdayNumber),
            Pair('W', ::parseWeekNumberMonday),
            Pair('x', ::parseLocaleDate),
            Pair('X', ::parseLocaleTime),
            Pair('y', ::parseYear),
            Pair('Y', ::parseFullYear),
//            Pair('Z', ::parseZone),
            Pair('%', ::parseLiteralPercent)
    )

    public val dateTimeFormat: (Instant) -> String = format(locale_dateTime)
    public val dateFormat: (Instant) -> String = format(locale_date)
    public val timeFormat: (Instant) -> String = format(locale_time)

    init {
        formats['c'] = fun(date: Instant, _: String): String { return dateTimeFormat(date) }
        formats['x'] = fun(date: Instant, _: String): String { return dateFormat(date) }
        formats['X'] = fun(date: Instant, _: String): String { return timeFormat(date) }
    }

    public fun autoFormat(): TimeZone.(Instant) -> String {
        val formatMillisecond = format(".%L")
        val formatSecond = format(":%S")
        val formatMinute = format("%H:%M")
        val formatHour = format("%I %p")
        val formatDay = format("%a %d")
        val formatWeek = format("%b %d")
        val formatMonth = format("%B")
        val formatYear = format("%Y")

        return fun TimeZone.(date: Instant): String {
            val ldt = date.toLocalDateTime()
            return if (ldt.nanosecond > 1_000_000) formatMillisecond(date)
            else if (ldt.second > 0) formatSecond(date)
            else if (ldt.minute > 0) formatMinute(date)
            else if (ldt.hour > 0) formatHour(date)
            else if (ldt.dayOfMonth > 1) formatWeek(date)
            else if (ldt.monthNumber > 1) formatMonth(date)
            else formatYear(date)
        }
    }

    public fun format(specifier: String): (Instant) -> String {
        return fun(date: Instant): String {
            val string = mutableListOf<String>()
            var i = 0
            var j = 0

            while (i < specifier.length) {
                if (specifier.get(i) == '%') {
                    string.add(specifier.substring(j, i))
                    i++
                    var c = specifier.get(i)
                    var pad = pads[c]
                    if (pad != null) {
                        i++
                        c = specifier.get(i)
                    } else {
                        pad = if (c == 'e') " " else "0"
                    }
                    val format = formats[c]
                    if (format != null) {
                        string.add(format(date, pad))
                    } else {
                        string.add(c.toString())
                    }
                    j = i + 1
                }
                i++
            }

            string.add(specifier.substring(j, i))
            return string.joinToString("")
        }
    }

    public fun parse(specifier: String): (String) -> Instant? {
        return fun(dateString: String): Instant? {
            val d = newYear(1900)
            val i = parseSpecifier(d, specifier, dateString, 0)
            if (i != dateString.length) return null

            // The am-pm flag is 0 for AM, and 1 for PM.
            if (d.period != null) {
                if (d.hour == null) d.hour = 0
                d.hour = d.hour!! % 12 + (d.period!! * 12)
            }

            // Convert day-of-week and week-of-year to day-of-year.
            // TODO change this to avoid managing it in date(parseDate) (days <= 0 or > 31...)
            if (d.weekNumberMonday != null || d.weekNumberSunday != null) {
                val preValue = if (d.weekNumberMonday != null) 1 else 0
                if (d.weekDay == null) d.weekDay = preValue
                val day = if (d.zone != null) {
                    0
                } else {
                    date(newYear(d.year)).toLocalDateTime(TimeZone.UTC).dayOfWeek.ordinal + 1
                }
                d.month = 1
                d.day = if (d.weekNumberMonday != null) {
                    (d.weekDay!! + 6) % 7 + d.weekNumberMonday!! * 7 - (day + 5) % 7
                } else {
                    d.weekDay!! + d.weekNumberSunday!! * 7 - (day + 6) % 7
                }
            }

            // If a time zone is specified, all fields are interpreted as UTC and then
            // offset according to the specified time zone.
            /*if ("Z" in d) {
                d.hour += d.zone / 100 | 0
                d.minute += d.zone % 100
                return utcDate(d)
            }*/

            // Otherwise, all fields are in local time.
            return date(d)
        }
    }
/*
    function parseSpecifier(d, specifier, string, j) {
        var i = 0,
        n = specifier.length,
        m = string.length,
        c,
        parse;

        while (i < n) {
            if (j >= m) return -1;
            c = specifier.charCodeAt(i++);
            if (c === 37) {
                c = specifier.charAt(i++);
                parse = parses[c in pads ? specifier.charAt(i++) : c];
                if (!parse || ((j = parse(d, string, j)) < 0)) return -1;
            } else if (c != string.charCodeAt(j++)) {
                return -1;
            }
        }

        return j;
    }*/

    private fun parseSpecifier(d: ParseDate, specifier: String, dateString: String, index: Int): Int {
        var i = 0
        var newIndex = index

        while (i < specifier.length) {
            if (newIndex >= dateString.length) return -1
            var c = specifier[i]
            i++
            if (c == '%') {
                c = specifier[i]
                i++
                if (c in pads) {
                    c = specifier[i]
                    i++
                }
                val parse = parses[c]
                if (parse == null) return -1
                val parsedIndex = parse(d, dateString, newIndex)
                if (parsedIndex < 0) return -1
                newIndex = parse(d, dateString, newIndex)
            } else {
                if (c != dateString[newIndex]) {
                    return -1
                }
                newIndex++
            }
        }

        return newIndex
    }

    public fun parsePeriod(d: ParseDate, string: String, i: Int): Int {
        val n = periodRe.find(string.substring(i))
        return if (n != null) {
            val period = periodLookup[n.groupValues[0].filter { it != ' ' }.lowercase()]
            d.period = if (period != null) period else 0
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseShortWeekday(d: ParseDate, string: String, i: Int): Int {
        val n = shortWeekdayRe.find(string.substring(i))
        return if (n != null) {
            val weekDay = shortWeekdayLookup[n.groupValues[0].filter { it != ' ' }.lowercase()]
            d.weekDay = if (weekDay != null) weekDay else 0
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseWeekday(d: ParseDate, string: String, i: Int): Int {
        val n = weekdayRe.find(string.substring(i))
        return if (n != null) {
            val weekDay = weekdayLookup[n.groupValues[0].filter { it != ' ' }.lowercase()]
            d.weekDay = if (weekDay != null) weekDay else 0
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseShortMonth(d: ParseDate, string: String, i: Int): Int {
        val n = shortMonthRe.find(string.substring(i))
        return if (n != null) {
            val month = shortMonthLookup[n.groupValues[0].filter { it != ' ' }.lowercase()]
            d.month = if (month != null) month + 1 else 0
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseMonth(d: ParseDate, string: String, i: Int): Int {
        val n = monthRe.find(string.substring(i))
        return if (n != null) {
            val month = monthLookup[n.groupValues[0].filter { it != ' ' }.lowercase()]
            d.month = if (month != null) month + 1 else 0
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseLocaleDateTime(d: ParseDate, string: String, i: Int): Int {
        return parseSpecifier(d, locale_dateTime, string, i)
    }

    public fun parseLocaleDate(d: ParseDate, string: String, i: Int): Int {
        return parseSpecifier(d, locale_date, string, i)
    }

    public fun parseLocaleTime(d: ParseDate, string: String, i: Int): Int {
        return parseSpecifier(d, locale_time, string, i)
    }

    public fun parseWeekdayNumber(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 1))
        return if (n != null) {
            d.weekDay = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseWeekNumberSunday(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i))
        return if (n != null) {
            d.weekNumberSunday = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseWeekNumberMonday(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i))
        return if (n != null) {
            d.weekNumberMonday = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseFullYear(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 4))
        return if (n != null) {
            d.year = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseYear(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.year = n.groupValues[0].toInt() + if (n.groupValues[0].toInt() > 68) 1900 else 2000
            i + n.groupValues[0].length
        } else -1
    }

    /*fun parseZone(d:ParseDate, string:String, i:Int):Int {
        val n = Regex("/^(Z)|([+-]\\d\\d)(?:\\:?(\\d\\d))?/").find(string.substring(i, i + 6))
        return if (n != null) {
            d.zone = if (n.groupValues[1] != null) 0 else -(n.groupValues[2].toInt() + (n.groupValues[3].toInt() || "00"))
            i + n.groupValues[0].length
        }
        else -1
    }*/

    public fun parseMonthNumber(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.month = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseDayOfMonth(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.day = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseDayOfYear(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 3))
        return if (n != null) {
            d.month = 0
            d.day = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseHour24(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.hour = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseMinutes(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.minute = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseSeconds(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.second = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseMilliseconds(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 3))
        return if (n != null) {
            d.millisecond = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    public fun parseLiteralPercent(d: ParseDate, string: String, i: Int): Int {
        val percentRe = Regex("^%")
        val input = string.substring(i, i + 1)
        val n = percentRe.find(input)
        return if (n != null) i + n.groupValues[0].length else -1
    }

    public fun formatShortWeekday(d: LocalDateTime, p: String): String = locale_shortWeekdays[(d.dayOfWeek.ordinal + 1) % 7]
    public fun formatShortWeekday(d: Instant, p: String): String = formatShortWeekday(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatWeekday(d: LocalDateTime, p: String): String = locale_weekdays[(d.dayOfWeek.ordinal + 1) % 7]
    public fun formatWeekday(d: Instant, p: String): String = formatWeekday(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatShortMonth(d: LocalDateTime, p: String): String = locale_shortMonths[d.monthNumber - 1]
    public fun formatShortMonth(d: Instant, p: String): String = formatShortMonth(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatMonth(d: LocalDateTime, p: String): String = locale_months[d.monthNumber - 1]
    public fun formatMonth(d: Instant, p: String): String = formatMonth(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatPeriod(d: LocalDateTime, p: String): String = locale_periods[if (d.hour >= 12) 1 else 0]
    public fun formatPeriod(d: Instant, p: String): String = formatPeriod(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatDayOfMonth(d: LocalDateTime, p: String): String = pad(d.dayOfMonth, p, 2)
    public fun formatDayOfMonth(d: Instant, p: String): String = formatDayOfMonth(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatHour24(d: LocalDateTime, p: String): String = pad(d.hour, p, 2)
    public fun formatHour24(d: Instant, p: String): String = formatHour24(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatHour12(d: LocalDateTime, p: String): String {
        val hour = d.hour % 12
        return pad(if (hour == 0) 12 else hour, p, 2)
    }
    public fun formatHour12(d: Instant, p: String): String = formatHour12(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatDayOfYear(d: LocalDateTime, p: String): String = pad(d.dayOfYear, p, 3)
    public fun formatDayOfYear(d: Instant, p: String): String = formatDayOfYear(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatMilliseconds(d: LocalDateTime, p: String): String = pad(d.nanosecond / 1_000_000, p, 3)
    public fun formatMilliseconds(d: Instant, p: String): String = formatMilliseconds(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatMonthNumber(d: LocalDateTime, p: String): String = pad(d.monthNumber, p, 2)
    public fun formatMonthNumber(d: Instant, p: String): String = formatMonthNumber(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatMinutes(d: LocalDateTime, p: String): String = pad(d.minute, p, 2)
    public fun formatMinutes(d: Instant, p: String): String = formatMinutes(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatSeconds(d: LocalDateTime, p: String): String = pad(d.second, p, 2)
    public fun formatSeconds(d: Instant, p: String): String = formatSeconds(d.toLocalDateTime(TimeZone.UTC), p)

//    public fun formatWeekNumberSunday(d: Instant, p: String): String {
//        return pad(timeSunday.count(TimeZone.UTC, timeYear.floor(TimeZone.UTC, d), d).toInt(), p, 2)
//    }

    public fun formatWeekdayNumber(d: LocalDateTime, p: String): String = d.dayOfWeek.toString()
    public fun formatWeekdayNumber(d: Instant, p: String): String = formatWeekdayNumber(d.toLocalDateTime(TimeZone.UTC), p)

//    public fun formatWeekNumberMonday(d: Instant, p: String): String {
//        return pad(timeMonday.count(TimeZone.UTC, timeYear.floor(TimeZone.UTC, d), d).toInt(), p, 2)
//    }

    public fun formatYear(d: LocalDateTime, p: String): String = pad(d.year % 100, p, 2)
    public fun formatYear(d: Instant, p: String): String = formatYear(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatFullYear(d: LocalDateTime, p: String): String = pad(d.year % 10000, p, 4)
    public fun formatFullYear(d: Instant, p: String): String = formatFullYear(d.toLocalDateTime(TimeZone.UTC), p)

    public fun formatZone(d: Instant, p: String): String {
//        var z = d.getTimezoneOffset()
        var z = 0
        val sign = if (z > 0) "-" else "+"
        z = abs(z)
        return sign + pad(z / 60, "0", 2) + pad(z % 60, "0", 2)
    }

    // TODO with formatter the "%" sign may differs depending on Locale
    public fun formatLiteralPercent(d: Instant, p: String): String {
        return "%"
    }

/*fun formatUTCShortWeekday(d:LocalDateTime): String {
    return locale_shortWeekdays[d.getUTCDay()]
}

fun formatUTCWeekday(d:LocalDateTime): String {
    return locale_weekdays[d.getUTCDay()]
}

fun formatUTCShortMonth(d:LocalDateTime): String {
    return locale_shortMonths[d.getUTCMonth()]
}

fun formatUTCMonth(d:LocalDateTime): String {
    return locale_months[d.getUTCMonth()]
}

fun formatUTCPeriod(d:Date): String {
    return locale_periods[+(d.getUTCHours() >= 12)]
}*/
}

/*fun localDate(d) {
    if (0 <= d.y && d.y < 100) {
        var LocalDateTime = new LocalDateTime (-1, d.m, d.d, d.H, d.M, d.S, d.L)
        date.setFullYear(d.y)
        return date
    }
    return LocalDateTime(d.y, d.m, d.d, d.H, d.M, d.S, d.L)
}

fun utcDate(d) {
    if (0 <= d.y && d.y < 100) {
        var date = new LocalDateTime (LocalDateTime.UTC(-1, d.m, d.d, d.H, d.M, d.S, d.L))
        date.setUTCFullYear(d.y)
        return date
    }
    return LocalDateTime(LocalDateTime.UTC(d.y, d.m, d.d, d.H, d.M, d.S, d.L))
}*/

public fun newYear(y: Int?): ParseDate {
    return ParseDate(y)
}

/// STRING MANIPULATIONS

private val pads = mapOf(Pair('-', ""), Pair('_', " "), Pair('0', "0"))
private val numberRe = Regex("^\\s*\\d+") // note: ignores next directive

private fun pad(value: Int, fill: String, width: Int): String {
    val sign = if (value < 0) "-" else ""
    val string = abs(value).toString()
    return sign + (0 until (width - string.length)).map { fill }.joinToString("") + string
}

private fun requote(s: String): String {
    val requoteRe = "/[\\\\^\$\\*\\+\\?\\|\\[\\]\\(\\)\\.\\{\\}]/g"
    return s.replace(requoteRe, "\\$&")
}

private fun formatRe(names: List<String>): Regex {
    val joinToString = names.map { it -> requote(it) }.joinToString("|")
    return Regex("^(?:$joinToString)", RegexOption.IGNORE_CASE)
}

private fun formatLookup(names: List<String>): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    var i = -1
    val n = names.size
    while (++i < n) map[names[i].lowercase()] = i
    return map.toMap()
}

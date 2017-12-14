package io.data2viz.timeFormat

import io.data2viz.time.*
import kotlin.math.abs

// return {y: y, m: 0, d: 1, H: 0, M: 0, S: 0, L: 0};
data class ParseDate(
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

private fun date(d: ParseDate): Date {
    val date = date(d.year ?: 0, d.month ?: 1, 1, d.hour ?: 0, d.minute ?: 0, d.second ?: 0, d.millisecond ?: 0)

    // add days (cause day value may be a number of days <= 0 or > 31)
    if (d.day != null) date.plusDays(d.day!!.toLong() - 1)

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

val defaultLocale = Locale()
fun format(specifier: String) = defaultLocale.format(specifier)
fun parse(specifier: String) = defaultLocale.parse(specifier)

class Locale(timeLocale: TimeLocale = Locales.defaultLocale()) {
    val locale_dateTime = timeLocale.dateTime
    val locale_date = timeLocale.date
    val locale_time = timeLocale.time
    val locale_periods = timeLocale.periods
    val locale_weekdays = timeLocale.days
    val locale_shortWeekdays = timeLocale.shortDays
    val locale_months = timeLocale.months
    val locale_shortMonths = timeLocale.shortMonths

    val periodRe = formatRe(locale_periods)
    val periodLookup = formatLookup(locale_periods)
    val weekdayRe = formatRe(locale_weekdays)
    val weekdayLookup = formatLookup(locale_weekdays)
    val shortWeekdayRe = formatRe(locale_shortWeekdays)
    val shortWeekdayLookup = formatLookup(locale_shortWeekdays)
    val monthRe = formatRe(locale_months)
    val monthLookup = formatLookup(locale_months)
    val shortMonthRe = formatRe(locale_shortMonths)
    val shortMonthLookup = formatLookup(locale_shortMonths)

    val formats = mutableMapOf<Char, ((Date, String) -> String)?>(
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
//        Pair('U', ::formatWeekNumberSunday),
            Pair('w', ::formatWeekdayNumber),
//        Pair('W', ::formatWeekNumberMonday),
            Pair('x', null),
            Pair('X', null),
            Pair('y', ::formatYear),
            Pair('Y', ::formatFullYear),
            Pair('Z', ::formatZone),
            Pair('%', ::formatLiteralPercent)
    )

    val parses = mutableMapOf<Char, ((ParseDate, String, Int) -> Int)?>(
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

    val dateTimeFormat = format(locale_dateTime)
    val dateFormat = format(locale_date)
    val timeFormat = format(locale_time)

    init {
        formats['c'] = fun(date: Date, _: String): String { return dateTimeFormat(date) }
        formats['x'] = fun(date: Date, _: String): String { return dateFormat(date) }
        formats['X'] = fun(date: Date, _: String): String { return timeFormat(date) }
    }

    fun format(specifier: String): (Date) -> String {
        return fun(date: Date): String {
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

    fun parse(specifier: String): (String) -> Date? {
        return fun(dateString: String): Date? {
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
                    0//utcDate(newYear(d.y)).getUTCDay()
                } else {
                    date(newYear(d.year)).dayOfWeek()
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

    fun parsePeriod(d: ParseDate, string: String, i: Int): Int {
        val n = periodRe.find(string.substring(i))
        return if (n != null) {
            val period = periodLookup[n.groupValues[0].filter { it != ' ' }.toLowerCase()]
            d.period = if (period != null) period else 0
            i + n.groupValues[0].length
        } else -1
    }

    fun parseShortWeekday(d: ParseDate, string: String, i: Int): Int {
        val n = shortWeekdayRe.find(string.substring(i))
        return if (n != null) {
            val weekDay = shortWeekdayLookup[n.groupValues[0].filter { it != ' ' }.toLowerCase()]
            d.weekDay = if (weekDay != null) weekDay else 0
            i + n.groupValues[0].length
        } else -1
    }

    fun parseWeekday(d: ParseDate, string: String, i: Int): Int {
        val n = weekdayRe.find(string.substring(i))
        return if (n != null) {
            val weekDay = weekdayLookup[n.groupValues[0].filter { it != ' ' }.toLowerCase()]
            d.weekDay = if (weekDay != null) weekDay else 0
            i + n.groupValues[0].length
        } else -1
    }

    fun parseShortMonth(d: ParseDate, string: String, i: Int): Int {
        val n = shortMonthRe.find(string.substring(i))
        return if (n != null) {
            val month = shortMonthLookup[n.groupValues[0].filter { it != ' ' }.toLowerCase()]
            d.month = if (month != null) month + 1 else 0
            i + n.groupValues[0].length
        } else -1
    }

    fun parseMonth(d: ParseDate, string: String, i: Int): Int {
        val n = monthRe.find(string.substring(i))
        return if (n != null) {
            val month = monthLookup[n.groupValues[0].filter { it != ' ' }.toLowerCase()]
            d.month = if (month != null) month + 1 else 0
            i + n.groupValues[0].length
        } else -1
    }

    fun parseLocaleDateTime(d: ParseDate, string: String, i: Int): Int {
        return parseSpecifier(d, locale_dateTime, string, i)
    }

    fun parseLocaleDate(d: ParseDate, string: String, i: Int): Int {
        return parseSpecifier(d, locale_date, string, i)
    }

    fun parseLocaleTime(d: ParseDate, string: String, i: Int): Int {
        return parseSpecifier(d, locale_time, string, i)
    }

    fun parseWeekdayNumber(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 1))
        return if (n != null) {
            d.weekDay = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseWeekNumberSunday(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i))
        return if (n != null) {
            d.weekNumberSunday = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseWeekNumberMonday(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i))
        return if (n != null) {
            d.weekNumberMonday = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseFullYear(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 4))
        return if (n != null) {
            d.year = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseYear(d: ParseDate, string: String, i: Int): Int {
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

    fun parseMonthNumber(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.month = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseDayOfMonth(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.day = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseDayOfYear(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 3))
        return if (n != null) {
            d.month = 0
            d.day = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseHour24(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.hour = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseMinutes(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.minute = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseSeconds(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 2))
        return if (n != null) {
            d.second = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseMilliseconds(d: ParseDate, string: String, i: Int): Int {
        val n = numberRe.find(string.substring(i, i + 3))
        return if (n != null) {
            d.millisecond = n.groupValues[0].filter { it != ' ' }.toInt()
            i + n.groupValues[0].length
        } else -1
    }

    fun parseLiteralPercent(d: ParseDate, string: String, i: Int): Int {
        val percentRe = Regex("^%")
        val input = string.substring(i, i + 1)
        val n = percentRe.find(input)
        return if (n != null) i + n.groupValues[0].length else -1
    }

    fun formatShortWeekday(d: Date, p: String): String {
        return locale_shortWeekdays[d.dayOfWeek()%7]
    }

    fun formatWeekday(d: Date, p: String): String {
        return locale_weekdays[d.dayOfWeek()%7]
    }

    fun formatShortMonth(d: Date, p: String): String {
        return locale_shortMonths[d.month() - 1]
    }

    fun formatMonth(d: Date, p: String): String {
        return locale_months[d.month() - 1]
    }

    fun formatPeriod(d: Date, p: String): String {
        return locale_periods[if (d.hour() >= 12) 1 else 0]
    }

    fun formatDayOfMonth(d: Date, p: String): String {
        return pad(d.dayOfMonth(), p, 2)
    }

    fun formatHour24(d: Date, p: String): String {
        return pad(d.hour(), p, 2)
    }

    fun formatHour12(d: Date, p: String): String {
        val hour = d.hour() % 12
        return pad(if (hour == 0) 12 else hour, p, 2)
    }

    // TODO JS version moved to date? (see comment)
    fun formatDayOfYear(d: Date, p: String): String {
        return pad(d.dayOfYear(), p, 3)
        //return pad(1 + timeDay().count(timeYear(d), d), p, 3)
    }

    fun formatMilliseconds(d: Date, p: String): String {
        return pad(d.millisecond(), p, 3)
    }

    fun formatMonthNumber(d: Date, p: String): String {
        return pad(d.month(), p, 2)
    }

    fun formatMinutes(d: Date, p: String): String {
        return pad(d.minute(), p, 2)
    }

    fun formatSeconds(d: Date, p: String): String {
        return pad(d.second(), p, 2)
    }

/*fun formatWeekNumberSunday(d:Date, p:String):String {
    return pad(timeSunday.count(timeYear(d), d), p, 2)
}*/

    fun formatWeekdayNumber(d: Date, p: String): String {
        return d.dayOfWeek().toString()
    }

/*fun formatWeekNumberMonday(d:Date, p:String):String {
    return pad(timeMonday.count(timeYear(d), d), p, 2)
}*/

    fun formatYear(d: Date, p: String): String {
        return pad(d.year() % 100, p, 2)
    }

    fun formatFullYear(d: Date, p: String): String {
        return pad(d.year() % 10000, p, 4)
    }

    fun formatZone(d: Date, p: String): String {
        var z = d.getTimezoneOffset()
        val sign = if (z > 0) "-" else "+"
        z = abs(z)
        return sign + pad(z / 60, "0", 2) + pad(z % 60, "0", 2)
    }

    fun formatLiteralPercent(d: Date, p: String): String {
        return "%"
    }

/*fun formatUTCShortWeekday(d:Date): String {
    return locale_shortWeekdays[d.getUTCDay()]
}

fun formatUTCWeekday(d:Date): String {
    return locale_weekdays[d.getUTCDay()]
}

fun formatUTCShortMonth(d:Date): String {
    return locale_shortMonths[d.getUTCMonth()]
}

fun formatUTCMonth(d:Date): String {
    return locale_months[d.getUTCMonth()]
}

fun formatUTCPeriod(d:Date): String {
    return locale_periods[+(d.getUTCHours() >= 12)]
}*/
}

/*fun localDate(d) {
    if (0 <= d.y && d.y < 100) {
        var date = new Date (-1, d.m, d.d, d.H, d.M, d.S, d.L)
        date.setFullYear(d.y)
        return date
    }
    return Date(d.y, d.m, d.d, d.H, d.M, d.S, d.L)
}

fun utcDate(d) {
    if (0 <= d.y && d.y < 100) {
        var date = new Date (Date.UTC(-1, d.m, d.d, d.H, d.M, d.S, d.L))
        date.setUTCFullYear(d.y)
        return date
    }
    return Date(Date.UTC(d.y, d.m, d.d, d.H, d.M, d.S, d.L))
}*/

fun newYear(y: Int?): ParseDate {
    return ParseDate(y)
}

/// STRING MANIPULATIONS

val pads = mapOf(Pair('-', ""), Pair('_', " "), Pair('0', "0"))
val numberRe = Regex("^\\s*\\d+") // note: ignores next directive

fun pad(value: Int, fill: String, width: Int): String {
    val sign = if (value < 0) "-" else ""
    val string = abs(value).toString()
    return sign + (0 until (width - string.length)).map { fill }.joinToString("") + string
}

fun requote(s: String): String {
    val requoteRe = "/[\\\\^\$\\*\\+\\?\\|\\[\\]\\(\\)\\.\\{\\}]/g"
    return s.replace(requoteRe, "\\$&")
}

fun formatRe(names: List<String>): Regex {
    val joinToString = names.map { it -> requote(it) }.joinToString("|")
    return Regex("^(?:$joinToString)", RegexOption.IGNORE_CASE)
}

fun formatLookup(names: List<String>): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    var i = -1
    val n = names.size
    while (++i < n) map.set(names[i].toLowerCase(), i)
    return map.toMap()
}
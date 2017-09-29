package io.data2viz.format

import kotlin.math.max


val formatRE: Regex = Regex("^(?:(.)?([<>=^]))?([+\\-\\( ])?([$#])?(0)?(\\d+)?(,)?(\\.\\d+)?([a-z%])?$", RegexOption.IGNORE_CASE)

/**
 * Returns a new format function for the given string specifier. The returned function takes a number as the only argument, and returns a string representing the formatted number. The general form of a specifier is:
 *
 * [​[fill]align][sign][symbol][0][width][,][.exponent][type]
 *
 * The fill can be any character. The presence of a fill character is signaled by the align character following it, which must be one of the following:
 * > - Forces the field to be right-aligned within the available space. (Default behavior).
 * < - Forces the field to be left-aligned within the available space.
 * ^ - Forces the field to be centered within the available space.
 * = - like >, but with any sign and symbol to the left of any padding.
 *
 * The sign can be:
 * - - nothing for zero or positive and a minus sign for negative. (Default behavior.)
 * + - a plus sign for zero or positive and a minus sign for negative.
 * ( - nothing for zero or positive and parentheses for negative.
 * (space) - a space for zero or positive and a minus sign for negative.
 *
 * The symbol can be:
 * $ - apply currency symbols per the locales definition.
 * # - for binary, octal, or hexadecimal notation, prefix by 0b, 0o, or 0x, respectively.
 *
 * The zero (0) option enables zero-padding; this implicitly sets fill to 0 and align to =.
 * The width defines the minimum field width; if not specified, then the width will be determined by the content.
 * The groupSeparation (,) option enables the use of a grouping separator, such as a groupSeparation for thousands.
 *
 * Depending on the type, the exponent either indicates the number of digits that follow the coefficient point (types f and %), or the number of significant digits (types ​"", e, g, r, s and p). If the exponent is not specified, it defaults to 6 for all types except ​ (none), which defaults to 12.
 * Precision is ignored for integer formats (types b, o, d, x, X and c).
 * See precisionFixed and precisionRound for help picking an appropriate exponent.
 *
 * The available type values are:
 *
 * e - exponent notation.
 * f - fixed point notation.
 * g - either coefficient or exponent notation, rounded to significant digits.
 * r - coefficient notation, rounded to significant digits.
 * s - coefficient notation with an SI prefix, rounded to significant digits.
 * % - multiply by 100, and then coefficient notation with a percent sign.
 * p - multiply by 100, round to significant digits, and then coefficient notation with a percent sign.
 * b - binary notation, rounded to integer.
 * o - octal notation, rounded to integer.
 * d - coefficient notation, rounded to integer.
 * x - hexadecimal notation, using lower-case letters, rounded to integer.
 * X - hexadecimal notation, using upper-case letters, rounded to integer.
 * c - converts the integer to the corresponding unicode character before printing.
 * ​ (none) - like g, but trim insignificant trailing zeros.
 * The type n is also supported as shorthand for ,g. For the g, n and ​ (none) types, coefficient notation is used if the resulting string would have exponent or fewer digits; otherwise, exponent notation is used. For example:
 *
 * d3.format(".2")(42);  // "42"
 * d3.format(".2")(4.2); // "4.2"
 * d3.format(".1")(42);  // "4e+1"
 * d3.format(".1")(4.2); // "4"
 */
data class FormatSpecifier(val specifier:String) {

    var fill: String = " "
    var align: String = ">"
    var sign: String = "-"
    var symbol: String = ""
    var zero: Boolean = false
    var width: Int? = null
    var groupSeparation: Boolean = false
    var precision: Int? = null
    var type: String = ""

    init {
        if (!formatRE.matches(specifier)) throw IllegalArgumentException("invalid format: " + specifier);

        val match = formatRE.find(specifier)!!.groupValues

        if (match[1].isNotEmpty()) fill = match[1]
        if (match[2].isNotEmpty()) align = match[2]
        if (match[3].isNotEmpty()) sign = match[3]
        if (match[4].isNotEmpty()) symbol = match[4]
        zero = (match[5] == "0")
        if (match[6].isNotEmpty() && match[6].toIntOrNull() != null) width = match[6].toInt()
        groupSeparation = (match[7] == ",")
        if (match[8].length > 1 && match[8].substring(1).toIntOrNull() != null) precision = match[8].substring(1).toInt()
        if (match[9].isNotEmpty()) type = match[9]

        // The "n" type is an alias for ",g".
        if (type == "n") {
            groupSeparation = true
            type = "g"
        }

        // Map invalid types to the default format.
        else if (!isValidType(type)) type = ""

        // If zero fill is specified, padding goes after sign and before digits.
        if (zero || (fill == "0" && align == "=")) {
            zero = true
            fill = "0"
            align = "="
        }
    }

    override fun toString(): String {
        return "$fill$align$sign$symbol${if (zero) "0" else ""}${if (width == null) "" else max(1, width!!)}${if (groupSeparation) "," else ""}${if (precision == null) "" else "." + max(0, precision!!)}$type"
    }
}
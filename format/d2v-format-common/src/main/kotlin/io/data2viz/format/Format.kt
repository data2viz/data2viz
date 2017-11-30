package io.data2viz.format

import kotlin.math.*

private val prefixes = listOf("y", "z", "a", "f", "p", "n", "µ", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y")
private var prefixExponent = 0

data class CoefficientExponent(val coefficient: String, val exponent: Int)



fun formatter(specify: String): (Double) -> String = Locale().formatter(specify)
fun Locale.formatter( specifier: String): (Double) -> String = formatter(specify(specifier))

fun formatter(
        type: Type = Type.NONE,
        fill: String = " ",
        align: Align = Align.RIGTH,
        sign: Sign = Sign.MINUS,
        symbol: Symbol? = null,
        zero: Boolean = false,
        width: Int? = null,
        group: Boolean = false,
        precision: Int? = null
) = Locale().formatter(specify(type, fill, align, sign, symbol, zero, width, group, precision))

fun Locale.formatter(
        type: Type = Type.NONE,
        fill: String = " ",
        align: Align = Align.RIGTH,
        sign: Sign = Sign.MINUS,
        symbol: Symbol? = null,
        zero: Boolean = false,
        width: Int? = null,
        groupSeparation: Boolean = false,
        precision: Int? = null
) = formatter(specify(type, fill, align, sign, symbol, zero, width, groupSeparation, precision))

private fun Locale.formatter( spec: FormatSpec): (Double) -> String {

    val width = spec.width

    val groupFunction = formatGroup(grouping, groupSeparator)

    // Compute the prefix and suffix.
    // For SI-prefix, the suffix is lazily computed.
    val prefix = if (spec.symbol == Symbol.CURRENCY)
        currency[0]
        else if (spec.symbol == Symbol.NUMBER_BASE && spec.type.isNumberBase) "0" + spec.type?.c?.toLowerCase() else ""

    val suffix = if (spec.symbol == Symbol.CURRENCY) currency[1] else if (spec.type.isPercent) "%" else ""

    // What format function should we use?
    // Is this an integer type?
    // Can this type generate exponential notation?
    val formatType = formatTypes(spec.type)
    val maybeSuffix = spec.type.maybeSuffix

    // Set the default exponent if not specified,
    // or clamp the specified exponent to the supported range.
    // For significant exponent, it must be in [1, 21].
    // For fixed exponent, it must be in [0, 20].
    val precision = if (spec.precision == null) {
        if (spec.type != null) 6 else 12
    } else {
        if (gprs.contains(spec.type))
            spec.precision.coerceIn(1, 21) else spec.precision.coerceIn(0, 20)
    }

    fun format(value: Double): String {

        var returnValue: String

        var valuePrefix = prefix
        var valueSuffix = suffix

        if (spec.type == Type.CHAR) {
            valueSuffix = formatType(value, 0) + valueSuffix
            returnValue = ""
        } else {
            // value = +value              // typé

            // Perform the initial formatting.
            var valueNegative = value < 0
            returnValue = formatType(abs(value), precision)

            // If a negative value rounds to zero during formatting, treat as positive.
            if (valueNegative && returnValue.toDouble() == 0.0) valueNegative = false

            // Compute the prefix and suffix.
            valuePrefix = (if (valueNegative) (if (spec.sign == Sign.PARENTHESES) spec.sign.c else "-") else if (spec.sign == Sign.MINUS || spec.sign == Sign.PARENTHESES) "" else spec.sign.c) + valuePrefix
            valueSuffix = valueSuffix + (if (spec.type == Type.DECIMAL_WITH_SI) prefixes[8 + prefixExponent / 3] else "") + (if (valueNegative && spec.sign == Sign.PARENTHESES) ")" else "")

            // Break the formatted value into the integer “value” part that can be
            // grouped, and fractional or exponential “suffix” part that is not.
            if (maybeSuffix) {
                var i = -1
                val n = returnValue.length
                while (++i < n) {
                    val c = returnValue[i]
                    if (c !in '0'..'9') {
                        valueSuffix = (if (c == '.') decimalSeparator + returnValue.slice(i + 1 until returnValue.length) else returnValue.slice(i until returnValue.length)) + valueSuffix
                        returnValue = returnValue.slice(0 until i)
                        break
                    }
                }
            }
        }

        // If the fill character is not "0", grouping is applied before padding.
        if (spec.groupSeparation && !spec.zero) returnValue = groupFunction(returnValue, 9999999)

        // Compute the padding.
        val length = valuePrefix.length + returnValue.length + valueSuffix.length
        var padding = if (width != null && length < width) "".padStart(width - length, spec.fill[0]) else ""

        // If the fill character is "0", grouping is applied after padding.
        if (spec.groupSeparation && spec.zero) {
            returnValue = groupFunction(padding + returnValue, if (padding.isNotEmpty()) width!! - valueSuffix.length else 9999999)
            padding = ""
        }

        // Reconstruct the final output based on the desired alignment.
        returnValue = when (spec.align) {
            Align.LEFT -> valuePrefix + returnValue + valueSuffix + padding
            Align.RIGHT_WITHOUT_SIGN -> valuePrefix + padding + returnValue + valueSuffix
            Align.CENTER -> {
                val padLength = padding.length / 2 - 1
                padding.slice(0..padLength) + valuePrefix + returnValue + valueSuffix + padding.slice(0 until padding.length - 1 - padLength)
            }
            Align.RIGTH -> padding + valuePrefix + returnValue + valueSuffix
        }
        return numerals(returnValue)
    }
    return ::format
}

fun Locale.numerals(valueAsString: String): String =
        if (numerals == null)
            valueAsString
        else valueAsString
                .fold(StringBuilder(), { acc, c ->
                    val intValue = c.toInt()
                    if (intValue in 48..57)
                        acc.append(numerals!![intValue - 48])
                    else
                        acc.append(c)
                })
                .toString()


fun formatPrefix(specifier: String, fixedPrefix: Double): (Double) -> String = Locale().formatPrefix(specifier, fixedPrefix)
fun Locale.formatPrefix(specifier: String, fixedPrefix: Double): (Double) -> String {
    val formatSpecifier = specify(specifier).copy(type = Type.FIXED_POINT)
    val f = formatter(formatSpecifier.toString())
    val e = floor(exponent(fixedPrefix).toDouble() / 3.0).coerceIn(-8.0, 8.0) * 3
    val k = 10.0.pow(-e)
    val prefix = prefixes[8 + (e / 3.0).toInt()]
    return fun(value: Double): String = f(k * value) + prefix
}

private fun exponent(value: Double): Int {
    val x = formatDecimal(abs(value))
    return if (x != null) x.exponent else 0
}

private fun formatGroup(group: List<Int>, groupSeparator: String): (String, Int) -> String {
    return fun(value: String, width: Int): String {
        var i = value.length
        val t = mutableListOf<String>()
        var j = 0
        var g = group[0]
        var length = 0

        while (i > 0 && g > 0) {
            if (length + g + 1 > width) g = max(1, width - length)
            i -= g
            t.add(value.substring(max(0, i), min(value.length, i + g)))
            length += g + 1
            if (length > width) break
            j = (j + 1) % group.size
            g = group[j]
        }

        t.reverse()
        return t.joinToString(separator = groupSeparator)
    }
}


fun formatTypes(type: Type): (Double, Int) -> String =
        when (type) {
            Type.FIXED_POINT            -> { x: Double, p: Int -> x.toFixed(p) }
            Type.PERCENT                -> { x: Double, p: Int -> (x * 100).toFixed(p) }
            Type.PERCENT_ROUNDED        -> { x: Double, p: Int -> formatRounded(x * 100, p) }
            Type.CHAR                   -> { x: Double, _: Int -> "$x" }
            Type.DECIMAL_ROUNDED        -> { x: Double, _: Int -> x.toStringDigits(10) }
            Type.DECIMAL                -> { x: Double, p: Int -> formatRounded(x, p) }
            Type.DECIMAL_WITH_SI        -> { x: Double, p: Int -> formatPrefixAuto(x, p) }
            Type.DECIMAL_OR_EXPONENT    -> { x: Double, p: Int -> x.toPrecision(p) }
            Type.EXPONENT               -> { x: Double, p: Int -> x.toExponential(p) }
            Type.BINARY                 -> { x: Double, _: Int -> x.toStringDigits(2) }
            Type.OCTAL                  -> { x: Double, _: Int -> x.toStringDigits(8) }
            Type.HEX_UPPERCASE          -> { x: Double, _: Int -> x.toStringDigits(16).toUpperCase() }
            Type.HEX_LOWERCASE          -> { x: Double, _: Int -> x.toStringDigits(16) }
            Type.NONE                   -> { x: Double, p: Int -> formatDefault(x,p)}
        }

fun formatDefault(x: Double, p: Int): String {
    val newX = x.toPrecision(p)
    var i0 = -1
    var i1 = 0

    loop@ for (i in 1 until newX.length) {
        val c = newX[i]
        when (c) {
            '.' -> {
                i0 = i
                i1 = i
            }
            '0' -> {
                if (i0 == 0) i0 = i
                i1 = i
            }
            'e' -> break@loop
            else -> if (i0 > 0) i0 = 0
        }
    }

    return if (i0 > 0) newX.slice(0 until i0) + newX.slice(i1 + 1 until newX.length) else newX
}

fun formatRounded(x: Double, p: Int): String {
    val ce = formatDecimal(x, p) ?: return "$x"

    return if (ce.exponent < 0) {
        "0.".padEnd(-ce.exponent + 1, '0') + ce.coefficient
    } else {
        if (ce.coefficient.length > ce.exponent + 1) {
            ce.coefficient.slice(0..ce.exponent) + "." +
                    ce.coefficient.slice(ce.exponent + 1 until ce.coefficient.length)
        } else {
            ce.coefficient + "".padStart(ce.exponent - ce.coefficient.length + 1, '0')
        }
    }
}

/**
 * Computes the decimal coefficient and exponent of the specified number x with
 * significant digits p, where x is positive and p is in [1, 21] or undefined.
 * For example, formatDecimal(1.23) returns ["123", 0].
 */
fun formatDecimal(x: Double, p: Int = 0): CoefficientExponent? {
    var newX: String = x.toExponential()
    if (p != 0) {
        newX = x.toExponential(p - 1)
    }

    val index = newX.indexOf("e")
    if (index < 0) return null

    val coefficient = newX.slice(0 until index)

    // The string returned by toExponential either has the form \d\.\d+e[-+]\d+
    // (e.g., 1.2e+3) or the form \de[-+]\d+ (e.g., 1e+3).
    val decimal: String = if (coefficient.length > 1) coefficient[0] + coefficient.slice(2 until coefficient.length) else coefficient

    val parsePrecision = newX.slice(index + 1 until newX.length).toIntOrNull()
    val precision = if (parsePrecision != null) parsePrecision else 0

    return CoefficientExponent(decimal, precision)
}

private fun formatPrefixAuto(x: Double, p: Int = 0): String {
    val ce = formatDecimal(x, p) ?: return "$x"

    prefixExponent = floor(ce.exponent / 3.0).toInt().coerceIn(-8, 8) * 3
    val i = ce.exponent - prefixExponent + 1
    val n = ce.coefficient.length

    return when {
        i == n -> ce.coefficient
        i > n -> ce.coefficient.padEnd(i, '0')
        i > 0 -> ce.coefficient.slice(0 until i) + "." + ce.coefficient.slice(i until ce.coefficient.length)
        else -> "0.".padEnd(2 - i, '0') + formatDecimal(x, max(0, p + i - 1))!!.coefficient
    } // less than 1y!
}

fun precisionFixed(step: Double): Int = max(0, -exponent(abs(step)))

fun precisionPrefix(step: Double, value: Double): Int = (floor(exponent(value) / 3.0).toInt().coerceIn(-8, 8) * 3 - exponent(abs(step))).coerceAtLeast(0)

fun precisionRound(step: Double, max: Double): Int {
    val newStep = abs(step)
    val newMax = abs(max) - step
    return max(0, exponent(newMax) - exponent(newStep)) + 1
}

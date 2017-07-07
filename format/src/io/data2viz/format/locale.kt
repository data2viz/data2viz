package io.data2viz.format

import kotlin.js.Math

private val prefixes = listOf<String>("y", "z", "a", "f", "p", "n", "µ", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y");

data class Locale(
        var decimalSeparator: Char = '.',
        var group: List<Int> = listOf(3),
        var groupSeparator: Char = ',',
        var currency: List<String> = listOf("", " €"),
        var numerals: List<String>? = null,
        var percent: String = "%")

// TODO check for slice as JS String.slice does not include last element (end)
fun Locale.format(specifier: FormatSpecifier): (Double) -> String {

    // TODO
    // val specifier = FormatSpecifier(specifier);

    val fill = specifier.fill
    val align = specifier.align
    val sign = specifier.sign
    val symbol = specifier.symbol
    var zero = specifier.zero
    val width = specifier.width
    var comma = specifier.comma
    val type = specifier.type

    // Compute the prefix and suffix.
    // For SI-prefix, the suffix is lazily computed.
    val prefix = if (symbol == "$") currency[0] else if (symbol == "#" && isTypeIn(type, "boxX")) "0" + type.toLowerCase() else ""
    val suffix = if (symbol == "$") currency[1] else if (isTypeIn(type, "%p")) "%" else ""

    // What format function should we use?
    // Is this an integer type?
    // Can this type generate exponential notation?
    val formatType = formatTypes(type)
    val maybeSuffix = isTypeIn(type, "defgprs%")

    // Set the default precision if not specified,
    // or clamp the specified precision to the supported range.
    // For significant precision, it must be in [1, 21].
    // For fixed precision, it must be in [0, 20].
    val precision = if (specifier.precision == null) {
        if (type.isNotEmpty()) 6 else 12
    } else {
        if (isTypeIn(type, "gprs")) Math.max(1, Math.min(21, specifier.precision!!)) else Math.max(0, Math.min(20, specifier.precision!!));
    }

    fun format(value: Double): String {

        var returnValue: String
        var newValue: Double

        var valuePrefix = prefix
        var valueSuffix = suffix

        if (type == "c") {
            valueSuffix = formatType(value, 0) + valueSuffix
            returnValue = ""
        } else {
            // value = +value              // typé

            // Perform the initial formatting.
            var valueNegative = value.toDouble() < 0
            returnValue = formatType(Math.abs(value), precision)

            // If a negative value rounds to zero during formatting, treat as positive.
            if (valueNegative && returnValue.toDouble() == 0.0) valueNegative = false

            // TODO was in formatPrefixAuto
            val prefixExponent = 0

            // Compute the prefix and suffix.
            valuePrefix = (if (valueNegative) (if (sign == '(') sign.toString() else "-") else if (sign == '-' || sign == '(') "" else sign.toString()) + valuePrefix
            valueSuffix = valueSuffix + (if (type == "s") prefixes[8 + prefixExponent / 3] else "") + (if (valueNegative && sign == '(') ")" else "")

            // Break the formatted value into the integer “value” part that can be
            // grouped, and fractional or exponential “suffix” part that is not.
            if (maybeSuffix) {
                var i = -1
                val n = returnValue.length;
                while (++i < n) {
                    val c = returnValue[i]
                    if (c !in '0'..'9') {
                        valueSuffix = (if (c == '.') decimalSeparator + returnValue.slice(IntRange(i + 1, returnValue.size - 1)) else returnValue.slice(IntRange(i, returnValue.size - 1))) + valueSuffix
                        returnValue = returnValue.slice(IntRange(0, i));
                        break;
                    }
                }
            }
        }

        // If the fill character is not "0", grouping is applied before padding.
        // TODO
        //if (comma && !zero) returnValue = group(value, Infinity);

        // Compute the padding.
        val length = valuePrefix.length + returnValue.length + valueSuffix.length
        val padding = if (width != null && length < width) "".padStart(width - length + 1, fill) else ""

        // If the fill character is "0", grouping is applied after padding.
        // TODO
        //if (comma && zero) value = group(padding + value, padding.length ? width -valueSuffix.length : Infinity), padding = "";

        // Reconstruct the final output based on the desired alignment.
        when (align) {
            '<' -> returnValue = valuePrefix + returnValue + valueSuffix + padding
            '=' -> returnValue = valuePrefix + padding + returnValue + valueSuffix
        //TODO
        // '^' -> returnValue = padding.slice(0, length = padding.length >> 1)+valuePrefix + value + valueSuffix + padding.slice(length)
            else -> returnValue = padding + valuePrefix + returnValue + valueSuffix
        }

        return returnValue

        // TODO
        //return numerals(value);
    }
    return ::format
}

private fun isTypeIn(type: String, types: String) = type.isNotEmpty() && types.contains(type)

private fun Double.toFixed(digits: Int): String = this.asDynamic().toFixed(digits)
private fun Double.toExponential(digits: Int): String = this.asDynamic().toExponential(digits)
private fun Double.toExponential(): String = this.asDynamic().toExponential()
private fun Double.toPrecision(digits: Int): String = this.asDynamic().toPrecision(digits)
private fun Int.toStringDigits(digits: Int): String = this.asDynamic().toString(digits)

fun formatTypes(type: String): (Double, Int) -> String {
    when (type) {
        "" -> return ::formatDefault
    /*"%" -> fun(x: Double, p: Int): String { return (x * 100).toFixed(p) }
    "b" -> fun(x: Double): String { return Math.round(x).toStringDigits(2) }
    "c" -> fun(x: Double): String { return "$x" }
    "d" -> fun(x: Double): String { return Math.round(x).toStringDigits(10) }
    "e" -> fun(x: Double, p: Int): String { return x.toExponential(p) }
    "f" -> fun(x: Double, p: Int): String { return x.toFixed(p) }
    "g" -> fun(x: Double, p: Int): String { return x.toPrecision(p) }
    "o" -> fun(x: Double): String { return Math.round(x).toStringDigits(8) }
    "p" -> fun(x: Double, p: Int): String { return formatRounded(x * 100, p) }
    "r" -> formatRounded
    "s" -> formatPrefixAuto
    "X" -> fun(x: Double): String { return Math.round(x).toStringDigits(16).toUpperCase() }
    "x" -> fun(x: Double): String { return Math.round(x).toStringDigits(16) }*/
        else -> return ::formatDefault
    }
}

fun formatDefault(x: Double, p: Int): String {
    val newX = x.toPrecision(p);
    var i0 = -1
    var i1 = 0

    (1..newX.length).forEach { i ->
        val c = newX[i]
        when (c) {
            '.' -> {
                i0 = i; i1 = i; return@forEach
            }
            '0' -> {
                if (i0 == 0) i0 = i else i1 = i; return@forEach
            }
            'e' -> return@forEach
            else -> {
                if (i0 > 0) i0 = 0; return@forEach
            }
        }
    }

    return if (i0 > 0) newX.slice(IntRange(0, i0)) + newX.slice(IntRange(i1 + 1, newX.size - 1)) else newX;
}

/*fun formatRounded(x: Double, p: Int): String {
    var d = formatDecimal(x, p)
    if (!d) return "$x"
    var coefficient = d[0]
    var exponent = d[1]
    return if (exponent < 0) "0." + new Array (-exponent).join("0") + coefficient
    else {
        if (coefficient.length > exponent + 1) coefficient.slice(0, exponent + 1) + "." + coefficient.slice(exponent + 1)
        else coefficient + new Array (exponent - coefficient.length + 2).join("0")
    }
}

fun formatDecimal(x: Double, p: Int): String? {
    var newX: String = x.toExponential()
    if (p > 1) {
        newX = x.toExponential(p - 1)
    }

    var index = newX.indexOf("e")
    if (index < 0) return null

    var coefficient = newX.slice(IntRange(0, index))

    //if ((i = (x = p ? x.toExponential(p-1) : x.toExponential()).indexOf("e")) < 0) return null; // NaN, ±Infinity
    //var i, coefficient = x.slice(0, i);

    // The string returned by toExponential either has the form \d\.\d+e[-+]\d+
    // (e.g., 1.2e+3) or the form \de[-+]\d+ (e.g., 1e+3).
    return [coefficient.length > 1 ? coefficient[0]+coefficient.slice(2) : coefficient, +x.slice(i+1)];
}*/
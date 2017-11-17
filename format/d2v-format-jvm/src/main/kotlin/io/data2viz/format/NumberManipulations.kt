package io.data2viz.format

import java.text.DecimalFormat


private fun formatExponential(number: Double, digits: Int = 6): String {
    val formatter = DecimalFormat("0.000000e000")
    var fnumber = formatter.format(number)
    if (!fnumber.contains("e-")) { //don't blast a negative sign
        fnumber = fnumber.replace("e", "e+")
    }
    return fnumber
}

private fun formatToDecimal(number: Double, digits: Int): String {
    val formatter = DecimalFormat("%.${digits}f")
    return formatter.format(number)
}

internal actual fun Double.toStringDigits(digits: Int): String = "%.{$digits}f".format(Math.round(this))
internal actual fun Double.toFixed(digits: Int): String = formatToDecimal(this, digits)
internal actual fun Double.toExponential(digits: Int): String = formatExponential(this, digits)
internal actual fun Double.toExponential(): String = formatExponential(this)
internal actual fun Double.toPrecision(digits: Int): String = "%.{$digits}f".format(this)
internal actual fun Int.toStringDigits(digits: Int): String = "%.{$digits}f".format(this)
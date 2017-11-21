package io.data2viz.format

import java.math.BigDecimal
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToLong

private fun toPrecisionJVM(number: Double, digits: Int): String {
    return String.format(Locale.US, "%." + digits + "g", number)
            .replace("e+0", "e+")
            .replace("e-0", "e-")
}

private fun formatExponentialJVM(number: Double, digits: Int): String {
    return String.format(Locale.US, "%." + digits + "e", number)
            .replace("e+0", "e+")
            .replace("e-0", "e-")
}

private fun formatExponentialJVM(number: Double): String {
    val preciseNum = BigDecimal(abs(number).toString())
    val fractionnalPart = (preciseNum % BigDecimal.ONE).toString()
    val integerPart = preciseNum.div(BigDecimal.ONE).toString()

    var fracRemovedDecExpZeros = fractionnalPart.substring(fractionnalPart.indexOfFirst { it == '.' }+1, fractionnalPart.length)
    val exponentIndex = fracRemovedDecExpZeros.indexOfFirst { it == 'e' || it == 'E' }
    if (exponentIndex >= 0) fracRemovedDecExpZeros = fracRemovedDecExpZeros.substring(0,  exponentIndex)
    fracRemovedDecExpZeros = fracRemovedDecExpZeros.dropWhile { it == '0' }

    val fractionnalPartSize = fracRemovedDecExpZeros.length
    val integerPartSize = integerPart.substring(0, integerPart.indexOfFirst { it == '.' }).length

    val pattern = "%" + integerPartSize + "." + (integerPartSize + fractionnalPartSize - 1) + "e"
    return String.format(Locale.US, pattern, number)
            .replace("e+0", "e+")
            .replace("e-0", "e-")
}

private fun Double.toStringDigitsJVM(digits: Int): String {
    if (this > Long.MAX_VALUE || this < Long.MIN_VALUE) return this.toExponential()
    return (this).roundToLong().toString(digits)
}

private fun Double.toFixedJVM(digits: Int): String {
    return BigDecimal(this.toString()).setScale(digits, BigDecimal.ROUND_HALF_UP).toString()
}

internal actual fun Double.toStringDigits(digits: Int): String = toStringDigitsJVM(digits)
internal actual fun Double.toFixed(digits: Int): String = toFixedJVM(digits)
internal actual fun Double.toExponential(digits: Int): String = formatExponentialJVM(this, digits)
internal actual fun Double.toExponential(): String = formatExponentialJVM(this)
internal actual fun Double.toPrecision(digits: Int): String = toPrecisionJVM(this, digits)

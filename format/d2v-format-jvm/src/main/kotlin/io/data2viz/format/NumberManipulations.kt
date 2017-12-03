package io.data2viz.format

import java.math.BigDecimal
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToLong


internal actual fun Double.toStringDigits(radix: Int): String = 
        if (this > Long.MAX_VALUE || this < Long.MIN_VALUE) 
            toExponential() 
        else 
            (this).roundToLong().toString(radix)


internal actual fun Double.toFixed(digits: Int): String = 
        BigDecimal(this)
                .setScale(digits, BigDecimal.ROUND_HALF_UP)
                .toString()

internal actual fun Double.toExponential(digits: Int): String = 
        String.format(Locale.US, "%." + digits + "e", this)
        .replace("e+0", "e+")
        .replace("e-0", "e-")

internal actual fun Double.toExponential(): String {
    val preciseNum = BigDecimal(abs(this).toString())
    val fractionalPart = (preciseNum % BigDecimal.ONE).toString()
    val integerPart = preciseNum.div(BigDecimal.ONE).toString()
    var fracRemovedDecExpZeros = fractionalPart.substring(fractionalPart.indexOfFirst { it == '.' }+1, fractionalPart.length)
    val exponentIndex = fracRemovedDecExpZeros.indexOfFirst { it == 'e' || it == 'E' }
    if (exponentIndex >= 0) fracRemovedDecExpZeros = fracRemovedDecExpZeros.substring(0,  exponentIndex)
    fracRemovedDecExpZeros = fracRemovedDecExpZeros.dropWhile { it == '0' }
    val fractionalPartSize = fracRemovedDecExpZeros.length
    val integerPartSize = integerPart.substring(0, integerPart.indexOfFirst { it == '.' }).length
    val pattern = "%" + integerPartSize + "." + (integerPartSize + fractionalPartSize - 1) + "e"
    return String.format(Locale.US, pattern, this)
            .replace("e+0", "e+")
            .replace("e-0", "e-")
}


internal actual fun Double.toPrecision(digits: Int): String = 
        String.format(Locale.US, "%." + digits + "g", this)
        .replace("e+0", "e+")
        .replace("e-0", "e-")



// TODO keep this, it will avoid errors when asking for high precision numbers (see FormatTests@check_platform_dependent_formatters_toFixed)
/*private fun Double.toFixedJVM(digits: Int): String {
    return BigDecimal(this.toString()).setScale(digits, BigDecimal.ROUND_HALF_UP).toString()
}*/

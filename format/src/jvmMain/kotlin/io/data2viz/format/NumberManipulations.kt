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
    val commaPosition = integerPart.indexOfFirst { it == '.' }
    val integerPartSize = if (commaPosition >= 0) integerPart.substring(0, commaPosition).length else integerPart.length
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

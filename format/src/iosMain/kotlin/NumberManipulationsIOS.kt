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

import platform.Foundation.*

import kotlin.math.roundToLong
import kotlin.math.abs
import kotlin.math.roundToLong


internal actual fun Double.toExponential(digits: Int): String =
    NSString.stringWithFormat("%.${digits}e", this).fixScientificExponent()


private val SCIENTIFIC: Regex = """(.+)(e[+-])([0]+)(.+)""".toRegex()


/**
 * As defined by the IEEE's printf specification (https://pubs.opengroup.org/onlinepubs/009695399/functions/printf.html)
 * , the exponent should contains at least two digits (123e+02) but we "prefer" a shorter version (123e+2)
 */
private fun String.fixScientificExponent(): String =
    when(val result = SCIENTIFIC.find(this)) {
        null -> this
        else -> result.groupValues[1] + result.groupValues[2] + result.groupValues[4]
    }


internal actual fun Double.toPrecision(digits: Int): String =
    NSString.stringWithFormat("%.${digits}g", this).fixScientificExponent()


internal actual fun Double.toStringDigits(radix:Int): String =
    if (this > Long.MAX_VALUE || this < Long.MIN_VALUE)
        this.toExponential()
    else this.roundToLong().toString(radix)

internal actual fun Double.toFixed(digits: Int): String =
    NSString.stringWithFormat("%.${digits}f", this)


internal actual fun Double.toExponential(): String {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterScientificStyle
    formatter.locale = NSLocale.localeWithLocaleIdentifier("en_US")
    formatter.positiveFormat = "0.###E+0"
    formatter.exponentSymbol = "e"
    return formatter.stringFromNumber(NSNumber(this)) ?: this.toString()
}

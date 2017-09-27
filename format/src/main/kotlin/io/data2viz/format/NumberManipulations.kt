package io.data2viz.format

import kotlin.js.Math

internal fun Double.toStringDigits(digits: Int): String = Math.round(this).toStringDigits(digits)
internal fun Double.toFixed(digits: Int): String = this.asDynamic().toFixed(digits)
internal fun Double.toExponential(digits: Int): String = this.asDynamic().toExponential(digits)
internal fun Double.toExponential(): String = this.asDynamic().toExponential()
internal fun Double.toPrecision(digits: Int): String = this.asDynamic().toPrecision(digits)
internal fun Int.toStringDigits(digits: Int): String = this.asDynamic().toString(digits)

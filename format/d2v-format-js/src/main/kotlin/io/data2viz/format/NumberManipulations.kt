package io.data2viz.format

import kotlin.math.roundToLong


internal actual fun Double.toStringDigits(digits: Int): String = (this).roundToLong().asDynamic().toString(digits)
internal actual fun Double.toFixed(digits: Int): String = this.asDynamic().toFixed(digits)
internal actual fun Double.toExponential(digits: Int): String = this.asDynamic().toExponential(digits)
internal actual fun Double.toExponential(): String = this.asDynamic().toExponential()
internal actual fun Double.toPrecision(digits: Int): String = this.asDynamic().toPrecision(digits)

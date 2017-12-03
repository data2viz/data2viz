package io.data2viz.format

import kotlin.math.round


internal actual fun Double.toStringDigits(radix: Int): String = round(this).asDynamic().toString(radix)
internal actual fun Double.toFixed(digits: Int): String = this.asDynamic().toFixed(digits)
internal actual fun Double.toExponential(digits: Int): String = this.asDynamic().toExponential(digits)
internal actual fun Double.toExponential(): String = this.asDynamic().toExponential()
internal actual fun Double.toPrecision(digits: Int): String = this.asDynamic().toPrecision(digits)

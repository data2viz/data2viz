package io.data2viz.format

internal expect fun Double.toStringDigits(digits: Int): String
internal expect fun Double.toFixed(digits: Int): String
internal expect fun Double.toExponential(digits: Int): String
internal expect fun Double.toExponential(): String
internal expect fun Double.toPrecision(digits: Int): String
internal expect fun Int.toStringDigits(digits: Int): String

package io.data2viz.format


/**
 * Theses are the minimum subset of multiplatform functions mandatory to provide formating
 * on all platforms.
 */


internal expect fun Double.toStringDigits(radix: Int): String
internal expect fun Double.toFixed(digits: Int): String
internal expect fun Double.toExponential(digits: Int): String
internal expect fun Double.toExponential(): String
internal expect fun Double.toPrecision(digits: Int): String

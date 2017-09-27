package io.data2viz.format

import kotlin.js.Math


impl internal fun Double.toStringDigits(digits: Int): String = Math.round(this).toStringDigits(digits)
impl internal fun Double.toFixed(digits: Int): String = this.asDynamic().toFixed(digits)
impl internal fun Double.toExponential(digits: Int): String = this.asDynamic().toExponential(digits)
impl internal fun Double.toExponential(): String = this.asDynamic().toExponential()
impl internal fun Double.toPrecision(digits: Int): String = this.asDynamic().toPrecision(digits)
impl internal fun Int.toStringDigits(digits: Int): String = this.asDynamic().toString(digits)

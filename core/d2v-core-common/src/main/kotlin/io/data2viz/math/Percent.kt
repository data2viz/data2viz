package io.data2viz.math

inline class Percent(val value:Double) {
    fun normalize(): Percent              = Percent(value.coerceIn(.0, 100.0))

    operator fun plus(other: Percent)     = Percent(value + other.value)
    operator fun minus(other: Percent)    = Percent(value - other.value)
    operator fun times(other: Percent)    = Percent(value * value)
    operator fun times(d: Number)         = Percent(value * d.toDouble())
    operator fun div(d: Number)           = Percent(value / d.toDouble())
}

val Number.pct:Percent
    get() = Percent(toDouble() / 100.0)
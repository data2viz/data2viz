package io.data2viz.math

operator fun Number.times(percent:Percent) = Percent(percent.value * toDouble())

inline class Percent(val value:Double) {

    fun coerceAtLeast(min:Percent)          = Percent(value.coerceAtLeast(min.value))
    fun coerceAtMost(max:Percent)           = Percent(value.coerceAtMost(max.value))
    fun coerceIn(min:Percent, max:Percent)  = Percent(value.coerceIn(min.value, max.value))
    fun coerceToDefault(): Percent          = Percent(value.coerceIn(.0, 1.0))                  // TODO: rename ?

    operator fun plus(other: Percent)       = Percent(value + other.value)
    operator fun minus(other: Percent)      = Percent(value - other.value)
    operator fun times(other: Percent)      = Percent(value * other.value)
    operator fun unaryMinus()               = Percent(-value)
    operator fun unaryPlus()                = this
    operator fun compareTo(other:Percent)   = this.value.compareTo(other.value)

    operator fun times(d: Number)           = Percent(value * d.toDouble())
    operator fun div(d: Number)             = Percent(value / d.toDouble())

    override fun toString(): String {
        return "${value*100}%"
    }
}

val Number.pct:Percent
    get() = Percent(toDouble() / 100.0)
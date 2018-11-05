package io.data2viz.math

inline class Percent(val value:Double) {

    fun coerceAtLeast(minimumValue:Double)  = Percent(value.coerceAtLeast(minimumValue))
    fun coerceAtMost(maximumValue:Double)   = Percent(value.coerceAtLeast(maximumValue))
    fun coerceIn(min:Double, max:Double)    = Percent(value.coerceIn(min, max))
    fun coerceToDefault(): Percent          = coerceIn(.0, 1.0)

    operator fun plus(other: Percent)       = Percent(value + other.value)
    operator fun minus(other: Percent)      = Percent(value - other.value)
    operator fun times(other: Percent)      = Percent(value * value)
    operator fun times(d: Number)           = Percent(value * d.toDouble())
    operator fun div(d: Number)             = Percent(value / d.toDouble())

    override fun toString(): String {
        return "${value*100}%"
    }
}

val Number.pct:Percent
    get() = Percent(toDouble() / 100.0)
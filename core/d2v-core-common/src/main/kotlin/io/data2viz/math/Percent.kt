package io.data2viz.math


/**
 * Utility class used to represent a percentage value.
 * You can create a Percent using the default constructor:
 *  - val p = Percent(0.15)             // 15%
 *
 *  But it is easier to do so using the extension value ".pct":
 *  - val p = 15.pct                    //15%
 */
inline class Percent(val value:Double) {

    operator fun plus(other: Percent)       = Percent(value + other.value)
    operator fun minus(other: Percent)      = Percent(value - other.value)
    operator fun times(other: Percent)      = Percent(value * other.value)
    operator fun div(d: Number)             = Percent(value / d.toDouble())

    /**
     * Operator to allow to write `20.pct * 3` (= 0.6)
     */
    operator fun times(d: Number)           = value * d.toDouble()
    operator fun unaryMinus()               = Percent(-value)

    operator fun unaryPlus()                = this
    operator fun compareTo(other:Percent)   = this.value.compareTo(other.value)


    /**
     * Ensures that this percent is not less than the specified min.
     * @return this percent if it's greater than or equal to the min or the min otherwise.
     */
    fun coerceAtLeast(min:Percent)          = Percent(value.coerceAtLeast(min.value))

    /**
     * Ensures that this percent is not greater than the specified max.
     * @return this percent if it's less than or equal to the max or the max otherwise.
     */
    fun coerceAtMost(max:Percent)           = Percent(value.coerceAtMost(max.value))

    /**
     * Ensures that this percent lies in the specified range min..max.
     *
     * @return this value if it's in the range, or minimumValue if this value is less than minimumValue,
     * or maximumValue if this value is greater than maximumValue.
     */
    fun coerceIn(min:Percent, max:Percent)  = Percent(value.coerceIn(min.value, max.value))

    /**
     * if > 100% returns 100%,
     * if < 0% returns 0%,
     * else return the current value.
     */
    fun coerceToDefault(): Percent          = Percent(value.coerceIn(.0, 1.0))                  // TODO: rename ?


    override fun toString(): String = "${value*100}%"
}

/**
 * Extension property to create a percent from any number.
 */
val Number.pct:Percent
    get() = Percent(toDouble() / 100.0)

/**
 * Extension operator to allow to write `3 * 20.pct` (= 0.6)
 */
operator fun Number.times(percent:Percent) = percent.value * toDouble()
/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.math

import kotlin.jvm.JvmInline


/**
 * Utility class used to represent a percentage value.
 * You can create a Percent using the default constructor:
 *  - val p = Percent(0.15)             // 15%
 *
 *  But it is easier to do so using the extension value ".pct":
 *  - val p = 15.pct                    //15%
 */
@JvmInline
public value class Percent(
    public val value:Double) {

    public operator fun plus(other: Percent)       :Percent = Percent(value + other.value)
    public operator fun minus(other: Percent)      :Percent = Percent(value - other.value)
    public operator fun times(other: Percent)      :Percent = Percent(value * other.value)
    public operator fun div(d: Number)             :Percent = Percent(value / d.toDouble())

    /**
     * Operator to allow to write `20.pct * 3` (= 0.6)
     */
    public operator fun times(d: Number)           :Double = value * d.toDouble()
    public operator fun unaryMinus()               :Percent = Percent(-value)
    public operator fun unaryPlus()                :Percent = this
    public operator fun compareTo(other:Percent)   : Int = this.value.compareTo(other.value)


    /**
     * Ensures that this percent is not less than the specified min.
     * @return this percent if it's greater than or equal to the min or the min otherwise.
     */
    public fun coerceAtLeast(min:Percent): Percent = Percent(value.coerceAtLeast(min.value))

    /**
     * Ensures that this percent is not greater than the specified max.
     * @return this percent if it's less than or equal to the max or the max otherwise.
     */
    public fun coerceAtMost(max:Percent): Percent = Percent(value.coerceAtMost(max.value))

    /**
     * Ensures that this percent lies in the specified range min..max.
     *
     * @return this value if it's in the range, or minimumValue if this value is less than minimumValue,
     * or maximumValue if this value is greater than maximumValue.
     */
    public fun coerceIn(min:Percent, max:Percent): Percent = Percent(value.coerceIn(min.value, max.value))

    /**
     * if > 100% returns 100%,
     * if < 0% returns 0%,
     * else return the current value.
     */
    public fun coerceToDefault(): Percent          = Percent(value.coerceIn(.0, 1.0))                  // TODO: rename ?


    override fun toString(): String = "${value*100}%"
}

/**
 * Extension property to create a percent from any number.
 */
public val Number.pct:Percent
    get() = Percent(toDouble() / 100.0)

/**
 * Extension operator to allow to write `3 * 20.pct` (= 0.6)
 */
public operator fun Number.times(percent:Percent): Double = percent.value * toDouble()

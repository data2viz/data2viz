/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.geom

/**
 * Creates a point.
 */
public fun point(x: Double, y: Double): Point = Point(x, y)

/**
 * Creates a point from Ints.
 */
public fun point(x: Int, y: Int) = Point(x.toDouble(), y.toDouble())

public data class Point(
    val x: Double = 0.0,
    val y: Double = 0.0) {

    public companion object {
        val origin = Point()
    }
    public operator fun plus(vector: Vector): Point = Point(x + vector.vx, y + vector.vy)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun div(value:Number) = Point(x / value.toDouble(), y / value.toDouble())
    operator fun times(value:Number) = Point(x * value.toDouble(), y * value.toDouble())

    operator fun unaryMinus(): Point = Point(-x, -y)
}
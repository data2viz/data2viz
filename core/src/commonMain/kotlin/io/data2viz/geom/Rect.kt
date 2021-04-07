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

import io.data2viz.ExperimentalD2V
import io.data2viz.math.Angle
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

public interface Rect: HasSize {

    public var x: Double
    public var y: Double

    public val top: Double
        get() = y

    public val bottom: Double
        get() = y + height


    public val left: Double
        get() = x

    public val right: Double
        get() = x + width


    public val topLeft: Point
        get() = Point(x, y)

    public val topRight: Point
        get() = Point(x + width, y)

    public val bottomLeft: Point
        get() = Point(x, y + height)

    public val bottomRight: Point
        get() = Point(x + width, y + height)

    public val center: Point
        get() = Point(x + .5 * width, y + .5 * height)

    public operator fun contains(point: Point): Boolean {
        val x = point.x
        val y = point.y
        return x >= this.x && y >= this.y
                && x <= this.x + this.width
                && y <= this.y + this.height
    }

    public operator fun contains(rect: Rect): Boolean {
        val x = rect.x
        val y = rect.y
        return x >= this.x && y >= this.y
                && x + rect.width <= this.x + this.width
                && y + rect.height <= this.y + this.height
    }

}

/**
 * Return the "bounding box" of a Rect that has a rotation of rotation radians at the (0,0) coordinates.
 */
public fun Rect.boundsWith(rotation: Angle): Rect {
    if (abs(rotation.normalize().rad) < 1e-3) return RectGeom(x, y, width, height)

    val cos = rotation.cos
    val sin = rotation.sin

    val rxcos = x * cos
    val rxsin = x * sin
    val rwcos = width * cos
    val rwsin = width * sin
    val rysin = y * sin
    val rycos = y * cos
    val rhcos = height * cos
    val rhsin = height * sin

    // compute point ABCD of the rotated rectangle
    val a = Point(rxcos - rysin, rxsin + rycos)
    val b = Point(a.x + rwcos, a.y + rwsin)
    val c = Point(b.x - rhsin, b.y + rhcos)
    val d = Point(a.x - rhsin, a.y + rhcos)

    val minX = min(a.x, min(b.x, min(c.x, d.x)))
    val minY = min(a.y, min(b.y, min(c.y, d.y)))
    val maxX = max(a.x, max(b.x, max(c.x, d.x)))
    val maxY = max(a.y, max(b.y, max(c.y, d.y)))

    return RectGeom(minX, minY, maxX - minX, maxY - minY)
}


/**
 * Return true if a rect overlap another rect.
 */
public fun Rect.overlap(r: Rect):Boolean = left < r.right && right > r.left && top < r.bottom && bottom > r.top

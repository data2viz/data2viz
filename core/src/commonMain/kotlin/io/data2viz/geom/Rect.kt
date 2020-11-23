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
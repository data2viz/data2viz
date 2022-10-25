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

package io.data2viz.geom

/**
 * Creates a size.
 */
public fun size(x: Double, y: Double): Size = Size(x, y)

/**
 * Creates a size from Ints.
 */
public fun size(x: Int, y: Int): Size = Size(x.toDouble(), y.toDouble())

/**
 * Size represents a rectangle dimension. The second parameter of the constructor (height)
 * is option and takes by default the same value as the width.
 */
public data class Size(val width: Double, val height: Double = width) {

    public operator fun plus(value: Double): Size  = Size(width + value, height + value)
    public operator fun minus(value: Double): Size = Size(width - value, height - value)
    public operator fun times(value: Double): Size = Size(width * value, height * value)
    public operator fun div(value: Double): Size   = Size(width / value, height / value)
    public operator fun rem(value: Double): Size   = Size(width % value, height % value)
    public operator fun plus(size: Size): Size     = Size(width + size.width, height + size.height)
    public operator fun minus(size: Size): Size    = Size(width - size.width, height - size.height)
    public operator fun times(size: Size): Size    = Size(width * size.width, height * size.height)
    public operator fun div(size: Size): Size      = Size(width / size.width, height / size.height)
    public operator fun rem(size: Size): Size      = Size(width % size.width, height % size.height)
}

/**
 * Indicates a class that has a width and a height. Adds a size property to
 * set and get width and height in a single line.
 */
public interface HasSize {

    public var width: Double
    public var height: Double

    public var size:Size
        get() = Size(width, height)
        set(value) {
            width = value.width
            height = value.height
        }
}

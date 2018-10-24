package io.data2viz.geom

/**
 * Size represents a rectangle dimension. The second parameter of the constructor (height)
 * is option and takes by default the same value as the width.
 */
data class Size(val width: Double, val height: Double = width) {

    operator fun plus(value: Double): Size  = Size(width + value, height + value)
    operator fun minus(value: Double): Size = Size(width - value, height - value)
    operator fun times(value: Double): Size = Size(width * value, height * value)
    operator fun div(value: Double): Size   = Size(width / value, height / value)
    operator fun rem(value: Double): Size   = Size(width % value, height % value)

    operator fun plus(size: Size): Size     = Size(width + size.width, height + size.height)
    operator fun minus(size: Size): Size    = Size(width - size.width, height - size.height)
    operator fun times(size: Size): Size    = Size(width * size.width, height * size.height)
    operator fun div(size: Size): Size      = Size(width / size.width, height / size.height)
    operator fun rem(size: Size): Size      = Size(width % size.width, height % size.height)
}

/**
 * Indicates a class that has a width and a height. Adds an size property to
 * set and get width and height in a single line.
 */
interface HasSize {

    var width: Double
    var height: Double

    var size:Size
        get() = Size(width, height)
        set(value) {
            width = value.width
            height = value.height
        }
}
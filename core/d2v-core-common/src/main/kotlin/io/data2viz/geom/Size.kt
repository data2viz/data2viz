package io.data2viz.geom

/**
 * Size represents a rectangle dimension. The second parameter of the constructor (height)
 * is option and takes by default the same value as the width.
 */
data class Size(val width: Double, val height: Double = width)

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
package io.data2viz.geom


data class Size(val width: Double, val height: Double)

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
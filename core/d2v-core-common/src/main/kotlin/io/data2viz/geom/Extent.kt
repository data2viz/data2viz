package io.data2viz.geom


/**
 * Todo this is really representing a rectangle. Is there a reason to name it Extent.
 * Todo make it immutable (data class)
 */
class Extent(
    var x0: Double, var y0: Double,
    var x1: Double, var y1: Double)
{
    var width
        get() = x1 - x0
        set(value) {
            x0 = .0
            x1 = value
        }

    var height
        get() = y1 - y0
        set(value) {
            y0 = .0
            y1 = value
        }

    fun copy(): Extent {
        return Extent(x0, y0, x1, y1)
    }
}
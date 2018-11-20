package io.data2viz.geom

// TODO : remove access to x1, y1, x2, y2
interface HasStartAndEnd {
    var x1: Double
    var y1: Double
    var x2: Double
    var y2: Double

    var start: Point
        get() = Point(x1, y1)
        set(value) {
            x1 = value.x
            y1 = value.y
        }

    var end: Point
        get() = Point(x2, y2)
        set(value) {
            x2 = value.x
            y2 = value.y
        }
}

interface HasPosition {
    var x: Double
    var y: Double

    var position: Point
        get() = Point(x, y)
        set(value) {
            x = value.x
            y = value.y
        }
}

/**
 * Creates a point.
 */
fun point(x: Double, y: Double) = Point(x, y)

/**
 * Creates a point from Ints.
 */
fun point(x: Int, y: Int) = Point(x.toDouble(), y.toDouble())

data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    companion object {
        val origin = Point()
    }

    operator fun plus(vector: Vector) = Point(x + vector.vx, y + vector.vy)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun div(value: Number) = Point(x / value.toDouble(), y / value.toDouble())
    operator fun times(value: Number) = Point(x * value.toDouble(), y * value.toDouble())

    operator fun unaryMinus(): Point = Point(-x, -y)
}
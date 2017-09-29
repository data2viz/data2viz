package io.data2viz.core


data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    companion object {
        val origin = Point()
    }
    operator fun plus(speed: Speed) = Point(x + speed.vx, y + speed.vy)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun div(value:Number) = Point(x/value.toDouble(), y/value.toDouble())
}

data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
}

expect fun random():Double

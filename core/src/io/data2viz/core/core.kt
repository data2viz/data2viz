package io.data2viz.core




data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    companion object {
        val origin = Point()
    }
    operator fun plus(speed: Speed) = Point(x.toDouble() + speed.vx, y.toDouble() + speed.vy)
    operator fun plus(other: Point) = Point(x.toDouble() + other.x.toDouble(), y.toDouble() + other.y.toDouble())
    operator fun div(value:Number) = Point(x.toDouble()/value.toDouble(), y.toDouble()/value.toDouble())
}

data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
}

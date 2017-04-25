package io.data2viz.core




data class Point(val x: Number = 0.0, val y: Number = 0.0) {

    companion object {
        val origin = Point()
    }
    operator fun plus(speed: Speed) = Point(x.toDouble() + speed.vx, y.toDouble() + speed.vy)
}

data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
}

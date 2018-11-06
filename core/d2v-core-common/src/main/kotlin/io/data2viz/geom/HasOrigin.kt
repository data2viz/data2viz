package io.data2viz.geom

// TODO : remove access to cx, cy, leave only access to origin
interface HasOrigin {
    var cx: Double
    var cy: Double

    var origin:Point
        get() = Point(cx,cy)
        set(value) {
            cx = value.x
            cy = value.y
        }
}
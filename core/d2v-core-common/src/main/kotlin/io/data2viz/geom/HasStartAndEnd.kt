package io.data2viz.geom

// TODO : remove access to x1, y1, x2, y2
// TODO : valid wording : start+end or from+to or ...
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
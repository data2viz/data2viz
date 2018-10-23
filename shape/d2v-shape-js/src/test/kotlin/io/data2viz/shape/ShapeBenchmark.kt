package io.data2viz.shape

import io.data2viz.geom.PathGeom
import io.data2viz.shape.curve.Point


var lineGenerator = line<Point> {
        x = { it.x.toDouble() }
        y = { it.y.toDouble() }
    defined = {!(it.x % 7 == 0)}
    }


val points = listOf(
        Point(0, 0),
        Point(1, 1),
        Point(2, 2),
        Point(3, 2),
        Point(4, 2),
        Point(5, 2),
        Point(6, 2),
        Point(7, 2),
        Point(8, 2),
        Point(9, 2),
        Point(10, 2),
        Point(11, 1),
        Point(12, 2),
        Point(13, 2),
        Point(14, 2),
        Point(15, 2),
        Point(16, 2),
        Point(17, 2),
        Point(18, 2),
        Point(19, 2),
        Point(20, 2),
        Point(21, 1),
        Point(22, 2),
        Point(23, 2),
        Point(24, 2),
        Point(25, 2),
        Point(26, 2),
        Point(27, 2),
        Point(28, 2),
        Point(29, 2),
        Point(30, 2),
        Point(31, 1),
        Point(32, 2),
        Point(33, 2),
        Point(34, 2),
        Point(35, 2),
        Point(36, 2),
        Point(37, 2),
        Point(38, 2),
        Point(39, 2),
        Point(40, 2),
        Point(41, 1),
        Point(42, 2),
        Point(43, 2),
        Point(44, 2),
        Point(45, 2),
        Point(46, 2),
        Point(47, 2),
        Point(48, 2),
        Point(49, 2)
)


@JsName("generatePath")
fun generatePath() {
    lineGenerator.buildLine(points, PathGeom())
}


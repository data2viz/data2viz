package io.data2viz.shape

import io.data2viz.path.SvgPath
import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class LineTest : TestBase() {

    @Test
    fun define() {
        assertEquals("M1,1ZM3,3L5,5".round(), line {
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
            defined = { it.x != 2 }
        }.toPath(Point(1, 1), Point(2, 2), Point(3, 3), Point(5, 5)).round())
    }


    @Test
    fun yConstant() {
        assertEquals("M0,5.5L0,5.5".round(), line {
            y = const(5.5)
        }.toPath(Point(1, 1), Point(2, 2)).round())
    }

    @Test
    fun xConstant() {
        assertEquals("M5.5,0L5.5,0".round(), line {
            x = const(5.5)
        }.toPath(Point(1, 1), Point(2, 2)).round())
    }


    data class Point(val x: Int, val y: Int)


    fun line(init: LineGenerator<Point>.() -> Unit): LineGenerator<Point> {
        return LineGenerator<Point>().apply(init)
    }

    private fun LineGenerator<Point>.toPath(vararg points: Point) =
            this.line(points as Array<Point>, SvgPath()).path
}
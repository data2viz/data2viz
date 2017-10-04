package io.data2viz.shape


import io.data2viz.test.matchers.Matchers
import org.junit.Test


class LineTest: Matchers {

    @Test
    fun define(){
        line {
            x = { it.x.toDouble() }
            y = { it.x.toDouble() }
            defined = { it.x != 2 }
        }.toPath(Point(1, 1), Point(2, 2), Point(3, 3), Point(5, 5)) shouldBe "M1.0,1.0ZM3.0,3.0L5.0,5.0"
    }


    @Test
    fun yConstant(){
        line {
            y = io.data2viz.shape.const(5.5)
        }.toPath(Point(1, 1), Point(2, 2)) shouldBe "M0.0,5.5L0.0,5.5"
    }

    @Test
    fun xConstant(){
        line {
            x = io.data2viz.shape.const(5.5)
        }.toPath(Point(1, 1), Point(2, 2)) shouldBe "M5.5,0.0L5.5,0.0"
    }

}

data class Point(val x: Int, val y:Int)

fun line(init: LineGenerator<Point>.()-> Unit) =
        LineGenerator<Point>().apply(init)

private fun LineGenerator<Point>.toPath(vararg points: Point)  =
    this.line(points as Array<Point>, io.data2viz.path.SvgPath()).path

package curve

import io.data2viz.path.SvgPath
import io.data2viz.shape.LineGenerator
import io.data2viz.test.TestBase
import kotlin.test.Test


data class Point(val x:Number, val y:Number)

class CurveLinearTest: TestBase(){

    fun pt(x: Number, y: Number) = Point(x,y)

    fun line(vararg points:Point): String {
        val lineGenerator = LineGenerator<Point>().apply {
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val context = SvgPath()
        return lineGenerator.line(points as Array<Point>, context).path
    }

    @Test
    fun line_curve_generates_one_point() {
        line(pt(0,1)) shouldBe "M0,1Z"
    }


    @Test
    fun line_curve_generates_two_points() {
        line(pt(0, 1 ), pt(2,3)) shouldBe "M0,1L2,3"
    }

    @Test
    fun line_curve_generates_three_pts() {
        line(pt(0, 1 ), pt(2,3), pt(4,5)) shouldBe "M0,1L2,3L4,5"
    }

}


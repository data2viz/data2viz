package curve

import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath
import io.data2viz.shape.Curve
import io.data2viz.shape.LineGenerator
import io.data2viz.test.TestBase

data class Point(val x:Number, val y:Number)

open class CurveTest(val curve: (PathAdapter) -> Curve) : TestBase() {

    fun pt(x: Number, y: Number) = Point(x,y)

    fun line(vararg points:Point): String {
        val lineGenerator = LineGenerator<Point>().apply {
            curve = this@CurveTest.curve
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val context = SvgPath()
        return lineGenerator.line(points as Array<Point>, context).path.round()
    }

}

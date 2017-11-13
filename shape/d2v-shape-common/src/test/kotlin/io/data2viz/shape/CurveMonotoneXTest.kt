package io.data2viz.shape

import io.data2viz.path.SvgPath
import kotlin.test.Test
import kotlin.test.assertEquals

class CurveMonotoneXTest : CurveTest(curves.monotoneY, { it.y != 40 }) {

    val data = arrayOf(
            pt(20, 20),
            pt(30, 60),
            pt(70, 70),
            pt(80, 20),
            pt(70, 40),
            pt(100, 60),
            pt(150, 80),
            pt(155, 80),
            pt(180, 20)
    )

    /*@Test
    fun line_curve_monotoneX() {
        val lineGenerator = LineGenerator<Point>().apply {
            curve = curves.monotoneX
//            defined = { it.y != 40 }
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val context = SvgPath()

        assertEquals(("M20.0,20.0C23.333333333333332,39.16666666666667,26.666666666666668,58.333333333333336,30.0," +
                "60.0C43.333333333333336,66.66666666666667,56.666666666666664,70.0,70.0,70.0C73.33333333333333,70.0," +
                "76.66666666666667,33.333333333333336,80.0,20.0C76.66666666666667,33.333333333333336,73.33333333333333," +
                "40.0,70.0,40.0C80.0,40.0,90.0,54.333333333333336,100.0,60.0C116.66666666666667,69.44444444444444,133.33333333333334," +
                "80.0,150.0,80.0C151.66666666666666,80.0,153.33333333333334,80.0,155.0,80.0C163.33333333333334,80.0,171.66666666666666," +
                "50.0,180.0,20.0"),

                lineGenerator.line(data, context).path
        )
    }

    @Test
    fun line_curve_monotoneX_undefined_points() {
        val lineGenerator = LineGenerator<Point>().apply {
            curve = curves.monotoneX
            defined = { it.y != 40 }
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val context = SvgPath()

        assertEquals(("M20.0,20.0C23.333333333333332,39.16666666666667,26.666666666666668,58.333333333333336," +
                "30.0,60.0C43.333333333333336,66.66666666666667,56.666666666666664,70.0,70.0," +
                "70.0C73.33333333333333,70.0,76.66666666666667,45.0,80.0,20.0M100.0,60.0C116.66666666666667," +
                "70.0,133.33333333333334,80.0,150.0,80.0C151.66666666666666,80.0,153.33333333333334,80.0,155.0," +
                "80.0C163.33333333333334,80.0,171.66666666666666,50.0,180.0,20.0"),

                lineGenerator.line(data, context).path
        )
    }*/

}
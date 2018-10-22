package io.data2viz.shape

class CurveMonotoneYTest : CurveTest(curves.monotoneY, { it.y != 40 }) {

    /*@Test
    fun line_curve_monotoneY() {
        val lineGenerator = LineGenerator<Point>().apply {
            curve = curves.monotoneY
//            defined = { it.y != 40 }
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val context = SvgPath()
        assertEquals(("M20.0,20.0C21.666666666666668,33.333333333333336,23.333333333333332,46.666666666666664," +
                "30.0,60.0C31.666666666666668,63.333333333333336,70.0,66.66666666666667,70.0,70.0C70.0,53.33333333333333," +
                "73.33333333333333,36.66666666666667,80.0,20.0C77.33333333333333,26.666666666666668,70.0," +
                "33.333333333333336,70.0,40.0C70.0,46.666666666666664,86.66666666666667,53.333333333333336," +
                "100.0,60.0C113.33333333333333,66.66666666666667,116.66666666666666,73.33333333333333,150.0,80.0C150.0," +
                "80.0,155.0,80.0,155.0,80.0C171.66666666666666,60.0,175.83333333333334,40.0,180.0,20.0"),


                //line(points as Array<Point>, context).path.round()
                lineGenerator.line(arrayOf(
                        pt(20, 20),
                        pt(30, 60),
                        pt(70, 70),
                        pt(80, 20),
                        pt(70, 40),
                        pt(100, 60),
                        pt(150, 80),
                        pt(155, 80),
                        pt(180, 20)
                ), context
                ).path
        )
    }

    @Test
    fun line_curve_with_3_points_1_undefined() {
        val lineGenerator = LineGenerator<Point>().apply {
            curve = curves.monotoneY
            defined = { it.y != 40 }
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val context = SvgPath()

        assertEquals(("M20.0,20.0C21.666666666666668,33.333333333333336,23.333333333333332,46.666666666666664,30.0," +
                "60.0C31.666666666666668,63.333333333333336,70.0,66.66666666666667,70.0,70.0C70.0,53.33333333333333,75.0," +
                "36.66666666666667,80.0,20.0M100.0,60.0C108.33333333333333,66.66666666666667,116.66666666666666," +
                "73.33333333333333,150.0,80.0C150.0,80.0,155.0,80.0,155.0,80.0C171.66666666666666,60.0," +
                "175.83333333333334,40.0,180.0,20.0"),


                //line(points as Array<Point>, context).path.round()
                lineGenerator.line(arrayOf(
                        pt(20, 20),
                        pt(30, 60),
                        pt(70, 70),
                        pt(80, 20),
                        pt(70, 40),
                        pt(100, 60),
                        pt(150, 80),
                        pt(155, 80),
                        pt(180, 20)
                ), context
                ).path
        )
    }*/

}
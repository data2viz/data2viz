package curve

import io.data2viz.shape.curves
import kotlin.test.Test

class CurveBasisTest : CurveTest(curves.basis) {

    @Test
    fun line_curve_generates_one_point() {
        line(pt(0,1)) shouldBe "M0,1Z"
    }

    @Test
    fun line_curve_generates_two_points() {
        line(pt(0, 1 ), pt(1,3)) shouldBe "M0,1L1,3"
    }

    @Test
    fun line_curve_generates_three_pts() {
        line(pt(0, 1 ), pt(1,3), pt(2,1)) shouldBe "M0,1L0.166667,1.333333C0.333333,1.666667,0.666667,2.333333,1,2.333333C1.333333,2.333333,1.666667,1.666667,1.833333,1.333333L2,1"
    }

}

package curve

import io.data2viz.shape.curves
import kotlin.test.Test

class CurveLinearTest: CurveTest(curves.linear) {

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


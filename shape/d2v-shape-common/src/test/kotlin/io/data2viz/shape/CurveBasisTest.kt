package io.data2viz.shape

import kotlin.test.Test
import kotlin.test.assertEquals

class CurveBasisTest : CurveTest(curves.basis, {it.x != 10}) {

    @Test
    fun line_curve_generates_one_point() {
        assertEquals("M0,1Z".round(), line(pt(0,1)).round())
    }

    @Test
    fun line_curve_generates_two_points() {
        assertEquals("M0,1L1,3".round(), line(pt(0, 1 ), pt(1,3)).round())
    }

    // TODO : rounding workaround in toFixed
    // see java.lang.ArithmeticException: Rounding necessary
    /*
    @Test
    fun line_curve_generates_three_pts() {
        assertEquals("M0,1L0.166667,1.333333C0.333333,1.666667,0.666667,2.333333,1,2.333333C1.333333,2.333333,1.666667,1.666667,1.833333,1.333333L2,1".round(),
                line(pt(0, 1 ), pt(1,3), pt(2,1)).round())
    }*/


    @Test
    fun line_curve_with_2_points_1_undefined() {
        assertEquals("M0,1Z".round(), line(pt(0,1), pt(10, 1)).round())
    }

    // TODO : actually fails, need to modify BASIC curve
    @Test
    fun line_curve_with_3_points_1_undefined() {
        assertEquals("M0,1ZM3,3Z".round(), line(pt(0,1), pt(10, 1), pt(3, 3)).round())
    }

}
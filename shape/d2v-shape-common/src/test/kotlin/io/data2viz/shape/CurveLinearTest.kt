package io.data2viz.shape

import kotlin.test.Test
import kotlin.test.assertEquals

class CurveLinearTest : CurveTest(curves.linear) {

    @Test
    fun line_curve_generates_one_point() {
        assertEquals("M0,1Z".round(), line(pt(0, 1)).round())
    }

    @Test
    fun line_curve_generates_two_points() {
        assertEquals("M0,1L2,3".round(), line(pt(0, 1), pt(2, 3)).round())
    }

    @Test
    fun line_curve_generates_three_pts() {
        assertEquals("M0,1L2,3L4,5".round(), line(pt(0, 1), pt(2, 3), pt(4, 5)).round())
    }

}
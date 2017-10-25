package io.data2viz.contour

import io.data2viz.test.TestBase
import kotlin.test.Test



class ContourJsTests : TestBase() {

    fun p(x: Number, y: Number) = arrayOf(x.toDouble(), y.toDouble())


    @Test
    fun collinear() {
        io.data2viz.contour.collinear(p(0, 0), p(1, 1), p(2, 2)) shouldBe true
        io.data2viz.contour.collinear(p(0, 0), p(1, 1), p(2, 1)) shouldBe false
        io.data2viz.contour.collinear(p(0, 0), p(2, 2), p(1, 1)) shouldBe true
    }

}

@JsName("squaredContour")
fun squaredContour(size: Int) = contour {
    size(size, size)
    thresholds = { arrayOf(0.5) }
}

@JsName("contours")
fun contours(n: Int, m:Int, thresholds:Array<Double>) = contour {
    size(n, m)
    this.thresholds = { thresholds }
}

@JsName("contour")
fun Contour.jsContour(values: Array<Double>) {
    contours(values)
}






package io.data2viz.math

import io.data2viz.geom.Point


/**
 * @name Matrix
 *
 * @class An affine transformation matrix performs a linear mapping from 2D
 *     coordinates to other 2D coordinates that preserves the "straightness" and
 *     "parallelness" of lines.
 *
 * Such a coordinate transformation can be represented by a 3 row by 3
 * column matrix with an implied last row of `[ 0 0 1 ]`. This matrix
 * transforms source coordinates `(x, y)` into destination coordinates `(x',y')`
 * by considering them to be a column vector and multiplying the coordinate
 * vector by the matrix according to the following process:
 *
 *     [ x ]   [ a  c  tx ] [ x ]   [ a * x + c * y + tx ]
 *     [ y ] = [ b  d  ty ] [ y ] = [ b * x + d * y + ty ]
 *     [ 1 ]   [ 0  0  1  ] [ 1 ]   [         1          ]
 *
 * Note the locations of b and c.
 *
 * This class is optimized for speed and minimizes calculations based on its
 * knowledge of the underlying matrix (as opposed to say simply performing
 * matrix multiplication).
 */
data class Matrix(
    var a: Double = 1.0,
    var b: Double = 0.0,
    var c: Double = 0.0,
    var d: Double = 1.0,
    var tx: Double = 0.0,
    var ty: Double = 0.0
) {

    fun reset(): Matrix {
        a = 1.0
        d = 1.0
        b = .0
        c = .0
        tx = .0
        ty = .0
        return this
    }

    fun translate(x: Double, y: Double): Matrix {
        tx += x * a + y * c
        ty += x * a + y * d
        return this
    }

    fun translate(pt: Point) = translate(pt.x, pt.y)

    fun scale(scaleX: Double, scaleY: Double, center: Point? = null): Matrix {

        center?.let {
            translate(it)
        }
        a *= scaleX
        b *= scaleX
        c *= scaleY
        d *= scaleY
        center?.let {
            translate(-it)
        }
        return this
    }

    fun rotate(angle: Angle, center: Point? = null): Matrix {
        val cos = angle.cos
        val sin = angle.sin
        val tempA = a
        val tempB = b
        val tempC = c
        val tempD = d

        a = cos * tempA + sin * tempC
        b = cos * tempB + sin * tempD
        c = -sin * tempA + cos * tempC
        d = -sin * tempB + cos * tempD

        center?.let {
            val x = it.x
            val y = it.y
            val tempTx = x - x * cos + y * sin
            val tempTy = y - x * sin - y * cos
            tx += tempTx * tempA + tempTy * tempC
            ty += tempTx * tempB + tempTy * tempD
        }
        return this
    }


}
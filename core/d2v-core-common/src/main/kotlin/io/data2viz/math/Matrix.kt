package io.data2viz.math

import io.data2viz.geom.*

/**
 * An affine transformation matrix performs a linear mapping from 2D
 * coordinates to other 2D coordinates that preserves the "straightness" and
 * "parallelness" of lines.
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
 *
 * todo should we accept non invertible matrix (scale = .0 or infinite transformation params)?
 * https://math.stackexchange.com/questions/2875241/what-does-a-non-invertible-affine-transformation-look-like-geometrically-in-term
 *
 * todo make it immutable?
 */
data class Matrix(
    internal var a: Double = 1.0,
    internal var b: Double = 0.0,
    internal var c: Double = 0.0,
    internal var d: Double = 1.0,
    internal var tx: Double = 0.0,
    internal var ty: Double = 0.0
) {

    /**
     * Change transformation parameters to make the transformation an identity.
     */
    fun reset(): Matrix {
        a = 1.0
        d = 1.0
        b = .0
        c = .0
        tx = .0
        ty = .0
        return this
    }

    fun isIdentity() = (a == 1.0
                    && b == .0
                    && c == .0
                    && d == 1.0
                    && tx == .0
                    && ty == .0)


    /**
     * Appends the specified matrix to this matrix. This is the equivalent of
     * multiplying `(this matrix) * (specified matrix)`.
     *
     * @param {Matrix} matrix the matrix to append
     * @return {Matrix} this matrix, modified
     */
    fun append (other: Matrix): Matrix {
        val a1 = a
        val b1 = b
        val c1 = c
        val d1 = d
        val a2 = other.a
        val b2 = other.b
        val c2 = other.c
        val d2 = other.d
        val tx2 = other.tx
        val ty2 = other.ty

        a = a2 * a1 + c2 * c1
        c = b2 * a1 + d2 * c1
        b = a2 * b1 + c2 * d1
        d = b2 * b1 + d2 * d1
        tx += tx2 * a1 + ty2 * c1
        ty += tx2 * b1 + ty2 * d1
        return this
    }

    /**
     * Prepends the specified matrix to this matrix. This is the equivalent of
     * multiplying `(specified matrix) * (this matrix)`.
     *
     * @param {Matrix} matrix the matrix to prepend
     * @return {Matrix} this matrix, modified
     */
    fun prepend(mx: Matrix): Matrix {
            val a1 = a
            val b1 = b
            val c1 = c
            val d1 = d
            val tx1 = tx
            val ty1 = ty
            val a2 = mx.a
            val b2 = mx.c
            val c2 = mx.b
            val d2 = mx.d
            val tx2 = mx.tx
            val ty2 = mx.ty
            a = a2 * a1 + b2 * b1
            c = a2 * c1 + b2 * d1
            b = c2 * a1 + d2 * b1
            d = c2 * c1 + d2 * d1
            tx = a2 * tx1 + b2 * ty1 + tx2
            ty = c2 * tx1 + d2 * ty1 + ty2
        return this
    }

    /**
     * Add a translation to the current transformation.
     */
    fun translate(pt: Point) = translate(pt.x, pt.y)

    /**
     * Add a translation to the current transformation.
     */
    fun translate(x: Double, y: Double): Matrix {
        tx += x * a + y * c
        ty += x * b + y * d
        return this
    }

    /**
     * Add a scale transformation to the current one, using the same scale
     * factor for X and Y and an optional point f
     */
    fun scale(scaleXY: Double, center: Point? = null) = scale(scaleXY, scaleXY, center)

    /**
     * Add a scale transformation to the current one, using the same scale
     * factor for X and Y
     */
    fun scale(scaleX: Double, scaleY: Double, center: Point? = null): Matrix {

        require(scaleX != .0) { "$scaleX should be different than 0.0 to ensure the matrix is invertible "}
        require(scaleY != .0) { "$scaleY should be different than 0.0 to ensure the matrix is invertible "}

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

    /**
     * Add a rotation to the current transformation. The rotation is done
     * using the optional center point as pivot. Without a center parameter,
     * the rotation uses the
     */
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

    /**
     * Apply the current transformation matrix to a point.
     * @return the point coordinates after apply the transformation.
     */
    fun transform(point: Point) = point(
        point.x * a + point.y * c + tx,
        point.x * b + point.y * d + ty
    )

    /**
     * Apply the inverse transformations
     * @return the point coordinates after apply the inverse transformation.
     */
    fun inverseTransform(point: Point):Point {
        //matrix should always be invertible
        val x = point.x - tx
        val y = point.y - ty
        val det = a * d  - b * c
        return point(
            (x * d - y * c) / det,
            (y * a - x * b) / det)
    }

    /**
     * Checks whether the matrix is invertible. A matrix is not invertible if
     * the determinant is 0 or any value is infinite or NaN.
     *
     * @return {Boolean} whether the matrix is invertible
     */
    inline internal fun isInvertible(): Boolean{
        val det = a * d - c * b
        return det != .0
                && !det.isNaN()
                && tx.isFinite()
                && ty.isFinite()
    }
}
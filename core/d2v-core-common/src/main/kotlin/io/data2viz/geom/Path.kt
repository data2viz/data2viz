package io.data2viz.geom


/**
 * This interface represents a path generator.
 *
 * It is a stateful class that stores the list of command applied on it.
 */
interface Path {


    fun moveTo(x: Double, y: Double)

    fun lineTo(x: Double, y: Double)

    fun closePath()

    fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double)

    fun bezierCurveTo(cpx1: Double, cpy1: Double, cpx2: Double, cpy2: Double, x: Double, y: Double)

    fun arcTo(cpx: Double, cpy: Double, x: Double, y: Double, radius: Double)

    /**
     * HTML CANVAS STANDARDS:
     *
     * Given x, y, radiusX, radiusY, rotation, startAngle, endAngle, and counterClockWise:
     *
     * TODO : check this before sending arguments to canvas? (then remove this doc line)
     * - If any of the arguments are infinite, then return.
     *
     * SPECIAL CASE OF ANGLE GREATER THAN 2π:
     * - If anticlockwise is false and endAngle-startAngle is equal to or greater than 2π, or, if anticlockwise is true
     * and startAngle-endAngle is equal to or greater than 2π, then the arc is the whole circumference of the circle,
     * and the point at startAngle along this circle's circumference, measured in radians acts as both the start point
     * and the end point.
     *
     * - Otherwise, the points at startAngle and endAngle along this circle's circumference, measured in radians
     * clockwise from the ellipse's semi-major axis, are the start and end points respectively, and the arc is the
     * path along the circumference of this ellipse from the start point to the end point, going anti-clockwise if
     * anticlockwise is true, and clockwise otherwise. Since the points are on the circle, as opposed to being simply
     * angles from zero, the arc can never cover an angle greater than 2π radians.
     */
    fun arc(
        centerX: Double,
        centerY: Double,
        radius: Double,
        startAngle: Double,
        endAngle: Double,
        counterClockWise: Boolean = false
    )

    @Deprecated("To be discussed: should it be available in addition to Rect class?")
    fun rect(x: Double, y: Double, w: Double, h: Double)
}
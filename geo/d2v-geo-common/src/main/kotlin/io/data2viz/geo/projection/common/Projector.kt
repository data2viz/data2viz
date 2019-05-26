package io.data2viz.geo.projection.common

import io.data2viz.geo.projection.OrthographicProjector

/**
 * Transform (project or invert) point
 *
 * For example: translating by x and y
 *
 * fun project(lambda: Double, phi: Double): DoubleArray = doubleArrayOf(
 *      lambda + dx,
 *      phi + dy
 * )
 *
 * Represents methods for points: [project] & [invert]
 * and separated coordinates  [projectLambda], [projectPhi], [invertLambda], [invertPhi]
 * Actually, [project] should just combine [projectLambda] & [projectPhi]
 * but for performance optimization it is better do not allocate doubleArray each time.
 * Sometimes internal code use [project] and sometimes [projectLambda] & [projectPhi]
 * So, result of [project] shouldBeEqual to doubleArray([projectLambda],[projectPhi])
 *
 * Same for [invert]
 *
 * If [project] & [invert] don't have common calculations you can use [NoCommonCalculationsProjector]
 * If you don't care about performance you can use [SimpleProjector] with only [project] & [invert]
 * If you don't want to support [invert] you can use [NoInvertProjector] or [SimpleNoInvertProjector]
 * 
 * @see OrthographicProjector
 * @see [Projection]]
 * @see [projection]]
 */
interface Projector {


    /**
     * Project a point
     * 
     * For example: translating by x and y
     *
     * fun project(lambda: Double, phi: Double): DoubleArray = doubleArrayOf(
     *      lambda + dx,
     *      phi + dy
     * )
     * 
     * Result may contains [Double.NaN]  when projection not possible
     * @return Projected point (new two dimensional array)
     */
    fun project(lambda: Double, phi: Double): DoubleArray

    /**
     * Project point first coordinate
     * Result maybe [Double.NaN] when projection not possible
     * @return projectedLambda 
     */
    fun projectLambda(lambda: Double, phi: Double): Double

    /**
     * Project point second coordinate
     * Result maybe [Double.NaN] when projection not possible
     * @return projectedLambda
     */
    fun projectPhi(lambda: Double, phi: Double): Double


    /**
     * Returns a new array [longitude, latitude] in degrees representing the unprojected
     * point of the given projected point.
     * Result may contains [Double.NaN]  when projection not possible
     * 
     * Maybe not supported see [NoInvertProjector]
     * 
     * @return Projected point (new two dimensional array)
     * @see project
     */
    fun invert(lambda: Double, phi: Double): DoubleArray

    /**
     * Invert point first coordinate
     * Result maybe [Double.NaN] when invert not possible
     * 
     * * Maybe not supported see [NoInvertProjector]
     * 
     * @return invertedLambda
     */
    fun invertLambda(lambda: Double, phi: Double): Double

    /**
     * Invert point second coordinate
     * Result maybe [Double.NaN] when invert not possible
     * 
     * * Maybe not supported see [NoInvertProjector]
     * 
     * @return invertedLambda
     */
    fun invertPhi(lambda: Double, phi: Double): Double

}


/**
 * Specific [Projection] implementation when you don't want to support [invert]
 *
 */
interface NoInvertProjector : Projector {

    override fun invert(lambda: Double, phi: Double) =
        invertError() as DoubleArray

    override fun invertLambda(lambda: Double, phi: Double): Double =
        invertError() as Double
    
    override fun invertPhi(lambda: Double, phi: Double): Double =
        invertError() as Double

    private fun invertError(): Any {
        error("$this don't support invert operation")
    }
}



/**
 *
 * Specific [Projection] implementation with only two methods to override [project] & [invert]
 *
 *
 * Useful for test purposes and will have bad performance
 *
 */
interface SimpleProjector : Projector {

    override fun projectLambda(lambda: Double, phi: Double): Double = project(lambda, phi)[0]

    override fun projectPhi(lambda: Double, phi: Double): Double = project(lambda, phi)[1]

    override fun invertLambda(lambda: Double, phi: Double): Double = invert(lambda, phi)[0]

    override fun invertPhi(lambda: Double, phi: Double): Double = invert(lambda, phi)[1]

}


/**
 *
 * Specific [Projection] implementation with only [projectLambda] & [projectPhi] and [invertLambda] & [invertPhi]
 *
 * Usefull when [project] don't have common calculations for Lambda and phi
 *
 * @see CylindricalEqualAreaProjector
 */
interface NoCommonCalculationsProjector : Projector {

    override fun project(lambda: Double, phi: Double) =
        doubleArrayOf(
            projectLambda(lambda, phi),
            projectPhi(lambda, phi)
        )

    override fun invert(lambda: Double, phi: Double) =
        doubleArrayOf(
            invertLambda(lambda, phi),
            invertPhi(lambda, phi)
        )
}


/**
 * Combine [SimpleProjector] and [NoInvertProjector]
 */
interface SimpleNoInvertProjector : NoInvertProjector {

    override fun projectLambda(lambda: Double, phi: Double): Double = project(lambda, phi)[0]

    override fun projectPhi(lambda: Double, phi: Double): Double = project(lambda, phi)[1]

}
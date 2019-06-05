package io.data2viz.geo


/**
 * An alias to GeoJson points.
 * The Array must have a size of 2 of 3. The first two elements are the longitude (phi)
 * and latitude (lambda). The last, if it exists, represents the altitude.
 */
typealias GeoPoint = DoubleArray

/**
 * The longitude in degrees
 */
val GeoPoint.lambda: Double
        get() = this[0]


/**
 * The latitude in degrees
 */
val GeoPoint.phi: Double
        get() = this[1]

/**
 * The altitude if specified
 */
val GeoPoint.alt: Double?
        get() = if (size > 2) this[2] else null


fun GeoPoint.component1() = this.lambda
fun GeoPoint.component2() = this.phi
fun GeoPoint.component3() = this.alt


fun GeoPoint(lambda:Double, phi:Double, alt: Double? = null): GeoPoint =
        if (alt == null)
                doubleArrayOf(lambda, phi)
        else
                doubleArrayOf(lambda, phi, alt)
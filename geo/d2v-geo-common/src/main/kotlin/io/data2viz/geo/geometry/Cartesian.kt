package io.data2viz.geo.geometry

import kotlin.math.*

fun spherical(cartesian: DoubleArray): DoubleArray {
    return doubleArrayOf(atan2(cartesian[1], cartesian[0]), asin(cartesian[2]))
}

fun cartesian(spherical: DoubleArray): DoubleArray {
    val lambda = spherical[0]
    val phi = spherical[1]
    val cosPhi = cos(phi)
    return doubleArrayOf(cosPhi * cos(lambda), cosPhi * sin(lambda), sin(phi))
}


fun cartesianDot(a: DoubleArray, b: DoubleArray): Double {
    return a[0] * b[0] + a[1] * b[1] + a[2] * b[2]
}

fun cartesianCross(a: DoubleArray, b: DoubleArray): DoubleArray {
    return doubleArrayOf(
        a[1] * b[2] - a[2] * b[1],
        a[2] * b[0] - a[0] * b[2],
        a[0] * b[1] - a[1] * b[0]
    )
}

fun cartesianScale(vector: DoubleArray, k: Double) = doubleArrayOf(vector[0] * k, vector[1] * k, vector[2] * k)

fun cartesianAdd(a: DoubleArray, b: DoubleArray): DoubleArray {
    a[0] += b[0]
    a[1] += b[1]
    a[2] += b[2]
    return a
}

fun cartesianNormalize(d: DoubleArray): DoubleArray {
    val l = sqrt(d[0] * d[0] + d[1] * d[1] + d[2] * d[2])
    d[0] /= l
    d[1] /= l
    d[2] /= l
    return d
}
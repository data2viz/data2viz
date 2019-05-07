package io.data2viz.geo

import io.data2viz.math.EPSILON
import io.data2viz.math.QUARTERPI
import io.data2viz.math.TAU
import kotlin.math.*

//fun polygonContains(polygon: List<List<DoubleArray>>, point: DoubleArray): Boolean {
fun polygonContains(polygon: List<List<Double>>, point: DoubleArray): Boolean {
    val lambda = point[0]
    val phi = point[1]
    val normal = doubleArrayOf(sin(lambda), -cos(lambda), 0.0)
    var angle = 0.0
    var winding = 0

    var sum = .0

    for (i in polygon.indices) {
        val ring = polygon[i]
        if (ring.isEmpty()) continue

//        var point0 = ring.last()
//        var lambda0 = point0[0]
//        val phi0 = point0[1] / 2 + QUARTERPI

        val lastIndex = ring.lastIndex

//        var point0 = ring.last()
        var lambda0 = ring[lastIndex - 1]
        var phi0 = ring[lastIndex] / 2 + QUARTERPI

        var sinPhi0 = sin(phi0)
        var cosPhi0 = cos(phi0)


        for (j in ring.indices step 2) {
//            val point1 = ring[j]
//            val lambda1 = point1[0]
//            val phi1 = point1[1] / 2 + QUARTERPI

//            val point1 = ring[j]
            val pointIndex = j
            val lambda1 = ring[pointIndex]
            val phi1 = ring[pointIndex+1] / 2 + QUARTERPI

            val sinPhi1 = sin(phi1)
            val cosPhi1 = cos(phi1)
            val delta = lambda1 - lambda0
            val sign = if (delta >= 0) 1 else -1
            val absDelta = sign * delta
            val antimeridian = absDelta > PI
            val k = sinPhi0 * sinPhi1

            sum += atan2(k * sign * sin(absDelta), cosPhi0 * cosPhi1 + k * cos(absDelta))
            angle += if (antimeridian) delta + sign * TAU else delta

            // Are the longitudes either side of the point’s meridian (lambda),
            // and are the latitudes smaller than the parallel (phi)?
            if (antimeridian xor (lambda0 >= lambda) xor (lambda1 >= lambda)) {
//                val arc = cartesianNormalize(cartesianCross(cartesian(point0), cartesian(point1)))
                val arc = cartesianNormalizeSpecial(lambda0, phi0, lambda1, phi1)
                val intersection = cartesianNormalize(cartesianCross(normal, arc))
                val phiArc = (if (antimeridian xor (delta >= 0)) -1 else 1) * asin(intersection[2])
                if (phi > phiArc || (phi == phiArc && ((arc[0] != .0 && !arc[0].isNaN()) || (arc[1] != .0 && !arc[1].isNaN())))) {
                    winding += if (antimeridian xor (delta >= 0)) 1 else -1
                }
            }

            lambda0 = lambda1
            sinPhi0 = sinPhi1
            cosPhi0 = cosPhi1
//            point0 = point1
            lambda0 = lambda1
            phi0 = phi1
        }
    }

    // First, determine whether the South pole is inside or outside:
    //
    // It is inside if:
    // * the polygon winds around it in a clockwise direction.
    // * the polygon does not (cumulatively) wind around it, but has a negative
    //   (counter-clockwise) area.
    //
    // Second, count the (signed) number of times a segment crosses a lambda
    // from the point to the South pole.  If it is zero, then the point is the
    // same side as the South pole.

    return (angle < -EPSILON || angle < EPSILON && sum < -EPSILON) xor ((winding and 1) != 0)
}

fun cartesianSpecial(lambda: Double, phi: Double): DoubleArray {

    val cosPhi = cos(phi)
    return doubleArrayOf(cosPhi * cos(lambda), cosPhi * sin(lambda), sin(phi))
}

//fun cartesianCrossSpecial(a: DoubleArray, b: DoubleArray): DoubleArray {
fun cartesianCrossSpecial(lambdaA: Double, phiA: Double, lambdaB: Double, phiB: Double): DoubleArray {

    val cosPhiA = cos(phiA)
    val a0 = cosPhiA * cos(lambdaA)
    val a1 = cosPhiA * sin(lambdaA)
    val a2 = sin(phiA)


    val cosPhiB = cos(phiB)
    val b0 = cosPhiB * cos(lambdaB)
    val b1 = cosPhiB * sin(lambdaB)
    val b2 = sin(phiB)


//
//    return doubleArrayOf(
//        a[1] * b[2] - a[2] * b[1],
//        a[2] * b[0] - a[0] * b[2],
//        a[0] * b[1] - a[1] * b[0]
//    )


    return doubleArrayOf(
        a1 * b2 - a2 * b1,
        a2 * b0 - a0 * b2,
        a0 * b1 - a1 * b0
    )
}

fun cartesianNormalizeSpecial(lambdaA: Double, phiA: Double, lambdaB: Double, phiB: Double): DoubleArray {

    val cosPhiA = cos(phiA)
    val a0 = cosPhiA * cos(lambdaA)
    val a1 = cosPhiA * sin(lambdaA)
    val a2 = sin(phiA)


    val cosPhiB = cos(phiB)
    val b0 = cosPhiB * cos(lambdaB)
    val b1 = cosPhiB * sin(lambdaB)
    val b2 = sin(phiB)


    val d = doubleArrayOf(
        a1 * b2 - a2 * b1,
        a2 * b0 - a0 * b2,
        a0 * b1 - a1 * b0
    )

    val l = sqrt(d[0] * d[0] + d[1] * d[1] + d[2] * d[2])
    d[0] /= l
    d[1] /= l
    d[2] /= l
    return d
}


fun polygonContainsOld(polygon: List<List<DoubleArray>>, point: DoubleArray): Boolean {
    val lambda = point[0]
    val phi = point[1]
    val normal = doubleArrayOf(sin(lambda), -cos(lambda), 0.0)
    var angle = 0.0
    var winding = 0

    var sum = .0

    for (i in polygon.indices) {
        val ring = polygon[i]
        if (ring.isEmpty()) continue

        var point0 = ring.last()
        var lambda0 = point0[0]
        val phi0 = point0[1] / 2 + QUARTERPI
        var sinPhi0 = sin(phi0)
        var cosPhi0 = cos(phi0)

        for (j in ring.indices) {
            val point1 = ring[j]
            val lambda1 = point1[0]
            val phi1 = point1[1] / 2 + QUARTERPI
            val sinPhi1 = sin(phi1)
            val cosPhi1 = cos(phi1)
            val delta = lambda1 - lambda0
            val sign = if (delta >= 0) 1 else -1
            val absDelta = sign * delta
            val antimeridian = absDelta > PI
            val k = sinPhi0 * sinPhi1

            sum += atan2(k * sign * sin(absDelta), cosPhi0 * cosPhi1 + k * cos(absDelta))
            angle += if (antimeridian) delta + sign * TAU else delta

            // Are the longitudes either side of the point’s meridian (lambda),
            // and are the latitudes smaller than the parallel (phi)?
            if (antimeridian xor (lambda0 >= lambda) xor (lambda1 >= lambda)) {
                val arc = cartesianNormalize(cartesianCross(cartesian(point0), cartesian(point1)))
                val intersection = cartesianNormalize(cartesianCross(normal, arc))
                val phiArc = (if (antimeridian xor (delta >= 0)) -1 else 1) * asin(intersection[2])
                if (phi > phiArc || (phi == phiArc && ((arc[0] != .0 && !arc[0].isNaN()) || (arc[1] != .0 && !arc[1].isNaN())))) {
                    winding += if (antimeridian xor (delta >= 0)) 1 else -1
                }
            }

            lambda0 = lambda1
            sinPhi0 = sinPhi1
            cosPhi0 = cosPhi1
            point0 = point1
        }
    }

    // First, determine whether the South pole is inside or outside:
    //
    // It is inside if:
    // * the polygon winds around it in a clockwise direction.
    // * the polygon does not (cumulatively) wind around it, but has a negative
    //   (counter-clockwise) area.
    //
    // Second, count the (signed) number of times a segment crosses a lambda
    // from the point to the South pole.  If it is zero, then the point is the
    // same side as the South pole.

    return (angle < -EPSILON || angle < EPSILON && sum < -EPSILON) xor ((winding and 1) != 0)
}
package io.data2viz.geo

import io.data2viz.math.EPSILON
import io.data2viz.math.QUARTERPI
import io.data2viz.math.TAU
import kotlin.math.*


fun polygonContains(polygon: List<List<DoubleArray>>, point: DoubleArray): Boolean {
    val lambda = point[0]
    val phi = point[1]
    val normal0 = sin(lambda)
    val normal1 = -cos(lambda)
    val normal2 = 0.0

    var angle = 0.0
    var winding = 0

    var sum = .0

    for (i in polygon.indices) {
        val ring = polygon[i]
        if (ring.isEmpty()) continue

        var point0 = ring.last()
        var lambda0 = point0[0]
        var phi0 = point0[1] / 2 + QUARTERPI

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

            // Optimized. Don't use normalize, cartesign and other functions for points to avoid memory allocation for double arrays
            if (antimeridian xor (lambda0 >= lambda) xor (lambda1 >= lambda)) {
                val lambdaA0 = point0[0]
                val phiA0 = point0[1]
                val cosPhiA = cos(phiA0)
                val a0 = cosPhiA * cos(lambdaA0)
                val a1 = cosPhiA * sin(lambdaA0)
                val a2 = sin(phiA0)

                val lambdaB0 = point1[0]
                val phiB0 = point1[1]
                val cosPhiB = cos(phiB0)
                val b0 = cosPhiB * cos(lambdaB0)
                val b1 = cosPhiB * sin(lambdaB0)
                val b2 = sin(phiB0)

                val cross0 = a1 * b2 - a2 * b1
                val cross1 = a2 * b0 - a0 * b2
                val cross2 = a0 * b1 - a1 * b0

                val normalize = sqrt(cross0 * cross0 + cross1 * cross1 + cross2 * cross2)
                val d0 = cross0 / normalize
                val d1 = cross1 / normalize
                val d2 = cross2 / normalize

                val intersectionD0 = normal1 * d2 - normal2 * d1
                val intersectionD1 = normal2 * d0 - normal0 * d2
                var intersectionD2 = normal0 * d1 - normal1 * d0

                val intersectionNormalize =
                    sqrt(intersectionD0 * intersectionD0 + intersectionD1 * intersectionD1 + intersectionD2 * intersectionD2)
                intersectionD2 /= intersectionNormalize

                val phiArc = (if (antimeridian xor (delta >= 0)) -1 else 1) * asin(intersectionD2)
                if (phi > phiArc ||
                    (phi == phiArc && ((d0 != .0 && !d0.isNaN()) ||
                            (d1 != .0 && !d1.isNaN())))
                ) {
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
    //   (counter-clockwise) drawArea.
    //
    // Second, count the (signed) number of times a segment crosses a lambda
    // from the point to the South pole.  If it is zero, then the point is the
    // same side as the South pole.

    return (angle < -EPSILON || angle < EPSILON && sum < -EPSILON) xor ((winding and 1) != 0)
}
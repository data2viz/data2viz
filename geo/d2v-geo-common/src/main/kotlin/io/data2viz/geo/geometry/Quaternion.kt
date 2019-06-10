package io.data2viz.geo.geometry


import io.data2viz.math.DEG_TO_RAD
import io.data2viz.math.RAD_TO_DEG
import kotlin.math.*

/**
 *
 * Port of https://github.com/Fil/versor
 * Returns the unit quaternion for the given Euler rotation angles [λ, φ, γ] in degrees.
 */
fun quaternion(eulerAngles: DoubleArray): DoubleArray {
    var l = eulerAngles[0] / 2 * DEG_TO_RAD
    var sl = sin(l)
    var cl = cos(l) // λ / 2
    var p = eulerAngles[1] / 2 * DEG_TO_RAD
    var sp = sin(p)
    var cp = cos(p) // φ / 2
    var g = eulerAngles[2] / 2 * DEG_TO_RAD
    var sg = sin(g)
    var cg = cos(g) // γ / 2
    return doubleArrayOf(
        cl * cp * cg + sl * sp * sg,
        sl * cp * cg - cl * sp * sg,
        cl * sp * cg + sl * cp * sg,
        cl * cp * sg - sl * sp * cg
    )
}

/**
 *
 * @return Euler rotation quaternion [λ, φ, γ] for the given quaternion.
 */
fun eulerRotation(q: DoubleArray): DoubleArray {
    return doubleArrayOf(
        atan2(2 * (q[0] * q[1] + q[2] * q[3]), 1 - 2 * (q[1] * q[1] + q[2] * q[2])) * RAD_TO_DEG,
        asin(max(-1.0, min(1.0, 2 * (q[0] * q[2] - q[3] * q[1])))) * RAD_TO_DEG,
        atan2(2 * (q[0] * q[3] + q[1] * q[2]), 1 - 2 * (q[2] * q[2] + q[3] * q[3])) * RAD_TO_DEG
    )
}

/**
 * alpha for tweening [0,1]
* @return The quaternion to rotate between two cartesian points on the sphere.
*/
fun quaternionDelta(v0: DoubleArray, v1: DoubleArray, alpha: Double = 1.0):DoubleArray {

    var w = cartesianCross(v0, v1)
    var l = sqrt(cartesianDot(w, w));
    if (l == -0.0 || l == 0.0 || l.isNaN()) return doubleArrayOf(1.0, 0.0, 0.0, 0.0)
    var t = alpha * acos(max(-1.0, min(1.0, cartesianDot(v0, v1)))) / 2
    var s = sin(t); // t = θ / 2
    return doubleArrayOf(cos(t), w[2] / l * s, -w[1] / l * s, w[0] / l * s)
};

/**
 *
 * @return The quaternion that represents q0 * q1.
 */
fun quaternionMultiply (q0: DoubleArray, q1: DoubleArray): DoubleArray {
    return doubleArrayOf(
        q0[0] * q1[0] - q0[1] * q1[1] - q0[2] * q1[2] - q0[3] * q1[3],
        q0[0] * q1[1] + q0[1] * q1[0] + q0[2] * q1[3] - q0[3] * q1[2],
        q0[0] * q1[2] - q0[1] * q1[3] + q0[2] * q1[0] + q0[3] * q1[1],
        q0[0] * q1[3] + q0[1] * q1[2] - q0[2] * q1[1] + q0[3] * q1[0]
    )
};


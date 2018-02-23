package io.data2viz.geo.clip

import io.data2viz.geo.projection.Stream
import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import io.data2viz.path.epsilon
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

fun clipAntimeridian() = { stream: Stream -> Clip(AntimeridianClip(), stream) }

class AntimeridianClip : Clippable {

    override var start = doubleArrayOf(-PI, -HALFPI)
    override fun pointVisible(x: Double, y: Double) = true

    override fun clipLine(stream: Stream): ClipStream {
        var lambda0 = Double.NaN
        var phi0 = Double.NaN
        var sign0 = Double.NaN

        return object : ClipStream {
            private var currentClean = 0

            override var clean
                get() = 2 - currentClean
                set(value) {
                    currentClean = value
                }

            override fun lineStart() {
                stream.lineStart()
                clean = 1
            }

            override fun point(x: Double, y: Double, z: Double) {
                var lambda1 = x
                val phi1 = y
                val sign1 = if (lambda1 > 0) PI else -PI
                val delta = abs(lambda1 - lambda0)
                if (abs(delta - PI) < epsilon) { // Line crosses pole
                    phi0 = if ((phi0 + phi1) / 2 > 0) HALFPI else -HALFPI
                    stream.point(lambda0, phi0, 0.0)
                    stream.point(sign0, phi0, 0.0)
                    stream.lineEnd()
                    stream.lineStart()
                    stream.point(sign1, phi0, 0.0)
                    stream.point(lambda1, phi0, 0.0)
                    clean = 0
                } else if (sign0 != sign1 && delta >= PI) {
                    if (abs(lambda0 - sign0) < epsilon) lambda0 -= sign0 * epsilon
                    if (abs(lambda1 - sign1) < epsilon) lambda1 -= sign1 * epsilon
                    phi0 = intersect(lambda0, phi0, lambda1, phi1)
                    stream.point(sign0, phi0, 0.0)
                    stream.lineEnd()
                    stream.lineStart()
                    stream.point(sign1, phi0, 0.0)
                    clean = 0
                }
                lambda0 = lambda1
                phi0 = phi1
                stream.point(lambda0, phi0, 0.0)
                sign0 = sign1
            }

            override fun lineEnd() {
                stream.lineEnd()
                lambda0 = Double.NaN
                phi0 = Double.NaN
            }

            private fun intersect(lambda0: Double, phi0: Double, lambda1: Double, phi1: Double): Double {
                val sinLambda0Lambda1 = sin(lambda0 - lambda1)
                return when {
                    abs(sinLambda0Lambda1) > epsilon -> {
                        val cosPhi0 = cos(phi0)
                        val cosPhi1 = cos(phi1)
                        atan(
                            (sin(phi0) * cosPhi1 * sin(lambda1)
                                    - sin(phi1) * cosPhi0 * sin(lambda0))
                                    / (cosPhi0 * cosPhi1 * sinLambda0Lambda1)
                        )
                    }
                    else -> (phi0 + phi1) / 2
                }
            }
        }
    }

    override fun interpolate(from: DoubleArray?, to: DoubleArray?, direction: Int, stream: Stream) {
        if (from == null || to == null) {
            val phi = direction * HALFPI
            stream.point(-PI, phi, 0.0)
            stream.point(0.0, phi, 0.0)
            stream.point(PI, phi, 0.0)
            stream.point(PI, 0.0, 0.0)
            stream.point(PI, -phi, 0.0)
            stream.point(0.0, -phi, 0.0)
            stream.point(-PI, -phi, 0.0)
            stream.point(-PI, 0.0, 0.0)
            stream.point(-PI, phi, 0.0)
        } else if (abs(from[0] - to[0]) > epsilon) {
            val lambda = if (from[0] < to[0]) PI else -PI
            val phi = direction * lambda / 2
            stream.point(-lambda, phi, 0.0)
            stream.point(0.0, phi, 0.0)
            stream.point(lambda, phi, 0.0)
        } else stream.point(to[0], to[1], 0.0)
    }
}
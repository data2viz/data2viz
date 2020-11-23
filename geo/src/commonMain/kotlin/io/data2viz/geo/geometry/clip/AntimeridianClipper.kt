/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.geometry.clip

import io.data2viz.geo.stream.Stream
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin



/**
 * A clipping function which transforms a stream such that geometries (lines or polygons)
 * that cross the antimeridian line are cut in two, one on each side. Typically used for pre-clipping.
 */
public val antimeridianPreClip = object : ClipStreamBuilder {
    val antimeridianClip = AntimeridianClipper()

    override fun bindTo(downstream: Stream): Stream {
        return ClippableStream(antimeridianClip, downstream)
    }

}


private class AntimeridianClipper : ClipperWithStart {

    override var start = doubleArrayOf(-PI, -HALFPI)
    override fun pointVisible(x: Double, y: Double) = true

    override fun clipLine(downstream: Stream): ClipStream {
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
                downstream.lineStart()
                clean = 1
            }

            override fun point(x: Double, y: Double, z: Double) {
                var lambda1 = x
                val phi1 = y
                val sign1 = if (lambda1 > 0) PI else -PI
                val delta = abs(lambda1 - lambda0)
                if (abs(delta - PI) < EPSILON) { // Line crosses pole
                    phi0 = if ((phi0 + phi1) / 2 > 0) HALFPI else -HALFPI
                    downstream.point(lambda0, phi0, 0.0)
                    downstream.point(sign0, phi0, 0.0)
                    downstream.lineEnd()
                    downstream.lineStart()
                    downstream.point(sign1, phi0, 0.0)
                    downstream.point(lambda1, phi0, 0.0)
                    clean = 0
                } else if (sign0 != sign1 && delta >= PI) {
                    if (abs(lambda0 - sign0) < EPSILON) lambda0 -= sign0 * EPSILON
                    if (abs(lambda1 - sign1) < EPSILON) lambda1 -= sign1 * EPSILON
                    phi0 = intersect(lambda0, phi0, lambda1, phi1)
                    downstream.point(sign0, phi0, 0.0)
                    downstream.lineEnd()
                    downstream.lineStart()
                    downstream.point(sign1, phi0, 0.0)
                    clean = 0
                }
                lambda0 = lambda1
                phi0 = phi1
                downstream.point(lambda0, phi0, 0.0)
                sign0 = sign1
            }

            override fun lineEnd() {
                downstream.lineEnd()
                lambda0 = Double.NaN
                phi0 = Double.NaN
            }

            private fun intersect(lambda0: Double, phi0: Double, lambda1: Double, phi1: Double): Double {
                val sinLambda0Lambda1 = sin(lambda0 - lambda1)
                return when {
                    abs(sinLambda0Lambda1) > EPSILON -> {
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
        } else if (abs(from[0] - to[0]) > EPSILON) {
            val lambda = if (from[0] < to[0]) PI else -PI
            val phi = direction * lambda / 2
            stream.point(-lambda, phi, 0.0)
            stream.point(0.0, phi, 0.0)
            stream.point(lambda, phi, 0.0)
        } else stream.point(to[0], to[1], 0.0)
    }
}
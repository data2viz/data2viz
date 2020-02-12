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

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.stream.Stream
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import io.data2viz.math.rad
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


/**
 * A clipping function which transforms a stream such that geometries (lines or polygons)
 * that cross the antimeridian line are cut in two, one on each side. Typically used for pre-clipping.
 *
 */
val antimeridianPreClip = object : ClipStreamBuilder<GeoPoint> {
    val antimeridianClip = AntimeridianClipper()

    override fun bindTo(downstream: Stream<GeoPoint>): Stream<GeoPoint> {
        return ClippableStream(antimeridianClip, downstream)
    }

}


private class AntimeridianClipper : ClipperWithStart<GeoPoint> {

    override var start = GeoPoint(-PI.rad, -HALFPI.rad)
    override fun pointVisible(point: GeoPoint) = true

    override fun clipLine(downstream: Stream<GeoPoint>): ClipStream<GeoPoint> {
        var lambda0 = Double.NaN
        var phi0 = Double.NaN
        var sign0 = Double.NaN

        return object : ClipStream<GeoPoint>() {
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

            override fun point(point: GeoPoint) {
                var lambda1 = point.lon.rad
                val phi1 = point.lat.rad
                val sign1 = if (lambda1 > 0) PI else -PI
                val delta = abs(lambda1 - lambda0)
                if (abs(delta - PI) < EPSILON) { // Line crosses pole
                    phi0 = if ((phi0 + phi1) / 2 > 0) HALFPI else -HALFPI
                    downstream.point(GeoPoint(lambda0.rad, phi0.rad, 0.0))
                    downstream.point(GeoPoint(sign0.rad, phi0.rad, 0.0))
                    downstream.lineEnd()
                    downstream.lineStart()
                    downstream.point(GeoPoint(sign1.rad, phi0.rad, 0.0))
                    downstream.point(GeoPoint(lambda1.rad, phi0.rad, 0.0))
                    clean = 0
                } else if (sign0 != sign1 && delta >= PI) {
                    if (abs(lambda0 - sign0) < EPSILON) lambda0 -= sign0 * EPSILON
                    if (abs(lambda1 - sign1) < EPSILON) lambda1 -= sign1 * EPSILON
                    phi0 = intersect(lambda0, phi0, lambda1, phi1)
                    downstream.point(GeoPoint(sign0.rad, phi0.rad, 0.0))
                    downstream.lineEnd()
                    downstream.lineStart()
                    downstream.point(GeoPoint(sign1.rad, phi0.rad, 0.0))
                    clean = 0
                }
                lambda0 = lambda1
                phi0 = phi1
                downstream.point(GeoPoint(lambda0.rad, phi0.rad, 0.0))
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

    override fun interpolate(from: GeoPoint?, to: GeoPoint?, direction: Int, stream: Stream<GeoPoint>) {
        if (from == null || to == null) {
            val phi = direction * HALFPI
            stream.point(GeoPoint(-PI.rad, phi.rad, 0.0))
            stream.point(GeoPoint(0.0.rad, phi.rad, 0.0))
            stream.point(GeoPoint(PI.rad, phi.rad, 0.0))
            stream.point(GeoPoint(PI.rad, 0.0.rad, 0.0))
            stream.point(GeoPoint(PI.rad, -phi.rad, 0.0))
            stream.point(GeoPoint(0.0.rad, -phi.rad, 0.0))
            stream.point(GeoPoint(-PI.rad, -phi.rad, 0.0))
            stream.point(GeoPoint(-PI.rad, 0.0.rad, 0.0))
            stream.point(GeoPoint(-PI.rad, phi.rad, 0.0))
        } else if (abs(from.lon.rad - to.lon.rad) > EPSILON) {
            val lambda = if (from.lon.rad < to.lon.rad) PI else -PI
            val phi = direction * lambda / 2
            stream.point(GeoPoint(-lambda.rad, phi.rad, 0.0))
            stream.point(GeoPoint(0.0.rad, phi.rad, 0.0))
            stream.point(GeoPoint(lambda.rad, phi.rad, 0.0))
        } else stream.point(to)
    }

}
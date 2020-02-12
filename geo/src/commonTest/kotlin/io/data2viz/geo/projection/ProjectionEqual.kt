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

package io.data2viz.geo.projection

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.projection.common.Projection
import io.data2viz.math.deg
import io.data2viz.math.rad
import kotlin.math.abs

internal fun pt(a: Double, b: Double) = doubleArrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = doubleArrayOf(a, b, c)

val projectTestPrecision = 1e-6
val invertTestPrecision = 1e-3

fun checkProjectAndInvert(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {
    checkProject(projection, lambda, phi, screenX, screenY, deltaPrecision ?: projectTestPrecision)
    checkInvert(projection, lambda, phi, screenX, screenY, deltaPrecision ?: invertTestPrecision)
}

fun checkProject(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {


    val projectPointResult = projection.project(GeoJsonPoint(lambda.deg, phi.deg))

    inDelta(projectPointResult.x, screenX, deltaPrecision ?: projectTestPrecision)
    inDelta(projectPointResult.y, screenY, deltaPrecision ?: projectTestPrecision)
}


fun checkInvert(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {

    val invert = projection.invert(Point3D( screenX, screenY))
    val delta = abs(lambda - invert.lon.deg) % 360
    if (delta > deltaPrecision ?: invertTestPrecision) {
        throw AssertionError("checkInvert lambda is invalid excepted = $lambda actual = ${invert.lon.deg} delta = $delta")
    }

    inDelta(invert.lat.deg, phi, deltaPrecision)
}

fun inDelta(actual: Double, expected: Double, delta: Double? = null) =
    abs(actual - expected) <= delta ?: 0.001

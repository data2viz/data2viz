/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

import io.data2viz.geo.projection.common.Projection
import kotlin.math.abs

internal fun pt(a: Double, b: Double) = arrayOf(a, b)
internal fun pt(a: Double, b: Double, c: Double) = arrayOf(a, b, c)

val projectTestPrecision = 0.000001 // 1e-6
val invertTestPrecision = 0.001 // 1e-3

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


    val projectPointResult = projection.project(lambda, phi)

    inDelta(projectPointResult[0], screenX, deltaPrecision ?: projectTestPrecision)
    inDelta(projectPointResult[1], screenY, deltaPrecision ?: projectTestPrecision)
}


fun checkInvert(
    projection: Projection,
    lambda: Double,
    phi: Double,
    screenX: Double,
    screenY: Double,
    deltaPrecision: Double? = null
) {

    val invert = projection.invert(screenX, screenY)

    val delta = abs(lambda - invert[0]) % 360

    if (delta > deltaPrecision ?: invertTestPrecision) {
        throw AssertionError("checkInvert lambda is invalid excepted = $lambda actual = ${invert[0]} delta = $delta")
    }

    inDelta(invert[1], phi, deltaPrecision)
}

fun inDelta(actual: Double, expected: Double, delta: Double? = null) =
    abs(actual - expected) <= delta ?: 0.001

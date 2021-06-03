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

package io.data2viz.geo.geometry

import kotlin.math.*

public fun spherical(cartesian: DoubleArray): DoubleArray {
    return doubleArrayOf(atan2(cartesian[1], cartesian[0]), asin(cartesian[2]))
}

public fun cartesian(spherical: DoubleArray): DoubleArray {
    val lambda = spherical[0]
    val phi = spherical[1]
    val cosPhi = cos(phi)
    return doubleArrayOf(cosPhi * cos(lambda), cosPhi * sin(lambda), sin(phi))
}


public fun cartesianDot(a: DoubleArray, b: DoubleArray): Double {
    return a[0] * b[0] + a[1] * b[1] + a[2] * b[2]
}

public fun cartesianCross(a: DoubleArray, b: DoubleArray): DoubleArray {
    return doubleArrayOf(
        a[1] * b[2] - a[2] * b[1],
        a[2] * b[0] - a[0] * b[2],
        a[0] * b[1] - a[1] * b[0]
    )
}

public fun cartesianScale(vector: DoubleArray, k: Double): DoubleArray = doubleArrayOf(vector[0] * k, vector[1] * k, vector[2] * k)

public fun cartesianAdd(a: DoubleArray, b: DoubleArray): DoubleArray {
    a[0] += b[0]
    a[1] += b[1]
    a[2] += b[2]
    return a
}

public fun cartesianNormalize(d: DoubleArray): DoubleArray {
    val l = sqrt(d[0] * d[0] + d[1] * d[1] + d[2] * d[2])
    d[0] /= l
    d[1] /= l
    d[2] /= l
    return d
}

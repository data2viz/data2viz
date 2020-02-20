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

package io.data2viz.geo

import io.data2viz.math.Angle
import io.data2viz.math.deg

sealed class KPoint


/**
 * A geographic point has a longitude (lon), a latitude (lat) and an option altitude (alt)
 */
data class GeoPoint(
    val lon: Angle = 0.deg,
    val lat: Angle = 0.deg,
    val alt: Double? = null): KPoint() {

    operator fun plus(other: GeoPoint): GeoPoint  = copy(lon + other.lon, lat + other.lat)

}

/**
 * A GeoPoint projected on a plan.
 */
data class Point3D(
    val x: Double = .0,
    val y: Double = .0,
    val z: Double? = null
): KPoint()

data class Rotation3D(
    val lambda  : Angle = 0.deg,
    val phi     : Angle = 0.deg,
    val gamma   : Angle = 0.deg
)




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

import io.data2viz.geojson.Position


/**
 * An alias to GeoJson Position.
 * The Array must have a size of 2 of 3. The first two elements are the longitude (phi)
 * and latitude (lambda). The last, if it exists, represents the altitude.
 */
typealias GeoPoint = Position

/**
 * The longitude in degrees
 */
val GeoPoint.lon: Double
        get() = this[0]

/**
 * The latitude in degrees
 */
val GeoPoint.lat: Double
        get() = this[1]

/**
 * The altitude if specified
 */
val GeoPoint.alt: Double?
        get() = if (this.size > 2) this[2] else null


//fun GeoPoint.component1() = this.lon
//fun GeoPoint.component2() = this.lat
//fun GeoPoint.component3() = this.alt


fun GeoPoint(lambda:Double, phi:Double, alt: Double? = null): GeoPoint =
        if (alt == null)
                doubleArrayOf(lambda, phi)
        else
                doubleArrayOf(lambda, phi, alt)
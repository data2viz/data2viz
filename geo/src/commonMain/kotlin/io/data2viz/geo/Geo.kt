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

package io.data2viz.geo


/**
 * An alias to GeoJson points.
 * The Array must have a size of 2 of 3. The first two elements are the longitude (phi)
 * and latitude (lambda). The last, if it exists, represents the altitude.
 */
public typealias GeoPoint = DoubleArray

/**
 * The longitude in degrees
 */
public val GeoPoint.lambda: Double
        get() = this[0]

/**
 * The latitude in degrees
 */
public val GeoPoint.phi: Double
        get() = this[1]

/**
 * The altitude if specified
 */
public val GeoPoint.alt: Double?
        get() = if (size > 2) this[2] else null


public fun GeoPoint.component1(): Double = this.lambda
public fun GeoPoint.component2(): Double = this.phi
public fun GeoPoint.component3(): Double? = this.alt


public fun GeoPoint(lambda:Double, phi:Double, alt: Double? = null): GeoPoint =
        if (alt == null)
                doubleArrayOf(lambda, phi)
        else
                doubleArrayOf(lambda, phi, alt)

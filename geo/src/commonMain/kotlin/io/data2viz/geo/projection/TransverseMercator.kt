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


import io.data2viz.geo.projection.common.Projector
import io.data2viz.math.Angle
import io.data2viz.math.HALFPI
import io.data2viz.math.deg
import kotlin.math.atan
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.tan


public fun transverseMercatorProjection(init: TransverseMercatorProjection.() -> Unit = {}): TransverseMercatorProjection =
    TransverseMercatorProjection().also {

    it.rotate(0.deg, 0.deg, 90.deg)
    it.scale = 159.155
}.also(init)


/**
 * @see MercatorProjector
 * @see TransverseMercatorProjector
 * @see TransverseMercatorProjection
 */
public class TransverseMercatorProjector : Projector {

    override fun project(lambda: Double, phi: Double): DoubleArray = doubleArrayOf(ln(tan((HALFPI + phi) / 2)), -lambda)
    override fun invert(x: Double, y: Double): DoubleArray = doubleArrayOf(-y, 2 * atan(exp(x)) - HALFPI)

}


/**
 * The transverse spherical [MercatorProjection] projection.
 *
 * @see TransverseMercatorProjector
 */
public class TransverseMercatorProjection : MercatorProjection(TransverseMercatorProjector()) {

    override var centerLat: Angle
        get() = super.centerLon
        set(value) {
            super.centerLon = value
        }

    override var centerLon: Angle
        get() = -super.centerLat
        set(value) {
            super.centerLat = -value
        }

    override fun center(lat: Angle, lon: Angle) {
        super.center(-lon, lat)
    }

    override var rotateGamma: Angle
        get() = super.rotateGamma - 90.0.deg
        set(value) {
            super.rotateGamma = value + 90.0.deg
        }

    override fun rotate(lambda: Angle, phi: Angle, gamma: Angle?) {
        if (gamma != null) {
            super.rotate(lambda, phi, gamma + 90.0.deg)

        } else {
            super.rotate(lambda, phi, 90.0.deg)

        }
    }
}
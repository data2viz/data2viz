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

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geo.projection.common.*
import io.data2viz.geom.Extent
import io.data2viz.math.*
import io.data2viz.math.PI
import kotlin.math.*


fun mercatorProjection(init: Projection.() -> Unit = {}) = MercatorProjection(MercatorProjector()).apply {
    scale = 961 / TAU
}.apply(init)

/**
 * The spherical Mercator projection.
 *
 * @see MercatorProjection
 */
class MercatorProjector : Projector<GeoPoint, Point3D> {
    override fun project(point: GeoPoint) =
        Point3D(
            point.lon.rad,
            ln(tan((HALFPI + point.lat.rad) / 2)))

    override fun invert(point: Point3D) =
        GeoPoint(
            point.x.rad,
            (2 * atan(exp(point.y)) - HALFPI).rad
        )
}

/**
 *
 * Defines a default projection [postClip] such that the world is projected to a square,
 * clipped to approximately ±85° latitude.
 *
 * @see MercatorProjector
 */
open class MercatorProjection(projector: Projector<GeoPoint, Point3D> = MercatorProjector()) : ProjectorProjection(projector) {

    override var scale: Double
        get() = super.scale
        set(value) {
            super.scale = value
            reclip()
        }

    override var translateX: Double
        get() = super.translateX
        set(value) {
            super.translateX = value
            reclip()
        }
    override var translateY: Double
        get() = super.translateY
        set(value) {
            super.translateY = value
            reclip()
        }

    override fun translate(x: Double, y: Double) {
        super.translate(x, y)
        reclip()
    }

    override var centerLat: Angle
        get() = super.centerLat
        set(value) {
            super.centerLat = value
            reclip()
        }
    override var centerLon: Angle
        get() = super.centerLon
        set(value) {
            super.centerLon = value
            reclip()
        }

    override fun center(lat: Angle, lon: Angle) {
        super.center(lat, lon)
        reclip()
    }

    // TODO check tests still some issues with extentPostClip. Don't properly clip bottom border.
    // TODO Implement different extentPostClip to pass null tests
    private fun reclip() {
        val k = PI * scale
        val invert = RotationProjector(rotateLambda, rotatePhi, rotateGamma).invert(GeoPoint())

        val projected = projector.project(invert)
        val t0 = projected.x
        val t1 = projected.y

        this.extentPostClip = when {
            extentPostClip == null -> Extent(t0 - k, t1 - k, k * 2, k * 2)
            projector is MercatorProjector -> Extent(
                max(t0 - k, extentPostClip!!.x0),
                extentPostClip!!.y0,
                max(0.0, min(k * 2, extentPostClip!!.width)),
                extentPostClip!!.height
            )
            else -> Extent(
                extentPostClip!!.x0,
                max(t1 - k, extentPostClip!!.y0),
                extentPostClip!!.width,
                min(k * 2, extentPostClip!!.height)
            )
        }
    }
}
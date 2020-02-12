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
import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geo.projection.common.ComposedProjection
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent
import io.data2viz.math.EPSILON
import io.data2viz.math.deg

/**
 * @see AlbersUSAProjection
 */
fun albersUSAProjection(init: AlbersUSAProjection.() -> Unit = {}) = AlbersUSAProjection().also {
    it.scale = 1070.0
}.also(init)


/**
 * A composite projection for the United States, configured by default for
 * 960×500. The projection also works quite well at 960×600 if you change the
 * scale to 1285 and adjust the translate accordingly. The set of standard
 * parallels for each region comes from USGS, which is published here:
 * [http://egsc.usgs.gov/isb/pubs/MapProjections/projections.html#albers]
 * This is a U.S.-centric composite projection of three ConicEqualArea projections:
 * Albers is used for the lower forty-eight states,
 * and separate conic equal-area projections are used for Alaska and Hawaii.
 * Note that the scale for Alaska is diminished: it is projected at 0.35× its true relative area.
 *
 * @see ConicEqualAreaProjector
 */
class AlbersUSAProjection : ComposedProjection() {


    private var streamPoint: Point3D = Point3D()
    // Strange logic from d3 need refactor. Look at project implementation
    lateinit var lower48Point: Stream<GeoJsonPoint>
    lateinit var alaskaPoint: Stream<GeoJsonPoint>
    lateinit var hawaiiPoint: Stream<GeoJsonPoint>

    val pointStream = object : Stream<Point3D>() {
        override fun point(point: Point3D) {
            streamPoint = point
        }
    }

    private val lower48 = albersProjection()
    private val alaska = conicEqualAreaProjection {
        rotate(154.0.deg, 0.0.deg)
        center((-2.0).deg, 58.5.deg)
        parallels(55.0.deg, 65.0.deg)
    }
    private val hawaii = conicEqualAreaProjection {
        rotate(157.0.deg, 0.0.deg)
        center((-3.0).deg, 19.9.deg)
        parallels(8.0.deg, 18.0.deg)

    }
    override val mainProjection: Projection
        get() = lower48

    override val allProjections: Collection<Projection> = listOf(lower48, alaska, hawaii)

    override var scale: Double
        get() = lower48.scale
        set(value) {
            lower48.scale = value
            alaska.scale = value * 0.35
            hawaii.scale = value
        }

    override var precision: Double
        get() = lower48.precision
        set(value) {
            lower48.precision = value
            alaska.precision = value * 0.35
            hawaii.precision = value
        }


    override var translateX: Double
        get() = super.translateX
        set(value) {
            translate(value, lower48.translateY)
        }

    override var translateY: Double
        get() = super.translateY
        set(value) {
            translate(lower48.translateX, value)
        }

    override fun translate(x: Double, y: Double) {
        val k = lower48.scale

        lower48.translate(x, y)
        alaska.translate(x - 0.307 * k, y + 0.201 * k)
        hawaii.translate(x - 0.205 * k, y + 0.212 * k)

        initClipExtent(x, y)
    }

    private fun initClipExtent(x: Double, y: Double) {

        val k = lower48.scale
        // TODO: need refactor
        // Strange logic from d3: lower48Point, alaskaPoint, hawaiiPoint should be refactored and removed

        lower48.extentPostClip = Extent(
            x - 0.455 * k,
            y - 0.238 * k,
            x + 0.455 * k,
            y + 0.238 * k
        )

        lower48Point = lower48.bindTo(pointStream)

        alaska.extentPostClip = Extent(
            x - 0.425 * k + EPSILON,
            y + 0.120 * k + EPSILON,
            x - 0.214 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )
        alaskaPoint = alaska.bindTo(pointStream)

        hawaii.extentPostClip = Extent(
            x - 0.214 * k + EPSILON,
            y + 0.166 * k + EPSILON,
            x - 0.115 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )
        hawaiiPoint = hawaii.bindTo(pointStream)
    }

    override fun project(point: GeoJsonPoint): Point3D {

        // TODO: need refactor
        // strange logic taken from d3. Should be refactored to something similar to invert implementation
        streamPoint = Point3D(Double.NaN,Double.NaN)

        lower48Point.point(point)

        if (streamPoint.x.isNaN() || streamPoint.y.isNaN()) {
            alaskaPoint.point(point)
        }
        if (streamPoint.x.isNaN() || streamPoint.y.isNaN()) {
            hawaiiPoint.point(point)
        }
        return Point3D(streamPoint.x, streamPoint.y)
    }

    override fun invert(point: Point3D): GeoJsonPoint {
        val k = lower48.scale

        val newX = (point.x - lower48.translateX) / k
        val newY = (point.y - lower48.translateY) / k


        val projection = when {
            newY >= 0.120 && newY < 0.234 && newX >= -0.425 && newX < -0.214 -> {
                alaska
            }
            newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115 ->  {
                hawaii
            }
            else ->  {
                lower48
            }
        }

        return projection.invert(point)
    }

    init {
        initClipExtent(lower48.translateX, lower48.translateY)
    }

}

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

package io.data2viz.geo.projection.common

import io.data2viz.geo.GeoJsonPoint
import io.data2viz.geo.Point3D
import io.data2viz.geo.geometry.clip.ClipStreamBuilder
import io.data2viz.geo.geometry.clip.NoClipPoint3D
import io.data2viz.geo.geometry.clip.antimeridianPreClip
import io.data2viz.geo.stream.DelegateStreamAdapter
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle
import io.data2viz.math.rad
import kotlin.math.sqrt


/**
 * Create [Projection] for give [Projector]
 */
fun projection(projector: Projector<GeoJsonPoint, Point3D>, init: ProjectorProjection.() -> Unit): Projection =
    ProjectorProjection(projector)
        .apply(init)


private fun transformRotate(rotateProjector: Projector<GeoJsonPoint, GeoJsonPoint>): (stream: Stream<GeoJsonPoint>) -> DelegateStreamAdapter<GeoJsonPoint> =
    { stream: Stream<GeoJsonPoint> ->
        object : DelegateStreamAdapter<GeoJsonPoint>(stream) {
            override fun point(point:GeoJsonPoint) {
                val projection = rotateProjector.project(point)
                stream.point(projection)
            }
        }
}

/**
 * Base [Projection] implementation
 *
 * @see Projection
 * @see ComposedProjection
 */
open class ProjectorProjection(val projector: Projector<GeoJsonPoint, Point3D>) : Projection() {

    private var _translateX = 480.0
    private var _translateY = 250.0


    // Center
    private var _recenterDx = 0.0
    private var _recenterDy = 0.0

    // in radians
    private var _centerLat = 0.0
    // in radians
    private var _centerLon = 0.0

    private var _scale = 150.0

    /**
     * TODO: rework to affine matrix transformations or at least separate Scale & Translate phase
     */
    protected lateinit var composedTransformationsProjector: Projector<GeoJsonPoint, Point3D>

    protected val translateAndScaleProjector = TranslateAndScaleProjector(projector, _scale, _recenterDx, _recenterDy)

    // Precision
    private var _precisionDelta2 = 0.5

    // Rotate
    protected var _rotationLambda = 0.0
    protected var _rotationPhi = 0.0
    protected var _rotationGamma = 0.0
    protected lateinit var rotator: Projector<GeoJsonPoint, GeoJsonPoint>

    override var preClip: ClipStreamBuilder<GeoJsonPoint> = antimeridianPreClip

    override var postClip: ClipStreamBuilder<Point3D> = NoClipPoint3D

    private var resampleProjector: (Stream<Point3D>) -> Stream<GeoJsonPoint> = resample(translateAndScaleProjector, _precisionDelta2)

    override var scale: Double
        get() = _scale
        set(value) {
            _scale = value
            recenter()
        }


    // Translate
    override var translateX
        get () = _translateX
        set(value) {
            _translateX = value
            recenter()
        }
    override var translateY
        get () = _translateY
        set(value) {
            _translateY = value
            recenter()
        }


    override fun translate(x: Double, y: Double) {
        _translateX = x
        _translateY = y
        recenter()
    }


    override var centerLat
        get() = _centerLat.rad
        set(value) {
            _centerLat = value.rad
            recenter()
        }
    override var centerLon
        get() = _centerLon.rad
        set(value) {
            _centerLon = value.rad
            recenter()
        }


    override fun center(lat: Angle, lon: Angle) {
        _centerLat = lat.rad
        _centerLon = lon.rad
        recenter()
    }

    override var rotateLambda
        get() = _rotationLambda.rad
        set(value) {
            _rotationLambda = value.rad
            recenter()
        }

    override var rotatePhi
        get() = _rotationPhi.rad
        set(value) {
            _rotationPhi = value.rad
            recenter()
        }


    override var rotateGamma
        get() = _rotationGamma.rad
        set(value) {
            _rotationGamma = value.rad
            recenter()
        }


    override fun rotate(lambda: Angle, phi: Angle, gamma: Angle?) {
        _rotationLambda = lambda.rad
        _rotationPhi = phi.rad
        _rotationGamma = gamma?.rad ?: 0.0
        recenter()
    }


    override var precision: Double
        get() = sqrt(_precisionDelta2)
        set(value) {
            _precisionDelta2 = value * value
            resampleProjector = resample(translateAndScaleProjector, _precisionDelta2)
        }


    override fun bindTo(downstream: Stream<Point3D>): Stream<GeoJsonPoint> {
        return transformRotate(rotator)(
                    preClip.bindTo(
                        resampleProjector(
                            postClip.bindTo(downstream)))
        )
    }


    override fun project(point: GeoJsonPoint): Point3D = composedTransformationsProjector.project(point)
    override fun invert(point: Point3D): GeoJsonPoint = composedTransformationsProjector.invert(point)

    private fun recenter() {
        rotator = createRotateRadiansProjector(_rotationLambda, _rotationPhi, _rotationGamma)
        composedTransformationsProjector = ComposedProjector(rotator, translateAndScaleProjector)

        val projectedCenter = projector.project(GeoJsonPoint(_centerLat.rad, _centerLon.rad))

        _recenterDx = translateX - (projectedCenter.x * _scale)
        _recenterDy = translateY + (projectedCenter.y * _scale)

        translateAndScaleProjector.scale = _scale
        translateAndScaleProjector.recenterDx = _recenterDx
        translateAndScaleProjector.recenterDy = _recenterDy
    }

}


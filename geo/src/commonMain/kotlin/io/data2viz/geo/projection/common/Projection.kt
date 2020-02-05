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


import io.data2viz.geo.geometry.clip.*
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle
import io.data2viz.math.deg


/**
 * A [Projector]
 * with transformations: [translate], [scale], [rotate], [precision], [center]
 * and clipping: [preClip], [postClip]
 *
 * Use [bindTo] to combine all operations
 *
 * @see Stream
 */
abstract class Projection : Projector {
    /**
     * The scale factor corresponds linearly to the distance between projected points;
     * however, absolute scale factors are not equivalent across projections.
     */
    abstract var scale: Double

    /**
     * Determines the pixel coordinates of the projection’s center by X axys
     * @see translate
     */
    open var translateX: Double = 0.0
    /**
     * Determines the pixel coordinates of the projection’s center by Y axys
     * @see translate
     */
    open var translateY: Double = 0.0

    /**
     * Determines latitude coordinate of projection geo center
     * @see center
     */
    open var centerLat: Angle = 0.deg
    /**
     * Determines longtitude coordinate of projection geo center
     * @see center
     */
    open var centerLon: Angle = 0.deg

    /**
     * The threshold for the projection’s adaptive resampling pixels.
     * This value corresponds to the Douglas–Peucker distance.
     * Defaults to √0.5 ≅ 0.70710…
     */
    abstract var precision: Double

    /**
     * Rotate projection by X axys
     * Correspond to yaw
     * @see rotate
     */
    open var rotateLambda: Angle = 0.deg

    /**
     * Rotate projection by Y axys
     * Correspond to pitch
     * @see rotate
     */
    open var rotatePhi: Angle = 0.deg

    /**
     * Rotate projection by Z axys
     * Correspond to roll
     * @see rotate
     */
    open var rotateGamma: Angle = 0.deg


    /**
     * The translation offset determines the pixel coordinates of the projection’s center.
     * The default translation offset places ⟨0°,0°⟩ at the center of a 960×500 area.
     * Useful, when you want change both x & y with minimum calculations
     * @see translateX
     * @see translateY
     */
    abstract fun translate(x: Double, y: Double)


    /**
     * a two-element array of longitude and latitude in degrees
     *
     * Useful, when you want change both lat & lon with minimum calculations
     *
     * @see centerLat
     * @see centerLon
     */
    abstract fun center(lat: Angle, lon: Angle)

    /**
     * The projection’s three-axis spherical rotation to the specified angles
     * specifying the rotation angles in degrees about each spherical axis
     * (these correspond to yaw, pitch and roll).
     *
     * Useful, when you want rotate all 3-axys with minimum calculations
     *
     * @see rotateLambda
     * @see rotatePhi
     * @see rotateGamma
     */
    abstract fun rotate(lambda: Angle, phi: Angle, gamma: Angle? = null)


    /**
     * If preclip is specified, sets the projection’s spherical clipping to the specified function
     * and returns the projection.
     * If preclip is not specified, returns the current spherical clipping function.
     *
     * By default [antimeridianPreClip]
     *
     * @see NoClip
     * @see anglePreClip
     * @see antimeridianPreClip
     */
    abstract var preClip: ClipStreamBuilder

    /**
     * If postclip is specified, sets the projection’s cartesian clipping
     * to the specified function and returns the projection.
     * If postclip is not specified,
     * returns the current cartesian clipping function .
     *
     * * By default [NoClip]
     *
     * @see extentPostClip
     */
    abstract var postClip: ClipStreamBuilder


    /**
     * Returns a projection stream that ultimately sends calls to the specified downstream.
     * Any input geometry is projected before being streamed to the downstream.
     *
     * A typical projection involves several geometry transformations:
     * the input geometry is first converted to radians, rotated on three axes,
     * clipped to the small circle or cut along the antimeridian, and lastly projected to the plane
     * with adaptive resampling, scale and translation.
     *
     */
    internal abstract fun bindTo(downstream: Stream): Stream


}




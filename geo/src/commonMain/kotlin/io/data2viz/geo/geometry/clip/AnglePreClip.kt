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

package io.data2viz.geo.geometry.clip

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.math.Angle
import io.data2viz.math.toRadians

private class AnglePreClip(val angle: Angle) : ClipStreamBuilder<GeoPoint> {

    val transformedAngleInDegrees = (angle.deg % 360)
    val clipCircle = CirclePreClip(transformedAngleInDegrees.toRadians())

    override fun bindTo(downstream: Stream<GeoPoint>): Stream<GeoPoint> {
        return clipCircle.bindTo(downstream)
    }

}

/**
 * Enable to set or get a small circle PreClip from an Angle.
 *
 * If set with null Angle, switches to antimeridian cutting rather than small-circle clipping.
 *
 * If angle is not specified, returns the current clip angle which defaults to null.
 *
 * Small-circle clipping is independent of viewport clipping via projection.clipExtent.
 */
var Projection.anglePreClip: Angle?
    get() = (preClip as? AnglePreClip)?.angle

    set(value) {
        if (value != null) {
            preClip = AnglePreClip(value)
        } else {
            preClip = antimeridianPreClip
        }
    }
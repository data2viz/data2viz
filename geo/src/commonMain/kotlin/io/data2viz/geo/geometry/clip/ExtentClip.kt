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

import io.data2viz.geo.StreamPoint
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent


class ExtentClip(val extent: Extent) : ClipStreamBuilder {

    val clipRectangle = RectangleClipper(extent)

    override fun bindTo(downstream: Stream<StreamPoint>): Stream<StreamPoint> {
        return clipRectangle.clipLine(downstream)
    }

}

/**
 * Enable to get or set a RectanglePostClip as an Extent (in pixels).
 */
var Projection.extentPostClip: Extent?
    get() = (postClip as? ExtentClip)?.extent

    set(value) {
        if (value != null) {
            postClip = ExtentClip(value)
        } else {
            postClip = NoClip
        }
    }
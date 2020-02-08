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

package io.data2viz.geo.geometry.path

import io.data2viz.geo.StreamPoint
import io.data2viz.geo.stream.Stream
import io.data2viz.math.TAU
import io.data2viz.geom.Path
import io.data2viz.geo.geojson.GeoPath

/**
 * This stream is the last operation of a projection: using a Path to draw the
 * GeoJson objects on a path.
 *
 * All polygons, and lines produce moveTo and lineTo calls on Path.
 * GeoJson points produce `arc` call to render a circle.
 *
 * @see GeoPath
 */
internal class PathStream(private val path: Path) : Stream<StreamPoint>() {


    enum class PathCmd {
        MOVE,
        LINE,
        POINT
    }

    /**
     * Radius of the circle used to render a GeoJson Point
     */
    var pointRadius = 4.5

    private var line = false

    private var pointCmd = PathCmd.POINT

    override fun polygonStart() {
        line = true
    }

    override fun polygonEnd() {
        line = false
    }

    override fun lineStart() {
        pointCmd = PathCmd.MOVE
    }

    override fun lineEnd() {
        if (line) path.closePath()
        pointCmd = PathCmd.POINT
    }

    /**
     * Process a Point. Depending of the current draw path
     * it results in different calls on the Path.
     */
    override fun point(point: StreamPoint) {
        when (pointCmd) {
            PathCmd.MOVE -> {
                path.moveTo(point.x, point.y)
                pointCmd = PathCmd.LINE
            }
            PathCmd.LINE -> path.lineTo(point.x, point.y)
            PathCmd.POINT ->  {
                path.moveTo(point.x + pointRadius, point.y)
                path.arc(point.x, point.y, pointRadius, 0.0, TAU, false)
            }
        }
    }
}
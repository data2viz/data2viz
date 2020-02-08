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

package io.data2viz.geo.stream

import io.data2viz.geo.StreamPoint

/**
 * Transforms geometry using a sequence of function calls, rather than materializing
 * intermediate representations, to minimize overhead. Streams must implement several
 * methods to receive input geometry. Streams are inherently stateful; the meaning of a
 * point depends on whether the point is inside of a line, and likewise a line is distinguished
 * from a ring by a polygon. Despite the name “stream”, these method calls are currently synchronous.
 */
abstract class Stream<T> {

    /**
     * Indicates a point with the specified coordinates translateX and translateY (and optionally z).
     * The coordinate system is unspecified and implementation-dependent; for example,
     * projection streams require spherical coordinates in degrees as input. Outside the
     * context of a polygon or line, a point indicates a point geometry object (Point or
     * MultiPoint). Within a line or polygon ring, the point indicates a control point.
     */
    open fun point(point: T) {}

    /**
     * Indicates the start of a line or ring. Within a polygon, indicates the start of a ring.
     * The first ring of a polygon is the exterior ring, and is typically clockwise.
     * Any subsequent rings indicate holes in the polygon, and are typically counterclockwise.
     */
    open fun lineStart() {}

    /**
     * Indicates the end of a line or ring. Within a polygon, indicates the end of a ring. Unlike GeoJSON,
     * the redundant closing coordinate of a ring is not indicated via point, and instead is implied
     * via lineEnd within a polygon.
     *
     * Thus, the given polygon input:
     *
     *  {
     *      "type": "Polygon",
     *      "coordinates": [
     *          [[0, 0], [0, 1], [1, 1], [1, 0], [0, 0]]
     *      ]
     *  }
     *
     *  Will produce the following series of method calls on the stream:
     *
     *  stream.polygonStart();
     *  stream.lineStart();
     *  stream.point(0, 0);
     *  stream.point(0, 1);
     *  stream.point(1, 1);
     *  stream.point(1, 0);
     *  stream.lineEnd();
     *  stream.polygonEnd();
     */
    open fun lineEnd() {}


    /**
     * Indicates the start of a polygon. The first line of a polygon indicates the exterior ring,
     * and any subsequent lines indicate interior holes.
     */
    open fun polygonStart() {}

    /**
     * Indicates the end of a polygon.
     */
    open fun polygonEnd() {}


    /**
     * Indicates the sphere (the globe; the unit sphere centered at ⟨0,0,0⟩).
     */
    open fun sphere() {}
}
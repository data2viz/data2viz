package io.data2viz.geo.stream

/**
 * Transforms geometry using a sequence of function calls, rather than materializing
 * intermediate representations, to minimize overhead. Streams must implement several
 * methods to receive input geometry. Streams are inherently stateful; the meaning of a
 * point depends on whether the point is inside of a line, and likewise a line is distinguished
 * from a ring by a polygon. Despite the name “stream”, these method calls are currently synchronous.
 */
interface Stream {

    /**
     * Indicates a point with the specified coordinates translateX and translateY (and optionally z).
     * The coordinate system is unspecified and implementation-dependent; for example,
     * projection streams require spherical coordinates in degrees as input. Outside the
     * context of a polygon or line, a point indicates a point geometry object (Point or
     * MultiPoint). Within a line or polygon ring, the point indicates a control point.
     */
    fun point(x: Double, y: Double, z: Double) {}

    /**
     * Indicates the start of a line or ring. Within a polygon, indicates the start of a ring.
     * The first ring of a polygon is the exterior ring, and is typically clockwise.
     * Any subsequent rings indicate holes in the polygon, and are typically counterclockwise.
     */
    fun lineStart() {}

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
    fun lineEnd() {}


    /**
     * Indicates the start of a polygon. The first line of a polygon indicates the exterior ring,
     * and any subsequent lines indicate interior holes.
     */
    fun polygonStart() {}

    /**
     * Indicates the end of a polygon.
     */
    fun polygonEnd() {}


    /**
     * Indicates the sphere (the globe; the unit sphere centered at ⟨0,0,0⟩).
     */
    fun sphere() {}
}
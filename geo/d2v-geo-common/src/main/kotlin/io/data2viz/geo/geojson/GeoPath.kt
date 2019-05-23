package io.data2viz.geo.geojson

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.geometry.path.*
import io.data2viz.geom.Extent
import io.data2viz.geo.projection.identityProjection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Path
import io.data2viz.geojson.*


/**
 * Creates a new geographic path generator with the default settings.
 * If projection is specified, sets the current projection.
 * If context is specified, sets the current context.
 * Renders the given object, which may be any GeoJSON feature or geometry object:
 * [Point] - a single position.
 * [MultiPoint] - an array of positions.
 * [LineString] - an array of positions forming a continuous line.
 * [MultiLineString] - an array of arrays of positions forming several lines.
 * [Polygon] - an array of arrays of positions forming a polygon (possibly with holes).
 * [MultiPolygon] - a multidimensional array of positions forming multiple polygons.
 * [GeometryCollection] - an array of geometry objects.
 * [Feature] - a feature containing one of the above geometry objects.
 * [FeatureCollection] - an array of feature objects.
 * The type [Sphere] is also supported, which is useful for rendering the outline of the globe;
 * a sphere has no coordinates. Any additional arguments are passed along to the pointRadius accessor.
 *
 */
fun geoPath(projection: Projection? = null, context: Path? = null) =
    GeoPath(projection ?: identityProjection(), context)

/**
 * If a projection is specified, sets the current projection to the specified projection.
 * If projection is not specified, use the identity transformation: the input geometry is not projected and is instead
 * rendered directly in raw coordinates.
 * This can be useful for fast rendering of pre-projected geometry, or for fast rendering of the equirectangular
 * projection.
 *
 * The given projection is typically one of built-in geographic projections; however, any object that exposes a
 * projection.stream function can be used, enabling the use of custom projections.
 *
 * @see PathStream
 */
class GeoPath(val projection: Projection, val context: Path?) {

    /**
     *
     * If radius is specified, sets the radius used to display Point and MultiPoint geometries to the specified number.
     * If radius is not specified, returns the current radius accessor, which defaults to 4.5.
     * @see PathStream.pointRadius
     */
    var pointRadius
        get() = contextStream!!.pointRadius
    set(value) {
        contextStream!!.pointRadius
    }


    private val areaStream      = AreaStream()
    private val boundsStream    = BoundsStream()
    private val centroidStream  = CentroidStream()
    private val measureStream   = MeasureStream()
    private val contextStream: PathStream? = if (context != null) PathStream(
        context
    ) else null

    /**
     * Renders the given object, which may be any GeoJSON feature or geometry object:
     * Point - a single position.
     * MultiPoint - an array of positions.
     * LineString - an array of positions forming a continuous line.
     * MultiLineString - an array of arrays of positions forming several lines.
     * Polygon - an array of arrays of positions forming a polygon (possibly with holes).
     * MultiPolygon - a multidimensional array of positions forming multiple polygons.
     * GeometryCollection - an array of geometry objects.
     * Feature - a feature containing one of the above geometry objects.
     * FeatureCollection - an array of feature objects.
     *
     * The type Sphere is also supported, which is useful for rendering the outline of the globe; a sphere has no
     * coordinates. Any additional arguments are passed along to the pointRadius accessor.
     *
     * Separate path elements are typically slower than a single path element.
     * However, distinct path elements are useful for styling and interaction (e.g., click or mouseover).
     */
    fun path(geo: GeoJsonObject): Path {
        requireNotNull(context) { "Cannot use GeoPath.svgPath() without a valid context." }
        requireNotNull(contextStream) { "Cannot use GeoPath.svgPath() without a valid context." }
        geo.stream(projection.stream(contextStream))
        return context
    }

    /**
     * Returns the projected planar centroid (typically in pixels) for the specified GeoJSON object.
     * This is handy for, say, labeling state or county boundaries, or displaying a symbol map.
     * For example, a noncontiguous cartogram might scale each state around its centroid.
     * This method observes any clipping performed by the projection; see projection.anglePreClip and projection.extentPostClip.
     * This is the planar equivalent of GeoCentroidStream.
     */
    fun centroid(geo: GeoJsonObject): DoubleArray {
        geo.stream(projection.stream(centroidStream))
        return centroidStream.result()
    }

    /**
     * Returns the projected planar area (typically in square pixels) for the specified GeoJSON object.
     * Point, MultiPoint, LineString and MultiLineString geometries have zero area.
     * For Polygon and MultiPolygon geometries, this method first computes the area of the exterior ring, and then
     * subtracts the area of any interior holes.
     * This method observes any clipping performed by the projection; see projection.anglePreClip and projection.extentPostClip.
     * This is the planar equivalent of GeoAreaStream.
     */
    fun area(geo: GeoJsonObject): Double {
        geo.stream(projection.stream(areaStream))
        return areaStream.result()
    }

    /**
     * Returns the projected planar bounding box (typically in pixels) for the specified GeoJSON object.
     * The bounding box is represented by an Extent: (translateX₀, translateY₀, translateX₁, translateY₁),
     * where translateX₀ is the minimum translateX-coordinate,
     * translateY₀ is the minimum translateY-coordinate,
     * translateX₁ is maximum translateX-coordinate,
     * and translateY₁ is the maximum translateY-coordinate.
     * This is handy for, say, zooming in to a particular feature.
     * (Note that in projected planar coordinates, the minimum latitude is typically the maximum translateY-value, and the
     * maximum latitude is typically the minimum translateY-value.)
     * This method observes any clipping performed by the projection; see projection.anglePreClip and projection.extentPostClip.
     * This is the planar equivalent of GeoBoundsStream.
     */
    fun bounds(geo: GeoJsonObject): Extent {
        geo.stream(projection.stream(boundsStream))
        return boundsStream.result()
    }

    /**
     * Returns the projected planar length (typically in pixels) for the specified GeoJSON object.
     * Point and MultiPoint geometries have zero length.
     * For Polygon and MultiPolygon geometries, this method computes the summed length of all rings.
     * This method observes any clipping performed by the projection; see projection.anglePreClip and projection.extentPostClip.
     * This is the planar equivalent of GeoLengthStream.
     */
    fun measure(geo: GeoJsonObject): Double {
        geo.stream(projection.stream(measureStream))
        return measureStream.result()
    }
}
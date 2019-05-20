package io.data2viz.geo.path

import io.data2viz.geom.Extent
import io.data2viz.geo.Projection
import io.data2viz.geo.projection.identityProjection
import io.data2viz.geo.stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Path

fun geoPath(projection: Projection? = null, context: Path? = null) =
    GeoPath(if (projection == null) identityProjection() else projection, context)

/**
 * If a projection is specified, sets the current projection to the specified projection.
 * If projection is not specified, use the identity transformation: the input geometry is not projected and is instead
 * rendered directly in raw coordinates.
 * This can be useful for fast rendering of pre-projected geometry, or for fast rendering of the equirectangular
 * projection.
 *
 * The given projection is typically one of built-in geographic projections; however, any object that exposes a
 * projection.stream function can be used, enabling the use of custom projections.
 */
class GeoPath(val projection: Projection, val context: Path?) {

    private val pathArea = PathArea()
    private val pathBounds = PathBounds()
    private val pathCentroid = PathCentroid()
    private val pathMeasure = PathMeasure()
    private val contextStream: PathContext? = if (context != null) PathContext(context) else null

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
     * Separate drawPath elements are typically slower than a single drawPath element.
     * However, distinct drawPath elements are useful for styling and interaction (e.g., click or mouseover).
     */
    fun drawPath(geo: GeoJsonObject): Path {
        requireNotNull(context) { "Cannot use GeoPath.svgPath() without a valid context." }
        requireNotNull(contextStream) { "Cannot use GeoPath.svgPath() without a valid context." }
        stream(geo, projection.stream(contextStream))
        return context
    }

    /**
     * Returns the projected planar drawCentroid (typically in pixels) for the specified GeoJSON object.
     * This is handy for, say, labeling state or county boundaries, or displaying a symbol map.
     * For example, a noncontiguous cartogram might scale each state around its drawCentroid.
     * This method observes any clipping performed by the projection; see projection.clipAngle and projection.clipExtent.
     * This is the planar equivalent of GeoCentroid.
     */
    fun drawCentroid(geo: GeoJsonObject): DoubleArray {
        stream(geo, projection.stream(pathCentroid))
        return pathCentroid.result()
    }

    /**
     * Returns the projected planar drawArea (typically in square pixels) for the specified GeoJSON object.
     * Point, MultiPoint, LineString and MultiLineString geometries have zero drawArea.
     * For Polygon and MultiPolygon geometries, this method first computes the drawArea of the exterior ring, and then
     * subtracts the drawArea of any interior holes.
     * This method observes any clipping performed by the projection; see projection.clipAngle and projection.clipExtent.
     * This is the planar equivalent of GeoArea.
     */
    fun drawArea(geo: GeoJsonObject): Double {
        stream(geo, projection.stream(pathArea))
        return pathArea.result()
    }

    /**
     * Returns the projected planar bounding box (typically in pixels) for the specified GeoJSON object.
     * The bounding box is represented by an Extent: (x₀, y₀, x₁, y₁),
     * where x₀ is the minimum x-coordinate,
     * y₀ is the minimum y-coordinate,
     * x₁ is maximum x-coordinate,
     * and y₁ is the maximum y-coordinate.
     * This is handy for, say, zooming in to a particular feature.
     * (Note that in projected planar coordinates, the minimum latitude is typically the maximum y-value, and the
     * maximum latitude is typically the minimum y-value.)
     * This method observes any clipping performed by the projection; see projection.clipAngle and projection.clipExtent.
     * This is the planar equivalent of GeoBounds.
     */
    fun drawBounds(geo: GeoJsonObject): Extent {
        stream(geo, projection.stream(pathBounds))
        return pathBounds.result()
    }

    /**
     * Returns the projected planar length (typically in pixels) for the specified GeoJSON object.
     * Point and MultiPoint geometries have zero length.
     * For Polygon and MultiPolygon geometries, this method computes the summed length of all rings.
     * This method observes any clipping performed by the projection; see projection.clipAngle and projection.clipExtent.
     * This is the planar equivalent of GeoLength.
     */
    fun drawMeasure(geo: GeoJsonObject): Double {
        stream(geo, projection.stream(pathMeasure))
        return pathMeasure.result()
    }
}

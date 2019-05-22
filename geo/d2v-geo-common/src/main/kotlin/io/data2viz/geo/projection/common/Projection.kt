package io.data2viz.geo.projection.common


import io.data2viz.geo.geometry.clip.clipAntimeridian
import io.data2viz.geo.geometry.clip.clipCircle
import io.data2viz.geo.stream.DelegateStreamAdapter
import io.data2viz.geo.stream.Stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.math.Angle
import io.data2viz.math.rad
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.sqrt

/**
 * Project a single Geo point (lon, lat)
 */
interface Projectable {

    /**
     * Project a stream point
     * Internal API usually call projectLambda & projectPhi separately to avoid new Double array allocation
     */
    fun project(lambda: Double, phi: Double) = doubleArrayOf(
        projectLambda(lambda, phi),
        projectPhi(lambda, phi)
    )

    /**
     * Default implementation of a longitude projection (can be overrided)
     * Required for performance (avoid allocating new Double array on each project)
     * see project(lambda: Double, phi: Double)
     */
    fun projectLambda(lambda: Double, phi: Double): Double

    /**
     * Default implementation of a latitude projection (can be overrided)
     * Required for performance (avoid allocating new Double array on each project)
     * see project(lambda: Double, phi: Double)
     */
    fun projectPhi(lambda: Double, phi: Double): Double
}

/**
 * Todo document
 */
interface Invertable {
    /**
     * Returns a new array [longitude, latitude] in degrees representing the unprojected point of the given projected point.
     * The point must be specified as a two-element array [x, y] (typically in pixels).
     * Todo document
     * May return null if the specified point has no defined projected position, such as when the point is outside the clipping bounds of the projection.
     */
    fun invert(lambda: Double, phi: Double): DoubleArray
}

/**
 * Todo document
 */
interface Projector : Projectable, Invertable



/**
 * Todo document
 */
interface Projection : Projectable, Invertable {
    /**
     * The scale factor corresponds linearly to the distance between projected points;
     * however, absolute scale factors are not equivalent across projections.
     */
    var scale: Double

    /**
     ** TODO: check with translate
     * Determines the pixel coordinates of the projection’s center by X axys
     */
    var x: Double
    /**
     ** TODO: check with translate
     * Determines the pixel coordinates of the projection’s center by Y axys
     */
    var y: Double

    /**
     * a two-element array of longitude and latitude in degrees
     */
    var center: Array<Angle>
    /**
     * The threshold for the projection’s adaptive resampling pixels.
     * This value corresponds to the Douglas–Peucker distance.
     * Defaults to √0.5 ≅ 0.70710…
     */
    var precision: Double
    /**
     * The projection’s three-axis spherical rotation to
     * the specified angles, which must be a two- or three-element array of numbers [lambda, phi, gamma]
     * specifying the rotation angles in degrees about each spherical axis
     * (these correspond to yaw, pitch and roll).
     */
    var rotate: Array<Angle>

    /**
     * TODO: check
     * If preclip is specified, sets the projection’s spherical clipping to the specified function and returns the projection. If preclip is not specified, returns the current spherical clipping function (see preclip).
     */
    var preClip: (Stream) -> Stream

    /**
     * TODO: check
     * If postclip is specified, sets the projection’s cartesian clipping to the specified function and returns the projection. If postclip is not specified, returns the current cartesian clipping function (see postclip).
     */
    var postClip: (Stream) -> Stream

    /**
     * TODO: check
     * If angle is specified, sets the projection’s clipping circle radius to the specified angle in degrees and returns the projection. If angle is null, switches to antimeridian cutting rather than small-circle clipping. If angle is not specified, returns the current clip angle which defaults to null. Small-circle clipping is independent of viewport clipping via projection.clipExtent.
     */
    var clipAngle: Double

    /**
     * TODO: check
     * If extent is specified, sets the projection’s viewport clip extent to the specified bounds in pixels and returns the projection. The extent bounds are specified as an array [[x₀, y₀], [x₁, y₁]], where x₀ is the left-side of the viewport, y₀ is the top, x₁ is the right and y₁ is the bottom. If extent is null, no viewport clipping is performed. If extent is not specified, returns the current viewport clip extent which defaults to null. Viewport clipping is independent of small-circle clipping via projection.clipAngle.
     */
    var clipExtent: Extent?

    /**
     * TODO: check
     * Returns a projection stream for the specified output stream.
     * Any input geometry is projected before being streamed to the output stream.
     * A typical projection involves several geometry transformations:
     * the input geometry is first converted to radians, rotated on three axes,
     * clipped to the small circle or cut along the antimeridian, and lastly projected to the plane
     * with adaptive resampling, scale and translation.
     */
    fun stream(stream: Stream): Stream


    /**
     * TODO: check
     * The translation offset determines the pixel coordinates of the projection’s center.
     * The default translation offset places ⟨0°,0°⟩ at the center of a 960×500 area.
     * It is equivalent for x = 0 & y = 0 but with better performance
     */
    fun translate(x: Double, y: Double)


    /**
     *
     * TODO: check
     * Sets the projection’s scale and translate to fit the specified GeoJSON object in the center of the given extent. The extent is specified as an array [[x₀, y₀], [x₁, y₁]], where x₀ is the left side of the bounding box, y₀ is the top, x₁ is the right and y₁ is the bottom. Returns the projection.

    For example, to scale and translate the New Jersey State Plane projection to fit a GeoJSON object nj in the center of a 960×500 bounding box with 20 pixels of padding on each side:
    var projection = d3.geoTransverseMercator()
    .rotate([74 + 30 / 60, -38 - 50 / 60])
    .fitExtent([[20, 20], [940, 480]], nj);
    Any clip extent is ignored when determining the new scale and translate. The precision used to compute the bounding box of the given object is computed at an effective scale of 150.
     */
    fun fitExtent(extent: Extent, geo: GeoJsonObject): Projection


    /**
     * TODO: check
     * A convenience method for projection.fitSize where the height is automatically chosen from the aspect ratio of object and the given constraint on width.
     *
     */
    fun fitWidth(width: Double, geo: GeoJsonObject): Projection

    /**
     * * TODO: check
     * A convenience method for projection.fitSize where the width is automatically chosen from the aspect ratio of object and the given contraint on height.
     */
    fun fitHeight(height: Double, geo: GeoJsonObject): Projection

    /**
     *
     * TODO: check
     * A convenience method for projection.fitExtent where the top-left corner of the extent is [0, 0]. The following two statements are equivalent:

    projection.fitExtent([[0, 0], [width, height]], object);
    projection.fitSize([width, height], object);
     */
    fun fitSize(width: Double, height: Double, geo: GeoJsonObject): Projection

    /**
     * TODO: remove from public API
     */
    fun recenter()
}

fun projection(projection: Projectable, init: MutableProjection.() -> Unit) = MutableProjection(
    projection
).apply(init)


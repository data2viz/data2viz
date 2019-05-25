package io.data2viz.geo.projection.common


import io.data2viz.geo.geometry.clip.*
import io.data2viz.geo.stream.Stream
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.Angle


/**
 * A [Projector]
 * with transformations: [translate], [scale], [rotate], [precision], [center]
 * and clipping: [preClip], [postClip]
 *
 * Use [stream] to combine all operations
 *
 * @see Stream
 */
interface Projection : Projector {
    /**
     * The scale factor corresponds linearly to the distance between projected points;
     * however, absolute scale factors are not equivalent across projections.
     */
    var scale: Double

    /**
     * Determines the pixel coordinates of the projection’s center by X axys
     * @see translate
     */
    var translateX: Double
    /**
     * Determines the pixel coordinates of the projection’s center by Y axys
     * @see translate
     */
    var translateY: Double

    /**
     * Determines latitude coordinate of projection geo center
     * @see center
     */
    var centerLat: Angle
    /**
     * Determines longtitude coordinate of projection geo center
     * @see center
     */
    var centerLon: Angle

    /**
     * The threshold for the projection’s adaptive resampling pixels.
     * This value corresponds to the Douglas–Peucker distance.
     * Defaults to √0.5 ≅ 0.70710…
     */
    var precision: Double

    /**
     * Rotate projection by X axys
     * Correspond to yaw
     * @see rotate
     */
    var rotateLambda: Angle

    /**
     * Rotate projection by Y axys
     * Correspond to pitch
     * @see rotate
     */
    var rotatePhi: Angle

    /**
     * Rotate projection by Z axys
     * Correspond to roll
     * @see rotate
     */
    var rotateGamma: Angle


    /**
     * The translation offset determines the pixel coordinates of the projection’s center.
     * The default translation offset places ⟨0°,0°⟩ at the center of a 960×500 area.
     * Useful, when you want change both x & y with minimum calculations
     * @see translateX
     * @see translateY
     */
    fun translate(x: Double, y: Double)


    /**
     * a two-element array of longitude and latitude in degrees
     *
     * Useful, when you want change both lat & lon with minimum calculations
     *
     * @see centerLat
     * @see centerLon
     */
    fun center(lat: Angle, lon: Angle)

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
    fun rotate(lambda: Angle, phi: Angle, gamma: Angle? = null)


    /**
     * If preclip is specified, sets the projection’s spherical clipping to the specified function
     * and returns the projection.
     * If preclip is not specified, returns the current spherical clipping function.
     *
     * By default [antimeridianPreClip]
     *
     * @see noPreClip
     * @see anglePreClip
     * @see antimeridianPreClip
     */
    var preClip: StreamPreClip

    /**
     * If postclip is specified, sets the projection’s cartesian clipping
     * to the specified function and returns the projection.
     * If postclip is not specified,
     * returns the current cartesian clipping function .
     *
     * * By default [noPostClip]
     *
     * @see extentPostClip
     * @see noPostClip
     */
    var postClip: StreamPostClip


    /**
     * Returns a projection stream for the specified output stream.
     * Any input geometry is projected before being streamed to the output stream.
     * A typical projection involves several geometry transformations:
     * the input geometry is first converted to radians, rotated on three axes,
     * clipped to the small circle or cut along the antimeridian, and lastly projected to the plane
     * with adaptive resampling, scale and translation.
     *
     * Example usage: [GeoJsonObject].stream()
     */
    fun stream(stream: Stream): Stream


}




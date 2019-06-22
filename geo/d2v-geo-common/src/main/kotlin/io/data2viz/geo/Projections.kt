package io.data2viz.geo

import io.data2viz.geo.projection.*
import io.data2viz.geo.projection.common.Projection


object Geo {
    object Projections {
        fun Albers(init: Projection.() -> Unit = {})            = albersProjection(init)
        fun AlbersUSA(init: Projection.() -> Unit = {})         = albersUSAProjection(init)
        fun AzimuthalEqualArea(init: Projection.() -> Unit = {})         = azimuthalEqualAreaProjection(init)
        fun AzimuthalEquidistant(init: Projection.() -> Unit = {})         = azimuthalEquidistant(init)
//        fun Conic(init: ConicProjection.() -> Unit = {})         = conicProjection(init)
        fun EqualEarth(init: Projection.() -> Unit = {})        = equalEarthProjection(init)
        fun Equirectangular(init: Projection.() -> Unit = {})   = equirectangularProjection(init)
        fun Identity(init: Projection.() -> Unit = {})          = identityProjection(init)
        fun Mercator(init: Projection.() -> Unit = {})          = mercatorProjection(init)
        fun NaturalEarth(init: Projection.() -> Unit = {})      = naturalEarthProjection(init)
        fun Orthographic(init: Projection.() -> Unit = {})      = orthographicProjection(init)
        fun Stereographic(init: Projection.() -> Unit = {})     = stereographicProjection(init)
        fun TransverseMercator(init: Projection.() -> Unit = {})     = transverseMercatorProjection(init)
    }
}


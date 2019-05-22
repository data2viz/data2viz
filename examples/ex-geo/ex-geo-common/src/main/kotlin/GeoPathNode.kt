package io.data2viz.viz

import io.data2viz.geo.projection.common.Projection
import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.projection.identityProjection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.PathGeom

open class GeoPathNode(
    var geoData: GeoJsonObject? = null,
    var geoProjection: Projection = identityProjection(),
    path: PathGeom = PathGeom()
): PathNode(path) {


    fun redrawPath() {
        val geoPath = geoPath(geoProjection, path)
        clearPath()
        geoPath.path(geoData!!)
    }
}

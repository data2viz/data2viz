package io.data2viz.geo.geometry

import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.test.TestBase
import io.data2viz.test.matchers.Matcher
import io.data2viz.test.matchers.Matchers
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToLong

fun TestBase.calculateSvgPath(projection: Projection, sourceGeoJsonObject: GeoJsonObject, precision: Int = 6, pointRadius : Double? = null): String {
    val path = PathGeom()

    val geoPath = geoPath(projection, path)
    if(pointRadius != null) {
        geoPath.pointRadius = pointRadius
    }
    geoPath.project(sourceGeoJsonObject)

    return path.svgPath.round()
}

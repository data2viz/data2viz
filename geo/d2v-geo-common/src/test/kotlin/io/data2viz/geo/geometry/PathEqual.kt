package io.data2viz.geo.geometry

import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToLong

fun calculateSvgPath(projection: Projection, sourceGeoJsonObject: GeoJsonObject,  precision: Int = 6, pointRadius : Double? = null): String {
    val path = PathGeom()

    val geoPath = geoPath(projection, path)
    if(pointRadius != null) {
        geoPath.pointRadius = pointRadius
    }
    geoPath.project(sourceGeoJsonObject)

    return normalizePath(path.svgPath, precision)
}

var reNumberRegex = Regex("""[-+]?(?:\d+\.\d+|\d+\.|\.\d+|\d+)(?:[eE][-]?\d+)?""")


/**
 * Replace numbers in svg path with given precision after floating point
 */
fun normalizePath(svgPath: String, precision: Int = 6): String =
    svgPath.replace(reNumberRegex) {

        val number = it.value.toDouble()

        val k = 10.0.pow(precision)

        val newNumber = (number * k).roundToLong() / k
        var newNumberString = newNumber.toString()
        val dotIndex = newNumberString.indexOf('.')

        val endIndex = min(dotIndex + precision + 1, newNumberString.length)
        if (dotIndex > 0) {
            newNumberString = newNumberString.substring(0, endIndex)
        }
        newNumberString
    }



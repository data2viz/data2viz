package io.data2viz.examples.geo

import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.equirectangular
import io.data2viz.geojson.FeatureCollection
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.promise
import org.w3c.fetch.Request
import kotlin.browser.window
import kotlin.js.Date


class Timer {
    var last = Date.now()

    fun log(msg: String) {
        val newTime = Date.now()
        println("$msg in ${newTime - last} ms")
        last = newTime
    }
}

private val timer = Timer()


fun main(args: Array<String>) {

    promise {
        val request = window.fetch(Request("ny.json"))
        val response = request.await()
        val geojson = response.text().await()
        timer.log("loading")
        val geoJsonObject = geojson.toGeoJsonObject()
        timer.log("parsing")

        val projection = equirectangular()
        projection.center = doubleArrayOf(-74.0, 40.7)
        projection.translate = doubleArrayOf(480.0, 350.0)
        projection.scale = 85000.0
//        projection.clipExtent = null
        projection.precision = .0
        timer.log("projection")
        val geoPath = geoPath(projection, svgPath())
        timer.log("geoPath")
        val path: SvgPath = geoPath.path(geoJsonObject) as SvgPath
        
        timer.log("path")

        val root = selectOrCreateSvg().apply {
            setAttribute("width", "$width")
            setAttribute("height", "$height")
        }
        
        root.viz { 
            addPath(path)
        }
        timer.log("adding path")

    }


}

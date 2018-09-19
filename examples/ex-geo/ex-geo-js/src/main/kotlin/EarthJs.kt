package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.timer.timer
import io.data2viz.viz.JsCanvasRenderer
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import io.data2viz.viz.bindRendererOn
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.promise
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import org.w3c.fetch.Request
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date

@Suppress("unused")

fun main(args: Array<String>) {
    promise {
        val request = window.fetch(Request("world-110m-30percent.json"))
        val response = request.await()
        geoViz(response.text().await().toGeoJsonObject()).bindRendererOn("viz")
    }
}
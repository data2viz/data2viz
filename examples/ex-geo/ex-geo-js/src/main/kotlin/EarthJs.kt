package io.data2viz.examples.geo

import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.bindRendererOn
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.promise
import org.w3c.fetch.Request
import kotlin.browser.window

@Suppress("unused")

fun main(args: Array<String>) {
    promise {
        val request = window.fetch(Request("world-110m-30percent.json"))
        val response = request.await()
        geoViz(response.text().await().toGeoJsonObject()).bindRendererOn("viz")
    }
}
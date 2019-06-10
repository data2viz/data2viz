package io.data2viz.examples.geo

import geoVizAutoRotate
import geoVizEventsControl
import io.data2viz.geo.geometry.geoGraticule
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.ExperimentalKZoomEvent
import io.data2viz.viz.Viz
import io.data2viz.viz.bindRendererOn
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import org.w3c.dom.*
import org.w3c.fetch.Request
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.createElement

@Suppress("unused")

var currentViz: Viz? = null

val canvaseVizHtmlElementId = "viz"
val selectFileHtmlElementId = "select_file"
val selectProjectionHtmlElementId = "select_projection"
val buttonStartStopHtmlElementId = "button_start_stop"

val vizWidth = 500.0
val vizHeight = 500.0


lateinit var selectFile: HTMLSelectElement
lateinit var selectProjection: HTMLSelectElement
lateinit var buttonStartStop: HTMLButtonElement

var isBenchmarkWithD3: Boolean = false
val isNeedAutoRotation: Boolean
    get() = isBenchmarkWithD3

var animationEnabled = true

@ExperimentalKZoomEvent
fun main(args: Array<String>) {

    selectFile = document.getElementById(selectFileHtmlElementId).unsafeCast<HTMLSelectElement>()
    selectProjection = document.getElementById(selectProjectionHtmlElementId).unsafeCast<HTMLSelectElement>()
    buttonStartStop = document.getElementById(buttonStartStopHtmlElementId).unsafeCast<HTMLButtonElement>()

    isBenchmarkWithD3 = document.getElementById("benchmark_with_d3") != null

    allFiles.forEach { filename ->
        selectFile.options.add(document.createElement("option") {
            setAttribute("value", filename)
            innerHTML = filename
        }.unsafeCast<UnionHTMLOptGroupElementOrHTMLOptionElement>())
    }

    allProjectionsNames.forEach { projectionName ->
        selectProjection.options.add(document.createElement("option") {
            setAttribute("value", projectionName)
            innerHTML = projectionName
        }.unsafeCast<UnionHTMLOptGroupElementOrHTMLOptionElement>())
    }

    selectFile.selectedIndex = defaultFileIndex
    selectProjection.selectedIndex = defaultProjectionIndex


    selectFile.onchange = { onSelectionChanged() }
    selectProjection.onchange = { onSelectionChanged() }
    buttonStartStop.onclick = {

        if (animationEnabled) {
            currentViz?.stopAnimations()
        } else {
            currentViz?.startAnimations()
        }

        animationEnabled = !animationEnabled
        it
    }

    onSelectionChanged()

}

@ExperimentalKZoomEvent
private fun onSelectionChanged() {

    val selectFile = document.getElementById(selectFileHtmlElementId).unsafeCast<HTMLSelectElement>()

    val selectProjection = document.getElementById(selectProjectionHtmlElementId).unsafeCast<HTMLSelectElement>()

    onSettingsChanged(selectFile, selectProjection)
}

@ExperimentalKZoomEvent
private fun onSettingsChanged(
    selectFile: HTMLSelectElement,
    selectProjection: HTMLSelectElement
) {

    var projectionValue = selectProjection.options[selectProjection.selectedIndex]!!.getAttribute("value")!!

    val fileValue = if (projectionsToSingleFile.containsKey(projectionValue)) {
        projectionsToSingleFile[projectionValue]!!
    } else {
        selectFile.options[selectFile.selectedIndex]!!.getAttribute("value")!!
    }

    loadEventsControlViz(fileValue, projectionValue)

    // call callback in raw js, needed for d3 comparasion sample
    js("onSettingsChanged(fileValue, projectionValue)")
}

@ExperimentalKZoomEvent
private fun loadEventsControlViz(filename: String, projectionName: String) {

    GlobalScope.promise {

        val geoJson =
            if (filename == "graticule")
                geoGraticule().graticule()
            else window.fetch(Request(filename))
                .await().text()
                .await().toGeoJsonObject()

        val oldCanvas = document.getElementById(canvaseVizHtmlElementId)
        val parent = oldCanvas!!.parentElement
        parent!!.removeChild(oldCanvas)
        val newCanvas = document.createElement("canvas") {
            this.id = canvaseVizHtmlElementId
            oldCanvas.getAttributeNames().forEach {
                attributeName ->
                this.setAttribute(attributeName, oldCanvas.getAttribute(attributeName)!!)
            }
        }.unsafeCast<HTMLCanvasElement>()
        parent.appendChild(newCanvas)

        currentViz?.stopAnimations()
        currentViz = loadViz(geoJson, projectionName)

        currentViz!!.bindRendererOn(newCanvas)
        val anim = animationEnabled

        if (!anim) {
            currentViz?.stopAnimations()
        }
    }
}

@ExperimentalKZoomEvent
private fun loadViz(geoJson: GeoJsonObject, projectionName: String): Viz = if (isNeedAutoRotation) {
    geoVizAutoRotate(geoJson, projectionName, vizWidth, vizHeight)
} else {
    geoVizEventsControl(geoJson, projectionName, vizWidth, vizHeight)
}





package io.data2viz.examples.geo

import io.data2viz.geojson.toGeoJsonObject
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
val selectClassNames = "geo_select"

val selectFileId = "select_file"
val selectProjectionId = "select_projection"
val buttonStartStopId = "button_start_stop"


lateinit var selectFile: HTMLSelectElement
lateinit var selectProjection: HTMLSelectElement
lateinit var buttonStartStop: HTMLButtonElement

var animationStarted = true

fun main(args: Array<String>) {

//    val filename = "world-110m-30percent.json"
//    val projectionName = "orthographicProjection"

    selectFile = document.getElementById(selectFileId).unsafeCast<HTMLSelectElement>()
    selectProjection = document.getElementById(selectProjectionId).unsafeCast<HTMLSelectElement>()
    buttonStartStop = document.getElementById(buttonStartStopId).unsafeCast<HTMLButtonElement>()

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


    selectFile.onchange = {onSelectionChanged()}
    selectProjection.onchange = {onSelectionChanged()}
    buttonStartStop.onclick = {

        if(animationStarted) {
            currentViz?.stopAnimations()
        } else {
            currentViz?.startAnimations()
        }

        animationStarted = !animationStarted
        it
    }

    onSelectionChanged()

//    loadViz(filename, projectionName)
}

private fun onSelectionChanged() {

    val selectFile = document.getElementById(selectFileId).unsafeCast<HTMLSelectElement>()

    val selectProjection = document.getElementById(selectProjectionId).unsafeCast<HTMLSelectElement>()

    val fileValue = selectFile.options[selectFile.selectedIndex]!!.getAttribute("value")
    val projectionValue = selectProjection.options[selectProjection.selectedIndex]!!.getAttribute("value")

    loadViz(fileValue!!, projectionValue!!)
}

@JsName("loadViz")
private fun loadViz(filename: String, projectionName: String) {
    console.log("loadViz filename = $filename projectionName = $projectionName")
    GlobalScope.promise {
        val request = window.fetch(Request(filename))
        val response = request.await()


        val oldCanvas = document.getElementById("viz")
        val parent = oldCanvas!!.parentElement
        parent!!.removeChild(oldCanvas!!)
        val newCanvas = document.createElement("canvas") {
            this.id = "viz"
        }.unsafeCast<HTMLCanvasElement>()
        parent!!.appendChild(newCanvas)

        currentViz?.stopAnimations()
        currentViz = geoViz(response.text().await().toGeoJsonObject(), projectionName, 500.0, 500.0)

        currentViz!!.bindRendererOn(newCanvas)
        if(!animationStarted) {
            currentViz?.stopAnimations()
        }
    }
}
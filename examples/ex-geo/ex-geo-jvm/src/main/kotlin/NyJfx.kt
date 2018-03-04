package io.data2viz.examples.geo


import com.fasterxml.jackson.databind.ObjectMapper
import io.data2viz.color.colors
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.equirectangularProjection
import io.data2viz.geojson.JacksonGeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.PathVizJfx
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage


class NyJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(NyJfx::class.java)
        }
    }

    class Timer {
        var last = System.currentTimeMillis()

        fun log(msg: String) {
            val newTime = System.currentTimeMillis()
            println("$msg in ${newTime - last} ms")
            last = newTime
        }
    }

    private val timer = Timer()

    override fun start(primaryStage: Stage?) {

        val input = this.javaClass.getResourceAsStream("/ny.json")
        val geojson = ObjectMapper().readValue(input, JacksonGeoJsonObject::class.java)
        timer.log("loading")
        val geoJsonObject = geojson.toGeoJsonObject()
        timer.log("parsing")

        val projection = equirectangularProjection()
        projection.center = doubleArrayOf(-74.0, 40.7)
        projection.translate = doubleArrayOf(480.0, 350.0)
        projection.scale = 85000.0
//        projection.clipExtent = null
        projection.precision = .0
        timer.log("projection")


        val pathVizJfx = PathVizJfx().apply {
            stroke = colors.black
            fill = null
        }
        val geoPath = geoPath(projection, pathVizJfx)

        timer.log("geoPath")

        geoPath.path(geoJsonObject)

        timer.log("path")

        val root = Pane()
        root.children.add(pathVizJfx.jfxElement)
        primaryStage!!.scene = (Scene(root, width, height))
        primaryStage.show()

        timer.log("primaryStage")
    }

}
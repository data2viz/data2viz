package io.data2viz.geo

import com.fasterxml.jackson.databind.ObjectMapper
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.MercatorProjection
import io.data2viz.geo.projection.equirectangular
import io.data2viz.geojson.JacksonGeoJsonObject
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.path.SvgPath
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import io.data2viz.path.svgPath
import io.data2viz.path.toJfxPath

fun main(args: Array<String>) {
    launch(MercatorPath::class.java)
}

class MercatorPath : Application() {
    override fun start(primaryStage: Stage?) {

        var time = System.currentTimeMillis()
        val input = this.javaClass.getResourceAsStream("/ny.json")
        val geojson = ObjectMapper().readValue(input, JacksonGeoJsonObject::class.java)
        val geoJsonObject = geojson.toGeoJsonObject()

        println("loading in ${System.currentTimeMillis()  -time} ms.")
        time = System.currentTimeMillis()


        val projection = equirectangular()
        projection.center = doubleArrayOf(-74.0, 40.7)
        projection.translate = doubleArrayOf(480.0, 350.0)
        projection.scale = 85000.0
//        projection.clipExtent = null
        projection.precision = .0

        println("projection in ${System.currentTimeMillis()  -time} ms.")
        time = System.currentTimeMillis()

        val geoPath = geoPath(projection, svgPath())

        println("geoPath in ${System.currentTimeMillis()  -time} ms.")
        time = System.currentTimeMillis()

        val path: SvgPath = geoPath.path(geoJsonObject) as SvgPath

        println("path in ${System.currentTimeMillis()  -time} ms.")
        time = System.currentTimeMillis()

        val root = Pane()
        root.children.add(path.toJfxPath().apply {
            fill = null
            stroke = Color.BLACK
            strokeWidth = 1.0
        })
        primaryStage!!.scene = (Scene(root, 960.0, 700.0))
        primaryStage.show()

        println("primaryStage in ${System.currentTimeMillis()  -time} ms.")
    }

}
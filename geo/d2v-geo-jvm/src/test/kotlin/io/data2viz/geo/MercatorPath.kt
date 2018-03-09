package io.data2viz.geo

import com.fasterxml.jackson.databind.ObjectMapper
import io.data2viz.color.colors
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.Extent
import io.data2viz.geo.projection.equirectangularProjection
import io.data2viz.geojson.JacksonGeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.PathVizJfx
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Timer {
    var last = System.currentTimeMillis()

    fun log(msg: String) {
        val newTime = System.currentTimeMillis()
        println("$msg in ${newTime - last} ms")
        last = newTime
    }
}

class MercatorPath : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MercatorPath::class.java)
        }
    }

    private val timer = Timer()

    override fun start(primaryStage: Stage?) {

        val extent = Extent(10.0, 10.0, 800.0, 500.0)

        val input = this.javaClass.getResourceAsStream("/world-110m.geojson")
        val geojson = ObjectMapper().readValue(input, JacksonGeoJsonObject::class.java)
        val geoJsonObject = geojson.toGeoJsonObject()

        val startLon = 0.0
        val startLat = 0.0
        val projection = equirectangularProjection {
            center = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(startLon, startLat)
            translate = doubleArrayOf(480.0, 300.0)
            scale = 150.0
            precision = .0
//            preClip = clipCircle(45.0)
        }

//        val projection = mercatorProjection()
//        projection.fitExtent(extent, geoJsonObject)

        val pathVizJfx = PathVizJfx().apply {
            stroke = colors.black
            fill = null
        }
        
        val geoPath = geoPath(projection, pathVizJfx)
        
        
        val timer = Timer()
        geoPath.path(geoJsonObject)
        timer.log("geoPath")



        val root = Pane()
        root.children.add(pathVizJfx.jfxElement)
        
        var initX = .0
        var initY = .0
        
        root.setOnMouseEntered { event ->
            initX = event.x
            initY = event.y
        }


        root.setOnMouseMoved { event ->
            projection.rotate = doubleArrayOf(
                startLon + .25 * (event.x - initX),
                startLat - .25 * (event.y - initY)
            )

            
            pathVizJfx.jfxElement.elements.clear()
            
            val newTimer = Timer()
            geoPath.path(geoJsonObject)
            newTimer.log("path update")
        }
        
        primaryStage!!.scene = (Scene(root, extent.width + 20, extent.height + 20))
        primaryStage.show()
        primaryStage.title = "JavaFx - data2viz - MercatorPath.kt"
    }

}
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

        val extent = Extent(10.0, 10.0, 500.0, 500.0)

        val input = this.javaClass.getResourceAsStream("/world-110m.geojson")
        val geojson = ObjectMapper().readValue(input, JacksonGeoJsonObject::class.java)
        val geoJsonObject = geojson.toGeoJsonObject()

//        val geoJsonObject = MultiPolygon(
//            arrayOf(
//                arrayOf(
//                    arrayOf(
//                        arrayOf(50.0, 50.0),
//                        arrayOf(25.5, 75.5),
//                        arrayOf(50.0, 100.0),
//                        arrayOf(100.0, 100.0),
//                        arrayOf(125.5, 75.5),
//                        arrayOf(100.0, 50.0),
//                        arrayOf(50.0, 50.0)
//                    )
//                )
//            )
//        )

//        val projection = mercatorProjection {
//            center = doubleArrayOf(10.0, 5.0)
//            translate = doubleArrayOf(480.0, 350.0)
//            scale = 200.0
//            precision = .0
//        }

        val projection = equirectangularProjection()
        projection.fitExtent(extent, geoJsonObject)

        val pathVizJfx = PathVizJfx().apply {
            stroke = colors.black
            fill = null
        }
        val geoPath = geoPath(projection, pathVizJfx)
        geoPath.path(geoJsonObject)

//        timer.log("geoPath")


//        var geoPath = geoPath(projection, PathVizJfx())
//        var path: PathVizJfx = geoPath.path(geoJsonObject) as PathVizJfx

        val root = Pane()
        root.children.add(pathVizJfx.jfxElement)
        primaryStage!!.scene = (Scene(root, extent.width + 20, extent.height + 20))
        primaryStage.show()
    }

}
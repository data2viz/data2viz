package io.data2viz.geo

import io.data2viz.color.Colors
import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geo.projection.*
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.geom.Extent
import io.data2viz.geom.PathGeom
import io.data2viz.math.deg
import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import io.data2viz.viz.viz
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollBar
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage


class TestGeoJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TestGeoJfx::class.java)
        }
    }

    private val cvizWidth = 1000.0
    private val cvizHeight = 1000.0
    private val root = VBox()
    private val content = HBox()
    private var canvas: Canvas = newCanvas()
    private var viz: Viz = newViz()
    private lateinit var projection: Projection
    private lateinit var geoPathNode: GeoPathNode

    override fun start(stage: Stage?) {

        val nativeFpsLabel = Label()
        val redraw = Button("Redraw")

        val scale = HBox().apply {
            children += Label("Scale")
            children += ScrollBar().apply {
                min = 50.0
                max = 800.0
                value = 200.0
                valueProperty().addListener { _,_,value ->
                    projection.scale = value.toDouble()
                    redraw()
                }
            }
        }

        val rotateLambda = HBox().apply {
            children += Label("lambda")
            children += ScrollBar().apply {
                min = -180.0
                max = +180.0
                value = 0.0
                valueProperty().addListener { _,_,value ->
                    projection.rotateLambda = value.deg
                    redraw()
                }
            }
        }

        val rotatePhi = HBox().apply {
            children += Label("phi")
            children += ScrollBar().apply {
                min = -180.0
                max = +180.0
                value = 0.0
                valueProperty().addListener { _,_,value ->
                    projection.rotatePhi = value.deg
                    redraw()
                }
            }
        }

        val rotateGamma = HBox().apply {
            children += Label("gamma")
            children += ScrollBar().apply {
                min = -180.0
                max = +180.0
                value = 0.0
                valueProperty().addListener { _,_,value ->
                    projection.rotateGamma = value.deg
                    redraw()
                }
            }
        }

        val circlePreclip = HBox().apply {
            children += Label("circlePreclip")
            children += ScrollBar().apply {
                min = .0
                max = 90.0
                value = 90.0
                valueProperty().addListener { _,_,value ->
                    projection.anglePreClip = value.deg
                    redraw()
                }
            }
        }

        val header = HBox().apply {
            with(children){
                add(redraw)
                add(nativeFpsLabel)
            }
        }

        val controllers = VBox().apply {
            children += scale
            children += rotateLambda
            children += rotatePhi
            children += rotateGamma
            children += circlePreclip
        }

        root.children.add(header)
        root.children.add(content)
        content.children.add(controllers)

        stage?.let {
            it.scene = (Scene(root, cvizWidth + 200, cvizHeight))
            it.show()
            stage.title = "JavaFx - data2viz - EarthJFXApplication.kt"
        }
        onSelectionChanged()
        viz.render()
    }

    private fun redraw() {
        geoPathNode.redrawPath()
        viz.render()
        println("${projection.describe()}")
    }


    private fun newCanvas(): Canvas {
        canvas.apply { content.children.remove(canvas) }
        val newCanvas = Canvas(cvizWidth, cvizHeight)
        content.children.add(0, newCanvas)
        return newCanvas
    }

    private fun onSelectionChanged() {
        canvas = newCanvas()
        viz = newViz()
    }

    private fun newViz(): Viz {
        val projectionName: String = getSelectedProjectionName()
        val jsonObject = getGeoJsonObject(projectionName)
        return geoViz(jsonObject, projectionName).also {
            JFxVizRenderer(canvas, it)
        }
    }

    private fun getSelectedProjectionName() = "orthographic"


    /**
     * GeoJson. If projection is AlbersUsa, only GeoJson for USA is loaded. If graticule, create a graticule.
     * Else load selected file.
     */
    private fun getGeoJsonObject(projectionName: String): GeoJsonObject {
        return  this.javaClass.getResourceAsStream("/world-110m.geojson").reader().readText().toGeoJsonObject()
    }

    fun geoViz(world: GeoJsonObject, projectionName: String,
               vizWidth: Double = cvizWidth,
               vizHeight: Double = cvizHeight): Viz {

        projection = allProjections[projectionName]!!
        projection.translateX = vizWidth / 2.0
        projection.translateY = vizHeight / 2.0
//        projection.anglePreClip = 50.deg
//        projection.extentPostClip = Extent(200.0, 200.0, 800.0, 600.0)


        return viz {
            width = vizWidth
            height = vizHeight

            geoPathNode = GeoPathNode().apply {
                stroke = Colors.Web.black
                strokeWidth = 0.5
                fill = Colors.Web.whitesmoke
                geoProjection = projection
                geoData = world
                redrawPath()
            }

            add(geoPathNode)
            text {
                x = 10.0
                y = 60.0
                fill = Colors.Web.red
                textContent = projectionName
            }


            // don't rotate projections which not don't support rotations in d3
            val isNeedRotate = when (projectionName) {
                "albersUSA", "identity" -> false
                else -> true
            }

            if (isNeedRotate) {
                geoPathNode.geoProjection.rotate(0.0.deg, 0.0.deg, 0.0.deg)
                geoPathNode.redrawPath()
            }

            onResize { newWidth, newHeight ->
                width = newWidth
                height = newHeight
                geoPathNode.redrawPath()
            }

        }
    }

}


val allProjections = hashMapOf(
    "albers" to albersProjection(),
    "albersUSA" to albersUSAProjection() {
        scale = 500.0
    },
    "azimuthalEqualArea" to azimuthalEqualAreaProjection(),
    "azimuthalEquidistant" to azimuthalEquidistant(),
    "conicConformal" to conicConformalProjection(),
    "conicEqual" to conicEqualAreaProjection(),
    "conicEquidistant" to conicEquidistantProjection(),
    "equalEarth" to equalEarthProjection(),
    "equirectangular" to equirectangularProjection(),
    "gnomonic" to gnomonicProjection(),
    "identity" to identityProjection(),
    "mercator" to mercatorProjection(),
    "naturalEarth" to naturalEarthProjection(),
    "orthographic" to orthographicProjection(),
    "stereographic" to stereographicProjection(),
    "transverseMercator" to transverseMercatorProjection()
)


open class GeoPathNode(
    var geoData: GeoJsonObject? = null,
    var geoProjection: Projection = identityProjection(),
    path: PathGeom = PathGeom()
): PathNode(path) {


    fun redrawPath() {
        val geoPath = geoPath(geoProjection, path)
        clearPath()
        geoPath.project(geoData!!)
    }
}

fun Projection.describe():String = "Projection scale[$scale], translateX[$translateX], translateY[$translateY], AnglePreclip[${anglePreClip?.deg}]"
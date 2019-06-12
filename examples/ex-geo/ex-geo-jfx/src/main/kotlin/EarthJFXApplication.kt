package io.data2viz.examples.geo

import geoVizEventsControl
import io.data2viz.geo.geometry.geoGraticule
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.ExperimentalKZoomEvent
import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.Viz
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage


@ExperimentalKZoomEvent
class EarthJFXApplication : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(EarthJFXApplication::class.java)
        }
    }

    private val vizWidth = 500.0
    private val vizHeight = 500.0

    private val root = Group()

    private val comboBoxFiles = ComboBox(FXCollections.observableArrayList(allFiles)).apply {
        selectionModel.select(defaultFileIndex)
    }

    private val comboBoxProjections = ComboBox(FXCollections.observableArrayList(allProjectionsNames)).apply {
        selectionModel.select(defaultProjectionIndex)
    }

    private val content = VBox()
    private var animationEnabled = true

    private val frameTimes = LongArray(100)
    private var frameTimeIndex = 0
    private var arrayFilled = false

    private var canvas: Canvas = newCanvas()
    private var viz: Viz = newViz()

    override fun start(stage: Stage?) {

        val nativeFpsLabel = Label()

        comboBoxProjections.valueProperty().addListener { _, _, _ -> onSelectionChanged() }
        comboBoxFiles.valueProperty().addListener { _, _, _ -> onSelectionChanged() }

        val frameRateMeter = object : AnimationTimer() {

            override fun handle(now: Long) {
                val oldFrameTime = frameTimes[frameTimeIndex]
                frameTimes[frameTimeIndex] = now
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.size
                if (frameTimeIndex == 0) {
                    arrayFilled = true
                }
                if (arrayFilled) {
                    val elapsedNanos = now - oldFrameTime
                    val elapsedNanosPerFrame = elapsedNanos / frameTimes.size
                    val frameRate = 1_000_000_000.0 / elapsedNanosPerFrame
                    nativeFpsLabel.text = String.format("Native JFX frame rate: %.3f", frameRate)
                }
            }
        }

        frameRateMeter.start()

        val header = HBox().apply {
            with(children) {
                add(comboBoxProjections)
                add(comboBoxFiles)
                add(nativeFpsLabel)
            }
        }



        content.children.apply {
            add(header)
            add(Label("Drag globe to rotate"))
            add(Label("Scale (ctrl + mouse wheel or touchpad gesture) to zoom"))
        }
        root.children.add(content)

        stage?.let {
            // 100 px for header
            it.scene = (Scene(root, vizWidth, vizHeight +100))
            it.show()
            stage.title = "JavaFx - data2viz - EarthJFXApplication.kt"
        }
        onSelectionChanged()
    }


    private fun newCanvas(): Canvas {
        canvas.apply { content.children.remove(canvas) }
        val newCanvas = Canvas(vizWidth, vizHeight)
        content.children.add(newCanvas)
        return newCanvas
    }

    private fun onSelectionChanged() {
        viz.stopAnimations()
        canvas = newCanvas()
        viz = newViz()
        if (animationEnabled)
            viz.startAnimations()
    }

    private fun newViz(): Viz {
        val projectionName: String = getSelectedProjectionName()
        val jsonObject = getGeoJsonObject(projectionName)
        return geoVizEventsControl(jsonObject, projectionName).also {
            JFxVizRenderer(canvas, it)
        }
    }

    private fun getSelectedProjectionName() = comboBoxProjections.selectionModel.selectedItem!!


    /**
     * GeoJson. If projection is AlbersUsa, only GeoJson for USA is loaded. If graticule, create a graticule.
     * Else load selected file.
     */
    private fun getGeoJsonObject(projectionName: String): GeoJsonObject {
        val name = projectionsToSingleFile.getOrDefault(projectionName, comboBoxFiles.selectionModel.selectedItem!!)
        return if (name == "graticule")
            geoGraticule().graticule()
        else
            this.javaClass.getResourceAsStream("/$name").reader().readText().toGeoJsonObject()

    }

}
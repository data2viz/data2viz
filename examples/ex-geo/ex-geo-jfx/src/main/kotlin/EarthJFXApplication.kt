package io.data2viz.examples.geo

import io.data2viz.geojson.toGeoJsonObject
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


class EarthJFXApplication : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(EarthJFXApplication::class.java)
        }
    }

    val vizWidth = 500.0
    val vizHeight = 500.0

    val root = Group()
    val startStopButton = Button()
    val comboBoxFiles = ComboBox(FXCollections.observableArrayList(allFiles));
    val comboBoxProjections = ComboBox(FXCollections.observableArrayList(allProjectionsNames));

    val vBox = VBox()
    var animationEnabled = true

    override fun start(stage: Stage?) {

        val nativeFpsLabel = Label()


        startStopButton.text = "Start/Stop viz animations"

        startStopButton.onAction = EventHandler<ActionEvent> {

            if (animationEnabled) {
                viz?.stopAnimations()
            } else {
                viz?.startAnimations()

            }

            animationEnabled = !animationEnabled
        }
        comboBoxFiles.selectionModel.select(defaultFileIndex)
        comboBoxProjections.selectionModel.select(defaultProjectionIndex)

        comboBoxProjections.valueProperty().addListener { observable, oldValue, newValue ->
            onSelectionChanged()
        }
        comboBoxFiles.valueProperty().addListener { observable, oldValue, newValue ->
            onSelectionChanged()
        }


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

        val hBox = HBox()

        hBox.children.add(startStopButton)
        hBox.children.add(comboBoxProjections)
        hBox.children.add(comboBoxFiles)
        hBox.children.add(nativeFpsLabel)


        vBox.children.add(hBox)
        root.children.add(vBox)

        stage?.let {
            it.scene = (Scene(root, vizWidth, vizHeight))
            it.show()
            stage.title = "JavaFx - data2viz - EarthJFXApplication.kt"
        }

        onSelectionChanged()

    }

    var canvas: Canvas? = null
    var viz: Viz? = null

    private fun onSelectionChanged() {

        viz?.stopAnimations()
        canvas?.apply { vBox.children.remove(canvas) }

        canvas = Canvas(vizWidth, vizHeight)

        val projection = comboBoxProjections.selectionModel.selectedItem!!

        val filename = if(projectionsToSingleFile.containsKey(projection)) {
            projectionsToSingleFile[projection]!!
        } else {
            comboBoxFiles.selectionModel.selectedItem!!
        }
        val jsonObject =
            this.javaClass.getResourceAsStream("/$filename").reader().readText().toGeoJsonObject()
        val world = jsonObject

        viz = geoViz(world, projection)
        JFxVizRenderer(canvas!!, viz!!)
        vBox.children.add(canvas)

        if (animationEnabled) {
            viz!!.startAnimations()
        }
    }

    private val frameTimes = LongArray(100)
    private var frameTimeIndex = 0
    private var arrayFilled = false
}
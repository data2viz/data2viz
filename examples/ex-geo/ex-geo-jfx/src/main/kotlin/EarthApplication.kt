package io.data2viz.examples.geo

import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.viz.JFxVizRenderer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import io.data2viz.test.matchers.start
import javafx.animation.AnimationTimer
import javafx.scene.control.Label


class EarthApplication : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(EarthApplication::class.java)
        }
    }

    override fun start(stage: Stage?) {


        val label = Label()

        val label = Label()
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
                    label.setText(String.format("Native JFX benchmark frame rate: %.3f", frameRate))
                }
            }
        }

        frameRateMeter.start()


        val vizWidth = 960.0
        val vizHeight = 700.0

        val root = Group()
        val canvas = Canvas(vizWidth, vizHeight)

        val world = this.javaClass.getResourceAsStream("/world-110m.geojson").reader().readText().toGeoJsonObject()

        val viz = geoViz(world)
        JFxVizRenderer(canvas,viz)
        root.children.add(canvas)
        root.children.add(label)

        stage?.let {
            it.scene = (Scene(root, vizWidth, vizHeight))
            it.show()
            stage.title = "JavaFx - data2viz - Earth Application.kt"
        }

        viz.startAnimations()


    }

    private val frameTimes = LongArray(100)
    private var frameTimeIndex = 0
    private var arrayFilled = false


}
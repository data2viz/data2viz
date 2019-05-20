package io.data2viz.examples.events



import io.data2viz.viz.ExperimentalKZoomEvent
import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.Viz
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage


@ExperimentalKZoomEvent
class ExEventsJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(ExEventsJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        stage?.let {
            it.scene = (Scene(root, vizSize.width, vizSize.height))
            it.show()

            val vert = VBox()
            val canvas = Canvas(vizSize.width, vizSize.height)
            root.children.add(vert)

            val button = Button()
            vert.children.add(button)
            vert.children.add(canvas)

            val viz = eventsViz()
            toggleEventsState(viz, button)
            button.setOnAction {
                toggleEventsState(viz, button)
            }

            JFxVizRenderer(canvas, viz)
            viz.render()

        }
    }

    private fun toggleEventsState(
        viz: Viz,
        button: Button
    ) {
        if (isEventsAdded) {
            viz.removeEvents()
            button.text = addEventsText
        } else {
            viz.addEvents()
            button.text = removeEventsText
        }
    }

}

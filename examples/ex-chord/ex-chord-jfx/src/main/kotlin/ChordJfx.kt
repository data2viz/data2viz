package io.data2viz.examples.chord


import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.KPointerDoubleClick
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage


class ChordJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(ChordJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        stage?.let {
            it.scene = (Scene(root, chordSize.width, chordSize.height))
            it.show()

            val vert = VBox()
            val canvas = Canvas(chordSize.width, chordSize.height)
            root.children.add(vert)
            vert.children.add(Button("Just to take some place"))
            vert.children.add(canvas)

            val viz = chordViz()
            viz.addEvents()
            JFxVizRenderer(canvas, viz)
            viz.render()

            viz.on(KPointerDoubleClick) { evt ->
                println("AFTER INIT Pointer double click:: ${evt.pos}")
            }
        }
    }

}

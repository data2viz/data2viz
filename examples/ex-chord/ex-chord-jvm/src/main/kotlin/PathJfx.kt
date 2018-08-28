package io.data2viz.examples.chord


import io.data2viz.math.PI
import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.Viz
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage
import javafx.scene.canvas.GraphicsContext




class PathJfx : Application() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(PathJfx::class.java)
        }
    }

    override fun start(stage: Stage?) {
        println("Building viz")
        val root = Group()
        val size = 600.0
        val canvas = Canvas(size, size)

        val renderer = JFxVizRenderer(canvas)

        val viz = getPathViz()
        viz.renderer = renderer
        viz.render()
//        drawShapes(canvas.graphicsContext2D)
        root.children.add(canvas)
        stage?.scene = Scene(root, size, size)
        stage?.show()
    }


    private fun getPathViz(): Viz =
        Viz().apply {
            with(root) {

                transform {
                    translate(300.0, 300.0)
                }

                path {
//                    moveTo(.5, .5)
                    arc(.0, .0, 100.0, -.0, -PI / 3, false)
                    arc(.0, .0, 80.0, -PI / 3, .0)
                    closePath()
                    stroke = null
                    fill = io.data2viz.color.colors.black
                }
            }
        }

    private fun drawShapes(gc: GraphicsContext) {

        gc.apply {
            translate(300.0, 300.0)
            beginPath()
//            moveTo(.0, .0)
            arc(.0, .0, 100.0, 100.0, .0, -60.0)
            arc(.0, .0, 80.0, 80.0, -60.0, 60.0)
            closePath()
            stroke()
//            fill()
        }
    }

}



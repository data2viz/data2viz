package io.data2viz.path

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.FillRule
import javafx.stage.Stage


fun main(args: Array<String>) {
    launch(HelloApp::class.java)
}

class HelloApp: Application(){
    override fun start(primaryStage: Stage?) {

        val path = PathJfx().apply {
            moveTo(10.0, 10.0)
            lineTo(70.0, 10.0)
            quadraticCurveTo(120.0, 60.0, 100.0, 20.0)
            lineTo(175.0, 55.0)
            lineTo(10.0, 100.0)
            closePath()

//            moveTo(50.0, 50.0)
            arc(50.0, 50.0, 20.0, 0.0, 360.0)
        }

        val root = Pane()
        root.children.add(path.path.apply {
            fill = null
            stroke = Color.BLACK
            strokeWidth = 2.0

        })
        primaryStage!!.scene = (Scene(root, 300.0, 250.0))
        primaryStage.show()
    }

}

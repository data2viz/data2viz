package io.data2viz.path

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage


fun main(args: Array<String>) {
    launch(HelloApp::class.java)
}

class HelloApp: Application(){
    override fun start(primaryStage: Stage?) {

        val path = svgPath().apply {
            moveTo(.0, 10.0)
            lineTo(70.0, 10)
            quadraticCurveTo(120, 60, 100, 20)
            lineTo(175, 55)
        }

        val root = Pane()
        root.children.add(path.toJfxPath())
        primaryStage!!.scene = (Scene(root, 300.0, 250.0))
        primaryStage.show()
    }

}

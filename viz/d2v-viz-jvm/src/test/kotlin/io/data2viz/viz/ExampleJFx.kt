package io.data2viz.viz

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage


fun main(args: Array<String>) {
    Application.launch(HelloApp::class.java)
}

class HelloApp : Application() {

    override fun start(primaryStage: Stage?) {

        val root = Group()

        root.viz {
            commonViz()
        }

        primaryStage!!.scene = (Scene(root, 500.0, 500.0))
        primaryStage.show()
    }
}

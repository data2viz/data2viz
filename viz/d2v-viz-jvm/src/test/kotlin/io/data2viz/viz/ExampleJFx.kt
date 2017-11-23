package io.data2viz.viz

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage


fun main(args: Array<String>) {
    Application.launch(HelloApp::class.java)
}


val data = listOf(
        Domain(10.0, 10.0),
        Domain(20.0, 40.0),
        Domain(30.0, 90.0)
)


class HelloApp : Application() {
    override fun start(primaryStage: Stage?) {
        val root = Group()
        root.viz {
            commonViz(data)
        }

        primaryStage!!.scene = (Scene(root, 500.0, 500.0))
        primaryStage.show()
    }
}

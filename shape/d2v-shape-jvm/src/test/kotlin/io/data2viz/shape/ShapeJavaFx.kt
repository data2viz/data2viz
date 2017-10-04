package io.data2viz.shape

import io.data2viz.path.svgPath
import io.data2viz.path.toJfxPath
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(HelloApp::class.java)
}


val lineGenerator = line<Point> {
    x = { it.x.toDouble() }
    y = { it.y.toDouble() }
//    defined = {!(it.x == 50 && it.y == 50)}
}

val points = arrayOf(Point(0, 0), Point(50, 50), Point(100, 0), Point(150, 100), Point(200, 0))


class HelloApp : Application() {
    override fun start(primaryStage: Stage?) {

        val path = svgPath()

        lineGenerator.curve = curves.basis
        lineGenerator.line(points, path)


        val root = Pane()
        root.children.add(path.toJfxPath().apply {
            fill = null
            stroke = Color.BLACK
            strokeWidth = 1.0

        })
        primaryStage!!.scene = (Scene(root, 300.0, 250.0))
        primaryStage.show()
    }
}

package io.data2viz.viz

import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import javax.imageio.ImageIO


const val size = 400

class RenderingJfxTest : ApplicationTest() {


    val jfxRoot = Group()

    override fun start(stage: Stage) {
        val scene = Scene(jfxRoot, size.toDouble(), size.toDouble())
        scene.fill = Color.color(1.0, 1.0, 1.0, .0)
        stage.scene = scene
        stage.show()
    }

    @Test
    fun renderImages() {

        val directory = File("build/images")
        if (! directory.exists()){
            directory.mkdir();
        }


        Platform.runLater {
            circleTests.forEach{
                renderNode(it.viz, it.name)
            }
        }
    }

    private fun renderNode(viz: Viz, name: String) {
        val canvas = Canvas(size.toDouble(), size.toDouble())
        jfxRoot.children.add(canvas)
        val renderer = JFxVizRenderer(canvas, viz)
        viz.renderer = renderer
        viz.render()

        
        //Show save file dialog
        val writableImage = WritableImage(size, size)
        canvas.snapshot(null, writableImage)
        val renderedImage = SwingFXUtils.fromFXImage(writableImage, null)
        val file = File("build/images/$name-jfx.png")
        ImageIO.write(renderedImage, "png", file)
        jfxRoot.children.remove(canvas)

    }


}
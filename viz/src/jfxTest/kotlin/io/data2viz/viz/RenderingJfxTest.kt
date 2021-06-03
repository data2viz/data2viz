/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz

import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.junit.Ignore
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import javax.imageio.ImageIO


const val size = 400

@Ignore("github actions")
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
            allRenderingTests.forEach{
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

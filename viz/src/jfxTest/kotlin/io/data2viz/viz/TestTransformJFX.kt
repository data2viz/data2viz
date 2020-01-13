/*
 * Copyright (c) 2018-2020. data2viz sàrl.
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

import io.data2viz.math.HALFPI
import io.data2viz.test.matchers.Matchers
import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.transform.Affine
import javafx.stage.Stage
import org.testfx.framework.junit.ApplicationTest
import kotlin.test.Test


class TestTransformJFX : ApplicationTest(), Matchers {


    val jfxRoot = Group()

    override fun start(stage: Stage) {
        val scene = Scene(jfxRoot, size.toDouble(), size.toDouble())
        scene.fill = Color.color(1.0, 1.0, 1.0, .0)
        stage.scene = scene
        stage.show()
    }


    @Test
    fun rotateTranslateAndBack() {
        Platform.runLater {
            val viz = viz {  }
            val canvas = Canvas(size.toDouble(), size.toDouble())
            jfxRoot.children.add(canvas)
            val renderer = JFxVizRenderer(canvas, viz)
            viz.renderer = renderer
            viz.render()

            val initial: Affine = canvas.graphicsContext2D.transform

            viz.apply {
                group {
                    transform {
                        rotate(HALFPI)
                        translate(200.0, 100.0)
                    }
                }
                render()
            }

            val actual: Affine = canvas.graphicsContext2D.transform
            actual.asArray() shouldBe initial.asArray()
        }
        Thread.sleep(400)

    }

}

fun Affine.asArray() = arrayOf(myx, mxy, myx, myy, tx, ty)
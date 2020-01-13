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
import io.data2viz.test.TestBase
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.DOMMatrix
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue


class TestTransformJS : TestBase(){

    @Test
    fun rotateTranslateAndBack() {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        val context = canvas.getContext("2d") as CanvasRenderingContext2D

        document.body?.appendChild(canvas)
        val viz = viz {}

        assertTrue { false } //Test failure

        viz.bindRendererOn(canvas)
        val initialTransform = context.getTransform()
        viz.apply {
            group {
                transform {
                    rotate(HALFPI)
                    translate(200.0, 100.0)
                }
            }
            render()
        }

        val actual: DOMMatrix = context.getTransform()
        actual.asArray() shouldBe initialTransform.asArray()
    }

}

fun DOMMatrix.asArray() = arrayOf(a, b, c, d, e, f)
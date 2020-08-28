/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.interpolate

import io.data2viz.test.namespace
import io.data2viz.test.TestBase
import kotlinx.browser.document
import kotlinx.dom.appendText
import kotlin.test.Test


val browserEnabled:Boolean = js("typeof document !== 'undefined'") as Boolean

class EaseTests : TestBase() {

    @Test fun testQuad() { testAndGraph("quad", ::quad) }
    @Test fun testCubicIn() { testAndGraph("cubicIn", ::cubicIn) }
    @Test fun testCubicOut() { testAndGraph("cubicOut", ::cubicOut) }
    @Test fun testCubicInOut() { testAndGraph("cubicInOut", ::cubicInOut) }
    @Test fun testSin() { testAndGraph("sin", ::sin) }
    @Test fun circleIn() { testAndGraph("circleIn", ::circleIn) }
    @Test fun circleOut() { testAndGraph("circleOut", ::circleOut) }



    fun testAndGraph(interpolatorName: String, interpolateFunction: (Double) -> Double = {t -> t}) {

        interpolateFunction(0.0) shouldBe (.0 plusOrMinus 0.01)
        interpolateFunction(1.0) shouldBe (1.0 plusOrMinus 0.01)

        if(browserEnabled) {

            h2(interpolatorName)
            document.body?.appendChild(
                document.createElementNS(namespace.svg, "svg").apply {
                    setAttribute("width", "100")
                    setAttribute("height", "100")

                    appendChild(
                        document.createElementNS(namespace.svg, "path").apply {
                            setAttribute("stroke", "black")
                            setAttribute("fill", "transparent")
                            val path = (0..100).map {
                                val x = it
                                val y = 100 - interpolateFunction(it.toDouble() / 100) * 100
                                "L $x $y"
                            }.joinToString(separator = " ")
                            setAttribute("d", "M 0 100 " + path)
                        }
                    )
                }

            )
        }
    }

}

internal fun h2(name: String) {
    document.body?.appendChild(document.createElement("h2").appendText(name))
}


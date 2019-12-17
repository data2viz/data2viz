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

import io.data2viz.color.*
import io.data2viz.math.*
import io.data2viz.test.namespace
import io.data2viz.test.TestBase
import kotlin.browser.document
import kotlin.test.Test

class HSLTestsJS : TestBase() {

    /**
     * "HSL SHORT linear interpolation [(300°, 100%, 25%), (38°, 100%, 50%)]
     * see https://github.com/d3/d3-interpolate#interpolateHsl for reference
     */
    @Test
    fun hslShortLinearInterpolation() {

        val iterator = hslInterpolator(Colors.hsl(300.deg, 100.pct, 25.pct), Colors.hsl(38.deg, 100.pct, 50.pct))
        displaySmallGradient("HslColor", iterator, 888, imageReference = "http://data2viz.io/img/hsl.png")
        iterator(50.pct).toRgb().rgbHex shouldBe 0xbf0023.col.rgbHex
    }

    /**
     * "HSL LONG linear interpolation [(300°, 100%, 25%), (38°, 100%, 50%)]"
     */
    @Test
    fun hslLongLinearInterpolation() {
        val iterator = hslLongInterpolator(Colors.hsl(300.deg, 100.pct, 25.pct), Colors.hsl(38.deg, 100.pct, 50.pct))
        displaySmallGradient("HslColor Long", iterator, 888, imageReference = "http://data2viz.io/img/hslLong.png")
        iterator(50.pct).toRgb().rgbHex shouldBe 0x00bf9c.col.rgbHex
    }

    /**
     * "HSL SHORT linear interpolation [(38°, 100%, 50%), (300°, 100%, 25%)]"
     */
    @Test
    fun hslShortLinea() {
        val iterator = hslInterpolator(Colors.hsl(38.deg, 100.pct, 50.pct), Colors.hsl(300.deg, 100.pct, 25.pct))
        displaySmallGradient("HslColor Reverse", iterator, 888, imageReference = "http://data2viz.io/img/hslReverse.png")
        iterator(50.pct).toRgb().rgbHex shouldBe 0xbf0023.col.rgbHex
    }

    /**
     * "HSL LONG linear interpolation [(38°, 100%, 50%), (300°, 100%, 25%)]"
     */
    @Test
    fun hslLongLinearInterpol() {
        val iterator = hslLongInterpolator(Colors.hsl(38.deg, 100.pct, 50.pct), Colors.hsl(300.deg, 100.pct, 25.pct))
        displaySmallGradient(
            "HslColor Long Reverse",
            iterator,
            888,
            imageReference = "http://data2viz.io/img/hslLongReverse.png"
        )
        iterator(50.pct).toRgb().rgbHex shouldBe 0x00bf9c.col.rgbHex
    }

    fun displaySmallGradient(
            context: String,
            percentToColor: Interpolator<Color>,
            width: Int = 256,
            imageReference: String? = null
    ) {

        if (browserEnabled) {


            h2(context)
            document.body?.appendChild(
                nodeSVG("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    setAttribute("x", "0")
                    setAttribute("y", "0")
                    (0 until width).forEach { index ->
                        appendChild(
                            nodeSVG("rect").apply {
                                setAttribute("fill", percentToColor(Percent(index / width.toDouble())).toRgb().rgbHex)
                                setAttribute("x", "$index")
                                setAttribute("y", "0")
                                setAttribute("width", "1")
                                setAttribute("height", "20")
                            }
                        )
                    }
                }
            )
            if (imageReference != null) {
                val div = document.createElement("div")
                div.appendChild(
                    document.createElement("img").apply {
                        setAttribute("src", imageReference)
                        setAttribute("height", "20")
                        setAttribute("width", "$width")
                    }
                )
                document.body?.appendChild(div)
            }
        }

    }

    fun nodeSVG(name: String) = document.createElementNS(namespace.svg, name)
}

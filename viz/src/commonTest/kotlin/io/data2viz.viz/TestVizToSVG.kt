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

import io.data2viz.color.Colors
import io.data2viz.color.col
import io.data2viz.geom.Size
import io.data2viz.geom.point
import io.data2viz.geom.size
import io.data2viz.math.pct
import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class TestVizToSVG : TestBase() {


    @Test
    fun testSvgFunctionality() {
        val simpleSvg = buildSvgString {
            add("svg", {
                add("width", 200); add("height", 200)
                add("version", 1.0)
                add("xmlns", "http://www.w3.org/2000/svg")
            }) {
                add("rect", {
                    add("x", 10); add("y", 10)
                    add("width", 30); add("height", 30)
                    addStyle {
                        add("stroke", "red")
                        add("fill", "transparent")
                        add("stroke-width", 5)
                    }
                    addTransform {
                        add("rotate", 45, 25, 25)
                    }
                })
                add("rect", {
                    add("x", 60); add("y", 10)
                    add("rx", 10); add("ry", 5)
                    add("width", 30); add("height", 30)
                    addStyle {
                        add("stroke", "black")
                        add("fill", "transparent")
                        add("stroke-width", 5)
                    }
                })
            }
        }

        simpleSvg shouldBe """
            <?xml version="1.0"?>
            <svg width="200" height="200" version="1.0" xmlns="http://www.w3.org/2000/svg">
            <rect x="10" y="10" width="30" height="30" style="stroke:red;fill:transparent;stroke-width:5" transform="rotate(45 25 25)"/>
            <rect x="60" y="10" rx="10" ry="5" width="30" height="30" style="stroke:black;fill:transparent;stroke-width:5"/>
            </svg>
            
            """.trimIndent()
    }

    @Test
    fun testEmptyViz() {
        val svg = viz {
            width = 100.0
            height = 100.0
        }.toSVG()

        val target = """
            <?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            </g>
            </svg>
            
            """.trimIndent()

        assertEquals(target, svg)

    }

    @Test
    fun testSquare() {
        val actual = viz {
            width = 100.0
            height = 100.0

            rect {
                x = 10.0
                y = 10.0
                width = 100.0
                height = 100.0
            }

        }.toSVG()
        val expected = """
            <?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            <rect x="10.0" y="10.0" width="100.0" height="100.0" style="stroke-width:1.0"/>
            </g>
            </svg>
            
            """.trimIndent()
        assertEquals(expected, actual)

    }

    @Test
    fun testLine() {
        val actual = viz {
            width = 100.0
            height = 100.0

            line {
                x1 = 1.0
                y1 = 1.0
                x2 = 70.0
                y2 = 70.0
                strokeColor = Colors.Web.greenyellow
            }
        }.toSVG()
        val expected = """
            <?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            <line x1="1.0" y1="1.0" x2="70.0" y2="70.0" style="stroke:rgba(173, 255, 47, 1.0);stroke-width:1.0"/>
            </g>
            </svg>
            
            """.trimIndent()
        assertEquals(expected,actual)
    }

    @Test
    fun testCircle() {
        val actual = viz {
            width = 100.0
            height = 100.0

            circle {
                x = 50.0
                y = 50.0
                radius = 30.0
                strokeColor = Colors.Web.red
                fill = Colors.Web.green
                strokeWidth = 5.0
            }
        }.toSVG()
        val expected = """
            <?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            <circle cx="50.0" cy="50.0" r="30.0" style="fill:rgba(0, 128, 0, 1.0);stroke:rgba(255, 0, 0, 1.0);stroke-width:5.0"/>
            </g>
            </svg>
            
            """.trimIndent()
        assertEquals(actual, expected)
    }

    @Test
    fun testLinearGradientLogo() {
        viz {
            size = size(400, 400)

            val gradient =
                Colors.Gradient.linear(point(0, 400), point(400, 0))
                    .withColor("#413FDE".col)
                    .andColor("#443DE2".col, 25.pct)
                    .andColor("#7641DF".col, 50.pct)
                    .andColor("#B945CE".col, 75.pct)
                    .andColor("#DE3D82".col, 100.pct)

            rect {
                size = Size(400.0, 400.0)
                fill = gradient
            }

            rect {
                x = 30.0
                y = 30.0
                size = Size(340.0, 340.0)
                fill = Colors.Web.white
            }

            rect {
                x = 60.0
                y = 60.0
                size = Size(280.0, 280.0)
                fill = gradient
            }

            path {
                moveTo(200.0, 340.0)
                lineTo(340.0, 60.0)
                lineTo(360.0, 360.0)
                fill = Colors.Web.white
            }

            path {
                moveTo(220.0, 360.0)
                lineTo(55.0, 37.5)
                strokeWidth = 30.0
                strokeColor = Colors.Web.white
            }

            path {
                moveTo(340.0, 300.0)
                lineTo(215.0, 37.5)
                strokeColor = Colors.Web.white
                strokeWidth = 30.0
            }

        }.toSVG() shouldBe """
            <?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="400.0" height="400.0">
            <g>
            <rect x="0.0" y="0.0" width="400.0" height="400.0" style="fill:url('#grad1');stroke-width:1.0"/>
            <rect x="30.0" y="30.0" width="340.0" height="340.0" style="fill:rgba(255, 255, 255, 1.0);stroke-width:1.0"/>
            <rect x="60.0" y="60.0" width="280.0" height="280.0" style="fill:url('#grad2');stroke-width:1.0"/>
            <path style="fill:rgba(255, 255, 255, 1.0);stroke:rgba(0, 0, 0, 0.0);stroke-width:1.0" d="M 200.0 340.0 L 340.0 60.0 L 360.0 360.0 "/>
            <path style="fill:rgba(0, 0, 0, 0.0);stroke:rgba(255, 255, 255, 1.0);stroke-width:30.0" d="M 220.0 360.0 L 55.0 37.5 "/>
            <path style="fill:rgba(0, 0, 0, 0.0);stroke:rgba(255, 255, 255, 1.0);stroke-width:30.0" d="M 340.0 300.0 L 215.0 37.5 "/>
            </g>
            <defs>
            <linearGradient id="grad1" x1="0.0%" y1="100.0%" x2="100.0%" y2="0.0%">
            <stop offset="0.0%" style="stop-color:rgba(65, 63, 222, 1.0)"/>
            <stop offset="25.0%" style="stop-color:rgba(68, 61, 226, 1.0)"/>
            <stop offset="50.0%" style="stop-color:rgba(118, 65, 223, 1.0)"/>
            <stop offset="75.0%" style="stop-color:rgba(185, 69, 206, 1.0)"/>
            <stop offset="100.0%" style="stop-color:rgba(222, 61, 130, 1.0)"/>
            </linearGradient>
            <linearGradient id="grad2" x1="0.0%" y1="100.0%" x2="100.0%" y2="0.0%">
            <stop offset="0.0%" style="stop-color:rgba(65, 63, 222, 1.0)"/>
            <stop offset="25.0%" style="stop-color:rgba(68, 61, 226, 1.0)"/>
            <stop offset="50.0%" style="stop-color:rgba(118, 65, 223, 1.0)"/>
            <stop offset="75.0%" style="stop-color:rgba(185, 69, 206, 1.0)"/>
            <stop offset="100.0%" style="stop-color:rgba(222, 61, 130, 1.0)"/>
            </linearGradient>
            </defs>
            </svg>
            
            """
            .trimIndent()
    }


}

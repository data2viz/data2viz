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
import io.data2viz.test.TestBase
import kotlin.test.Test

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

        simpleSvg shouldBe """<?xml version="1.0"?>
            <svg width="200" height="200" version="1.0" xmlns="http://www.w3.org/2000/svg">
            <rect x="10" y="10" width="30" height="30" style="stroke:red;fill:transparent;stroke-width:5" transform="rotate(45 25 25)"/>
            <rect x="60" y="10" rx="10" ry="5" width="30" height="30" style="stroke:black;fill:transparent;stroke-width:5"/>
            </svg>""".trimIndent()
    }

    @Test
    fun testEmptyViz() {
        viz {
            width = 100.0
            height = 100.0
        }.toSVG() shouldBe """<?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            </g>
            </svg>""".trimIndent()

    }

    @Test
    fun testSquare() {
        viz {
            width = 100.0
            height = 100.0

            rect {
                x = 10.0
                y = 10.0
                width = 100.0
                height = 100.0
            }

        }.toSVG() shouldBe """<?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            <rect x="10.0" y="10.0" width="100.0" height="100.0" style="stroke-width:1.0">
            </rect>
            </g>
            </svg>"""
            .trimIndent()

    }

    @Test
    fun testLine() {
        viz {
            width = 100.0
            height = 100.0

            line {
                x1 = 1.0
                y1 = 1.0
                x2 = 70.0
                y2 = 70.0
                strokeColor = Colors.Web.greenyellow
            }
        }.toSVG() shouldBe """<?xml version="1.0"?>
                <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
                <g>
                <line x1="1.0" y1="1.0" x2="70.0" y2="70.0" style="stroke:rgba(173, 255, 47, 1.0);stroke-width:1.0"/>
                </g>
                </svg>""".trimIndent()
    }

    @Test
    fun testCircle() {
        viz {
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
        }.toSVG() shouldBe """<?xml version="1.0"?>
                <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
                <g>
                <circle cx="50.0" cy="50.0" r="30.0" style="fill:rgba(0, 128, 0, 1.0);stroke:rgba(255, 0, 0, 1.0);stroke-width:5.0"/>
                </g>
                </svg>""".trimIndent()
    }


}

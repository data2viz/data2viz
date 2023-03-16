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
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class TestVizToSVG : TestBase() {


    @Test
    fun testSvgFunctionality() {
        val actual = buildSvgString {
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

        val expect = buildString {
            append("""
            <?xml version="1.0" encoding="UTF-8"?>
            <svg width="${200 as Number}" height="${200 as Number}" version="${1.0 as Number}" xmlns="http://www.w3.org/2000/svg">
            <rect x="${10 as Number}" y="${10 as Number}" width="${30 as Number}" height="${30 as Number}" style="stroke:red;fill:transparent;stroke-width:${5 as Number}" transform="rotate(${45 as Number} ${25 as Number} ${25 as Number})"/>
            <rect x="${60 as Number}" y="${10 as Number}" rx="${10 as Number}" ry="${5 as Number}" width="${30 as Number}" height="${30 as Number}" style="stroke:black;fill:transparent;stroke-width:${5 as Number}"/>
            </svg>

            """.trimIndent()
            )
        }

        assertEquals(expected = expect, actual = actual)
    }

    @Test
    @Ignore //Not the same results on JS and JFX
    fun testText() {
        val svg = viz {
            width = 100.0
            height = 100.0

            text {
                x = 10.0; y = 10.0
                textAlign(TextHAlign.MIDDLE, TextVAlign.MIDDLE)
                textContent = "O"
            }
            text {
                x = 90.0; y = 10.0
                textAlign(TextHAlign.MIDDLE, TextVAlign.MIDDLE)
                textContent = "O"
            }
            text {
                x = 10.0; y = 90.0
                textAlign(TextHAlign.MIDDLE, TextVAlign.MIDDLE)
                textContent = "O"
            }
            text {
                x = 90.0; y = 90.0
                textAlign(TextHAlign.MIDDLE, TextVAlign.MIDDLE)
                textContent = "O"
            }
        }.toSVG()

        val target = """
            <?xml version="1.0" encoding="UTF-8"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="${100.0 as Number}" height="${100.0 as Number}">
            <g>
            <text x="${10.0 as Number}" y="${5.845703125 as Number}" font-size="${12.0 as Number}" font-family="sans-serif" font-weight="NORMAL" font-style="NORMAL" style="stroke-width:${1.0 as Number}">
            O</text>
            <text x="${90.0 as Number}" y="${5.845703125 as Number}" font-size="${12.0 as Number}" font-family="sans-serif" font-weight="NORMAL" font-style="NORMAL" style="stroke-width:${1.0 as Number}">
            O</text>
            <text x="${10.0 as Number}" y="${85.845703125 as Number}" font-size="${12.0 as Number}" font-family="sans-serif" font-weight="NORMAL" font-style="NORMAL" style="stroke-width:${1.0 as Number}">
            O</text>
            <text x="${90.0 as Number}" y="${85.845703125 as Number}" font-size="${12.0 as Number}" font-family="sans-serif" font-weight="NORMAL" font-style="NORMAL" style="stroke-width:${1.0 as Number}">
            O</text>
            </g>
            </svg>

        """.trimIndent()

        assertEquals(target, svg)
    }

    @Test
    fun testEmptyViz() {
        val svg = viz {
            width = 100.0
            height = 100.0
        }.toSVG()

        val target = """
            <?xml version="1.0" encoding="UTF-8"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="${100.0 as Number}" height="${100.0 as Number}">
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
            <?xml version="1.0" encoding="UTF-8"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="${100.0 as Number}" height="${100.0 as Number}">
            <g>
            <rect x="${10.0 as Number}" y="${10.0 as Number}" width="${100.0 as Number}" height="${100.0 as Number}" style="stroke-width:${1.0 as Number}"/>
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
            <?xml version="1.0" encoding="UTF-8"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="${100.0 as Number}" height="${100.0 as Number}">
            <g>
            <line x1="${1.0 as Number}" y1="${1.0 as Number}" x2="${70.0 as Number}" y2="${70.0 as Number}" style="stroke:rgba(${173 as Number}, ${255 as Number}, ${47 as Number}, ${1.0 as Number});stroke-width:${1.0 as Number}"/>
            </g>
            </svg>

            """.trimIndent()
        assertEquals(expected, actual)
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
            <?xml version="1.0" encoding="UTF-8"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="${100.0 as Number}" height="${100.0 as Number}">
            <g>
            <circle cx="${50.0 as Number}" cy="${50.0 as Number}" r="${30.0 as Number}" style="fill:rgba(${0 as Number}, ${128 as Number}, ${0 as Number}, ${1.0 as Number});stroke:rgba(${255 as Number}, ${0 as Number}, ${0 as Number}, ${1.0 as Number});stroke-width:${5.0 as Number}"/>
            </g>
            </svg>

            """.trimIndent()
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun testLinearGradientLogo() {
        val actual = viz {
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

        }.toSVG()

        val expect = """<?xml version="1.0" encoding="UTF-8"?>
            |<svg xmlns="http://www.w3.org/2000/svg" width="${400.0 as Number}" height="${400.0 as Number}">
            |<g>
            |<rect x="${0.0 as Number}" y="${0.0 as Number}" width="${400.0 as Number}" height="${400.0 as Number}" style="fill:url('#grad1');stroke-width:${1.0 as Number}"/>
            |<rect x="${30.0 as Number}" y="${30.0 as Number}" width="${340.0 as Number}" height="${340.0 as Number}" style="fill:rgba(${255 as Number}, ${255 as Number}, ${255 as Number}, ${1.0 as Number});stroke-width:${1.0 as Number}"/>
            |<rect x="${60.0 as Number}" y="${60.0 as Number}" width="${280.0 as Number}" height="${280.0 as Number}" style="fill:url('#grad2');stroke-width:${1.0 as Number}"/>
            |<path style="fill:rgba(${255 as Number}, ${255 as Number}, ${255 as Number}, ${1.0 as Number});stroke:rgba(${0 as Number}, ${0 as Number}, ${0 as Number}, ${0.0 as Number});stroke-width:${1.0 as Number}" d="M ${200.0 as Number} ${340.0 as Number} L ${340.0 as Number} ${60.0 as Number} L ${360.0 as Number} ${360.0 as Number} "/>
            |<path style="fill:rgba(${0 as Number}, ${0 as Number}, ${0 as Number}, ${0.0 as Number});stroke:rgba(${255 as Number}, ${255 as Number}, ${255 as Number}, ${1.0 as Number});stroke-width:${30.0 as Number}" d="M ${220.0 as Number} ${360.0 as Number} L ${55.0 as Number} ${37.5 as Number} "/>
            |<path style="fill:rgba(${0 as Number}, ${0 as Number}, ${0 as Number}, ${0.0 as Number});stroke:rgba(${255 as Number}, ${255 as Number}, ${255 as Number}, ${1.0 as Number});stroke-width:${30.0 as Number}" d="M ${340.0 as Number} ${300.0 as Number} L ${215.0 as Number} ${37.5 as Number} "/>
            |</g>
            |<defs>
            |<linearGradient id="grad1" x1="${0.0 as Number} px" y1="${400.0 as Number} px" x2="${400.0 as Number} px" y2="${0.0 as Number} px">
            |<stop offset="${0.0 as Number}%" style="stop-color:rgba(${65 as Number}, ${63 as Number}, ${222 as Number}, ${1.0 as Number})"/>
            |<stop offset="${25.0 as Number}%" style="stop-color:rgba(${68 as Number}, ${61 as Number}, ${226 as Number}, ${1.0 as Number})"/>
            |<stop offset="${50.0 as Number}%" style="stop-color:rgba(${118 as Number}, ${65 as Number}, ${223 as Number}, ${1.0 as Number})"/>
            |<stop offset="${75.0 as Number}%" style="stop-color:rgba(${185 as Number}, ${69 as Number}, ${206 as Number}, ${1.0 as Number})"/>
            |<stop offset="${100.0 as Number}%" style="stop-color:rgba(${222 as Number}, ${61 as Number}, ${130 as Number}, ${1.0 as Number})"/>
            |</linearGradient>
            |<linearGradient id="grad2" x1="${0.0 as Number} px" y1="${400.0 as Number} px" x2="${400.0 as Number} px" y2="${0.0 as Number} px">
            |<stop offset="${0.0 as Number}%" style="stop-color:rgba(${65 as Number}, ${63 as Number}, ${222 as Number}, ${1.0 as Number})"/>
            |<stop offset="${25.0 as Number}%" style="stop-color:rgba(${68 as Number}, ${61 as Number}, ${226 as Number}, ${1.0 as Number})"/>
            |<stop offset="${50.0 as Number}%" style="stop-color:rgba(${118 as Number}, ${65 as Number}, ${223 as Number}, ${1.0 as Number})"/>
            |<stop offset="${75.0 as Number}%" style="stop-color:rgba(${185 as Number}, ${69 as Number}, ${206 as Number}, ${1.0 as Number})"/>
            |<stop offset="${100.0 as Number}%" style="stop-color:rgba(${222 as Number}, ${61 as Number}, ${130 as Number}, ${1.0 as Number})"/>
            |</linearGradient>
            |</defs>
            |</svg>
            |""".trimMargin()

        assertEquals(actual, expect)
    }

    @Test
    fun testRadialGradient() {
        val radialGradient = Colors.Gradient.radial(point(400, 100), 100.0)
            .withColor(Colors.Web.hotpink, 0.pct)       // gradient center is "hot pink"
            .andColor(Colors.Web.skyblue, 100.pct)      // gradient end "is sky blue"

        val radialGradient2 = Colors.Gradient.radial(point(200, 100), 120.0)
            .withColor(Colors.Web.red, 0.pct)       // gradient center is "hot pink"
            .andColor(Colors.Web.orange, 50.pct)       // gradient center is "hot pink"
            .andColor(Colors.Web.purple, 100.pct)      // gradient end "is sky blue"

        val actual = viz {
            size = size(800, 200)
            circle {
                x = 400.0
                y = 100.0
                radius = 100.0
                fill = radialGradient
            }
            circle {
                x = 200.0
                y = 100.0
                radius = 120.0
                fill = radialGradient2
            }


        }.toSVG()

        val expect = """<?xml version="1.0" encoding="UTF-8"?>
                |<svg xmlns="http://www.w3.org/2000/svg" width="${800.0 as Number}" height="${200.0 as Number}">
                |<g>
                |<circle cx="${400.0 as Number}" cy="${100.0 as Number}" r="${100.0 as Number}" style="fill:url('#grad1');stroke-width:${1.0 as Number}"/>
                |<circle cx="${200.0 as Number}" cy="${100.0 as Number}" r="${120.0 as Number}" style="fill:url('#grad2');stroke-width:${1.0 as Number}"/>
                |</g>
                |<defs>
                |<radialGradient id="grad1" cx="${400.0 as Number} px" cy="${100.0 as Number} px" r="${100.0 as Number} px">
                |<stop offset="${0.0 as Number}%" style="stop-color:rgba(${255 as Number}, ${105 as Number}, ${180 as Number}, ${1.0 as Number})"/>
                |<stop offset="${100.0 as Number}%" style="stop-color:rgba(${135 as Number}, ${206 as Number}, ${235 as Number}, ${1.0 as Number})"/>
                |</radialGradient>
                |<radialGradient id="grad2" cx="${200.0 as Number} px" cy="${100.0 as Number} px" r="${120.0 as Number} px">
                |<stop offset="${0.0 as Number}%" style="stop-color:rgba(${255 as Number}, ${0 as Number}, ${0 as Number}, ${1.0 as Number})"/>
                |<stop offset="${50.0 as Number}%" style="stop-color:rgba(${255 as Number}, ${165 as Number}, ${0 as Number}, ${1.0 as Number})"/>
                |<stop offset="${100.0 as Number}%" style="stop-color:rgba(${128 as Number}, ${0 as Number}, ${128 as Number}, ${1.0 as Number})"/>
                |</radialGradient>
                |</defs>
                |</svg>
                |""".trimMargin()

        assertEquals(expect, actual)
    }
}

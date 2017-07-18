package io.data2viz.axis

import io.data2viz.core.namespace
import io.data2viz.test.StringSpec
import kotlin.browser.document

class AxisTests : StringSpec() {
    init {
        "display x axis" {
            display()
        }
    }

    fun display() {

        val data = listOf(55, 44, 30, 23, 17, 14, 16, 25, 41, 61, 85,
                101, 95, 105, 114, 150, 180, 210, 125, 100, 71, 75, 72, 67)

        val max = data.max()!!
        val barWidth = 15
        val barPadding = 3
        val xLoc = { i: Int -> i * (barPadding + barWidth) }
        val yLoc = { d: Int -> max - d }

        fun node(name: String) = document.createElementNS(namespace.svg, name)

        val body = document.querySelector("body")!!
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "1000")
                    setAttribute("height", "250")

                    appendChild(node("g").apply {
                        data.forEachIndexed { index, d ->
                            appendChild(node("rect").apply {
                                setAttribute("fill", "steelblue")
                                setAttribute("width", "$barWidth")
                                setAttribute("height", "$d")
                                setAttribute("transform", "translate(${xLoc(index)}, ${yLoc(d)})")

                            })
                        }

                    })

//                    appendChild(node("g").apply {
//                        className = "x axix"
//                        data.forEach {
//                            tick ->
//                            appendChild(node("g").apply {
//                                className = "tick"
//                                setAttribute("transform", "translate(${scale(tick)})")
//                                setAttribute("style", "opacity : 1;")
//                                appendChild(node("line").apply {
//                                    setAttribute("y2", "6")
//                                    setAttribute("x2", "0")
//                                })
//                            })
//                        }
//
//                    })
                })
    }
}

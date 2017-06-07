package io.data2viz.interpolate

import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import kotlin.browser.document

class EaseTests : StringSpec() {

    init {
        "io.data2viz.interpolate.identity"  { testAndGraph() }
        "quad"      { testAndGraph(::quad) }
        "cubicIn"   { testAndGraph(::cubicIn) }
        "cubicOut"  { testAndGraph(::cubicOut) }
        "cubicInOut"{ testAndGraph(::cubicInOut) }
        "sin"       { testAndGraph(::sin) }
        "circleIn"  { testAndGraph(::circleIn) }
        "circleOut" { testAndGraph(::circleOut) }
    }

    fun testAndGraph(function: (Double) -> Double = ::identity) {

        function(0.0) shouldBe (.0 plusOrMinus 0.01)
        function(1.0) shouldBe (1.0 plusOrMinus 0.01)

        val body = document.querySelector("body")!!
        body.appendChild(
                document.createElementNS(namespace.svg, "svg").apply {
                    setAttribute("width", "100")
                    setAttribute("height", "100")

                    appendChild(
                            document.createElementNS(namespace.svg, "path").apply {
                                setAttribute("stroke", "black")
                                setAttribute("fill", "transparent")
                                val path = (0..100).map {
                                    val x = it
                                    val y = 100 - function(it.toDouble() / 100) * 100
                                    "L $x $y"
                                }.joinToString(separator = " ")
                                setAttribute("d", "M 0 100 " + path)
                            }
                    )
                }
        )
    }

}

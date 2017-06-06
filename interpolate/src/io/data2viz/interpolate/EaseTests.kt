package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.green
import io.data2viz.color.colors.red
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

        val valueToColor = scale.linear.numberToColor(0 linkedTo red, 100 linkedTo blue)
        "FloatToColor LINEAR(0-> Red, 100->Blue) 0 to 100" { displayScaleGradient(valueToColor, 0f, 100f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 50 to 150" { displayScaleGradient(valueToColor, 50f, 150f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) -50 to 50" { displayScaleGradient(valueToColor, -50f, 50f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) -100 to 200" { displayScaleGradient(valueToColor, -100f, 200f) }
        "FloatToColor LINEAR(0-> Red, 100->Blue) 100 to 0" { displayScaleGradient(valueToColor, 100f, 0f) }

        val valueToColor2 = scale.linear.numberToColor(arrayListOf(0 linkedTo red, 50 linkedTo green, 100 linkedTo blue))
        "FloatToColor LINEAR(0-> Red, 50-> Green, 100->Blue) 0 to 100" { displayScaleGradient(valueToColor2, 0f, 100f) }
        "FloatToColor LINEAR(0-> Red, 50-> Green, 100->Blue) 50 to 150" { displayScaleGradient(valueToColor2, 50f, 150f) }
        "FloatToColor LINEAR(0-> Red, 50-> Green, 100->Blue) -50 to 50" { displayScaleGradient(valueToColor2, -50f, 50f) }
        "FloatToColor LINEAR(0-> Red, 50-> Green, 100->Blue) -100 to 200" { displayScaleGradient(valueToColor2, -100f, 200f) }
        "FloatToColor LINEAR(0-> Red, 50-> Green, 100->Blue) 100 to 0" { displayScaleGradient(valueToColor2, 100f, 0f) }
    }

    fun displayScaleGradient(domainToViz: (Float) -> Color, start: Float, end: Float, width: Int = 800) {
        val body = document.querySelector("body")!!
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "30")

                    val tr: Float = (end - start) / width.toFloat()

                    (0 until width).forEach { index ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", domainToViz((index.toFloat()) * tr + start).rgbHex)
                                    setAttribute("x", "$index")
                                    setAttribute("y", "0")
                                    setAttribute("width", "1")
                                    setAttribute("height", "30")
                                }
                        )
                    }
                }
        )
    }

    fun testAndGraph(function: (Double) -> Double = ::identity) {

        function(0.0) shouldBe (.0 plusOrMinus 0.01)
        function(1.0) shouldBe (1.0 plusOrMinus 0.01)

        val body = document.querySelector("body")!!
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "100")
                    setAttribute("height", "100")

                    appendChild(
                            node("path").apply {
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

    fun node(name: String) = document.createElementNS(namespace.svg, name)
}

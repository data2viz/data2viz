package io.data2viz.interpolate

import io.data2viz.test.StringSpec
import io.data2viz.core.namespace
import io.data2viz.test.ExecutionContext
import io.data2viz.test.DOMExecutionContext
import kotlin.browser.document

class EaseTests : StringSpec() {

    init {
        "io.data2viz.interpolate.identity"  { context -> testAndGraph(context) }
        "quad"      { context -> testAndGraph(context, ::quad) }
        "cubicIn"   { context -> testAndGraph(context, ::cubicIn) }
        "cubicOut"  { context -> testAndGraph(context, ::cubicOut) }
        "cubicInOut"{ context -> testAndGraph(context, ::cubicInOut) }
        "sin"       { context -> testAndGraph(context, ::sin) }
        "circleIn"  { context -> testAndGraph(context, ::circleIn) }
        "circleOut" { context -> testAndGraph(context, ::circleOut) }
    }

    fun testAndGraph(context: ExecutionContext, function: (Double) -> Double = ::identity) {

        function(0.0) shouldBe (.0 plusOrMinus 0.01)
        function(1.0) shouldBe (1.0 plusOrMinus 0.01)

        if (context !is DOMExecutionContext)  return
        context.element.appendChild(
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

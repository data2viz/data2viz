package io.data2viz.color

import io.data2viz.color.EncodedColors.Companion.category10
import io.data2viz.color.EncodedColors.Companion.category20
import io.data2viz.color.EncodedColors.Companion.category20b
import io.data2viz.color.EncodedColors.Companion.category20c
import io.data2viz.color.EncodedColors.Companion.inferno
import io.data2viz.color.EncodedColors.Companion.magma
import io.data2viz.color.EncodedColors.Companion.plasma
import io.data2viz.color.EncodedColors.Companion.viridis
import io.data2viz.core.namespace
import io.data2viz.test.ExecutionContext
import io.data2viz.test.DOMExecutionContext
import io.data2viz.test.StringSpec
import kotlin.browser.document

class EncodedColorsTests : StringSpec() {

    init {
        "category10" { context -> category10.colors.size shouldBe 10;  displaySmallGradient(context, category10, imageReference = "http://data2viz.io/img/category10.png") }
        "category20" { context -> category20.colors.size shouldBe 20; displaySmallGradient(context, category20, imageReference = "http://data2viz.io/img/category20.png") }
        "category20b" { context -> category20b.colors.size shouldBe 20; displaySmallGradient(context, category20b, imageReference = "http://data2viz.io/img/category20b.png") }
        "category20c" { context -> category20c.colors.size shouldBe 20; displaySmallGradient(context, category20c, imageReference = "http://data2viz.io/img/category20c.png") }

        "viridis"   {  context -> testAndGraph(context, viridis, imageReference = "http://data2viz.io/img/viridis.png") }
        "magma"     {  context -> testAndGraph(context, magma, imageReference = "http://data2viz.io/img/magma.png") }
        "inferno"   {  context -> testAndGraph(context, inferno, imageReference = "http://data2viz.io/img/inferno.png") }
        "plasma"    {  context -> testAndGraph(context, plasma, imageReference = "http://data2viz.io/img/plasma.png") }
    }

    fun displaySmallGradient(context: ExecutionContext, colors: EncodedColors, imageReference: String? = null) {
        if (context !is DOMExecutionContext)  return
        val width = 20 * colors.colors.size
        context.element.appendChild(
                node("svg").apply {
                    setAttribute("width", "$width")
                    setAttribute("height", "20")
                    colors.colors.forEachIndexed { index, color ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", color.rgbHex)
                                    setAttribute("x", "${index * 20}")
                                    setAttribute("y", "0")
                                    setAttribute("width", "20")
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
            context.element.appendChild(div)
        }
    }

    fun testAndGraph(context: ExecutionContext, gradient: EncodedColors, imageReference: String? = null) {
        if (context !is DOMExecutionContext)  return

        //generate a list of 6, 10, 20 colors from the gradient.
        listOf(6, 10, 20).forEach { size ->
            val colors = (0..size - 1)
                    .map { gradient.color(it.toDouble() / (size - 1)) }
//            println(colors.joinToString(transform = { it.rgbHex }))
        }

        context.element.appendChild(
                node("svg").apply {
                    setAttribute("width", "400")
                    setAttribute("height", "20")
                    (0..399).forEach {
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", gradient.color(it.toDouble() / 400).rgbHex)
                                    setAttribute("x", "${it}")
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
                        setAttribute("width", "400")
                    }
            )
            context.element.appendChild(div)
        }
    }

    fun node(name: String) = document.createElementNS(namespace.svg, name)

}

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
import io.data2viz.test.DomUtils.Companion.body
import io.data2viz.test.StringSpec
import kotlin.browser.document

class EncodedColorsTests : StringSpec(){

    init {
        "category10" { category10.colors.size shouldBe 10; displaySmallGradient(category10)}
        "category20" { category20.colors.size shouldBe 20; displaySmallGradient(category20)}
        "category20b" { category20b.colors.size shouldBe 20; displaySmallGradient(category20b)}
        "category20c" { category20c.colors.size shouldBe 20; displaySmallGradient(category20c)}

        "viridis"   { testAndGraph(viridis) }
        "magma"     { testAndGraph(magma) }
        "inferno"   { testAndGraph(inferno) }
        "plasma"    { testAndGraph(plasma) }
    }


    fun displaySmallGradient(colors: EncodedColors) {
        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "${30* colors.colors.size}")
                    setAttribute("height", "30")
                    colors.colors.forEachIndexed { index, color ->
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", color.rgbHex)
                                    setAttribute("x", "${index*30}")
                                    setAttribute("y", "0")
                                    setAttribute("width", "30")
                                    setAttribute("height", "30")
                                }
                        )
                    }
                }
        )

    }
    fun testAndGraph(gradient: EncodedColors) {

        //generate a list of 6, 10, 20 colors from the gradient.
        listOf(6, 10, 20).forEach { size ->
            val colors   =  (0..size-1)
                    .map { gradient.color(it.toDouble() / (size-1)) }
            println( colors.joinToString(transform = {it.rgbHex}))
        }

        body.appendChild(
                node("svg").apply {
                    setAttribute("width", "400")
                    setAttribute("height", "20")
                    (0..399).forEach {
                        appendChild(
                                node("rect").apply {
                                    setAttribute("fill", gradient.color(it.toDouble()/400).rgbHex)
                                    setAttribute("x", "${it}")
                                    setAttribute("y", "0")
                                    setAttribute("width", "1")
                                    setAttribute("height", "20")
                                }
                        )
                    }
                }
        )
    }

    fun node(name: String) = document.createElementNS(namespace.svg, name)

}

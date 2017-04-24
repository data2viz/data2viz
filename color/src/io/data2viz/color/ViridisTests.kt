package io.data2viz.color

import io.data2viz.color.EncodedGradient
import io.data2viz.color.EncodedGradient.Companion.inferno
import io.data2viz.color.EncodedGradient.Companion.magma
import io.data2viz.color.EncodedGradient.Companion.plasma
import io.data2viz.color.EncodedGradient.Companion.viridis
import test.StringSpec
import kotlin.browser.document

class ViridisTests: StringSpec(){

    init {
        "viridis"   { testAndGraph(viridis) }
        "magma"     { testAndGraph(magma) }
        "inferno"   { testAndGraph(inferno) }
        "plasma"    { testAndGraph(plasma) }
    }

    fun testAndGraph(gradient: EncodedGradient) {

        fun node(name: String) = document.createElementNS(namespace.svg, name)

        val body = document.querySelector("body")!!
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
}

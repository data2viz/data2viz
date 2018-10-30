package io.data2viz.examples.sankey

import io.data2viz.color.LinearGradient
import io.data2viz.color.colors
import io.data2viz.sankey.SankeyAlignment
import io.data2viz.sankey.SankeyLayout
import io.data2viz.sankey.sankeyLinkHorizontal
import io.data2viz.sankey.sankeyLinkVertical
import io.data2viz.scale.scales
import io.data2viz.viz.GroupNode
import io.data2viz.viz.Viz
import io.data2viz.viz.viz
import kotlin.math.max
import kotlin.math.min

data class Element(
        val name: String
)

data class EnergyFlux(
        val fromIndex: Element,
        val toIndex: Element,
        val volume: Double
)

val elements = listOf(
        Element("from1"),
        Element("from2"),
        Element("from3"),

        Element("mid1"),
        Element("mid2"),
        Element("mid3"),
        Element("mid4"),
//    Element("mid5"),

        Element("next1"),
        Element("next2"),
        Element("next3"),
        Element("next4"),
        Element("next5"),

        Element("last1"),
        Element("last2"),
        Element("last3"),
        Element("last4")
)

val energyFluxes = listOf(
        buildFlux("from1", "mid1", 1.4),
        buildFlux("from1", "mid2", 2.4),
        buildFlux("from1", "mid3", 1.0),
        buildFlux("from2", "mid2", 1.4),
        buildFlux("from2", "mid3", 2.2),
        buildFlux("from2", "mid4", 1.7),
        buildFlux("from3", "mid3", 1.0),
        buildFlux("from3", "mid4", 1.2),

        buildFlux("mid1", "next1", 1.0),
        buildFlux("mid1", "next2", 0.4),
        buildFlux("mid2", "next2", 2.0),
        buildFlux("mid2", "next4", 0.9),
        buildFlux("mid2", "next5", 0.9),
        buildFlux("mid3", "next2", 0.9),
        buildFlux("mid3", "next3", 1.1),
        buildFlux("mid3", "next4", 1.6),
        buildFlux("mid3", "next5", 0.6),
        buildFlux("mid4", "next5", 2.9),

        buildFlux("next1", "last1", 0.5),
        buildFlux("next1", "last2", 0.5),
        buildFlux("next2", "last2", 2.0),
        buildFlux("next2", "last4", 1.3),
        buildFlux("next3", "last4", 1.1),
        buildFlux("next4", "last4", 2.5),
        buildFlux("next5", "last3", 1.4),
        buildFlux("next5", "last4", 3.0)

)

fun buildFlux(from: String, to: String, value: Double): EnergyFlux {
    val elementFrom = elements.find { it.name == from }
    val elementTo = elements.find { it.name == to }
    requireNotNull(elementFrom)
    requireNotNull(elementTo)
    return EnergyFlux(elementFrom!!, elementTo!!, value)
}

var vizWidth = 800.0
var vizHeight = 800.0
val margin = 20.0

fun sankeyViz(): Viz = viz {

    width = vizWidth
    height = vizHeight

    val sankeyGroup = group {
        transform { translate(margin, margin) }

        buildSankey()
    }

    onResize { newWidth, newHeight ->
        vizWidth = newWidth
        vizHeight = newHeight

        width = vizWidth
        height = vizHeight

        sankeyGroup.clear()
        sankeyGroup.buildSankey()
    }

}

fun GroupNode.buildSankey() {
    val fills = scales.colors.category20<Int>()

    val sankeyHorizontal = vizWidth >= vizHeight
    val sankeyWidth = max(vizWidth, vizHeight)
    val sankeyHeight = min(vizWidth, vizHeight)

    val sankeyLayout = SankeyLayout<Element>().apply {
        extent(.0, sankeyWidth - (2 * margin), .0, sankeyHeight - (2 * margin))
        nodePadding = 15.0
        nodeWidth = 10.0
        align = SankeyAlignment.JUSTIFY
    }

    val sankey = sankeyLayout.sankey(elements) { from, to ->
        energyFluxes.find { it.fromIndex == from && it.toIndex == to }?.volume
    }

    sankey.nodes.forEachIndexed { index, node ->
        rect {
            style.fill = fills(index % 20)
            style.stroke = colors.black
            x = if (sankeyHorizontal) node.x0 else node.y0
            y = if (sankeyHorizontal) node.y0 else node.x0
            width = if (sankeyHorizontal) node.x1 - node.x0 else node.y1 - node.y0
            height = if (sankeyHorizontal) node.y1 - node.y0 else node.x1 - node.x0
        }
    }

    sankey.links.forEach { link ->
        val gradient = LinearGradient().apply {
            x1 = if (sankeyHorizontal) link.source.x1 else link.source.y1
            y1 = if (sankeyHorizontal) link.source.y1 else link.source.x1
            x2 = if (sankeyHorizontal) link.target.x0 else link.target.y0
            y2 = if (sankeyHorizontal) link.target.y0 else link.target.x0

            //Set the starting color (at 0%)
            addColor(.0, fills(link.source.index % 20).withAlpha(.6f))
            addColor(1.0, fills(link.target.index % 20).withAlpha(.6f))
        }
        path {
            style.stroke = gradient
            style.fill = null
            style.strokeWidth = max(1.0, link.width)

            if (sankeyHorizontal) sankeyLinkHorizontal.link(link, this) else sankeyLinkVertical.link(link, this)
        }
        return@forEach
    }
}

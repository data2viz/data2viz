package io.data2viz.examples.sankey

import io.data2viz.color.LinearGradient
import io.data2viz.viz.VizContext
import io.data2viz.color.colors
import io.data2viz.sankey.SankeyAlignment
import io.data2viz.sankey.SankeyLayout
import io.data2viz.sankey.sankeyLinkHorizontal
import io.data2viz.scale.scales
import kotlin.math.cos
import kotlin.math.max

data class Element(
    val name: String
)

data class EnergyFlux(
    val fromIndex: Element,
    val toIndex: Element,
    val volume: Double
)

val elements = listOf(
    Element("Energy"),
    Element("Industrial Processes"),
    Element("Electricity and heat"),
    Element("Industry"),
    Element("Land Use Change"),
    Element("Agriculture"),
    Element("Waste"),
    Element("Transportation"),
    Element("Other Fuel Combustion"),
    Element("Fugitive Emissions"),
    Element("Road"),
    Element("Air"),
    Element("Rail - Ship and Other Transport"),
    Element("Residential Buildings"),
    Element("Commercial Buildings"),
    Element("Unallocated Fuel Combustion"),
    Element("Iron and Steel"),
    Element("Aluminium Non-Ferrous Metals"),
    Element("Machinery"),
    Element("Pulp - Paper and Printing"),
    Element("Food and Tobacco"),
    Element("Chemicals"),
    Element("Cement"),
    Element("Other Industry"),
    Element("T and D Losses"),
    Element("Coal Mining"),
    Element("Oil and Gas Processing"),
    Element("Deforestation"),
    Element("Harvest / Management"),
    Element("Agricultural Energy Use"),
    Element("Agriculture Soils"),
    Element("Livestock and Manure"),
    Element("Rice Cultivation"),
    Element("Other Agriculture"),
    Element("Landfills"),
    Element("Waste water - Other Waste"),
    Element("Carbon Dioxide"),
    Element("HFCs - PFCs"),
    Element("Methane"),
    Element("Nitrous Oxide")
)

val energyFluxes = listOf(
    buildFlux("Agricultural Energy Use", "Carbon Dioxide", 1.4),
    buildFlux("Agriculture", "Agriculture Soils", 5.2),
    buildFlux("Agriculture", "Livestock and Manure", 5.4),
    buildFlux("Agriculture", "Other Agriculture", 1.7),
    buildFlux("Agriculture", "Rice Cultivation", 1.5),
    buildFlux("Agriculture Soils", "Nitrous Oxide", 5.2),
    buildFlux("Air", "Carbon Dioxide", 1.7),
    buildFlux("Aluminium Non-Ferrous Metals", "Carbon Dioxide", 1.0),
    buildFlux("Aluminium Non-Ferrous Metals", "HFCs - PFCs", 0.2),
    buildFlux("Cement", "Carbon Dioxide", 5.0),
    buildFlux("Chemicals", "Carbon Dioxide", 3.4),
    buildFlux("Chemicals", "HFCs - PFCs", 0.5),
    buildFlux("Chemicals", "Nitrous Oxide", 0.2),
    buildFlux("Coal Mining", "Carbon Dioxide", 0.1),
    buildFlux("Coal Mining", "Methane", 1.2),
    buildFlux("Commercial Buildings", "Carbon Dioxide", 6.3),
    buildFlux("Deforestation", "Carbon Dioxide", 10.9),
    buildFlux("Electricity and heat", "Agricultural Energy Use", 0.4),
    buildFlux("Electricity and heat", "Aluminium Non-Ferrous Metals", 0.4),
    buildFlux("Electricity and heat", "Cement", 0.3),
    buildFlux("Electricity and heat", "Chemicals", 1.3),
    buildFlux("Electricity and heat", "Commercial Buildings", 5.0),
    buildFlux("Electricity and heat", "Food and Tobacco", 0.5),
    buildFlux("Electricity and heat", "Iron and Steel", 1.0),
    buildFlux("Electricity and heat", "Machinery", 1.0),
    buildFlux("Electricity and heat", "Oil and Gas Processing", 0.4),
    buildFlux("Electricity and heat", "Other Industry", 2.7),
    buildFlux("Electricity and heat", "Pulp - Paper and Printing", 0.6),
    buildFlux("Electricity and heat", "Residential Buildings", 5.2),
    buildFlux("Electricity and heat", "T and D Losses", 2.2),
    buildFlux("Electricity and heat", "Unallocated Fuel Combustion", 2.0),
    buildFlux("Energy", "Electricity and heat", 24.9),
    buildFlux("Energy", "Fugitive Emissions", 4.0),
    buildFlux("Energy", "Industry", 14.7),
    buildFlux("Energy", "Other Fuel Combustion", 8.6),
    buildFlux("Energy", "Transportation", 14.3),
    buildFlux("Food and Tobacco", "Carbon Dioxide", 1.0),
    buildFlux("Fugitive Emissions", "Coal Mining", 1.3),
    buildFlux("Fugitive Emissions", "Oil and Gas Processing", 3.2),
    buildFlux("Harvest / Management", "Carbon Dioxide", 1.3),
    buildFlux("Industrial Processes", "Aluminium Non-Ferrous Metals", 0.4),
    buildFlux("Industrial Processes", "Cement", 2.8),
    buildFlux("Industrial Processes", "Chemicals", 1.4),
    buildFlux("Industrial Processes", "Other Industry", 0.5),
    buildFlux("Industry", "Aluminium Non-Ferrous Metals", 0.4),
    buildFlux("Industry", "Cement", 1.9),
    buildFlux("Industry", "Chemicals", 1.4),
    buildFlux("Industry", "Food and Tobacco", 0.5),
    buildFlux("Industry", "Iron and Steel", 3.0),
    buildFlux("Industry", "Oil and Gas Processing", 2.8),
    buildFlux("Industry", "Other Industry", 3.8),
    buildFlux("Industry", "Pulp - Paper and Printing", 0.5),
    buildFlux("Iron and Steel", "Carbon Dioxide", 4.0),
    buildFlux("Land Use Change", "Deforestation", 10.9),
    buildFlux("Land Use Change", "Harvest / Management", 1.3),
    buildFlux("Landfills", "Methane", 1.7),
    buildFlux("Livestock and Manure", "Methane", 5.1),
    buildFlux("Livestock and Manure", "Nitrous Oxide", 0.3),
    buildFlux("Machinery", "Carbon Dioxide", 1.0),
    buildFlux("Oil and Gas Processing", "Carbon Dioxide", 3.6),
    buildFlux("Oil and Gas Processing", "Methane", 2.8),
    buildFlux("Other Agriculture", "Methane", 1.4),
    buildFlux("Other Agriculture", "Nitrous Oxide", 0.3),
    buildFlux("Other Fuel Combustion", "Agricultural Energy Use", 1.0),
    buildFlux("Other Fuel Combustion", "Commercial Buildings", 1.3),
    buildFlux("Other Fuel Combustion", "Residential Buildings", 5.0),
    buildFlux("Other Fuel Combustion", "Unallocated Fuel Combustion", 1.8),
    buildFlux("Other Industry", "Carbon Dioxide", 6.6),
    buildFlux("Other Industry", "HFCs - PFCs", 0.4),
    buildFlux("Pulp - Paper and Printing", "Carbon Dioxide", 1.1),
    buildFlux("Rail - Ship and Other Transport", "Carbon Dioxide", 2.5),
    buildFlux("Residential Buildings", "Carbon Dioxide", 10.2),
    buildFlux("Rice Cultivation", "Methane", 1.5),
    buildFlux("Road", "Carbon Dioxide", 10.5),
    buildFlux("T and D Losses", "Carbon Dioxide", 2.2),
    buildFlux("Transportation", "Air", 1.7),
    buildFlux("Transportation", "Rail - Ship and Other Transport", 2.5),
    buildFlux("Transportation", "Road", 10.5),
    buildFlux("Unallocated Fuel Combustion", "Carbon Dioxide", 3.0),
    buildFlux("Unallocated Fuel Combustion", "Methane", 0.4),
    buildFlux("Unallocated Fuel Combustion", "Nitrous Oxide", 0.4),
    buildFlux("Waste", "Landfills", 1.7),
    buildFlux("Waste", "Waste water - Other Waste", 1.5),
    buildFlux("Waste water - Other Waste", "Methane", 1.2),
    buildFlux("Waste water - Other Waste", "Nitrous Oxide", 0.3)
)

fun buildFlux(from: String, to: String, value: Double): EnergyFlux {
    val elementFrom = elements.find { it.name == from }
    val elementTo = elements.find { it.name == to }
    requireNotNull(elementFrom)
    requireNotNull(elementTo)
    return EnergyFlux(elementFrom!!, elementTo!!, value)
}

val vizWidth = 1200.0
val vizHeight = 800.0
val margin = 20.0

val sankeyLayout = SankeyLayout<Element>().apply {
    extent(.0, vizWidth - (2 * margin), .0, vizHeight - (2 * margin))
    nodePadding = 15.0
    nodeWidth = 10.0
    align = SankeyAlignment.JUSTIFY
}

val sankey = sankeyLayout.sankey(
    elements,
    { from, to -> energyFluxes.find { it.fromIndex == from && it.toIndex == to }?.volume })

fun VizContext.sankeyViz() {
    transform { translate(margin, margin) }

    val fills = scales.colors.category20<Int>()

    sankey.nodes.forEachIndexed { index, node ->
        rect {
            fill = fills(index%20)
            stroke = colors.black
            x = node.x0
            y = node.y0
            width = node.x1 - node.x0
            height = node.y1 - node.y0
        }
    }

    sankey.links.forEach { link ->
        /*LinearGradient().apply {
            x1 = link.source.x1
            y1 = link.source.y1
            x2 = link.target.x0
            y2 = link.target.y0

            //Set the starting color (at 0%)
            addColor(.0, fills(20).apply { alpha = .6})
            addColor(1.0, fills(18).apply { alpha = .6 })
        }*/
        path {
            stroke = colors.rgba(0, 0, 0, 0.2)
            fill = colors.rgba(0, 0, 0, 0)
            strokeWidth = max(1.0, link.width)
            sankeyLinkHorizontal.link(link, this)
        }
    }
}

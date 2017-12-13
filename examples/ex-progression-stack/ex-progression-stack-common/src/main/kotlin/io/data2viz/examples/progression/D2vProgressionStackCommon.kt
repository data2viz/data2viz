package io.data2viz.examples.progression

import io.data2viz.axis.Orient
import io.data2viz.axis.axis
import io.data2viz.color.color
import io.data2viz.viz.VizContext
import io.data2viz.scale.*
import io.data2viz.viz.Margins


val margins = Margins(50.0)

val width = 1600.0 - margins.hMargins
val height = 500.0 - margins.vMargins


val moduleNames = modules.map {  it.module}

val xScale = scales.band(moduleNames){
    range = intervalOf(.0, width)
    paddingInner = .1
    paddingOuter = .3
}

val Progression.totalLOC: Int
    get() = commonLOC + jsLOC + JVMLOC

fun checkData(data:List<Progression>){
    data.forEach { 
        check( it.estimatedLOC >= it.totalLOC) { "Estimated LOC under total LOC (${it.estimatedLOC} > ${it.totalLOC}"}
    }
}

fun VizContext.progression() {

    transform {
        translate(x = margins.left, y = margins.top)
    }

    checkData(modules)
    
    val maxEstimated = modules.maxBy { it.estimatedLOC }!!.estimatedLOC.toDouble()
    val maxTest = modules.maxBy { it.testsLOC }!!.testsLOC.toDouble()

    val yScale = scales.continuous.linear{
        domain = listOf(.0, maxTest + maxEstimated)
        range = listOf(.0, height)
    }
    
    val y0 = yScale(maxEstimated)

    modules.forEach { progression ->
        rect { // <- tests LOC rectangle
            fill = "#d6604d".color
            x = xScale(progression.module)
            width = xScale.bandwidth
            y = y0
            height = yScale(progression.testsLOC.toDouble())
        }

        val commonLocHeight = yScale(progression.commonLOC.toDouble())
        val jvmLocHeight = yScale(progression.JVMLOC.toDouble())
        val jsLocHeight = yScale(progression.jsLOC.toDouble())

        rect { // <- common LOC rectangle
            fill = "#2166ac".color
            x = xScale(progression.module)
            width = xScale.bandwidth
            y = y0 - commonLocHeight
            this.height = commonLocHeight
        }

        rect { // <- Js LOC rectangle
            fill = "#4393c3".color
            x = xScale(progression.module)
            width = xScale.bandwidth
            y = y0 - commonLocHeight - jsLocHeight
            this.height = jsLocHeight
        }
        rect { // <- common LOC rectangle
            fill = "#92c5de".color
            x = xScale(progression.module)
            width = xScale.bandwidth
            y = y0 - commonLocHeight - jsLocHeight - jvmLocHeight
            this.height = jvmLocHeight
        }

    }
    
    group {
        transform {
            translate( y = y0)
        }
        axis(Orient.BOTTOM, xScale)
    }
    

}

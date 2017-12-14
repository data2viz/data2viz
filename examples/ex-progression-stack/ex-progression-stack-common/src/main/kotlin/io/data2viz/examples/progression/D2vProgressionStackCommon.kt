package io.data2viz.examples.progression

import io.data2viz.axis.*
import io.data2viz.color.*
import io.data2viz.format.formatter
import io.data2viz.scale.*
import io.data2viz.viz.*
import kotlin.math.absoluteValue

val legendLineHeight = 30.0

val margins = Margins(50.0, bottom =  4 * legendLineHeight + 40.0)

val width = 1600.0 - margins.hMargins
val height = 450.0

//Colors from http://colorbrewer2.org/#type=diverging&scheme=RdBu&n=7
val colorExpected = "#dddddd".color
val colorTest = "#b2182b".color
val colorCommon = "#2166ac".color
val colorJs = "#67a9cf".color
val colorJVM = "#d1e5f0".color

val ModuleState.totalLOC: Int
    get() = commonLOC + jsLOC + JVMLOC

fun checkData(data: List<ModuleState>) {
    data.forEach {
        check(it.estimatedLOC >= it.totalLOC) { "Estimated LOC under total LOC (${it.estimatedLOC} > ${it.totalLOC}" }
    }
}

fun VizContext.progression() {

    checkData(modules)

    transform {
        translate(x = margins.left, y = margins.top)
    }

    val maxEstimated = modules.maxBy { it.estimatedLOC }?.estimatedLOC?.toDouble() ?: .0
    val maxTest = modules.maxBy { it.testsLOC }?.testsLOC?.toDouble() ?: .0

    val yScale = scales.continuous.linear {
        domain = listOf(.0, maxTest + maxEstimated)
        range = listOf(.0, height)
    }

    val y0 = yScale(maxEstimated)

    val moduleNames = modules.map { it.name }

    val xScale = scales.band(moduleNames) {
        range = intervalOf(.0, width)
        padding = .1
    }

    axis(Orient.LEFT, scales.continuous.linear {
        domain = listOf(maxEstimated, - maxTest)
        range = listOf(.0, height)
    }) {
        tickFormat = {it.absoluteValue.toInt().toString()}
    }
    
    
    legend()

    modules.forEach { progression ->

        // tests LOC rectangle
        rect {
            fill = colorTest
            stroke = null
            x = xScale(progression.name)
            width = xScale.bandwidth
            y = y0
            height = yScale(progression.testsLOC.toDouble())
        }

        val expectedLocHeight = yScale(progression.estimatedLOC.toDouble())
        val commonLocHeight = yScale(progression.commonLOC.toDouble())
        val jvmLocHeight = yScale(progression.JVMLOC.toDouble())
        val jsLocHeight = yScale(progression.jsLOC.toDouble())

        // Expected LOC rectangle
        rect {
            fill = colorExpected
            stroke = null
            x = xScale(progression.name)
            width = xScale.bandwidth
            y = y0 - expectedLocHeight
            this.height = expectedLocHeight
        }

        // common LOC rectangle
        rect {
            fill = colorCommon
            stroke = null
            x = xScale(progression.name)
            width = xScale.bandwidth
            y = y0 - commonLocHeight
            this.height = commonLocHeight
        }

        // Js LOC rectangle
        rect {
            fill = colorJs
            stroke = null
            x = xScale(progression.name)
            width = xScale.bandwidth
            y = y0 - commonLocHeight - jsLocHeight
            this.height = jsLocHeight
        }

        // JVM LOC rectangle
        rect {
            fill = colorJVM
            stroke = null
            x = xScale(progression.name)
            width = xScale.bandwidth
            y = y0 - commonLocHeight - jsLocHeight - jvmLocHeight
            this.height = jvmLocHeight
        }
    }

    group {
        transform {
            translate(y = y0)
        }
        axis(Orient.BOTTOM, xScale)
    }

    //module vertical separations
    val modulesByVersion = modules
            .groupBy { it.version }

    val versions = modulesByVersion.keys.sorted()
    var xSeparation = xScale.paddingInner * xScale.step / 2
    (0 until versions.size).forEach {

        val key = versions[it]
        val packageCountForVersion = modulesByVersion.get(key)?.size ?: 0
        val versionWidth = xScale.step * packageCountForVersion

        text {
            anchor = TextAnchor.MIDDLE
            transform {
                translate(x = xSeparation + .5 * versionWidth)
            }
            textContent = "version $key"
        }
        xSeparation += versionWidth

        if (it != versions.size - 1)
            group {
                transform { translate(x = xSeparation) }
                line {
                    y1 = .0
                    y2 = height
                }
            }
    }
}

private fun VizContext.legend() {
    group {
        transform { translate(x = 20.0, y = height) }
        colorLegend(colorJVM, "Current kotlin jvm LOC", line = 0)
        colorLegend(colorJs, "Current kotlin js LOC", line = 1)
        colorLegend(colorCommon, "Current kotlin common LOC", line = 2)
        colorLegend(colorTest, "Current total test LOC", line = 3)
    }
}

private fun ParentItem.colorLegend(color: Color, legend: String, line: Int = 0) =
    group {
        transform {
            translate(y = line * legendLineHeight)
        }
        rect {
            fill = color
            stroke = colors.black
            height = 20.0
            width = 20.0
        }

        text {
            transform { translate(x = 25.0, y = 15.0) }
            textContent = legend
        }
    }

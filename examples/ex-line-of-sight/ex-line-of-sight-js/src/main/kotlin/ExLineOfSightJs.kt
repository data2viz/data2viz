package io.data2viz.examples.chord

import io.data2viz.examples.lineOfSight.vizHeight
import io.data2viz.examples.lineOfSight.lineOfSightViz
import io.data2viz.examples.lineOfSight.vizWidth
import io.data2viz.timer.timer
import io.data2viz.viz.JsCanvasRenderer
import io.data2viz.viz.Viz
import io.data2viz.viz.bindRendererOn
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document


@Suppress("unused")

fun main(args: Array<String>) {
    val viz = lineOfSightViz()
    viz.bindRendererOn("viz")
    timer {
        viz.render()
    }
}


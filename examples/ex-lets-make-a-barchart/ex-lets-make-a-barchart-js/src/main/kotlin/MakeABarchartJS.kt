package io.data2viz.examples.letsMakeABarchart

import io.data2viz.viz.JsCanvasRenderer
import io.data2viz.viz.bindRendererOnNewCanvas
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document


@Suppress("unused")

fun main(args: Array<String>) {
    barchartViz().bindRendererOnNewCanvas()
}


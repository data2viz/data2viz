package io.data2viz.viz

import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document

@Suppress("DEPRECATION")
fun main(args: Array<String>) {
    val tests = allRenderingTests

    tests.forEach {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        document.body?.appendChild(canvas)
        canvas.id = it.name
        it.viz.bindRendererOn(canvas)
    }

    //spans is only here to make puppeteer wait for all test renderings
    val span = document.createElement("span")
    document.body?.appendChild(span)

}




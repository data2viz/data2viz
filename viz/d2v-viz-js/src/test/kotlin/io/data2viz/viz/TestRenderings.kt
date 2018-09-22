package io.data2viz.viz

import io.data2viz.color.colors.red
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.reflect.KProperty



data class RenderingTest(val name: String, val viz: Viz) {
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): RenderingTest {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun renderingTest(name: String, init: Viz.() -> Unit): RenderingTest {

    val viz = viz {
        width = 400.0
        height = 400.0
        init()
    }

    return RenderingTest(name,viz)
}


fun main(args: Array<String>) {
    val tests = circleTests

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




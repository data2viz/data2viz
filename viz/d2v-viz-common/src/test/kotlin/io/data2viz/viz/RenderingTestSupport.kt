package io.data2viz.viz


data class RenderingTest(val name: String, val viz: Viz)

fun renderingTest(name: String, init: Viz.() -> Unit): RenderingTest {

    val viz = viz {
        width = 400.0
        height = 400.0
        init()
    }

    return RenderingTest(name,viz)
}

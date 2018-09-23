package io.data2viz.viz

import io.data2viz.color.colors


data class RenderingTest(val name: String, val viz: Viz)


val allRenderingTests = listOf(
        renderingTest("circle1") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                fill = colors.red
            }
        },
        renderingTest("circle2") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                stroke = colors.red
                strokeWidth = 20.0
            }
        },
        renderingTest("circle3") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                stroke = colors.red
            }
        },
        renderingTest("circle4") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                fill = colors.blue
                stroke = colors.red.withAlpha(.5f)
                strokeWidth = 20.0
            }
        },
        renderingTest("path1") {
            path {
                moveTo(20.0,20.0)
                lineTo(40.0,40.0)
                lineTo(60.0,20.0)
                moveTo(80.0,40.0)
                lineTo(100.0,20.0)
                stroke = colors.red
            }
        }
)

fun renderingTest(name: String, init: Viz.() -> Unit): RenderingTest {

    val viz = viz {
        width = 400.0
        height = 400.0
        init()
    }

    return RenderingTest(name,viz)
}

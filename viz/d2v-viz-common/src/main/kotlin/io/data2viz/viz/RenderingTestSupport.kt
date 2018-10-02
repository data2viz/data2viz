package io.data2viz.viz

import io.data2viz.color.colors
import io.data2viz.math.PI


/**
 * Todo: move in test sources https://github.com/data2viz/data2viz/issues/24
 */
@Deprecated("Temporary workaround https://github.com/data2viz/data2viz/issues/24")
data class RenderingTest(val name: String, val viz: Viz)

@Deprecated("Temporary workaround https://github.com/data2viz/data2viz/issues/24")
val allRenderingTests = listOf(
        renderingTest("circle1") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                style.fill = colors.red
            }
        },
        renderingTest("circle2") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                style.stroke = colors.red
                style.strokeWidth = 20.0
            }
        },
        renderingTest("circle3") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                style.stroke = colors.red
            }
        },
        renderingTest("circle4") {
            circle {
                x = 200.0
                y = 200.0
                radius = 100.0
                style.fill = colors.blue
                style.stroke = colors.red.withAlpha(.5f)
                style.strokeWidth = 20.0
            }
        },
        renderingTest("arc1-positive-clockwise") {

            var x = -25.0
            var y = 25.0

            fun next(){
                if (x >=375.0) {
                    x = 25.0
                    y += 50.0
                }
                else x += 50.0
            }

            path {
                next()
                moveTo(x, y)
                arc(x, y, 25.0, .0, .001 * PI)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
            path {
                next()
                moveTo(x, y)
                arc(x, y, 25.0, +0.0, .25 * PI)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
            path {
                next()
                moveTo(x, y)
                arc(x, y, 25.0, +0.0, 2 * PI)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
            path {
                next()
                moveTo(x, y)
                arc(x, y, 25.0, .25 * PI, 2 * PI)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
            path {
                next()
                moveTo(x, y)
                arc(x, y, 25.0, .25 * PI, 2.25 * PI)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }

//            path {
//                moveTo(250.0, 50.0)
//                arc(250.0, 50.0, 50.0, .0, PI / 4, true)
//                stroke = colors.grey
//                fill = null
//            }
//            path {
//                moveTo(350.0, 50.0)
//                arc(350.0, 50.0, 50.0, PI / 4, .0, true)
//                stroke = colors.grey
//                fill = null
//            }
//            path {
//                moveTo(50.0, 150.0)
//                arc(50.0, 150.0, 50.0, PI / 4, 7*PI / 4)
//                closePath()
//                fill = colors.grey
//                stroke = null
//            }
//            path {
//                moveTo(150.0, 150.0)
//                arc(150.0, 150.0, 50.0, 7 * PI / 4, PI/4)
//                closePath()
//                fill = colors.grey
//                stroke = null
//            }


        },
        renderingTest("path1") {
            path {
                moveTo(20.0,20.0)
                lineTo(40.0,40.0)
                lineTo(60.0,20.0)
                moveTo(80.0,40.0)
                lineTo(100.0,20.0)
                style.stroke = colors.red
            }
        },
        renderingTest("path.rect") {
            path {
                rect(10.0, 10.0, 200.0, 100.0)
                style.fill = colors.red
            }
        },
        renderingTest("visible1") {
            circle {
                x = 50.0
                y = 50.0
                radius = 50.0
                style.fill = colors.black
                visible = false
            }
            circle {
                x = 150.0
                y = 50.0
                radius = 50.0
                style.fill = colors.black
            }
        },
        renderingTest("visible2-layer") {
            activeLayer.visible = false
            circle {
                x = 50.0
                y = 50.0
                radius = 50.0
                style.fill = colors.black
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

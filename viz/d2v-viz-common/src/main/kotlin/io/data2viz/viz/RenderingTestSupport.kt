package io.data2viz.viz

import io.data2viz.color.color
import io.data2viz.color.colors
import io.data2viz.math.PI


/**
 * Todo: move in test sources https://github.com/data2viz/data2viz/issues/24
 */
@Deprecated("Temporary workaround https://github.com/data2viz/data2viz/issues/24")
data class RenderingTest(val name: String, val viz: Viz)

private fun Pair<Double, Double>.next(): Pair<Double, Double> {
    var x = first
    var y = second
    if (x >= 350) {
        x = 25.0
        y += 50.0
    } else x += 50.0
    return Pair(x, y)
}

@Deprecated("Temporary workaround https://github.com/data2viz/data2viz/issues/24")
val allRenderingTests = listOf(
//        renderingTest("text1") { //add text tests when font-familly will be enabled in style.
//            group {
//                transform { translate( 100.0, 100.0) }
//                text {
//                    textContent = "Bépo"
//                }
//            }
//        },


    ///////////// CIRCLES /////////////////////////////////

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
            style.fill = 0xfdc658.color
            style.stroke = 0x0c0887.color
                .withAlpha(.5f)
            style.strokeWidth = 40.0
        }
    },



    ///////////// ARCS /////////////////////////////////

    renderingTest("arc1-positive-clockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)

        (0..15).forEach {
            path {
                pos = pos.next()
                moveTo(pos.first, pos.second)
                arc(pos.first, pos.second, 25.0, .0, it * (2 * PI / 8.0), false)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
        }
    },

    renderingTest("arc2-negative-clockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)

        (0..15).forEach {
            path {
                pos = pos.next()
                moveTo(pos.first, pos.second)
                arc(pos.first, pos.second, 25.0, .0, -it * (2 * PI / 8.0), false)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
        }
    },

    renderingTest("arc3-positive-counterclockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)

        (0..15).forEach {
            path {
                pos = pos.next()
                moveTo(pos.first, pos.second)
                arc(pos.first, pos.second, 25.0, .0, it * (2 * PI / 8.0), true)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
        }
    },

    renderingTest("arc4-negative-counterclockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)

        (0..15).forEach {
            path {
                pos = pos.next()
                moveTo(pos.first, pos.second)
                arc(pos.first, pos.second, 25.0, .0, -it * (2 * PI / 8.0), true)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
        }
    },

    renderingTest("arc5-positive-negative-clockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)
        var posNeg = 1.0

        (0..15).forEach {
            path {
                pos = pos.next()
                posNeg *= -1
                moveTo(pos.first, pos.second)
                arc(pos.first, pos.second, 25.0, it * posNeg / 10.0, -posNeg * it * (2 * PI / 8.0), false)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
        }
    },

    renderingTest("arc6-positive-negative-counterclockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)
        var posNeg = 1.0

        (0..15).forEach {
            path {
                pos = pos.next()
                posNeg *= -1
                moveTo(pos.first, pos.second)
                arc(pos.first, pos.second, 25.0, it * posNeg / 10.0, -posNeg * it * (2 * PI / 8.0), true)
                closePath()
                style.fill = colors.grey
                style.stroke = null
            }
        }
    },

    renderingTest("arc7-checking-points-order-clockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)
        var posNeg = 1.0

        (0..15).forEach {
            path {
                pos = pos.next()
                posNeg *= -1
                moveTo(pos.first - 15.0, pos.second - 15.0)
                lineTo(pos.first - 15.0, pos.second - 5.0)
                arc(pos.first, pos.second, 25.0, it * posNeg / 10.0, -posNeg * it * (2 * PI / 8.0), false)
                lineTo(pos.first + 15.0, pos.second + 15.0)
                closePath()
                style.fill = colors.grey
                style.stroke = colors.blue
            }
        }
    },

    renderingTest("arc8-checking-points-order-counterclockwise") {
        var pos: Pair<Double, Double> = Pair(-25.0, 25.0)
        var posNeg = 1.0

        (0..15).forEach {
            path {
                pos = pos.next()
                posNeg *= -1
                moveTo(pos.first - 15.0, pos.second - 15.0)
                lineTo(pos.first - 15.0, pos.second - 5.0)
                arc(pos.first, pos.second, 25.0, it * posNeg / 10.0, -posNeg * it * (2 * PI / 8.0), true)
                lineTo(pos.first + 15.0, pos.second + 15.0)
                closePath()
                style.fill = colors.grey
                style.stroke = colors.blue
            }
        }
    },

    renderingTest("transform") {
        var depth = 0
        fun addToParent(parent: Group) {
            depth++
            if (depth == 41) return

            with(Group()) {
                parent.add(this)
                transform {
                    translate(x = 10.0, y = 10.0)
                    rotate(0.1 * PI / 2)
                }
                rect {
                    height = 10.0
                    width = 10.0
                    style.fill = colors.black
                }
                addToParent(this)
            }
            depth--
        }
        addToParent(group {
            transform {
                translate(x = 250.0, y = 125.0)
            }
            rect {
                height = 10.0
                width = 10.0
                style.fill = colors.black
            }
        })
    },
    renderingTest("path1") {
        path {
            moveTo(20.0, 20.0)
            lineTo(40.0, 40.0)
            lineTo(60.0, 20.0)
            moveTo(80.0, 40.0)
            lineTo(100.0, 20.0)
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

    return RenderingTest(name, viz)
}
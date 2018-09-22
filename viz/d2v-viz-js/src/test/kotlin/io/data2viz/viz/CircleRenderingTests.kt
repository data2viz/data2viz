package io.data2viz.viz

import io.data2viz.color.color
import io.data2viz.color.colors


val circleTests = listOf(
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
        }
)
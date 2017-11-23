package io.data2viz.viz

import io.data2viz.color.colors

data class Domain(val val1: Double, val val2: Double)


fun VizContext.commonViz(data: List<Domain>) {

    text {
        textContent = "This a common présentation"
        y = 20.0
        x = 20.0
    }

    rect {
        x = 30.0
        y = 130.0
        rx = 4.0
        ry = 4.0
        height = 30.0
        width = 80.0
        fill = colors.blueviolet
        stroke = colors.darkslategray
        strokeWidth = 3.0
    }


    group {

        group {

            data.forEach { datum ->
                circle {
                    cx = datum.val1
                    cy = datum.val2
                    radius = 5.0
                    fill = colors.steelblue
                    stroke = colors.red
                    strokeWidth = 3.0

                    transform {
                        translate(datum.val1 + 100)
                    }
                }

            }
        }
    }
}

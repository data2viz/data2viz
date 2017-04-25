package io.data2viz.experiments.mouse

import io.data2viz.color.colors.red
import io.data2viz.color.colors.steelblue
import io.data2viz.svg.svg

fun mouse() {

    val datas = listOf(30, 20, 40)
    var x = 0

    svg {
        width = 600
        height = 600

        datas.forEach { data ->

            circle {
                x += data + 5
                cx = x
                cy = 40
                r = data / 2
                fill = steelblue

                on("mouseenter") { fill = red }
                on("mouseout")   { fill = steelblue }
            }
        }
    }
}

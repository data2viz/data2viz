package io.data2viz.experiments.mouse

import io.data2viz.color.colors.red
import io.data2viz.color.colors.steelblue
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.svg.svg

fun mouse() {

    val datas = listOf(30, 20, 40)
    var x = 0

    svg {
        width = 600
        height = 600

        datas.forEach { data ->

            rect {
                x += data + 5
                transform {
                    translate(x, 40)
//                    rotate(Angle(20.0))
                }
                width = data / 2
                height = data / 2
                fill = steelblue

                on("mouseenter") { fill = red }
                on("mouseout")   { fill = steelblue }
            }
        }
    }
}

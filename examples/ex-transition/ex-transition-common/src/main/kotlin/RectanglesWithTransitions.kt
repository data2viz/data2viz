package io.data2viz.transition

import io.data2viz.color.colors
import io.data2viz.viz.Group

fun Group.rectanglesWithTransition() {

    (1..50).forEach {
        rect {
            x = 10.0 * 2 * it
            y = 10.0
            width = 10.0
            height = 10.0
            fill = colors.orange

            transition {
                x = 110.0
                y = 110.0
                fill = colors.grey
            }
        }
    }

}
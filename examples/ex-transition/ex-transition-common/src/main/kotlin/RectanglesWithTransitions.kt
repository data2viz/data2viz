package io.data2viz.transition

import io.data2viz.color.colors
import io.data2viz.ease.ease
import io.data2viz.viz.Group

fun Group.rectanglesWithTransition() {

    rect {
        width = 10.0
        height = width
        fill = colors.black

        transitionTo {
            x = 100.0
            fill = colors.orange
        }.thenTransitionTo {
            y = 100.0
            fill = colors.blueviolet
        }.thenTransitionTo {
            x = 0.0
            fill = colors.aquamarine
        }.thenTransitionTo {
            y = 0.0
            fill = colors.black
        }
    }


}
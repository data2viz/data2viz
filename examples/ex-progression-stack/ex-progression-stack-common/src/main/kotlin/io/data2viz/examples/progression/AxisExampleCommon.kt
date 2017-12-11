package io.data2viz.examples.progression

import io.data2viz.viz.VizContext
import io.data2viz.scale.*


val scaleX = scales.continuous.linear {
    domain = listOf(.0, 15000.0)
    range = listOf(.0, 500.0)
}

val ticks = (1..14 step 2).map { it * 1000.0 }


val x = scaleBand(modules.map(Progression::module)).apply {
    range = intervalOf(0.0, 1100.0)
}


fun VizContext.progression() {
    group {
        transform {
            translate(x = 90.0, y = 40.0)
        }
//        axis(Orient.BOTTOM, x) {
//            tickValues = ticks
//            tickFormat = formatter(Type.DECIMAL_OR_EXPONENT)
//        }
    }

}

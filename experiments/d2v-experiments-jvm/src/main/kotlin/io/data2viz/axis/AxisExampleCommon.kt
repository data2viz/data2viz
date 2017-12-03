package io.data2viz.axis

import io.data2viz.viz.VizContext
import io.data2viz.scale.*
import io.data2viz.format.Type
import io.data2viz.format.formatter


val scaleX = scaleLinear {
    domain = listOf(.0, 15000.0)
    range = listOf(.0, 500.0)
}

val ticks = (1..14 step 2).map { it * 1000.0 }



fun VizContext.axisExample() {
    group {
        transform {
            translate(x = 30.0, y = 10.0)
        }
        axis(Orient.TOP, scaleX) {
            tickValues = ticks
            tickFormat = formatter(Type.DECIMAL_OR_EXPONENT)
        }
    }
    group {
        transform {
            translate(x = 30.0, y = 20.0)
        }
        axis(Orient.BOTTOM, scaleX) {
            tickValues = ticks
            tickFormat = formatter(Type.DECIMAL_WITH_SI, precision = 2)
        }
    }
    group {
        transform {
            translate(x = 10.0, y = 30.0)
        }
        axis(Orient.LEFT, scaleX) {
            tickValues = ticks
            tickFormat = formatter(Type.DECIMAL_WITH_SI, precision = 2)
        }
    }
    group {
        transform {
            translate(x = 20.0, y = 30.0)
        }
        axis(Orient.RIGHT, scaleX) {
            tickValues = ticks
            tickFormat = formatter(Type.DECIMAL_WITH_SI, precision = 2)
        }
    }
}

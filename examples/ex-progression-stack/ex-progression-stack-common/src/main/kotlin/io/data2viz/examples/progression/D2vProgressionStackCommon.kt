package io.data2viz.examples.progression

import io.data2viz.axis.Orient
import io.data2viz.axis.axis
import io.data2viz.viz.VizContext
import io.data2viz.scale.*
import io.data2viz.viz.Margins


val margins = Margins(50.0)

val width = 1600.0 - margins.hMargins
val height = 500.0 - margins.vMargins


val data = modules.map {  it.module}

val xScale = scales.band(data){
    range = intervalOf(.0, width)
    paddingInner = .1
    paddingOuter = .3
}


fun VizContext.progression() {

    transform {
        translate(x = margins.right, y = margins.top)
    }

    group {
        transform {
            translate(x = 90.0, y = 40.0)
        }
        axis(Orient.BOTTOM, xScale)
    }

}

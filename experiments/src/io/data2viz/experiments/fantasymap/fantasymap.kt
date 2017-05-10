package io.data2viz.experiments.fantasymap

import io.data2viz.experiments.animate.svg


data class Params(
        val npts:Int = 16384,
        val ncities:Int = 15,
        val nterrs:Int = 5,
        val fontSizeRegion:Int = 40,
        val fontSizeCity:Int = 25,
        val fontSizeTown:Int = 20
)


fun buildFantasyMap(){
    println("building fantasy map")

    svg {
        width = 600
        height = 600
    }
}

package io.data2viz.experiments.voronoi

import io.data2viz.svg.svg
import io.data2viz.voronoi.Point
import kotlin.js.Math


fun voronoi(){
    println("test voronoi")
    println("${Math.atan2(90.0, 15.0)}")

    val datas = listOf(
            Point(10.0, 12.0),
            Point(50.0, 33.0),
            Point(25.0, 120.0),
            Point(65.0, 73.0)
    )

//    voronoi(datas.toTypedArray())



    svg {
        width = 600
        height = 600
    }
}

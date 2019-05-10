package io.data2viz.geo.projection

import io.data2viz.math.EPSILON

//import conicEqualArea from "./conicEqualArea";

//fun albers() {
//
//    projection(ConicEqualAreaRaw)
//
//    return conicEqualArea()
//        .parallels([29.5, 45.5])
//        .scale(1070)
//        .translate([480, 250])
//        .rotate([96, 0])
//        .center([-0.6, 38.7]);
//}


fun albersProjection() = albersProjection {
}

fun albersProjection(init: ConicProjection.() -> Unit) = conicEqualAreaProjection() {
    scale = 155.424
    center = doubleArrayOf(0.0, 33.6442)

    parallels = doubleArrayOf(29.5, 45.5)
    scale = 1070.0
    translate = doubleArrayOf(480.0, 250.0)
    rotate = doubleArrayOf(96.0, 0.0)
    center = doubleArrayOf(-0.6, 38.7)
    init()
}

//export default function() {
//    return conicEqualArea()
//        .parallels([29.5, 45.5])
//        .scale(1070)
//        .translate([480, 250])
//        .rotate([96, 0])
//        .center([-0.6, 38.7]);
//}
package io.data2viz.geo.projection

import io.data2viz.math.EPSILON
import io.data2viz.math.deg

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
    parallels = arrayOf(29.5.deg, 45.5.deg)
    scale = 1070.0
    translate = doubleArrayOf(480.0, 250.0)
    rotate = arrayOf(96.0.deg, 0.0.deg)
    center = arrayOf((-0.6).deg, 38.7.deg)
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
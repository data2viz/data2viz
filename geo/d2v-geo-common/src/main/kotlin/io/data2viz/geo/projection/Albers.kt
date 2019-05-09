package io.data2viz.geo.projection

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

fun albers() = conicEqualAreaProjection().also {
    it.scale = 155.424
    it.center = doubleArrayOf(0.0, 33.6442)

    it.parallels = doubleArrayOf(29.5, 45.5)
    it.scale = 1070.0
    it.translate = doubleArrayOf(480.0, 250.0)
    it.rotate = doubleArrayOf(96.0, 0.0)
    it.center = doubleArrayOf(-0.6, 38.7)
}

//export default function() {
//    return conicEqualArea()
//        .parallels([29.5, 45.5])
//        .scale(1070)
//        .translate([480, 250])
//        .rotate([96, 0])
//        .center([-0.6, 38.7]);
//}
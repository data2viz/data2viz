package io.data2viz.geo.projection

import io.data2viz.geo.clip.clipAntimeridian
import io.data2viz.geo.clip.clipCircle
import io.data2viz.math.PI
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians

//import {degrees, pi, radians} from "../math";
//import {projectionMutator} from "./index";

class ConicProjection(projectAt: Projectable,
                      var phi0:Double = 0.0,
                      var phi1:Double = PI / 3.0): MutableProjection(projectAt) {


    var parallels: DoubleArray
    get() = doubleArrayOf(phi0.toDegrees(), phi1.toDegrees())
    set(value) {
        phi0 = value[0].toRadians()
        phi1 = value[1].toRadians()
    }

}

fun conicProjection(projection: Projectable, init: ConicProjection.() -> Unit) = ConicProjection(projection).apply(init)

//fun conicProjection(projectAt: Projectable) {
//
//    var mutableProjection = projection(projectAt) {
//
//    }
//    var projection = mutableProjection.project(phi0, phi1);
//
//    p.parallels = function(_) {
//        return arguments.length ? m(phi0 = _[0] * radians, phi1 = _[1] * radians) : [phi0 * degrees, phi1 * degrees];
//    };
//
//    return p;
//}

//import {degrees, pi, radians} from "../math";
//import {projectionMutator} from "./index";
//
//export function conicProjection(projectAt) {
//    var phi0 = 0,
//    var phi1 = pi / 3,
//    var m = projectionMutator(projectAt),
//    var p = m(phi0, phi1);
//
//    p.parallels = function(_) {
//        return arguments.length ? m(phi0 = _[0] * radians, phi1 = _[1] * radians) : [phi0 * degrees, phi1 * degrees];
//    };
//
//    return p;
//}
/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.interpolate

import io.data2viz.geom.Point
import io.data2viz.math.Percent
import io.data2viz.math.pct

public class PointComparatorX : Comparator<Point> {
    override fun compare(a: Point, b: Point): Int {
        return a.x.compareTo(b.x)
    }
}

public class PointComparatorY : Comparator<Point> {
    override fun compare(a: Point, b: Point): Int {
        return a.x.compareTo(b.x)
    }
}

public fun interpolatePoint(start: Point, end: Point): Interpolator<Point> {
    val diff = end - start
    return { percent -> start + diff * percent.value }
}

// TODO ?
//fun interpolateRound(start: Double, end: Double): Interpolator<Double> {
//    val diff = end - start
//    return { percent -> round(start + percent.value * diff) }
//}

// TODO : should do uninterpolate (precise only) / uninterpolateX (on x-axis) / uninterpolateY (on y-axis)
public fun uninterpolatePointOnX(start: Point, end: Point): UnInterpolator<Point> {
    val diff = end.x - start.x
    return if (diff != .0) { point -> Percent((point.x - start.x) / diff) } else { _ -> 0.pct }
}

public fun uninterpolatePointOnY(start: Point, end: Point): UnInterpolator<Point> {
    val diff = end.y - start.y
    return if (diff != .0) { point -> Percent((point.y - start.y) / diff) } else { _ -> 0.pct }
}

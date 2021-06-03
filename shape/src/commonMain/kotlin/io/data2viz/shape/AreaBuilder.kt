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

package io.data2viz.shape

import io.data2viz.geom.Path

public fun <D> areaBuilder(init: AreaBuilder<D>.() -> Unit): AreaBuilder<D> = AreaBuilder<D>().apply(init)

/**
 * The area generator produces an area, as in an area chart. An area is defined by two bounding lines, either splines
 * or polylines. Typically, the two lines share the same x-values (x0 = x1), differing only in y-value (y0 and y1);
 * most commonly, y0 is defined as a constant representing zero. The first line (the topline) is defined by x1 and y1
 * and is rendered first; the second line (the baseline) is defined by x0 and y0 and is rendered second, with the
 * points in reverse order. With a curveLinear curve, this produces a clockwise polygon.
 */
public class AreaBuilder<D> {

    /**
     * The type of curve used to draw the bounding lines of the area.
     */
    public var curve: (Path) -> Curve = curves.linear

    /**
     * X-value for the base-line, should take a Domain object and return a Double.
     */
    public var xBaseline: (D) -> Double = const(.0)

    /**
     * Y-value for the base-line, should take a Domain object and return a Double.
     */
    public var yBaseline: (D) -> Double = const(.0)

    /**
     * X-value for the top-line, should take a Domain object and return a Double.
     * If xTopline is the same as xBaseline you may let it null.
     */
    public var xTopline: ((D) -> Double)? = null

    /**
     * Y-value for the top-line, should take a Domain object and return a Double.
     * If yTopline is the same as yBaseline you may let it null.
     */
    public var yTopline: ((D) -> Double)? = null

    /**
     * This will indicate if a Domain value should be displayed.
     * If defined(d) returns true, the point is on the line.
     * If defined(d) returns false, the point is omitted.
     */
    public var defined: (D) -> Boolean = const(true)


    // TODO : implements ?
    /*fun arealine() {
        return line().defined(defined).curve(curve).path(path);
    }

    fun lineX0() { return lineY0()}

    fun lineY0() {
        return arealine().x(x0).y(y0)
    }

    fun lineY1() {
        return arealine().x(x0).y(y1)
    }

    fun lineX1() {
        return arealine().x(x1).y(y0)
    }*/

    /**
     * Use the data to generate an area on the path
     */
    public fun <C : Path> render(data: List<D>, path: C): C {
        val n = data.size

        val x0z = Array(n, { 0.0 })
        val y0z = Array(n, { 0.0 })

        var j = 0
        var defined0 = false
        val output = curve(path)

        for (i in 0..n) {
            val areaNotEnded = i < n
            val undefined = !(areaNotEnded && defined(data[i]))
            if (undefined == defined0) {
                defined0 = !defined0
                if (defined0) {
                    j = i
                    output.areaStart()
                    output.lineStart()
                } else {
                    output.lineEnd()
                    output.lineStart()
                    for (k in i - 1 downTo j) {
                        output.point(x0z[k], y0z[k])
                    }
                    output.lineEnd()
                    output.areaEnd()
                }
            }
            if (defined0) {
                val d = data[i]
                x0z[i] = xBaseline(d)
                y0z[i] = yBaseline(d)
                val outputX = if (xTopline != null) xTopline!!(d) else x0z[i]
                val outputY = if (yTopline != null) yTopline!!(d) else y0z[i]
                output.point(outputX, outputY)
            }
        }
        return path
    }
}

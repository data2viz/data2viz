/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

@file:Suppress("UNCHECKED_CAST")

package io.data2viz.shape

import io.data2viz.geom.PathGeom
import io.data2viz.geom.Path
import io.data2viz.geom.svgPath
import io.data2viz.test.TestBase

data class Point(val x: Int, val y: Int)

open class CurveTest(val curve: (Path) -> Curve, val defined: (Point) -> Boolean = const(true)) : TestBase() {

    fun pt(x: Int, y: Int) = Point(x, y)

    fun line(vararg points: Point): String {
        val lineGenerator = LineBuilder<Point>().apply {
            curve = this@CurveTest.curve
            defined = this@CurveTest.defined
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
        }
        val path = PathGeom()
        return lineGenerator.buildLine(listOf(*points), path).svgPath.round()
    }

}

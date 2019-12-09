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
import io.data2viz.geom.svgPath
import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class LineTest : TestBase() {

    @Test
    fun define() {
        assertEquals("M1,1ZM3,3L5,5".round(), line {
            x = { it.x.toDouble() }
            y = { it.y.toDouble() }
            defined = { it.x != 2 }
        }.toPath(Point(1, 1), Point(2, 2), Point(3, 3), Point(5, 5)).round())
    }


    @Test
    fun yConstant() {
        assertEquals("M0,5.5L0,5.5".round(), line {
            y = const(5.5)
        }.toPath(Point(1, 1), Point(2, 2)).round())
    }

    @Test
    fun xConstant() {
        assertEquals("M5.5,0L5.5,0".round(), line {
            x = const(5.5)
        }.toPath(Point(1, 1), Point(2, 2)).round())
    }


    data class Point(val x: Int, val y: Int)


    fun line(init: LineBuilder<Point>.() -> Unit): LineBuilder<Point> {
        return LineBuilder<Point>().apply(init)
    }

    private fun LineBuilder<Point>.toPath(vararg points: Point) =
            this.buildLine(listOf(*points), PathGeom()).svgPath
}
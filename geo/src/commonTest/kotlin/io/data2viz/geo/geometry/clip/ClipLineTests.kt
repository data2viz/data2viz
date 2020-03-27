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

package io.data2viz.geo.projection


import io.data2viz.geo.Point3D
import io.data2viz.geo.geometry.clip.ClipLineResult
import io.data2viz.geo.geometry.clip.clipLine
import io.data2viz.geom.Extent
import io.data2viz.test.TestBase
import kotlin.test.Test



class ClipLineTests : TestBase() {

    val extent = Extent(.0, .0, 10.0, 10.0)

    private fun pt(x: Double, y:Double) = Point3D(x, y)

    private fun testClipLine() {
        val ret = clipLine(lineToTest.a, lineToTest.b, extent)
        lineToTest = lineToTest.copy(clipped = ret)
    }

    internal data class LineAndResult(val a: Point3D, val b: Point3D, val clipped: ClipLineResult? = null)

    private lateinit var lineToTest: LineAndResult

    fun toTest(a:Point3D, b: Point3D) {
        lineToTest = LineAndResult(a, b)
    }

    private fun resultShouldBe(a: Point3D, b: Point3D) {
        lineToTest.clipped!!.a.x shouldBeClose a.x
        lineToTest.clipped!!.a.y shouldBeClose a.y
        lineToTest.clipped!!.b.x shouldBeClose b.x
        lineToTest.clipped!!.b.y shouldBeClose b.y
    }

    private fun resultShouldBeNull() {
        lineToTest.clipped shouldBe null
    }


    @Test
    fun clip_horizontal_east() {
        toTest(pt(-2.0, 2.0), pt(5.0, 2.0))
        testClipLine()
        resultShouldBe(pt(.0, 2.0), pt(5.0, 2.0))
    }

    @Test
    fun clip_horizontal_north() {
        toTest(pt(-2.0, -2.0), pt(12.0, -2.0))
        testClipLine()
        resultShouldBeNull()
    }

    @Test
    fun clip_horizontal_south() {
        toTest(pt(-2.0, 12.0), pt(12.0, 12.0))
        testClipLine()
        resultShouldBeNull()
    }

    @Test
    fun clip_horizontal_full() {
        toTest(pt(-2.0, 2.0), pt(12.0, 2.0))
        testClipLine()
        resultShouldBe(pt(.0, 2.0), pt(10.0, 2.0))
    }

    @Test
    fun clip_horizontal_west() {
        toTest(pt(5.0, 2.0), pt(12.0, 2.0))
        testClipLine()
        resultShouldBe(pt(5.0, 2.0), pt(10.0, 2.0))
    }

    @Test
    fun clip_corner_north_west() {
        toTest(pt(-2.0, 2.0), pt(4.0, -1.0))
        testClipLine()
        resultShouldBe(pt(0.0, 1.0), pt(2.0, 0.0))
    }
    @Test
    fun clip_corner_north_west1() {
        toTest(pt(1.0, 0.5), pt(4.0, -1.0))
        testClipLine()
        resultShouldBe(pt(1.0, 0.5), pt(2.0, 0.0))
    }

    @Test
    fun clip_corner_north_west2() {
        toTest(pt(-2.0, 2.0), pt(1.0, 0.5))
        testClipLine()
        resultShouldBe(pt(0.0, 1.0), pt(1.0, 0.5))
    }

    @Test
    fun clip_corner_south_west() {
        toTest(pt(-2.0, 8.0), pt(4.0, 11.0))
        testClipLine()
        resultShouldBe(pt(0.0, 9.0), pt(2.0, 10.0))
    }

    @Test
    fun clip_corner_south_west1() {
        toTest(pt(-2.0, 8.0), pt(1.0, 9.5))
        testClipLine()
        resultShouldBe(pt(0.0, 9.0), pt(1.0, 9.5))
    }

    @Test
    fun clip_corner_south_west2() {
        toTest(pt(1.0, 9.5), pt(4.0, 11.0))
        testClipLine()
        resultShouldBe(pt(1.0, 9.5), pt(2.0, 10.0))
    }

    @Test
    fun clip_corner_north_est() {
        toTest(pt(6.0, -1.0), pt(12.0, 2.0))
        testClipLine()
        resultShouldBe(pt(8.0, 0.0), pt(10.0, 1.0))
    }

    @Test
    fun clip_corner_north_est1() {
        toTest(pt(6.0, -1.0), pt(9.0, 0.5))
        testClipLine()
        resultShouldBe(pt(8.0, 0.0), pt(9.0, 0.5))
    }

    @Test
    fun clip_corner_north_est2() {
        toTest(pt(9.0, 0.5), pt(12.0, 2.0))
        testClipLine()
        resultShouldBe(pt(9.0, 0.5), pt(10.0, 1.0))
    }

    @Test
    fun clip_corner_south_est() {
        toTest(pt(6.0, 11.0), pt(12.0, 8.0))
        testClipLine()
        resultShouldBe(pt(8.0, 10.0), pt(10.0, 9.0))
    }

    @Test
    fun clip_corner_south_est1() {
        toTest(pt(6.0, 11.0), pt(9.0, 9.5))
        testClipLine()
        resultShouldBe(pt(8.0, 10.0), pt(9.0, 9.5))
    }

    @Test
    fun clip_corner_south_est2() {
        toTest(pt(9.0, 9.5), pt(12.0, 8.0))
        testClipLine()
        resultShouldBe(pt(9.0, 9.5), pt(10.0, 9.0))
    }

}
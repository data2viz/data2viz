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

package io.data2viz.contour

import io.data2viz.test.matchers.Matchers
import org.junit.Assert.assertEquals
import org.junit.Test

class ContourTests : Matchers {

    @Test
    fun area() {
        doubleArea(arrayOf(pt(0, 0), pt(1, 0), pt(1, 1), pt(0, 1))) shouldBe -2.0
        doubleArea(arrayOf(pt(0, 0), pt(0, 1), pt(1, 1), pt(1, 0))) shouldBe 2.0

        doubleArea(arrayOf(pt(1, 1), pt(1, 2), pt(2, 2), pt(2, 1))) shouldBe 2.0
        doubleArea(arrayOf(pt(1, 1), pt(1, 2), pt(3, 2), pt(3, 1))) shouldBe 4.0
        doubleArea(arrayOf(pt(1, 1), pt(1, 2), pt(4, 2), pt(4, 1))) shouldBe 6.0
        doubleArea(arrayOf(pt(1, 1), pt(1, 3), pt(4, 3), pt(4, 1))) shouldBe 12.0
        doubleArea(arrayOf(pt(2, 0), pt(3, 1), pt(2, 2), pt(1, 1))) shouldBe -4.0
    }

    @Test
    fun collinear() {
        collinear(pt(0, 0), pt(1, 1), pt(2, 2)) shouldBe true
        collinear(pt(0, 0), pt(1, 1), pt(2, 1)) shouldBe false
        collinear(pt(0, 0), pt(2, 2), pt(1, 1)) shouldBe true
    }

    @Test
    fun within() {
        within(1.0, 2.0, 3.0) shouldBe true
        within(3.0, 2.0, 1.0) shouldBe true
        within(1.0, 3.0, 2.0) shouldBe false

        within(-1.0, -2.0, -3.0) shouldBe true
        within(-3.0, -2.0, -1.0) shouldBe true
        within(-1.0, -3.0, -2.0) shouldBe false

        within(-1.0, 2.0, -3.0) shouldBe false
        within(-3.0, -2.0, 1.0) shouldBe true
        within(1.0, -2.0, 3.0) shouldBe false
        within(-1.0, .0, 3.0) shouldBe true
        within(1.0, .0, -3.0) shouldBe true
    }

    @Test
    fun segmentContains() {
        segmentContains(pt(0, 0), pt(1, 1), pt(2, 2)) shouldBe false
        segmentContains(pt(0, 0), pt(2, 2), pt(0, 0)) shouldBe true
        segmentContains(pt(0, 0), pt(2, 2), pt(1, 1)) shouldBe true
        segmentContains(pt(0, 0), pt(2, 2), pt(2, 2)) shouldBe true
        segmentContains(pt(0, 0), pt(2, 2), pt(2.0000000001, 2.0000000001)) shouldBe false
        segmentContains(pt(0, 0), pt(0, 2), pt(0, 1)) shouldBe true
    }

    @Test
    fun `ring contains`() {
        val diamond = listOf(pt(2, 0), pt(3, 1), pt(2, 2), pt(1, 1))
        ringContains(diamond, pt(2, 1)) shouldBe 1
        ringContains(diamond, pt(2.0, 0.1)) shouldBe 1
        ringContains(diamond, pt(2, 0)) shouldBe 0
        ringContains(diamond, pt(0, 0)) shouldBe -1
        ringContains(diamond, pt(3, 1)) shouldBe 0
    }

    @Test
    fun `check contains`() {
        // container is a 10x10 ring
        val container = listOf(pt(0, 0), pt(10, 0), pt(10, 10), pt(0, 10), pt(0, 0))

        // totally inside, no common points
        val ringInside = listOf(pt(5, 5), pt(5, 6), pt(6, 6), pt(6, 5), pt(5, 5))

        // totally outside, no common points
        val ringOutside = listOf(pt(11, 11), pt(11, 12), pt(12, 12), pt(12, 11), pt(11, 11))

        // ring "across" the container: partially inside, partially outside
        // this should NEVER happen due to the "contour" algorithm, there can't be contours (or holes) that intersect
        // val ringAcross = listOf(pt(5, 5), pt(15, 5), pt(15, 15), pt(5, 15), pt(5, 5))

        // totally inside, some common points
        val ringInsideOnBorder = listOf(pt(0, 0), pt(5, 0), pt(5, 5), pt(0, 5), pt(0, 0))

        // totally outside, some common points
        val ringOutsideOnBorder = listOf(pt(10, 10), pt(5, 10), pt(5, 15), pt(10, 15), pt(10, 10))

        contains(container, ringInside) shouldBe 1
        contains(container, ringOutside) shouldBe -1
        contains(container, ringInsideOnBorder) shouldBe 1
        contains(container, ringOutsideOnBorder) shouldBe -1
    }

    @Test
    fun `contours 1111`() {

        val result = simpleContour(2).contours(
            values(
                1, 1,
                1, 1
            )
        )

        val expected = arrayOf(
            pt(2.0, 1.5),
            pt(2.0, 0.5),
            pt(1.5, 0.0),
            pt(0.5, 0.0),
            pt(0.0, 0.5),
            pt(0.0, 1.5),
            pt(0.5, 2.0),
            pt(1.5, 2.0),
            pt(2.0, 1.5)
        )

        // only 1 threshold -> 1 GeoJson
        result.size shouldBe 1

        // only one contour (the exterior line)
        val multiRings = result[0].coordinates
        multiRings.size shouldBe 1

        // only 1 ring here, there is no hole "inside" the external contour
        val rings = multiRings[0]
        rings.size shouldBe 1

        val ring = rings[0]
        checkValues(expected, ring)
    }

    @Test
    fun `contours(values) returns the expected result for an empty polygon`() {

        val result = simpleContour(2).contours(
                values(
                        0, 1,
                        0, 1
                ))[0].coordinates[0][0]


        val expected = arrayOf(
            pt(2.0, 1.5),
            pt(2.0, 0.5),
            pt(1.5, 0.0),
            pt(1.0, 0.5),
            pt(1.0, 1.5),
            pt(1.5, 2.0),
            pt(2.0, 1.5)
        )

        checkValues(expected, result)
    }

    @Test
    fun `contours(values) returns the expected result for a simple polygon`() {
        val contours = simpleContour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val result = contours[0].coordinates[0][0]

        val expected = arrayOf(
                pt(6.0, 7.5),
                pt(6.0, 6.5),
                pt(6.0, 5.5),
                pt(6.0, 4.5),
                pt(6.0, 3.5),
                pt(5.5, 3.0),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.5, 8.0),
                pt(6.0, 7.5)
        )
        checkValues(expected, result)
    }

    @Test
    fun `contours(values) with different thresholds returns the expected result for a simple polygon`() {
        val contours = contour {
            size(10, 10)
            thresholds = { arrayOf(0.2, 0.4, 0.6, 0.8) }
        }.contours(
            values(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            ))

        val expected = arrayOf(
            pt(6.0, 7.5),
            pt(6.0, 6.5),
            pt(6.0, 5.5),
            pt(6.0, 4.5),
            pt(6.0, 3.5),
            pt(5.5, 3.0),
            pt(4.5, 3.0),
            pt(3.5, 3.0),
            pt(3.0, 3.5),
            pt(3.0, 4.5),
            pt(3.0, 5.5),
            pt(3.0, 6.5),
            pt(3.0, 7.5),
            pt(3.5, 8.0),
            pt(4.5, 8.0),
            pt(5.5, 8.0),
            pt(6.0, 7.5)
        )

        checkValues(expected, contours[0].coordinates[0][0])
        checkValues(expected, contours[1].coordinates[0][0])
        checkValues(expected, contours[2].coordinates[0][0])
        checkValues(expected, contours[3].coordinates[0][0])
    }

    @Test
    fun `contours(values) should draw the external contour if all values are superior to threshold`() {
        val contours = contour {
            size(2, 2)
            thresholds = { arrayOf(.0) }
        }.contours(
            arrayOf(
                .1, .1,
                .1, .1,
            )
        )

        val expected = arrayOf(
            pt(2.0, 1.5),
            pt(2.0, 0.5),
            pt(1.5, 0.0),
            pt(0.5, 0.0),
            pt(0.0, 0.5),
            pt(0.0, 1.5),
            pt(0.5, 2.0),
            pt(1.5, 2.0),
            pt(2.0, 1.5)
        )

        contours.size shouldBe 1
        contours[0].value shouldBeClose .0
        contours[0].coordinates.size shouldBe 1
        checkValues(expected, contours[0].coordinates[0][0])
    }

    @Test
    fun `contours(values) with different thresholds returns the expected results`() {
        val contours = contour {
            size(5, 5)
            thresholds = { arrayOf(0.2, 0.4, 0.6) }
        }.contours(
            arrayOf(
                .1, .1, .1, .1, .1,
                .1, .3, .3, .3, .1,
                .1, .3, .5, .3, .1,
                .1, .3, .3, .3, .1,
                .1, .1, .1, .1, .1
            )
        )

        val expected02 = arrayOf(
            pt(4.0, 3.5),
            pt(4.0, 2.5),
            pt(4.0, 1.5),
            pt(3.5, 1.0),
            pt(2.5, 1.0),
            pt(1.5, 1.0),
            pt(1.0, 1.5),
            pt(1.0, 2.5),
            pt(1.0, 3.5),
            pt(1.5, 4.0),
            pt(2.5, 4.0),
            pt(3.5, 4.0),
            pt(4.0, 3.5)
        )
        val expected04 = arrayOf(
            pt(3.0, 2.5),
            pt(2.5, 2.0),
            pt(2.0, 2.5),
            pt(2.5, 3.0),
            pt(3.0, 2.5)
        )

        // 3 thresholds --> 3 contours
        contours.size shouldBe 3

        contours[0].value shouldBeClose 0.2
        checkValues(expected02, contours[0].coordinates[0][0])

        contours[1].value shouldBeClose 0.4
        checkValues(expected04, contours[1].coordinates[0][0])

        contours[2].value shouldBeClose 0.6
        contours[2].coordinates.size shouldBe 0
    }

    @Test
    fun `contours smooth(false)(values) returns the expected result for a simple polygon`() {
        val contours = simpleContour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                        0, 0, 0, 1, 2, 1, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                        0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val result = contours[0].coordinates[0][0]

        val expected = arrayOf(
                pt(6.0, 7.5),
                pt(6.0, 6.5),
                pt(6.0, 5.5),
                pt(6.0, 4.5),
                pt(6.0, 3.5),
                pt(5.5, 3.0),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.5, 8.0),
                pt(6.0, 7.5)
        )
        checkValues(expected, result)
    }


    @Test
    fun `contours(values) returns the expected result for a polygon with a hole`() {
        val contours = simpleContour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val extern = contours[0].coordinates[0][0]
        val hole = contours[0].coordinates[0][1]

        val expected = arrayOf(
                pt(6.0, 7.5),
                pt(6.0, 6.5),
                pt(6.0, 5.5),
                pt(6.0, 4.5),
                pt(6.0, 3.5),
                pt(5.5, 3.0),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.5, 8.0),
                pt(6.0, 7.5)
        )
        val expectedHole = arrayOf(
                pt(4.5, 7.0),
                pt(4.0, 6.5),
                pt(4.0, 5.5),
                pt(4.0, 4.5),
                pt(4.5, 4.0),
                pt(5.0, 4.5),
                pt(5.0, 5.5),
                pt(5.0, 6.5),
                pt(4.5, 7.0)
        )
        checkValues(expected, extern)
        checkValues(expectedHole, hole)
    }

    @Test
    fun `contours(values) returns the expected results for the inverted previous test`() {
        val contours = simpleContour(10).contours(
            values(
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1, 1,
                1, 1, 1, 0, 1, 0, 1, 1, 1, 1,
                1, 1, 1, 0, 1, 0, 1, 1, 1, 1,
                1, 1, 1, 0, 1, 0, 1, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1
            ))

        val extern = contours[0].coordinates[0][0]
        val exp2 = contours[0].coordinates[1][0]
        val exp2Hole = contours[0].coordinates[1][1]

        val expected1 = arrayOf(
            pt(5.0, 6.5),
            pt(5.0, 5.5),
            pt(5.0, 4.5),
            pt(4.5, 4.0),
            pt(4.0, 4.5),
            pt(4.0, 5.5),
            pt(4.0, 6.5),
            pt(4.5, 7.0),
            pt(5.0, 6.5)
        )
        val expected2 = arrayOf(
             pt(10.0, 9.5),
             pt(10.0, 8.5),
             pt(10.0, 7.5),
             pt(10.0, 6.5),
             pt(10.0, 5.5),
             pt(10.0, 4.5),
             pt(10.0, 3.5),
             pt(10.0, 2.5),
             pt(10.0, 1.5),
             pt(10.0, 0.5),
             pt(9.5, .0),
             pt(8.5, .0),
             pt(7.5, .0),
             pt(6.5, .0),
             pt(5.5, .0),
             pt(4.5, .0),
             pt(3.5, .0),
             pt(2.5, .0),
             pt(1.5, .0),
             pt(0.5, .0),
             pt(.0, 0.5),
             pt(.0, 1.5),
             pt(.0, 2.5),
             pt(.0, 3.5),
             pt(.0, 4.5),
             pt(.0, 5.5),
             pt(.0, 6.5),
             pt(.0, 7.5),
             pt(.0, 8.5),
             pt(.0, 9.5),
             pt(0.5, 10.0),
             pt(1.5, 10.0),
             pt(2.5, 10.0),
             pt(3.5, 10.0),
             pt(4.5, 10.0),
             pt(5.5, 10.0),
             pt(6.5, 10.0),
             pt(7.5, 10.0),
             pt(8.5, 10.0),
             pt(9.5, 10.0),
             pt(10.0, 9.5)
        )
        val expected2Hole = arrayOf(
            pt(5.5, 8.0),
            pt(4.5, 8.0),
            pt(3.5, 8.0),
            pt(3.0, 7.5),
            pt(3.0, 6.5),
            pt(3.0, 5.5),
            pt(3.0, 4.5),
            pt(3.0, 3.5),
            pt(3.5, 3.0),
            pt(4.5, 3.0),
            pt(5.5, 3.0),
            pt(6.0, 3.5),
            pt(6.0, 4.5),
            pt(6.0, 5.5),
            pt(6.0, 6.5),
            pt(6.0, 7.5),
            pt(5.5, 8.0)
        )

        checkValues(expected1, extern)
        checkValues(expected2, exp2)
        checkValues(expected2Hole, exp2Hole)
    }

    @Test
    fun `contours(values) returns the expected result for a multipolygon`() {
        val contours = simpleContour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val extern1 = contours[0].coordinates[0][0]
        val extern2 = contours[0].coordinates[1][0]

        val expected1 = arrayOf(
                pt(5.0, 7.5),
                pt(5.0, 6.5),
                pt(5.0, 5.5),
                pt(5.0, 4.5),
                pt(5.0, 3.5),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.0, 7.5)
        )

        val expected2 = arrayOf(
                pt(7.0, 7.5),
                pt(7.0, 6.5),
                pt(7.0, 5.5),
                pt(7.0, 4.5),
                pt(7.0, 3.5),
                pt(6.5, 3.0),
                pt(6.0, 3.5),
                pt(6.0, 4.5),
                pt(6.0, 5.5),
                pt(6.0, 6.5),
                pt(6.0, 7.5),
                pt(6.5, 8.0),
                pt(7.0, 7.5)
        )
        checkValues(expected1, extern1)
        checkValues(expected2, extern2)
    }

    @Test
    fun `contours(values) returns the expected result for a multipolygon with 4 holes`() {
        val contours = simpleContour(7).contours(
            values(
                0, 0, 0, 0, 0, 0, 0,
                0, 1, 1, 1, 1, 1, 0,
                0, 1, 0, 1, 0, 1, 0,
                0, 1, 1, 1, 1, 1, 0,
                0, 1, 0, 1, 0, 1, 0,
                0, 1, 1, 1, 1, 1, 0,
                0, 0, 0, 0, 0, 0, 0
            )
        )

        // 1 threshold means 1 contour
        contours.size shouldBe 1

        // There should be 1 MultiRing (only one "external" contour)
        val multiRings = contours[0].coordinates
        multiRings.size shouldBe 1

        // This MultiRing is composed of 5 rings
        val multiRing = multiRings[0]
        multiRing.size shouldBe 5

        // the first ring should be the external one
        multiRing[0].size shouldBe 21

        // the 4 others rings should be the 4 "holes" inside the main ring
        multiRing[1].size shouldBe 5
        multiRing[2].size shouldBe 5
        multiRing[3].size shouldBe 5
        multiRing[4].size shouldBe 5
    }

    @Test
    fun `contours(values) returns the expected result for a multipolygon with holes`() {
        val contours = simpleContour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 1, 1, 0, 1, 1, 1, 0, 0,
                        0, 1, 0, 1, 0, 1, 0, 1, 0, 0,
                        0, 1, 1, 1, 0, 1, 1, 1, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val expected1 = arrayOf(
              pt(4.0, 5.5),
              pt(4.0, 4.5),
              pt(4.0, 3.5),
              pt(3.5, 3.0),
              pt(2.5, 3.0),
              pt(1.5, 3.0),
              pt(1.0, 3.5),
              pt(1.0, 4.5),
              pt(1.0, 5.5),
              pt(1.5, 6.0),
              pt(2.5, 6.0),
              pt(3.5, 6.0),
              pt(4.0, 5.5)
        )
        val expectedHole1 = arrayOf(
                pt(2.5, 5.0),
                pt(2.0, 4.5),
                pt(2.5, 4.0),
                pt(3.0, 4.5),
                pt(2.5, 5.0)
        )
        val expected2 = arrayOf(
                pt(8.0, 5.5),
                pt(8.0, 4.5),
                pt(8.0, 3.5),
                pt(7.5, 3.0),
                pt(6.5, 3.0),
                pt(5.5, 3.0),
                pt(5.0, 3.5),
                pt(5.0, 4.5),
                pt(5.0, 5.5),
                pt(5.5, 6.0),
                pt(6.5, 6.0),
                pt(7.5, 6.0),
                pt(8.0, 5.5)
        )
        val expectedHole2 = arrayOf(
                pt(6.5, 5.0),
                pt(6.0, 4.5),
                pt(6.5, 4.0),
                pt(7.0, 4.5),
                pt(6.5, 5.0)
        )


        val geoJson = contours[0]

        // there should be 2 polygons
        geoJson.coordinates.size shouldBe 2

        // the first polygon only contains an external envelope and a hole
        val polygon1 = geoJson.coordinates[0]
        val extern1 = polygon1[0]
        val hole1 = polygon1[1]

        polygon1.size shouldBe 2
        checkValues(expected1, extern1)
        checkValues(expectedHole1, hole1)

        // the second polygon only contains an external envelope and a hole
        val polygon2 = geoJson.coordinates[1]
        val extern2 = polygon2[0]
        val hole2 = polygon2[1]

        polygon2.size shouldBe 2
        checkValues(expected2, extern2)
        checkValues(expectedHole2, hole2)
    }


    private fun pt(x: Double, y: Double) = RingPoint(x, y)
    private fun pt(x: Int, y: Int) = RingPoint(x.toDouble(), y.toDouble())


    private fun checkValues(expected: Array<RingPoint>, result: Array<RingPoint>) {
        assertEquals(expected.size, result.size)
        val zip = expected.zip(result)
        zip.forEach {
            assertEquals(it.first.x, it.second.x, .000001)
            assertEquals(it.first.y, it.second.y, .000001)
        }
    }


    private fun values(vararg vals: Int) = vals
            .map { it.toDouble() }
            .toTypedArray()

//    @Test
//    fun `empty polygon`() {
//
//    }


    private fun simpleContour(size: Int) = contour {
        size(size, size)
        thresholds = { arrayOf(0.5) }
    }


//    @Test
//    fun `goldsteinPrice with 30000 values`(){
//        fun goldsteinPrice(x : Double, y: Double): Double  =
//                (1 + Math.pow(x + y + 1, 2.0) * (19 - 14 * x + 3 * x * x - 14 * y + 6 * x * x + 3 * y * y)) *
//                        (30 + Math.pow(2 * x - 3 * y, 2.0) * (18 - 32 * x + 12 * x * x + 48 * y - 36 * x * y + 27 * y * y))
//
//
//        val n = 240
//        val m = 125
//        var k = 0
//        val values = arrayOfNulls<Double>(n*m)
//        for (j in 0 until m)
//            for (i in 0 until n){
//                values[k++] = goldsteinPrice((i+ .5) / n * 4 - 2, 1 - (j+ .5) / m * 3)
//            }
//
//
//        val contours = contour {
//            size(n, m)
//            this.thresholds = {(1..21).map { Math.pow(2.0, it.toDouble()) }.toTypedArray()}
//        }
//    }

    @Test
    fun `simplexNoise random values and contours check 6x6`(){
        val result = contour {
            size(6, 6)
            thresholds = { arrayOf(0.4, 0.6) }
        }.contours(
            arrayOf(0.5,0.5,0.502882857873381,0.5369738211385282,0.6145285171827292,0.6916987091024387,0.298231965,
                0.31414124838151636,0.352946215063879,0.4087580838147531,0.49253044713935457,0.5804077946413501,
                0.18264707670631303,0.20790016590953952,0.25384837253172887,0.30978339761371565,0.387258607824023,
                0.4829003513397903,0.14811944248851577,0.18654648967979914,0.21898072984398076,0.250528547816806,
                0.31377329638934737,0.4170486516903804,0.12615730455516466,0.17909028479861383,0.2079219129425514,
                0.2151328088569393,0.2587354813542429,0.3751744054325384,0.08301200079400378,0.13860657290602874,
                0.1721708071991646,0.17849411149835193,0.21456921302763649,0.3464217431863919
            )
        )

        val expected1 = arrayOf(
            pt(6.0, 3.5),
            pt(6.0, 2.5),
            pt(6.0, 1.5),
            pt(6.0, 0.5),
            pt(5.5, 0.0),
            pt(4.5, 0.0),
            pt(3.5, 0.0),
            pt(2.5, 0.0),
            pt(1.5, 0.0),
            pt(0.5, 0.0),
            pt(0.0, 0.5),
            pt(0.5, 1.0),
            pt(1.5, 1.0),
            pt(2.5, 1.0),
            pt(3.0, 1.5),
            pt(3.5, 2.0),
            pt(4.5, 2.0),
            pt(5.0, 2.5),
            pt(5.0, 3.5),
            pt(5.5, 4.0),
            pt(6.0, 3.5)
        )
        val expected2 = arrayOf(
            pt(6.0, 0.5),
            pt(5.5, 0.0),
            pt(4.5, 0.0),
            pt(4.0, 0.5),
            pt(4.5, 1.0),
            pt(5.5, 1.0),
            pt(6.0, 0.5)
        )

        result[0].coordinates.size shouldBe 1
        checkValues(expected1, result[0].coordinates[0][0])

        result[1].coordinates.size shouldBe 1
        checkValues(expected2, result[1].coordinates[0][0])
    }

    @Test
    fun `simplexNoise random values and contours check 8x8 single threshold`(){
        val result = contour {
            size(8, 8)
            thresholds = { arrayOf(0.6) }
        }.contours(
            arrayOf(0.5,0.5,0.502882857873381,0.5369738211385282,0.6145285171827292,0.6916987091024387,
                0.7055875470024386,0.6267084570553255,0.298231965,0.31414124838151636,0.352946215063879,
                0.4087580838147531,0.49253044713935457,0.5804077946413501,0.623738882352182,0.5944719607436638,
                0.18264707670631303,0.20790016590953952,0.25384837253172887,0.30978339761371565,0.387258607824023,
                0.4829003513397903,0.5623467876635349,0.5928593312107928,0.14811944248851577,0.18654648967979914,
                0.21898072984398076,0.250528547816806,0.31377329638934737,0.4170486516903804,0.5364127119953963,
                0.6276103167100674,0.12615730455516466,0.17909028479861383,0.2079219129425514,0.2151328088569393,
                0.2587354813542429,0.3751744054325384,0.5384865537339909,0.6819691706491647,0.08301200079400378,
                0.13860657290602874,0.1721708071991646,0.17849411149835193,0.21456921302763649,0.3464217431863919,
                0.5470211812965052,0.7258833743115971,0.041957514008907515,0.08392907699949159,0.12364437040208481,
                0.14880172556635185,0.20024862788349523,0.33941967958255137,0.5491408521483306,0.7364335418278741,
                0.0683235356681905,0.08545783009160524,0.1277604260082677,0.17143459477691753,0.23263601258720573,
                0.3575225014136292,0.5436621919458642,0.7084281809582962
            )
        )

        // 1 thresholds > 1 geoJson
        result.size shouldBe 1

        val geoJson = result[0]
        val multiRings = geoJson.coordinates

        // 2 contour lines...
        multiRings.size shouldBe 2

        // .. each without hole..
        multiRings[0].size shouldBe 1
        multiRings[1].size shouldBe 1

        // .. each made of 13 points
        multiRings[0][0].size shouldBe 13
        multiRings[1][0].size shouldBe 13
    }

    @Test
    fun `simplexNoise random values and contours check 8x8`(){
        val result = contour {
            size(8, 8)
            thresholds = { arrayOf(0.4, 0.6) }
        }.contours(
            arrayOf(0.5,0.5,0.502882857873381,0.5369738211385282,0.6145285171827292,0.6916987091024387,
                0.7055875470024386,0.6267084570553255,0.298231965,0.31414124838151636,0.352946215063879,
                0.4087580838147531,0.49253044713935457,0.5804077946413501,0.623738882352182,0.5944719607436638,
                0.18264707670631303,0.20790016590953952,0.25384837253172887,0.30978339761371565,0.387258607824023,
                0.4829003513397903,0.5623467876635349,0.5928593312107928,0.14811944248851577,0.18654648967979914,
                0.21898072984398076,0.250528547816806,0.31377329638934737,0.4170486516903804,0.5364127119953963,
                0.6276103167100674,0.12615730455516466,0.17909028479861383,0.2079219129425514,0.2151328088569393,
                0.2587354813542429,0.3751744054325384,0.5384865537339909,0.6819691706491647,0.08301200079400378,
                0.13860657290602874,0.1721708071991646,0.17849411149835193,0.21456921302763649,0.3464217431863919,
                0.5470211812965052,0.7258833743115971,0.041957514008907515,0.08392907699949159,0.12364437040208481,
                0.14880172556635185,0.20024862788349523,0.33941967958255137,0.5491408521483306,0.7364335418278741,
                0.0683235356681905,0.08545783009160524,0.1277604260082677,0.17143459477691753,0.23263601258720573,
                0.3575225014136292,0.5436621919458642,0.7084281809582962
            )
        )

        // 2 thresholds > 2 geoJson
        result.size shouldBe 2

        ///////////////// First threshold 0.4 //////////////////////

        val geoJson1 = result[0]
        val multiRings1 = geoJson1.coordinates

        // one contour line...
        multiRings1.size shouldBe 1

        // .. without hole..
        multiRings1[0].size shouldBe 1

        // .. and made of 33 points
        multiRings1[0][0].size shouldBe 33


        ///////////////// Second threshold 0.6 //////////////////////

        val geoJson2 = result[1]
        val multiRings2 = geoJson2.coordinates

        // 2 contour lines...
        multiRings2.size shouldBe 2

        // .. each without hole..
        multiRings2[0].size shouldBe 1
        multiRings2[1].size shouldBe 1

        // .. each made of 13 points
        multiRings2[0][0].size shouldBe 13
        multiRings2[1][0].size shouldBe 13
    }

    @Test
    fun `simplexNoise random values and contours check 10x10`(){
        val result = contour {
            size(10, 10)
            thresholds = { arrayOf(0.4, 0.6) }
        }.contours(
            arrayOf(0.5,0.5,0.502882857873381,0.5369738211385282,0.6145285171827292,0.6916987091024387,
                0.7055875470024386,0.6267084570553255,0.4843589070441124,0.3421730266587188,0.298231965,
                0.31414124838151636,0.352946215063879,0.4087580838147531,0.49253044713935457,0.5804077946413501,
                0.623738882352182,0.5944719607436638,0.5058906375595049,0.3852501054601786,0.18264707670631303,
                0.20790016590953952,0.25384837253172887,0.30978339761371565,0.387258607824023,0.4829003513397903,
                0.5623467876635349,0.5928593312107928,0.5548303116482021,0.437339277554763,0.14811944248851577,
                0.18654648967979914,0.21898072984398076,0.250528547816806,0.31377329638934737,0.4170486516903804,
                0.5364127119953963,0.6276103167100674,0.6316898444208745,0.5086394960729775,0.12615730455516466,
                0.17909028479861383,0.2079219129425514,0.2151328088569393,0.2587354813542429,0.3751744054325384,
                0.5384865537339909,0.6819691706491647,0.719930698481196,0.5958964016624432,0.08301200079400378,
                0.13860657290602874,0.1721708071991646,0.17849411149835193,0.21456921302763649,0.3464217431863919,
                0.5470211812965052,0.7258833743115971,0.7905269347690888,0.6807391006897793,0.041957514008907515,
                0.08392907699949159,0.12364437040208481,0.14880172556635185,0.20024862788349523,0.33941967958255137,
                0.5491408521483306,0.7364335418278741,0.8141108200034313,0.7267225905107523,0.0683235356681905,
                0.08545783009160524,0.1277604260082677,0.17143459477691753,0.23263601258720573,0.3575225014136292,
                0.5436621919458642,0.7084281809582962,0.7670813765921035,0.680264922733395,0.19556656046502352,
                0.18088497864380132,0.2105782997451957,0.2523546057953821,0.3046167236435923,0.39757041079842137,
                0.5342308565617573,0.6447440577915102,0.6539580462355488,0.5475132518102512,0.3586669580092629,
                0.3047753929591012,0.31284658231568624,0.3489948708052999,0.39503799727255773,0.46050394267823463,
                0.5391818365729534,0.5761286316652915,0.5317124409236006,0.41433258621516855
            )
        )

        result[0].coordinates.size shouldBe 1
        result[0].coordinates[0][0].size shouldBe 43

        result[1].coordinates.size shouldBe 2
        result[1].coordinates[0][0].size shouldBe 13
        result[1].coordinates[1][0].size shouldBe 19
    }

    @Test
    fun `simplexNoise random values and contours check 20x20`(){
        val result = contour {
            size(20, 20)
            thresholds = { arrayOf(0.4, 0.6) }
        }.contours(
            arrayOf(0.5, 0.5, 0.500000106608248, 0.5011044833077226, 0.5107925371211025, 0.5369738211385282,
                0.5800701506675079, 0.632093467807453, 0.679503269481029, 0.7077092115737081, 0.7055875470024389,
                0.6688308636038429, 0.601401395801831, 0.5148036416403599, 0.42421364431015635, 0.34217302665871896,
                0.27926625855144677, 0.2443494464510182, 0.24268379450866534, 0.27308580943837, 0.37248937160248063,
                0.37614826599809026, 0.3866490530350799, 0.40244047604786826, 0.42455286481953664,
                0.45822636009511736, 0.5050434379344205, 0.5595998889967823, 0.6109231219878962, 0.6462948176754484,
                0.6555672129801817, 0.6344988810739148, 0.586072951778524, 0.5192022826289556, 0.4434261039453887,
                0.3677172008974653, 0.3033992029621493, 0.2617944054399468, 0.25024720709022386,
                0.27035336311527847, 0.266458393373083, 0.2733021207653946, 0.2922404513860253, 0.3184813997316255,
                0.34868225195493685, 0.38580114707188135, 0.43301796338922005, 0.4879749055117512,
                0.5425917102642276, 0.5860883724376367, 0.6090252915521814, 0.6065931056095991, 0.5798062879280437,
                0.5328910649713512, 0.4688034862472122, 0.39449729151136304, 0.32330979332737864,
                0.27060920252883175, 0.24757461880429882, 0.2577009557297628, 0.1973232011303252,
                0.20712274948984788, 0.22922838004620644, 0.2579872243066178, 0.28998200771714056,
                0.32624702807951966, 0.37088001923147973, 0.42438722245901783, 0.4816245149307892,
                0.5337804954663287, 0.5719386820666604, 0.590157487221047, 0.5863362502325606, 0.5581087930844224,
                0.5025953917735111, 0.4255615058203688, 0.3427606625110653, 0.27422080623014095, 0.2363574070584204,
                0.23627625848699024, 0.1629994677561895, 0.17792178522874813, 0.20045804333769235,
                0.22470576750742777, 0.25083005543214343, 0.28183234088368636, 0.32157740505177645,
                0.37249900730609764, 0.43205244197736076, 0.4932985485573958, 0.5476603195392956,
                0.5874971816343769, 0.6063668027347948, 0.5949333082361793, 0.5456387508842974, 0.46337614064655835,
                0.3661734437409169, 0.2786567461837255, 0.2229738997355703, 0.21131766017461667, 0.1481194424885156,
                0.17008040275241137, 0.19423225110604908, 0.21355894834885103, 0.2296869158301384,
                0.25052854781680584, 0.28344467994829137, 0.33153572316188545, 0.39391946550312246,
                0.4650894876389138, 0.5364127119953962, 0.5977747404121985, 0.6376728337616278, 0.6410184846819041,
                0.5966477996088363, 0.5086394960729778, 0.39683551974843234, 0.2899228468789904,
                0.21554912122643993, 0.1914014371934169, 0.13686957975973862, 0.1657665991026721,
                0.1929287857064153, 0.21009668772783674, 0.21858245820263805, 0.2281317248152171,
                0.25192028147833023, 0.2973752187526061, 0.36400555834568665, 0.44661283591255313,
                0.5355552344385823, 0.6171231357265521, 0.6754521592105877, 0.6917121997269825, 0.6521326409556563,
                0.5599400178814908, 0.43614077053449435, 0.31257363926737736, 0.22150887567058464,
                0.18552810414456633, 0.11928737502365283, 0.15279157580552377, 0.1829497613344223,
                0.20056637133607458, 0.20591630141827244, 0.2084587531811904, 0.22384020559603174,
                0.2662976738358733, 0.3385411738742294, 0.4341022198659471, 0.5403873839991901, 0.6394896393769247,
                0.7130786389651975, 0.7406368406930622, 0.7068040347203163, 0.6138923918027388, 0.48327412297188643,
                0.34867264593759784, 0.24545317723431037, 0.19946821077074006, 0.09301008892910156,
                0.12724129126691863, 0.1589230827374159, 0.17840333182445195, 0.18482415659341767,
                0.1863643121819762, 0.19865832034141429, 0.23955414590059504, 0.3175072961882628, 0.425174184014642,
                0.545703615715471, 0.6583379168668422, 0.7434585223042971, 0.780725097466067, 0.7542474661922904,
                0.6653249417024005, 0.5347035203261187, 0.39629252284248595, 0.2863990407495407,
                0.23226841312359015, 0.06324876514397954, 0.0938166197103637, 0.12482876960978923,
                0.14660629710211975, 0.15730827419688465, 0.16342217866626713, 0.17905161914181378,
                0.22226335373784933, 0.30497293629864203, 0.42010476075891434, 0.5487943088145166,
                0.669188823605765, 0.7610342985345087, 0.805609698890204, 0.787605284303667, 0.7069359910571541,
                0.5821788975138158, 0.4457578140216341, 0.3337018768829778, 0.2738640987979169, 0.04195751400890746,
                0.0649522684066774, 0.09322047799378491, 0.11725742060516398, 0.1342948219851401,
                0.1488017255663519, 0.17213618461632518, 0.21974680666471957, 0.30356237652050067,
                0.4195363254247078, 0.5491408521483303, 0.670405902804708, 0.7631231040152096, 0.8108874332230132,
                0.7994225947877933, 0.7267225905107524, 0.6092298152392253, 0.4776838926116934, 0.36702529891687496,
                0.30464527485631887, 0.04529865795574489, 0.05791522870205967, 0.08184317376292138,
                0.10758802818303626, 0.13111949034124826, 0.15419802621538314, 0.1842966752807242,
                0.23350144436665038, 0.3134992036334642, 0.42354181866544893, 0.5466988553886611,
                0.6618310637187333, 0.7491952344524144, 0.7928079483990725, 0.7808418375234967,
                0.7117349084043973, 0.6005738649070823, 0.47588427149481505, 0.3702805536873572,
                0.30928911934666836, 0.08663784687829246, 0.08722006335762228, 0.10510564177687642,
                0.13025497011834242, 0.15682632613061182, 0.1838709140529453, 0.21581664777334753,
                0.2621195630456649, 0.3335818145739682, 0.4316541301153562, 0.541785472652406, 0.6442808766680351,
                0.7188158550937771, 0.7495195685018072, 0.7290269689335043, 0.6587181926902462, 0.5529141766329808,
                0.43723067675345434, 0.34058772026386397, 0.2851632175103965, 0.16417689112182005,
                0.15190953690923248, 0.1612556056309445, 0.18184649235920924, 0.20677247929425624,
                0.23317370964913897, 0.26293991155108154, 0.3029894848124614, 0.36264522528469467,
                0.44426084135807903, 0.5357947977062905, 0.6188182513316005, 0.6736295579244936, 0.6859768227602997,
                0.6519708144356781, 0.5775418931522421, 0.4770751322532441, 0.3727824580796632, 0.28870517233505566,
                0.24238295861366094, 0.262132983181671, 0.2358077378158271, 0.2340194985505606, 0.24726464770829115,
                0.2681600048521808, 0.2922191631458103, 0.31891361396624496, 0.3525074346893955,
                0.40021519807131023, 0.4639158465859282, 0.5330051881624894, 0.5907030690231851, 0.6208962764261273,
                0.6133333725441075, 0.5663831105592954, 0.48825570123955675, 0.394376164559966, 0.3036494369256181,
                0.234580387670712, 0.19928293698903327, 0.3586669580092628, 0.3168936948460399, 0.3025938745636215,
                0.30800727819817725, 0.3255179013971786, 0.3489948708052998, 0.37536836993935513, 0.405961733181547,
                0.4451759716696227, 0.4930128152676618, 0.5391818365729533, 0.5698764608013883, 0.5742066859528865,
                0.547405436690903, 0.4914417488410912, 0.4143325862151687, 0.32985776781310217, 0.2542667708385842,
                0.20077935813873854, 0.17615250744733063, 0.4329133801277585, 0.3756159385186824,
                0.3490941349251956, 0.34822798117581055, 0.3653777487459975, 0.39267646646417603,
                0.42428792693169276, 0.4581979656410204, 0.49519613956761416, 0.532153120178035, 0.5590683114785516,
                0.5659830534542896, 0.547663827101141, 0.5050965369683019, 0.44438397769133475, 0.3742096527640626,
                0.304042363430864, 0.2448559915531594, 0.2056758761752247, 0.18923811092723392, 0.47111122877393075,
                0.4006254587010186, 0.36371689902692383, 0.35923049998058776, 0.38001066542541384,
                0.416531571599575, 0.46002450708775416, 0.5047161846954542, 0.54692352545393, 0.5798521657257202,
                0.594344394477213, 0.5843819560337317, 0.5500119437565817, 0.49743208252684146, 0.43654631567706004,
                0.3770523988235423, 0.325150697533619, 0.28413116767883984, 0.257534072135371, 0.2462949638990088,
                0.46870841952487224, 0.39070000327764237, 0.34688708008735564, 0.34166315853164786,
                0.36950702306423483, 0.41974268415723087, 0.4807488338447204, 0.542664201688975, 0.5966212658933807,
                0.6326239909189096, 0.6426512343110459, 0.624174390690454, 0.5816682031047096, 0.5255418114963032,
                0.468682813056225, 0.4216030144093772, 0.38821627170437123, 0.36557633450111393,
                0.35070079107526114, 0.3440390651107734, 0.42920066624074704, 0.3524152277097652,
                0.3076191723771946, 0.3046689837130988, 0.3415011669349397, 0.40751208238446235, 0.4887729678963568,
                0.5712566581235438, 0.640928212755287, 0.6854929504068683, 0.6978674011968546, 0.6782874223832931,
                0.6346078224769208, 0.5802965513858991, 0.5302662918546365, 0.4954875529257055, 0.4783833039247575,
                0.47231673496412274, 0.46930798949047675, 0.46795361872591235)
        )

        result[0].coordinates.size shouldBe 2
        result[0].coordinates[0][0].size shouldBe 13
        result[0].coordinates[1][0].size shouldBe 95

        result[1].coordinates.size shouldBe 3
        result[1].coordinates[0][0].size shouldBe 19
        result[1].coordinates[1][0].size shouldBe 35
        result[1].coordinates[2][0].size shouldBe 15
    }

}

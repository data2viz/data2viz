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

package io.data2viz.geom

import io.data2viz.test.TestBase
import kotlin.test.Test


@Suppress("FunctionName")
class RectTests : TestBase() {

    @Test
    fun rectConstructByPoints() {
        val rect = RectGeom(from = Point(200.0, 100.0), to = Point(20.0, 10.0))
        rect.x shouldBeClose 20.0
        rect.y shouldBeClose 10.0
        rect.width shouldBeClose 180.0
        rect.height shouldBeClose 90.0
    }

    @Test
    fun rectConstructByPointAndSize() {
        val rect = RectGeom(Point(20.0, 10.0), Size(180.0, 90.0))
        rect.x shouldBeClose 20.0
        rect.y shouldBeClose 10.0
        rect.width shouldBeClose 180.0
        rect.height shouldBeClose 90.0
    }

    @Test
    fun rectPoints(){
        val rect = RectGeom(10.0, 20.0, 30.0, 40.0)

        rect.top shouldBeClose 20.0
        rect.left shouldBeClose 10.0
        rect.right shouldBeClose 10.0 + 30.0
        rect.bottom shouldBeClose 20.0 + 40.0

        rect.topLeft shouldBe Point(10.0, 20.0)
        rect.topRight shouldBe Point(40.0, 20.0)
        rect.bottomLeft shouldBe Point(10.0, 60.0)
        rect.bottomRight shouldBe Point(40.0, 60.0)

        rect.center shouldBe Point(25.0, 40.0)
    }

    @Test
    fun rectSize() {
        val rect = RectGeom(10.0, 20.0, 30.0, 40.0)
        rect.size shouldBe Size(30.0, 40.0)
        rect.size = Size(10.0, 20.0)
        rect.width shouldBeClose 10.0
        rect.height shouldBeClose 20.0
    }

    @Test
    fun rectContains(){
        val rect = RectGeom(10.0, 10.0, 10.0, 10.0)
        (Point(10.0, 9.99) in rect) shouldBe false
        (Point(9.99, 10.0) in rect) shouldBe false
        (Point(20.01, 10.0) in rect) shouldBe false
        (Point(10.0, 20.01) in rect) shouldBe false

        (Point(10.0, 10.0) in rect) shouldBe true
        (Point(10.0, 20.0) in rect) shouldBe true
        (Point(20.0, 10.0) in rect) shouldBe true
        (Point(20.0, 20.0) in rect) shouldBe true

        // EQUIVALENT
        (RectGeom(10.0, 10.0, 10.0, 10.0) in rect) shouldBe true
        (rect.contains(RectGeom(10.0, 10.0, 10.0, 10.0))) shouldBe true

        (RectGeom(10.0, 9.99999, 10.0, 10.0) in rect) shouldBe false
        (RectGeom(9.9999, 10.0, 10.0, 10.0) in rect) shouldBe false
        (RectGeom(10.0, 10.0, 10.00001, 10.0) in rect) shouldBe false
        (RectGeom(10.0, 10.0, 10.0, 10.0001) in rect) shouldBe false
    }
}

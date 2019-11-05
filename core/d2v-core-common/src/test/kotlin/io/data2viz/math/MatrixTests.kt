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

package io.data2viz.math


import io.data2viz.geom.*
import io.data2viz.test.TestBase
import kotlin.test.Test


class MatrixTests : TestBase() {

    @Test
    fun default() {
        val matrix = Matrix()
        matrix.isIdentity() shouldBe true
        val default = Matrix(1.0, 0.0, 0.0, 1.0, 0.0, 0.0)
        matrix shouldEqual default
        matrix.translate(point(10, 10)) shouldEqual Matrix(1.0, 0.0, 0.0, 1.0, 10.0, 10.0)
        matrix.reset() shouldEqual default
    }

    @Test
    fun translate() {
        val matrix = Matrix()
        matrix.translate(point(1, 0)) shouldEqual Matrix(1.0, 0.0, 0.0, 1.0, 1.0, 0.0)
        matrix.translate(point(1, 0)) shouldEqual Matrix(1.0, 0.0, 0.0, 1.0, 2.0, 0.0)
        matrix.transform(point(0, 0)) shouldEqual point(2, 0)
    }

    @Test
    fun scale() {
        val matrix = Matrix()
        matrix.scale(2.0) shouldEqual Matrix(2.0, 0.0, 0.0, 2.0, 0.0, 0.0)
        matrix.transform(point(10, 10)) shouldEqual point(20, 20)
        matrix.scale(2.0, point(1, 1)) shouldEqual Matrix(4.0, 0.0, 0.0, 4.0, -2.0, -2.0)
        matrix.transform(point(10, 10)) shouldBeClose point(38, 38)
        matrix.inverseTransform(point(38, 38)) shouldBeClose point(10, 10)
    }

    @Test
    fun rotate() {
        val matrix = Matrix()
        matrix.rotate(PI_ANGLE / 2) shouldBeClose Matrix(.0, 1.0, -1.0, 0.0, 0.0, 0.0)
        matrix.transform(point(0, 0)) shouldEqual point(0, 0)
        matrix.transform(point(10, 0)) shouldBeClose  point(0, 10)

        matrix.reset()
        matrix
            .rotate(PI_ANGLE / 2)
            .rotate(PI_ANGLE / 2) shouldBeClose Matrix().rotate(PI_ANGLE)

        matrix.reset()
        matrix
            .rotate(PI_ANGLE / 2)
            .rotate(-PI_ANGLE / 2) shouldBeClose Matrix()

        matrix.reset()
        matrix
            .rotate(PI_ANGLE / 2)
            .rotate(-PI_ANGLE / 2, point(10, 10))
        matrix shouldBeClose Matrix().translate(point(-20, 0))
    }

    @Test
    fun append(){
        Matrix()
            .translate(10.0, 10.0)
            .append(Matrix().scale(2.0))
            .append(Matrix().translate(10.0, 10.0)) shouldBeClose
                Matrix(2.0, .0, .0, 2.0, 30.0, 30.0)

    }


    private infix fun Matrix.shouldBeClose(matrix: Matrix) {
        asArray shouldBeClose matrix.asArray
    }

    private infix fun Point.shouldBeClose(other: Point) {
        asArray shouldBeClose other.asArray
    }

}



private val Matrix.asArray: Array<Double>
    get() = arrayOf(a, b, c, d, tx, ty)

private val Point.asArray: Array<Double>
    get() = arrayOf(x,y)


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

import io.data2viz.test.TestBase
import kotlin.test.Test



class ContourJsTests : TestBase() {

    fun p(x: Number, y: Number) = arrayOf(x.toDouble(), y.toDouble())


    @Test
    fun collinear() {
        collinear(p(0, 0), p(1, 1), p(2, 2)) shouldBe true
        collinear(p(0, 0), p(1, 1), p(2, 1)) shouldBe false
        collinear(p(0, 0), p(2, 2), p(1, 1)) shouldBe true
    }
}






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
class SizeTests : TestBase() {

    @Test
    fun sizeOperator(){
        val size = Size(10.0, 20.0)
        size + 5.0 shouldBe Size(15.0, 25.0)
        size - 5.0 shouldBe Size( 5.0, 15.0)
        size * 3.0 shouldBe Size(30.0, 60.0)
        size / 2.0 shouldBe Size( 5.0, 10.0)
        size % 3.0 shouldBe Size( 1.0, 2.0)

        size + Size(2.0, 3.0) shouldBe Size( 12.0, 23.0)
        size - Size(2.0, 3.0) shouldBe Size(  8.0, 17.0)
        size * Size(2.0, 3.0) shouldBe Size( 20.0, 60.0)
        size / Size(2.0, 4.0) shouldBe Size(  5.0, 5.0)
        size % Size(4.0, 6.0) shouldBe Size(  2.0, 2.0)

    }
}

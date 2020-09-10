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

package io.data2viz.time

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class DeprecatedDateTests : TestDate() {


    @Test
    fun test_deprecated_date_constructor() {
        date(2020) shouldBe LocalDateTime(2020, 1, 1, 0, 0)
        date(2020, 1, 1) shouldBe LocalDateTime(2020, 1, 1, 0, 0)

        var catchedExceptionMonth = false
        var catchedExceptionDay = false
        try {
            date(2020, 0, 1)
        } catch (e:IllegalArgumentException) {
            catchedExceptionMonth = true
        }
        catchedExceptionMonth shouldBe true

        try {
            date(2020, 1, 0)
        } catch (e:IllegalArgumentException) {
            catchedExceptionDay = true
        }
        catchedExceptionDay shouldBe true

        date(2020, 12, 10, 11, 9, 8) shouldBe LocalDateTime(2020, 12, 10, 11, 9, 8)
    }


}
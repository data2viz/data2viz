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

package io.data2viz.time

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class DeprecatedDateTests : TestDate() {

    @Test
    fun test_deprecated_date_constructor() {
        date(2020) shouldBe LocalDateTime(2020, 1, 1, 0, 0)
        date(2020, 1, 1) shouldBe LocalDateTime(2020, 1, 1, 0, 0)

        var caughtExceptionMonth = false
        var caughtExceptionDay = false
        try {
            date(2020, 0, 1)
        } catch (e:IllegalArgumentException) {
            caughtExceptionMonth = true
        }
        caughtExceptionMonth shouldBe true

        try {
            date(2020, 1, 0)
        } catch (e:IllegalArgumentException) {
            caughtExceptionDay = true
        }
        caughtExceptionDay shouldBe true

        date(2020, 12, 10, 11, 9, 8) shouldBe LocalDateTime(2020, 12, 10, 11, 9, 8)
    }

    @Test
    fun test_deprecated_date_functions() {
        val date = date(2020)
        date.year() shouldBe 2020
        date.month() shouldBe 1
        date.dayOfYear() shouldBe 1
        date.dayOfMonth() shouldBe 1
        date.hour() shouldBe 0
        date.minute() shouldBe 0
        date.second() shouldBe 0
        date.millisecond() shouldBe 0

        val date2 = date(2017, 8, 6, 14, 22, 53, 997)
        date2.year() shouldBe 2017
        date2.month() shouldBe 8
        date2.dayOfYear() shouldBe 218
        date2.dayOfMonth() shouldBe 6
        date2.hour() shouldBe 14
        date2.minute() shouldBe 22
        date2.second() shouldBe 53
        date2.millisecond() shouldBe 997

        date2.isBefore(date) shouldBe true
    }
}

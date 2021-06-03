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

package io.data2viz.interpolate

import io.data2viz.math.*
import io.data2viz.test.TestBase
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DateIntepolatorTests : TestBase() {

    val now = Clock.System.now()
    val future = now + 12.toDuration(DurationUnit.SECONDS)


    @Test
    fun interpolateInstant() {
        val f = interpolateDate(now, future)
        f(25.pct) shouldBe (now + 3.toDuration(DurationUnit.SECONDS))
    }

    @Test
    fun uninterpolate() {
        val f = uninterpolateDate(now, future)
        f(now + 3.toDuration(DurationUnit.SECONDS)).value shouldBeClose 0.25
    }
}

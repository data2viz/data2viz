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

package io.data2viz.timeFormat

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class LocaleTest : TestDate() {

    
    @Test
    fun timeFormat_specifier_defaults_to_en_US() {
        format("%c")(LocalDateTime(2000, 4, 1, 0, 0)) shouldBe "4/1/2000, 12:00:00 AM"
        format("%c")(LocalDateTime(2000, 4, 1, 23, 16, 4)) shouldBe "4/1/2000, 11:16:04 PM"
    }

    
    @Test
    fun timeFormat_specifier_for_locale_frFr() {
        val localeFr = Locale(Locales.fr_FR())
        localeFr.format("%c")(LocalDateTime(2000, 1, 1, 0, 0)) shouldBe "samedi, le  1 janvier 2000, 00:00:00"
    }

    
    @Test
    fun timeParse_specifier_defaults_to_en_US() {
        parse("%c")("4/1/2000, 12:00:00 AM") shouldBe LocalDateTime(2000, 4, 1, 0, 0)
        parse("%c")("4/1/2000, 11:16:04 PM") shouldBe LocalDateTime(2000, 4, 1, 23, 16, 4)
    }

    
    @Test
    fun timeParse_specifier_for_locale_frFr() {
        val localeFr = Locale(Locales.fr_FR())
        localeFr.parse("%c")("samedi, le  1 janvier 2000, 00:00:00") shouldBe LocalDateTime(2000, 1, 1, 0, 0)
        localeFr.parse("%c")("dimanche, le  9 janvier 2000, 20:4:12") shouldBe LocalDateTime(2000, 1, 9, 20, 4, 12)
        localeFr.parse("%c")("dimanche, le  9 janvier 2000, 20:04:12") shouldBe LocalDateTime(2000, 1, 9, 20, 4, 12)
    }
}

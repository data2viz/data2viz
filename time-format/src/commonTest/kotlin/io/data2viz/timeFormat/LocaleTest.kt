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

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test

class LocaleTest : TestDate() {

    private fun date(y: Int, mo: Int, d: Int, h: Int = 0, mi:Int = 0, s:Int = 0, ms: Int = 0) =
        LocalDateTime(y, mo, d, h, mi, s, ms).toInstant(TimeZone.UTC)

    
    @Test
    fun timeFormat_specifier_defaults_to_en_US() {
        format("%c")(date(2000, 4, 1, 0, 0)) shouldBe "4/1/2000, 12:00:00 AM"
        format("%c")(date(2000, 4, 1, 23, 16, 4)) shouldBe "4/1/2000, 11:16:04 PM"
    }

    
    @Test
    fun timeFormat_specifier_for_locale_frFr() {
        val localeFr = Locale(Locales.fr_FR())
        localeFr.format("%c")(date(2000, 1, 1, 0, 0)) shouldBe "samedi, le  1 janvier 2000, 00:00:00"
    }

    
    @Test
    fun timeParse_specifier_defaults_to_en_US() {
        parse("%c")("4/1/2000, 12:00:00 AM") shouldBe date(2000, 4, 1, 0, 0)
        parse("%c")("4/1/2000, 11:16:04 PM") shouldBe date(2000, 4, 1, 23, 16, 4)
    }

    
    @Test
    fun timeParse_specifier_for_locale_frFr() {
        val localeFr = Locale(Locales.fr_FR())
        localeFr.parse("%c")("samedi, le  1 janvier 2000, 00:00:00") shouldBe date(2000, 1, 1, 0, 0)
        localeFr.parse("%c")("dimanche, le  9 janvier 2000, 20:4:12") shouldBe date(2000, 1, 9, 20, 4, 12)
        localeFr.parse("%c")("dimanche, le  9 janvier 2000, 20:04:12") shouldBe date(2000, 1, 9, 20, 4, 12)
    }

    @Test
    fun locale_formats() {
        val localeFr = Locale(Locales.fr_FR())
        val date = Instant.parse("2011-10-05T14:48:20.300Z")

        localeFr.format(localeFr.locale_dateTime)(date) shouldBe "mercredi, le  5 octobre 2011, 14:48:20"
        localeFr.format(localeFr.locale_date)(date) shouldBe "05/10/2011"
        localeFr.format(localeFr.locale_time)(date) shouldBe "14:48:20"

        defaultLocale.format(defaultLocale.locale_dateTime)(date) shouldBe "10/5/2011, 2:48:20 PM"
        defaultLocale.format(defaultLocale.locale_date)(date) shouldBe "10/5/2011"
        defaultLocale.format(defaultLocale.locale_time)(date) shouldBe "2:48:20 PM"
    }
}

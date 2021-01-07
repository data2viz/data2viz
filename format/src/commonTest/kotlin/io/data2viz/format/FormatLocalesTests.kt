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

@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatLocalesTests : TestBase() {
    @Test
    fun arabic_locales() {
        Locales.ar_001.formatter("$,.2f")(-1234.56)   shouldBe "-١٬٢٣٤٫٥٦"
        Locales.ar_AE .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ د.إ."
        Locales.ar_BH .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ د.ب."
        Locales.ar_DJ .formatter("$,.2f")(1234.56)    shouldBe "\u200fFdj ١٬٢٣٤٫٥٦"
        Locales.ar_DZ .formatter("$,.2f")(1234.56)    shouldBe "د.ج. 1.234,56"
        Locales.ar_EG .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ج.م."
        Locales.ar_EH .formatter("$,.2f")(1234.56)    shouldBe "د.م. 1,234.56"
        Locales.ar_ER .formatter("$,.2f")(1234.56)    shouldBe "Nfk ١٬٢٣٤٫٥٦"
        Locales.ar_IL .formatter("$,.2f")(1234.56)    shouldBe "₪ ١٬٢٣٤٫٥٦"
        Locales.ar_IQ .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ د.ع."
        Locales.ar_JO .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ د.أ."
        Locales.ar_KM .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ف.ج.ق."
        Locales.ar_KW .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ د.ك."
        Locales.ar_LB .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ل.ل."
        Locales.ar_MA .formatter("$,.2f")(1234.56)    shouldBe "د.م. 1.234,56"
        Locales.ar_MR .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ أ.م."
        Locales.ar_OM .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ر.ع."
        Locales.ar_PS .formatter("$,.2f")(1234.56)    shouldBe "₪ ١٬٢٣٤٫٥٦"
        Locales.ar_QA .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ر.ق."
        Locales.ar_SA .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ر.س."
        Locales.ar_SD .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ج.س."
        Locales.ar_SO .formatter("$,.2f")(1234.56)    shouldBe "‏S ١٬٢٣٤٫٥٦"
        Locales.ar_SS .formatter("$,.2f")(1234.56)    shouldBe "£ ١٬٢٣٤٫٥٦"
        Locales.ar_SY .formatter("$,.2f")(1234.56)    shouldBe "١٬٢٣٤٫٥٦ ل.س."
        Locales.ar_TD .formatter("$,.2f")(1234.56)    shouldBe "\u200fFCFA ١٬٢٣٤٫٥٦"
        Locales.ar_TN .formatter("$,.2f")(1234.56)    shouldBe "د.ت. 1.234,56"
    }

    @Test
    fun frFR_locale() {
        Locales.fr_FR.formatter("$,.2f")(12345678.90) shouldBe "12 345 678,90 €"
    }

}

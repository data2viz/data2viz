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

@file:Suppress("unused")

package io.data2viz.format

internal val arabicNumerals = arrayOf("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669")

public data class Locale(
        var decimalSeparator: String = ".",
        var grouping: List<Int> = listOf(3),
        var groupSeparator: String = ",",
        var currency: List<String> = listOf("$", ""),
        var numerals: Array<String>? = null,
        var percent: String = "%")


@Suppress("FunctionName")
public class Locales {

    public companion object {
        public fun locale(
                decimalSeparator: String = ".",
                groupSeparator: String = ",",
                currency: List<String> = listOf("$", ""),
                numerals: Array<String>? = null,
                grouping: List<Int> = listOf(3),
                percent: String = "%"
        ): Locale = Locale(decimalSeparator, grouping, groupSeparator, currency, numerals, percent)

        public fun ar_001():Locale = locale("\u066b", "\u066c", currency = listOf("", ""), numerals = arabicNumerals)
        public fun ar_AE(): Locale = ar_001().copy(currency = listOf("", " \u062f\u002e\u0625\u002e"))
        public fun ar_BH(): Locale = ar_001().copy(currency = listOf("", " \u062f\u002e\u0628\u002e"))
        public fun ar_DJ(): Locale = ar_001().copy(currency = listOf("\u200f\u0046\u0064\u006a ", ""))
        public fun ar_DZ(): Locale = locale("\u002c", "\u002e", currency = listOf("\u062f\u002e\u062c\u002e ", ""))
        public fun ar_EG(): Locale = ar_001().copy(currency = listOf("", " \u062c\u002e\u0645\u002e"))
        public fun ar_EH(): Locale = locale("\u002e", "\u002c", currency = listOf("\u062f\u002e\u0645\u002e ", ""))
        public fun ar_ER(): Locale = ar_001().copy(currency = listOf("\u004e\u0066\u006b ", ""))
        public fun ar_IL(): Locale = ar_001().copy(currency = listOf("\u20aa ", ""))
        public fun ar_IQ(): Locale = ar_001().copy(currency = listOf("", " \u062f\u002e\u0639\u002e"))
        public fun ar_JO(): Locale = ar_001().copy(currency = listOf("", " \u062f\u002e\u0623\u002e"))
        public fun ar_KM(): Locale = ar_001().copy(currency = listOf("", " \u0641\u002e\u062c\u002e\u0642\u002e"))
        public fun ar_KW(): Locale = ar_001().copy(currency = listOf("", " \u062f\u002e\u0643\u002e"))
        public fun ar_LB(): Locale = ar_001().copy(currency = listOf("", " \u0644\u002e\u0644\u002e"))
        public fun ar_LY(): Locale = ar_001().copy(decimalSeparator = "\u002c", groupSeparator = "\u002e", currency = listOf("\u062f\u002e\u0644\u002e ", ""))
        public fun ar_MA(): Locale = locale("\u002c", groupSeparator = "\u002e", currency = listOf("\u062f\u002e\u0645\u002e ", ""))
        public fun ar_MR(): Locale = ar_001().copy(currency = listOf("",  " \u0623\u002e\u0645\u002e" ))
        public fun ar_OM(): Locale = ar_001().copy(currency = listOf("", " \u0631\u002e\u0639\u002e"))
        public fun ar_PS(): Locale = ar_001().copy(currency = listOf("\u20aa ", ""))
        public fun ar_QA(): Locale = ar_001().copy(currency = listOf("", " \u0631\u002e\u0642\u002e"))
        public fun ar_SA(): Locale = ar_001().copy(currency = listOf("", " \u0631\u002e\u0633\u002e"))
        public fun ar_SD(): Locale = ar_001().copy(currency = listOf("", " \u062c\u002e\u0633\u002e"))
        public fun ar_SO(): Locale = ar_001().copy(currency = listOf("\u200f\u0053 ", ""))
        public fun ar_SS(): Locale = ar_001().copy(currency = listOf("\u00a3 ", ""))
        public fun ar_SY(): Locale = ar_001().copy(currency = listOf("", " \u0644\u002e\u0633\u002e"))
        public fun ar_TD(): Locale = ar_001().copy(currency = listOf("\u200f\u0046\u0043\u0046\u0041 ", ""))
        public fun ar_TN(): Locale = locale("\u002c", "\u002e", currency = listOf("\u062f\u002e\u062a\u002e ", ""))
        public fun ar_YE(): Locale = ar_001().copy(currency = listOf("", " \u0631\u002e\u0649\u002e"))
        public fun ca_ES(): Locale = locale(".", ".", listOf("", "\u00a0€"))
        public fun cs_CZ(): Locale = locale(",", "\u00a0", listOf("", "\u00a0Kč"))
        public fun de_CH(): Locale = locale(",", "'", listOf("", "\u00a0CHF"))
        public fun de_DE(): Locale = locale(",", ".", listOf("", "\u00a0€"))
        public fun en_CA(): Locale = locale(".", ",", listOf("$", ""))
        public fun en_GB(): Locale = locale(".", ",", listOf("£", ""))
        public fun en_IN(): Locale = locale(".", ",", listOf("₹", ""), grouping = listOf(3, 2, 2, 2, 2, 2, 2, 2, 2, 2))
        public fun en_US(): Locale = locale(".", ",", listOf("$", ""))
        public fun es_ES(): Locale = locale(",", ".", listOf("", "\u00a0€"))
        public fun es_MX(): Locale = locale(".", ",", listOf("$", ""))
        public fun fi_FI(): Locale = locale(",", "\u00a0", listOf("", "\u00a0€"))
        public fun fr_CA(): Locale = locale(",", "\u00a0", listOf("", "$"))
        public fun fr_FR(): Locale = locale(",", "\u00a0", listOf("", "\u00a0€"))
        public fun he_IL(): Locale = locale(".", ",", listOf("₪", ""))
        public fun hu_HU(): Locale = locale(",", "\u00a0", listOf("", "\u00a0Ft"))
        public fun it_IT(): Locale = locale(",", ".", listOf("€", ""))
        public fun ja_JP(): Locale = locale(".", ",", listOf("", "円"))
        public fun ko_KR(): Locale = locale(",", "", listOf("₩", ""))
        public fun mk_MK(): Locale = locale(",", ".", listOf("", "\u00a0ден."))
        public fun nl_NL(): Locale = locale(",", ".", listOf("€\u00a0", ""))
        public fun pl_PL(): Locale = locale(",", ".", listOf("", "zł"))
        public fun pt_BR(): Locale = locale(",", ".", listOf("R$", ""))
        public fun ru_RU(): Locale = locale(",", "\u00a0", listOf("", "\u00a0руб."))
        public fun sv_SE(): Locale = locale(",", "\u00a0", listOf("", "SEK"))
        public fun uk_UA(): Locale = locale(",", "\u00a0", listOf("", "\u00a0₴."))
        public fun zh_CN(): Locale = locale(".", ",", listOf("¥", ""))
    }
}

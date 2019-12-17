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

data class Locale(
        var decimalSeparator: String = ".",
        var grouping: List<Int> = listOf(3),
        var groupSeparator: String = ",",
        var currency: List<String> = listOf("$", ""),
        var numerals: Array<String>? = null,
        var percent: String = "%")


@Suppress("FunctionName")
class Locales {

    companion object {
        fun locale(
                decimalSeparator: String = ".",
                groupSeparator: String = ",",
                currency: List<String> = listOf("$", ""),
                numerals: Array<String>? = null,
                grouping: List<Int> = listOf(3),
                percent: String = "%"
        ) = Locale(decimalSeparator, grouping, groupSeparator, currency, numerals, percent)

        fun ar_001() = locale("\u066b", "\u066c", currency = listOf("", ""), numerals = arabicNumerals)
        fun ar_AE() = ar_001().copy(currency = listOf("", " \u062f\u002e\u0625\u002e"))
        fun ar_BH() = ar_001().copy(currency = listOf("", " \u062f\u002e\u0628\u002e"))
        fun ar_DJ() = ar_001().copy(currency = listOf("\u200f\u0046\u0064\u006a ", ""))
        fun ar_DZ() = locale("\u002c", "\u002e", currency = listOf("\u062f\u002e\u062c\u002e ", ""))
        fun ar_EG() = ar_001().copy(currency = listOf("", " \u062c\u002e\u0645\u002e"))
        fun ar_EH() = locale("\u002e", "\u002c", currency = listOf("\u062f\u002e\u0645\u002e ", ""))
        fun ar_ER() = ar_001().copy(currency = listOf("\u004e\u0066\u006b ", ""))
        fun ar_IL() = ar_001().copy(currency = listOf("\u20aa ", ""))
        fun ar_IQ() = ar_001().copy(currency = listOf("", " \u062f\u002e\u0639\u002e"))
        fun ar_JO() = ar_001().copy(currency = listOf("", " \u062f\u002e\u0623\u002e"))
        fun ar_KM() = ar_001().copy(currency = listOf("", " \u0641\u002e\u062c\u002e\u0642\u002e"))
        fun ar_KW() = ar_001().copy(currency = listOf("", " \u062f\u002e\u0643\u002e"))
        fun ar_LB() = ar_001().copy(currency = listOf("", " \u0644\u002e\u0644\u002e"))
        fun ar_LY() = ar_001().copy(decimalSeparator = "\u002c", groupSeparator = "\u002e", currency = listOf("\u062f\u002e\u0644\u002e ", ""))
        fun ar_MA() = locale("\u002c", groupSeparator = "\u002e", currency = listOf("\u062f\u002e\u0645\u002e ", ""))
        fun ar_MR() = ar_001().copy(currency = listOf("",  " \u0623\u002e\u0645\u002e" ))
        fun ar_OM() = ar_001().copy(currency = listOf("", " \u0631\u002e\u0639\u002e"))
        fun ar_PS() = ar_001().copy(currency = listOf("\u20aa ", ""))
        fun ar_QA() = ar_001().copy(currency = listOf("", " \u0631\u002e\u0642\u002e"))
        fun ar_SA() = ar_001().copy(currency = listOf("", " \u0631\u002e\u0633\u002e"))
        fun ar_SD() = ar_001().copy(currency = listOf("", " \u062c\u002e\u0633\u002e"))
        fun ar_SO() = ar_001().copy(currency = listOf("\u200f\u0053 ", ""))
        fun ar_SS() = ar_001().copy(currency = listOf("\u00a3 ", ""))
        fun ar_SY() = ar_001().copy(currency = listOf("", " \u0644\u002e\u0633\u002e"))
        fun ar_TD() = ar_001().copy(currency = listOf("\u200f\u0046\u0043\u0046\u0041 ", ""))
        fun ar_TN() = locale("\u002c", "\u002e", currency = listOf("\u062f\u002e\u062a\u002e ", ""))
        fun ar_YE() = ar_001().copy(currency = listOf("", " \u0631\u002e\u0649\u002e"))
        fun ca_ES() = locale(".", ".", listOf("", "\u00a0€"))
        fun cs_CZ() = locale(",", "\u00a0", listOf("", "\u00a0Kč"))
        fun de_CH() = locale(",", "'", listOf("", "\u00a0CHF"))
        fun de_DE() = locale(",", ".", listOf("", "\u00a0€"))
        fun en_CA() = locale(".", ",", listOf("$", ""))
        fun en_GB() = locale(".", ",", listOf("£", ""))
        fun en_IN() = locale(".", ",", listOf("₹", ""), grouping = listOf(3, 2, 2, 2, 2, 2, 2, 2, 2, 2))
        fun en_US() = locale(".", ",", listOf("$", ""))
        fun es_ES() = locale(",", ".", listOf("", "\u00a0€"))
        fun es_MX() = locale(".", ",", listOf("$", ""))
        fun fi_FI() = locale(",", "\u00a0", listOf("", "\u00a0€"))
        fun fr_CA() = locale(",", "\u00a0", listOf("", "$"))
        fun fr_FR() = locale(",", "\u00a0", listOf("", "\u00a0€"))
        fun he_IL() = locale(".", ",", listOf("₪", ""))
        fun hu_HU() = locale(",", "\u00a0", listOf("", "\u00a0Ft"))
        fun it_IT() = locale(",", ".", listOf("€", ""))
        fun ja_JP() = locale(".", ",", listOf("", "円"))
        fun ko_KR() = locale(",", "", listOf("₩", ""))
        fun mk_MK() = locale(",", ".", listOf("", "\u00a0ден."))
        fun nl_NL() = locale(",", ".", listOf("€\u00a0", ""))
        fun pl_PL() = locale(",", ".", listOf("", "zł"))
        fun pt_BR() = locale(",", ".", listOf("R$", ""))
        fun ru_RU() = locale(",", "\u00a0", listOf("", "\u00a0руб."))
        fun sv_SE() = locale(",", "\u00a0", listOf("", "SEK"))
        fun uk_UA() = locale(",", "\u00a0", listOf("", "\u00a0₴."))
        fun zh_CN() = locale(".", ",", listOf("¥", ""))
    }
}

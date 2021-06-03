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

@file:Suppress("unused")

package io.data2viz.format

internal val arabicNumerals = arrayOf("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669")

public data class Locale(
    var decimalSeparator: String = ".",
    var groupSeparator: String = ",",
    var currency: List<String> = listOf("$", ""),
    var numerals: Array<String>? = null,
    var grouping: List<Int> = listOf(3),
    var percent: String = "%"
)

public class Locales {
    public companion object {
        public val ar_001:Locale by lazy { Locale("\u066b", "\u066c", currency = listOf("", ""), numerals = arabicNumerals) }
        public val ar_AE: Locale by lazy { ar_001.copy(currency = listOf("", " \u062f\u002e\u0625\u002e")) }
        public val ar_BH: Locale by lazy { ar_001.copy(currency = listOf("", " \u062f\u002e\u0628\u002e")) }
        public val ar_DJ: Locale by lazy { ar_001.copy(currency = listOf("\u200f\u0046\u0064\u006a ", "")) }
        public val ar_DZ: Locale by lazy { Locale("\u002c", "\u002e", currency = listOf("\u062f\u002e\u062c\u002e ", "")) }
        public val ar_EG: Locale by lazy { ar_001.copy(currency = listOf("", " \u062c\u002e\u0645\u002e")) }
        public val ar_EH: Locale by lazy { Locale("\u002e", "\u002c", currency = listOf("\u062f\u002e\u0645\u002e ", "")) }
        public val ar_ER: Locale by lazy { ar_001.copy(currency = listOf("\u004e\u0066\u006b ", "")) }
        public val ar_IL: Locale by lazy { ar_001.copy(currency = listOf("\u20aa ", "")) }
        public val ar_IQ: Locale by lazy { ar_001.copy(currency = listOf("", " \u062f\u002e\u0639\u002e")) }
        public val ar_JO: Locale by lazy { ar_001.copy(currency = listOf("", " \u062f\u002e\u0623\u002e")) }
        public val ar_KM: Locale by lazy { ar_001.copy(currency = listOf("", " \u0641\u002e\u062c\u002e\u0642\u002e")) }
        public val ar_KW: Locale by lazy { ar_001.copy(currency = listOf("", " \u062f\u002e\u0643\u002e")) }
        public val ar_LB: Locale by lazy { ar_001.copy(currency = listOf("", " \u0644\u002e\u0644\u002e")) }
        public val ar_LY: Locale by lazy { ar_001.copy(decimalSeparator = "\u002c", groupSeparator = "\u002e", currency = listOf("\u062f\u002e\u0644\u002e ", "")) }
        public val ar_MA: Locale by lazy { Locale("\u002c", groupSeparator = "\u002e", currency = listOf("\u062f\u002e\u0645\u002e ", "")) }
        public val ar_MR: Locale by lazy { ar_001.copy(currency = listOf("",  " \u0623\u002e\u0645\u002e" )) }
        public val ar_OM: Locale by lazy { ar_001.copy(currency = listOf("", " \u0631\u002e\u0639\u002e")) }
        public val ar_PS: Locale by lazy { ar_001.copy(currency = listOf("\u20aa ", "")) }
        public val ar_QA: Locale by lazy { ar_001.copy(currency = listOf("", " \u0631\u002e\u0642\u002e")) }
        public val ar_SA: Locale by lazy { ar_001.copy(currency = listOf("", " \u0631\u002e\u0633\u002e")) }
        public val ar_SD: Locale by lazy { ar_001.copy(currency = listOf("", " \u062c\u002e\u0633\u002e")) }
        public val ar_SO: Locale by lazy { ar_001.copy(currency = listOf("\u200f\u0053 ", "")) }
        public val ar_SS: Locale by lazy { ar_001.copy(currency = listOf("\u00a3 ", "")) }
        public val ar_SY: Locale by lazy { ar_001.copy(currency = listOf("", " \u0644\u002e\u0633\u002e")) }
        public val ar_TD: Locale by lazy { ar_001.copy(currency = listOf("\u200f\u0046\u0043\u0046\u0041 ", "")) }
        public val ar_TN: Locale by lazy { Locale("\u002c", "\u002e", currency = listOf("\u062f\u002e\u062a\u002e ", "")) }
        public val ar_YE: Locale by lazy { ar_001.copy(currency = listOf("", " \u0631\u002e\u0649\u002e")) }
        public val ca_ES: Locale by lazy { Locale(".", ".", listOf("", "\u00a0€")) }
        public val cs_CZ: Locale by lazy { Locale(",", "\u00a0", listOf("", "\u00a0Kč")) }
        public val de_CH: Locale by lazy { Locale(",", "'", listOf("", "\u00a0CHF")) }
        public val de_DE: Locale by lazy { Locale(",", ".", listOf("", "\u00a0€")) }
        public val en_CA: Locale by lazy { Locale(".", ",", listOf("$", "")) }
        public val en_GB: Locale by lazy { Locale(".", ",", listOf("£", "")) }
        public val en_IE: Locale by lazy { Locale(".", ",", listOf("€", "")) }
        public val en_IN: Locale by lazy { Locale(".", ",", listOf("₹", ""), grouping = listOf(3, 2, 2, 2, 2, 2, 2, 2, 2, 2)) }
        public val en_US: Locale by lazy { Locale(".", ",", listOf("$", "")) }
        public val es_BO: Locale by lazy { Locale(",", ".", listOf("Bs\u00a0", ""), grouping = listOf(3), percent = "\u202f%") }
        public val es_ES: Locale by lazy { Locale(",", ".", listOf("", "\u00a0€")) }
        public val es_MX: Locale by lazy { Locale(".", ",", listOf("$", "")) }
        public val fi_FI: Locale by lazy { Locale(",", "\u00a0", listOf("", "\u00a0€")) }
        public val fr_CA: Locale by lazy { Locale(",", "\u00a0", listOf("", "$")) }
        public val fr_FR: Locale by lazy { Locale(",", "\u00a0", listOf("", "\u00a0€")) }
        public val he_IL: Locale by lazy { Locale(".", ",", listOf("₪", "")) }
        public val hu_HU: Locale by lazy { Locale(",", "\u00a0", listOf("", "\u00a0Ft")) }
        public val it_IT: Locale by lazy { Locale(",", ".", listOf("€", "")) }
        public val ja_JP: Locale by lazy { Locale(".", ",", listOf("", "円")) }
        public val ko_KR: Locale by lazy { Locale(",", "", listOf("₩", "")) }
        public val mk_MK: Locale by lazy { Locale(",", ".", listOf("", "\u00a0ден.")) }
        public val nl_NL: Locale by lazy { Locale(",", ".", listOf("€\u00a0", "")) }
        public val pl_PL: Locale by lazy { Locale(",", ".", listOf("", "zł")) }
        public val pt_BR: Locale by lazy { Locale(",", ".", listOf("R$", "")) }
        public val ru_RU: Locale by lazy { Locale(",", "\u00a0", listOf("", "\u00a0руб.")) }
        public val sv_SE: Locale by lazy { Locale(",", "\u00a0", listOf("", " kr")) }
        public val uk_UA: Locale by lazy { Locale(",", "\u00a0", listOf("", "\u00a0₴.")) }
        public val zh_CN: Locale by lazy { Locale(".", ",", listOf("¥", "")) }
    }
}

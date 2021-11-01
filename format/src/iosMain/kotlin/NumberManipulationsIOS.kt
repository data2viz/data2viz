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

package io.data2viz.format


import kotlin.math.abs
import kotlin.math.roundToLong


internal actual fun Double.toStringDigits(radix: Int): String = ""


internal actual fun Double.toFixed(digits: Int): String =""

internal actual fun Double.toExponential(digits: Int): String = ""

internal actual fun Double.toExponential(): String =""

internal actual fun Double.toPrecision(digits: Int): String = ""



// TODO keep this, it will avoid errors when asking for high precision numbers (see FormatTests@check_platform_dependent_formatters_toFixed)
/*private fun Double.toFixedJVM(digits: Int): String {
    return BigDecimal(this.toString()).setScale(digits, BigDecimal.ROUND_HALF_UP).toString()
}*/

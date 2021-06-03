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

import kotlin.math.round


internal actual fun Double.toStringDigits(radix: Int): String = round(this).asDynamic().toString(radix)
internal actual fun Double.toFixed(digits: Int): String = this.asDynamic().toFixed(digits)
internal actual fun Double.toExponential(digits: Int): String = this.asDynamic().toExponential(digits)
internal actual fun Double.toExponential(): String = this.asDynamic().toExponential()
internal actual fun Double.toPrecision(digits: Int): String = this.asDynamic().toPrecision(digits)

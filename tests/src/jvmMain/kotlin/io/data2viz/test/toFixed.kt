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

package io.data2viz.test

import java.math.BigDecimal
import java.math.RoundingMode


public actual fun Double.toFixed(): String {
    return if (kotlin.math.abs(this - kotlin.math.round(this)) < 1e-6)
        kotlin.math.round(this).toString().dropLast(2)
    else BigDecimal(this).setScale(6, RoundingMode.HALF_UP).toString()
}

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

package io.data2viz.geo.geometry

import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import kotlin.math.acos
import kotlin.math.asin

/**
 * @return HALFPI if value > 1 or value < -1, in other cases asin(value)
 */
public val Double.limitedAsin: Double
    get() = when {
        this > 1 -> HALFPI
        this < -1 -> -HALFPI
        else -> this.asin
    }

public val Double.asin: Double
    get() = asin(this)

public val Double.acos: Double
    get() = acos(this)

/**
 * @return 0 if value > 1, PI if value < -1, in other cases acos(value)
 */
public val Double.limitedAcos: Double
    get() = when {
        this > 1 -> .0
        this < -1 -> PI
        else -> this.acos
    }

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

package io.data2viz.interpolate

import io.data2viz.math.Percent
import kotlinx.datetime.Instant

public fun interpolateDate(start: Instant, end: Instant): Interpolator<Instant>{
    val range = end - start
    return { percent -> start + (range * percent.value) }
}

public fun uninterpolateDate(start: Instant, end: Instant): UnInterpolator<Instant> {
    val range = end - start
    val nullRange = (start == end)
    return { date ->
        if (nullRange) Percent(.0)
        else Percent((date - start) / range)
    }
}
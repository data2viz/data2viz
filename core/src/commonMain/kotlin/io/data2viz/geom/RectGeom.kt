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

package io.data2viz.geom

import kotlin.math.absoluteValue
import kotlin.math.min


public data class RectGeom(
        override var x: Double = .0,
        override var y: Double = .0,
        override var width: Double = .0,
        override var height: Double = .0) : Rect {

    public constructor(point: Point, size: Size): this(point.x, point.y, size.width, size.height)

    public constructor(from: Point, to: Point):
            this(
                    min(from.x, to.x)
                    ,min(from.y, to.y),
                    (to.x - from.x).absoluteValue,
                    (to.y - from.y).absoluteValue)



}


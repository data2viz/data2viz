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

package io.data2viz.geom


/**
 * Todo this is really representing a rectangle. Is there a reason to name it Extent.
 * Todo make it immutable (data class)
 */
public class Extent(
    public var x0: Double, public var y0: Double,
    public var x1: Double, public var y1: Double)
{
    public var width: Double
        get() = x1 - x0

        set(value) {
            x0 = .0
            x1 = value
        }

    public var height: Double
        get() = y1 - y0
        set(value) {
            y0 = .0
            y1 = value
        }

    public fun copy(): Extent {
        return Extent(x0, y0, x1, y1)
    }
}
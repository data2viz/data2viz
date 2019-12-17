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

package io.data2viz.geo.projection.common


/**
 * Create new Projector which combines both projectors transformations
 * For example can be used to combine Translate and Rotate transformations
 *
 * @see ProjectorProjection
 */
class ComposedProjector(val a: Projector, val b:Projector): Projector  {

    override fun project(lambda: Double, phi: Double): DoubleArray {
        val p = a.project(lambda, phi)
        return b.project(p[0], p[1])
    }

    override fun invert(x: Double, y: Double): DoubleArray {
        val p = b.invert(x, y)
        return a.invert(p[0], p[1])
    }
}

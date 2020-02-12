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
class ComposedProjector<FROM,INTERMEDIATE, TO>(val a: Projector<FROM, INTERMEDIATE>, val b:Projector<INTERMEDIATE, TO>): Projector<FROM, TO>  {

    override fun project(point: FROM): TO {
        val p = a.project(point)
        return b.project(p)
    }

    override fun invert(point: TO): FROM {
        val p = b.invert(point)
        return a.invert(p)
    }
}

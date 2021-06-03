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

package io.data2viz.geo.projection.common

import io.data2viz.geo.projection.*
/**
 * Abstract Projector which delegate projector & invert to another projection depending current settings
 * @see BaseConditionalProjector
 */
public abstract class ConditionalProjector : Projector {

    override fun invert(x: Double, y: Double): DoubleArray = activeProjector.invert(x, y)

    override fun project(lambda: Double, phi: Double): DoubleArray = activeProjector.project(lambda, phi)

    public abstract val activeProjector: Projector
}

/**
 * ConditionalProjector implementation for two projectors
 *
 * @see ConditionalProjector
 * @see ConicEqualAreaBaseConditionalProjector
 * @see ConicConformalBaseConditionalProjector
 * @see ConicEquidistantBaseConditionalProjector
 */
public abstract class BaseConditionalProjector : ConditionalProjector() {

    public abstract val baseProjector: Projector
    public abstract val nestedProjector: Projector
    public abstract val isNeedUseBaseProjector: Boolean


    override val activeProjector: Projector
        get() = if (isNeedUseBaseProjector) {
            baseProjector
        } else {
            nestedProjector
        }
}

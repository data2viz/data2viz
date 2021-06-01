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

package io.data2viz.viz

import io.data2viz.geom.Size
import io.data2viz.ExperimentalD2V

/**
 * A VizContainer allows to create superposed Viz with the sames size through the [newViz] function.
 *
 * The VizContainer is bound to a platform element (View for Android, Pane for JFX, Div for JS).
 *
 * The [size] of the VizContainer is synchronized with the size of its underlying platform element.
 * The VizContainer takes the size of the platform element when it's created. Later modification of size
 * must be done through the [size] property of the VizContainer (and not by modifying the underlying
 * platform element), the underlying platform size is then modified accordingly.
 *
 * A VizContainer is mandatory to create a chart. The VizContainer holds the charting visualization(s).
 * Depending on the configuration one chart can generate one or two visualizations to optimize
 * rendering performances.
 *
 * You can create a VizContainer from a platform element , using the [newVizContainer]
 * extension function.
 *
 * Linked to a platform element that can hold multiple canvas.
 */
public interface VizContainer {

    /**
     * Creates a new [Viz] inside the platform element.
     */
    public fun newViz(init: Viz.() -> Unit = {}): Viz

    /**
     * The size of this [VizContainer], on change updates the underlying platform element and the existing canvas.
     */
    public var size: Size

    public val density: Double
}

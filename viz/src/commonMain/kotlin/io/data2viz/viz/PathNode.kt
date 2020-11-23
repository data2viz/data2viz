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

package io.data2viz.viz

import io.data2viz.geom.PathGeom
import io.data2viz.geom.Path

public open class PathNode(

    public val path: PathGeom = PathGeom()): Node(),
        HasStroke,
        HasFill,
        HasTransform,
        Path by path {

    override var transform: Transform? = null

    /**
     * Remove all segments of the path.
     * Todo should it be defined as a function of Path.
     */
    public fun clearPath() {
        path.clearPath()
    }

}


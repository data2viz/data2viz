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

package io.data2viz.shape.link

import io.data2viz.geom.Path
import io.data2viz.shape.const


/**
 * Instanciate and configure an horizontal link builder
 */
public fun <D> linkBuilderH(init: LinkBuilder<D>.() -> Unit): LinkBuilder<D> = LinkBuilder<D>().apply {
    curve = this::curveHorizontal
    init()
}

/**
 * Instanciates and configure a vertical link builder.
 */
public fun <D> linkBuilderV(init: LinkBuilder<D>.() -> Unit): LinkBuilder<D> = LinkBuilder<D>().apply {
    curve = this::curveVertical
    init()
}

/**
 * The link shape generates a smooth cubic Bézier curve from a source point to a target point.
 * The tangents of the curve at the start and end are either vertical, horizontal or radial.
 */
public class LinkBuilder<D> {

    public var x0: (D) -> Double = const(.0)
    public var x1: (D) -> Double = const(.0)
    public var y0: (D) -> Double = const(.0)
    public var y1: (D) -> Double = const(.0)
    public var curve: (Path, Double, Double, Double, Double) -> Unit = ::curveHorizontal

    public fun <C : Path> link(data:D, path:C) {
        curve(path, x0(data), y0(data), x1(data), y1(data))
    }

    internal fun <C : Path> curveHorizontal(path:C, x0:Double, y0:Double, x1:Double, y1:Double) {
        path.moveTo(x0, y0)
        val newX0 = (x0 + x1) / 2
        path.bezierCurveTo(newX0, y0, newX0, y1, x1, y1)
    }

    internal fun <C : Path> curveVertical(path:C, x0:Double, y0:Double, x1:Double, y1:Double) {
        path.moveTo(x0, y0)
        val newY0 = (y0 + y1) / 2
        path.bezierCurveTo(x0, newY0, x1, newY0, x1, y1)
    }
}

/*
 * Copyright (c) 2018-2022. data2viz sàrl.
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
import io.data2viz.viz.cinterop.UIViewWithOverridesProtocol
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.copy
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.UIKit.*

@ExportObjCClass
public class VizContainerView(
    frame: CValue<CGRect> = CGRectMake(.0, .0, .0, .0),
    private val resizableSupport: ResizableSupport = ResizableSupport()
) : UIView(
    frame = frame,
), UIViewWithOverridesProtocol {

    public val container: VizContainer get() = _container
    private val _container = object : VizContainer, Resizable by resizableSupport {

        override fun newViz(init: Viz.() -> Unit): Viz = Viz().also {
            it.size = size
            it.init()
            addSubview(IOSCanvasView(it, frame))
            _vizList.add(it)
        }

        override val vizList: List<Viz> get() = _vizList

        private val _vizList = mutableListOf<Viz>()

        override var size: Size = bounds.useContents { Size(this.size.width, this.size.height) }
            set(value) {
                setBounds(bounds.copy {
                    size.width = value.width
                    size.height = value.height
                })
                field = value
                vizList.forEach { it.size = value }
                resizableSupport.notifyNewSize(value)
            }

        override var density: Double = UIScreen.mainScreen.scale
    }

    override fun layoutSubviews() {
        _container.size = bounds.useContents {
            Size(this.size.width, this.size.height)
        }
        _container.density = UIScreen.mainScreen.scale

        subviews.forEach {
            val view = it as IOSCanvasView
            view.setFrame(frame)
        }
        setNeedsDisplay()
    }

    override fun drawRect(rect: CValue<CGRect>) {}
}

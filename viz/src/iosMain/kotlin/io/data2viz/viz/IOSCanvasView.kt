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

import io.data2viz.viz.cinterop.UIViewWithOverridesProtocol
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.UIKit.*

@ExportObjCClass
public class IOSCanvasView(
    public val viz: Viz,
    frame: CValue<CGRect> = CGRectMake(.0, .0, .0, .0)
) : UIView(frame = frame), UIViewWithOverridesProtocol {

    private val renderer = IOSCanvasRenderer(viz, this)

    internal var uiTouchesHandler: UITouchesHandler? = null

    init {
        renderer.render()
        viz.renderer = renderer
        backgroundColor = UIColor(white = 1.0, alpha = 0.0)
    }

    override fun layoutSubviews() {
//        println("IOSCanvasView.layoutSubviews")
        setNeedsDisplay()
    }

    override fun drawRect(rect: CValue<CGRect>) {
//		println("IOSCanvasView.drawRect")
        renderer.draw(rect)
    }


    @Suppress("unchecked_cast")
    override fun touchesBegan(touches: Set<*>, withEvent: UIEvent?) {
        uiTouchesHandler?.touchesBegan(touches as Set<UITouch>, withEvent)
    }

    @Suppress("unchecked_cast")
    override fun touchesMoved(touches: Set<*>, withEvent: UIEvent?) {
        uiTouchesHandler?.touchesMoved(touches as Set<UITouch>, withEvent)
    }

    @Suppress("unchecked_cast")
    override fun touchesEnded(touches: Set<*>, withEvent: UIEvent?) {
        uiTouchesHandler?.touchesEnded(touches as Set<UITouch>, withEvent)
    }

    @Suppress("unchecked_cast")
    override fun touchesCancelled(touches: Set<*>, withEvent: UIEvent?) {
        uiTouchesHandler?.touchesCancelled(touches as Set<UITouch>, withEvent)
    }
}



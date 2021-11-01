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

import io.data2viz.viz.cinterop.UIViewWithOverridesProtocol
import io.data2viz.viz.Viz
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExportObjCClass
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView
import platform.UIKit.setNeedsDisplay

@ExportObjCClass
class IOSCanvasView(val viz: Viz) : UIView(frame = CGRectMake(.0, .0, .0, .0)), UIViewWithOverridesProtocol {

    private val renderer = IOSCanvasRenderer(viz, this)

    init {
        renderer.render()
    }

    override fun layoutSubviews() {
//        println("IOSCanvasView.layoutSubviews")
        setNeedsDisplay()
    }

    override fun drawRect(aRect: CValue<CGRect>) {
//		println("IOSCanvasView.drawRect")
        renderer.draw(aRect)
    }

}



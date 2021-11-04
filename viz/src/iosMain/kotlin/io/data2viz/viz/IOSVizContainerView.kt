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
import io.data2viz.geom.Size
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.UIKit.*


@ExportObjCClass
public class VizContainerView():

    UIView(frame = CGRectMake(.0, .0, .0, .0)),
    UIViewWithOverridesProtocol{

    internal val vizContainer:IosVizContainer = IosVizContainer().apply {
        size = bounds.useContents { Size(this.size.width, this.size.height) }
    }

    val container:VizContainer
        get() = vizContainer

    override fun layoutSubviews() {
//        println("VizContainerView.layoutSubviews... " + this.bounds.toDescription())
        vizContainer.size = bounds.useContents { Size(this.size.width, this.size.height) }
        vizContainer.density = UIScreen.mainScreen.scale
//        println(vizContainer.toString())

        subviews.forEach {
            val view = it as UIView
            view.removeFromSuperview()
        }

        println("${vizContainer.vizList.size} subview(s)")
        vizContainer.vizList.forEach {
            addSubview(IOSCanvasView(it, bounds))
        }
        setNeedsDisplay()
    }

    override fun drawRect(aRect: CValue<CGRect>) {
//        println("VizContainerView.drawRect...")
//		val context = UIGraphicsGetCurrentContext()
//		CGContextSetFillColor(context, color(150,0,0) )
//		CGContextFillRect(context, aRect)
    }
}


internal class IosVizContainer(
    internal val resizableSupport: ResizableSupport = ResizableSupport()
) :
    VizContainer,
    Resizable by resizableSupport
{

    override var size: Size = Size(100.0, 100.0)
        set(value) {
            field = value
            vizList.forEach {
                it.size = value
            }
            resizableSupport.notifyNewSize(value)
        }


    override var density: Double = 1.0

    internal var vizList = mutableListOf<Viz>()

    public override fun newViz(init: Viz.() -> Unit): Viz{
        val viz = Viz().apply(init)
        vizList.add(viz)
        return viz
    }

    override fun toString(): String {
        return "IosVizContainer(size=$size, density=$density)"
    }

}
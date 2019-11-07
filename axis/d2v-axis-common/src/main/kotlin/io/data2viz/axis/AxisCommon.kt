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

package io.data2viz.axis

import io.data2viz.color.Color
import io.data2viz.color.Colors
import io.data2viz.scale.*
import io.data2viz.viz.*
import kotlin.math.round


/**
 * Create an Axis 
 */
fun <D> GroupNode.axis(orient: Orient, scale: FirstLastRange<D,Double>, init:AxisElement<D>.() -> Unit = {}):AxisElement<D> =

        AxisElement(orient, scale).apply {
            init(this)
            build(this@axis)
        }


class AxisElement<D>(val orient: Orient, val scale: FirstLastRange<D,Double>)  {

    var tickValues:List<D> = listOf()
    var tickSizeInner = 6.0
    var tickSizeOuter = 6.0
    var tickPadding = 3.0
    var tickFormat = {n:D -> n.toString()}

    var tickColor:Color = Colors.Web.black
    var textColor:Color = Colors.Web.black
    var lineColor:Color = Colors.Web.black

    var color:Color = Colors.Web.black
        set(value) {
            tickColor = value
            textColor = value
            lineColor = value
            field = value
        }

    val k = if (orient == Orient.TOP || orient == Orient.LEFT) -1 else 1

    fun center(scale: BandedScale<D>): (D) -> Double {
        var offset = (scale.bandwidth - 1).coerceAtLeast(0.0) / 2 // Adjust for 0.5px offset.
        if (scale.round) offset = round(offset)
        return { d:D -> +scale(d) + offset }
    }

    fun number(scale: Scale<D,Double>) : (D) -> Double  = { d -> scale(d) }

    fun build(content: GroupNode) {
        
        val values: List<D> = if (tickValues.isEmpty() && scale is Tickable<*>) scale.ticks() as List<D> else tickValues
        val spacing = tickSizeInner.coerceAtLeast(0.0) + tickPadding
        val range0 = scale.start()
        val range1 = scale.end()
        val position = if (scale is BandedScale) center(scale) else number(scale)

        with(content){
            path {
                stroke = lineColor
                fill = null
                strokeWidth = 1.0

                if(orient.isVertical()) {
                    moveTo(tickSizeOuter * k, range0)
                    lineTo(- 0.5 * k, range0)
                    lineTo(- 0.5 * k, range1)
                    lineTo(tickSizeOuter * k, range1)
                } else {
                    moveTo(range0, tickSizeOuter * k)
                    lineTo(range0, - 0.5 * k)
                    lineTo(range1, - 0.5 * k)
                    lineTo(range1, tickSizeOuter * k)
                }
            }
            
            values.forEach {
                group { 
                    transform { 
                        if (orient.isHorizontal()) translate(x = position(it)) else translate(y = position(it))
                    }
                    if (orient.isHorizontal())
                        line {
                            y2 = k * tickSizeInner
                            stroke = tickColor
                        }
                    else
                        line {
                            x2 = k * tickSizeInner
                            stroke = tickColor
                        }
                    text {
                        hAlign = when (orient) {
                            Orient.LEFT -> TextHAlign.RIGHT
                            Orient.RIGHT -> TextHAlign.LEFT
                            else -> TextHAlign.MIDDLE
                        }
                        
                        vAlign = when (orient){
                            Orient.TOP -> TextVAlign.BASELINE
                            Orient.BOTTOM -> TextVAlign.HANGING
                            else -> TextVAlign.MIDDLE
                        }
                        fill = textColor
                        if(orient.isHorizontal()) 
                            y = spacing * k
                        else
                            x = spacing * k
                        textContent = tickFormat(it)
                    }
                    
                }
            }
        }
    }

}

enum class Orient {
    TOP, BOTTOM, LEFT, RIGHT;
    
    fun isVertical() = (this == LEFT || this == RIGHT)
    fun isHorizontal() = (this == TOP || this == BOTTOM)
}
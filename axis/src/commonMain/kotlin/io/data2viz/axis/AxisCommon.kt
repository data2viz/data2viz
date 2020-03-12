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

import io.data2viz.color.ColorOrGradient
import io.data2viz.color.Colors
import io.data2viz.scale.BandedScale
import io.data2viz.scale.FirstLastRange
import io.data2viz.scale.Scale
import io.data2viz.scale.Tickable
import io.data2viz.viz.*
import kotlin.math.round


/**
 * Create an Axis
 */
fun <D> GroupNode.axis(
    orient: Orient,
    scale: FirstLastRange<D, Double>,
    init: AxisElement<D>.() -> Unit = {}
): AxisElement<D> =

    AxisElement(orient, scale).apply {
        init(this)
        build(this@axis)
    }

class AxisElement<D>(val orient: Orient, val scale: FirstLastRange<D, Double>) {

    var tickValues: List<D> = listOf()
    var tickSizeInner = 6.0
    var tickSizeOuter = 6.0
    var tickPadding = 3.0
    var stroke: ColorOrGradient = Colors.Web.black
    var strokeWidth: Double = 1.0
    var fontSize: Double = 12.0
    var fontColor: ColorOrGradient = Colors.Web.black
    var fontFamily: FontFamily = FontFamily.SANS_SERIF
    var fontWeight: FontWeight = FontWeight.NORMAL
    var fontStyle: FontPosture = FontPosture.NORMAL

    var tickFormat = { n: D -> n.toString() }

    private val k = if (orient == Orient.TOP || orient == Orient.LEFT) -1 else 1

    private fun center(scale: BandedScale<D>): (D) -> Double {
        var offset = (scale.bandwidth - 1).coerceAtLeast(0.0) / 2 // Adjust for 0.5px offset.
        if (scale.round) offset = round(offset)
        return { d: D -> +scale(d) + offset }
    }

    private fun number(scale: Scale<D, Double>): (D) -> Double = { scale(it) }

    fun build(content: GroupNode) {

        val values: List<D> = if (tickValues.isEmpty() && scale is Tickable<*>) scale.ticks() as List<D> else tickValues
        val spacing = tickSizeInner.coerceAtLeast(0.0) + tickPadding
        val start = scale.start()
        val end = scale.end()
        val position = if (scale is BandedScale) center(scale) else number(scale)

        with(content) {

            // the main axis line
            path {
                stroke = this@AxisElement.stroke
                fill = null
                strokeWidth = this@AxisElement.strokeWidth

                if (orient.isVertical()) {
                    moveTo(tickSizeOuter * k, start)
                    lineTo(.0, start)
                    lineTo(.0, end)
                    lineTo(tickSizeOuter * k, end)
                } else {
                    moveTo(start, tickSizeOuter * k)
                    lineTo(start, .0)
                    lineTo(end, .0)
                    lineTo(end, tickSizeOuter * k)
                }
            }

            // each tick and associated label
            values.forEach {
                group {
                    transform {
                        if (orient.isHorizontal()) translate(x = position(it)) else translate(y = position(it))
                    }
                    if (orient.isHorizontal())
                        line {
                            y2 = k * tickSizeInner
                            stroke = this@AxisElement.stroke
                            strokeWidth = this@AxisElement.strokeWidth
                        }
                    else
                        line {
                            x2 = k * tickSizeInner
                            stroke = this@AxisElement.stroke
                            strokeWidth = this@AxisElement.strokeWidth
                        }
                    text {
                        textColor = fontColor
                        fontWeight = this@AxisElement.fontWeight
                        fontSize = this@AxisElement.fontSize
                        fontFamily = this@AxisElement.fontFamily
                        fontStyle = this@AxisElement.fontStyle
                        hAlign = when (orient) {
                            Orient.LEFT -> TextHAlign.RIGHT
                            Orient.RIGHT -> TextHAlign.LEFT
                            else -> TextHAlign.MIDDLE
                        }

                        vAlign = when (orient) {
                            Orient.TOP -> TextVAlign.BASELINE
                            Orient.BOTTOM -> TextVAlign.HANGING
                            else -> TextVAlign.MIDDLE
                        }
                        if (orient.isHorizontal())
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
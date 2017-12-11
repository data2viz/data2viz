package io.data2viz.axis

import io.data2viz.color.colors
import io.data2viz.scale.ContinuousScale
import io.data2viz.viz.ParentItem
import io.data2viz.viz.TextAlignmentBaseline
import io.data2viz.viz.TextAnchor


/**
 * Create an Axis 
 */
fun ParentItem.axis(orient: Orient, scale: ContinuousScale<Double>, init:AxisElement<Double>.() -> Unit = {}) {
    AxisElement<Double>(orient, scale)
            .apply(init)
            .render(this)
}


class AxisElement<D>(val orient: Orient, val scale: ContinuousScale<Double>)  {

    var tickValues:List<Double> = listOf()
    var tickSizeInner = 6.0
    var tickSizeOuter = 6.0
    var tickPadding = 3.0
    var tickFormat = {n:Double -> n.toString()}

    val k = if (orient == Orient.TOP || orient == Orient.LEFT) -1 else 1

    fun render(content:ParentItem) {
        
//        fun values():List<Double> {
//            if (tickValues.isNotEmpty()) return tickValues
//            if (scale is Tickable<Double>) return scale.ticks()
//            
//        }

        
        val values = if (tickValues.isEmpty() ) scale.ticks() else tickValues  
        val spacing = tickSizeInner.coerceAtLeast(0.0) + tickPadding
        val range0 = scale.range.first() + 0.5
        val range1 = scale.range.last() + 0.5

        with(content){
            path { 
                stroke = colors.black
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
            
            values.sorted().forEach { 
                group { 
                    transform { 
                        if (orient.isHorizontal()) translate(x = scale(it)) else translate(y = scale(it)) 
                    }
                    if (orient.isHorizontal()) line { y2 = k * tickSizeInner } else line { x2 = k * tickSizeInner }
                    text {
                        anchor = when (orient) {
                            Orient.LEFT -> TextAnchor.END
                            Orient.RIGHT -> TextAnchor.START
                            else -> TextAnchor.MIDDLE
                        }
                        
                        baseline = when (orient){
                            Orient.TOP -> TextAlignmentBaseline.BASELINE
                            Orient.BOTTOM -> TextAlignmentBaseline.HANGING
                            else -> TextAlignmentBaseline.MIDDLE
                        }
                        fill = colors.black
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
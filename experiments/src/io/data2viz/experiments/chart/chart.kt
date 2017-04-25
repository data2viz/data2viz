package io.data2viz.experiments.chart

import io.data2viz.interpolate.cubicInOut
import io.data2viz.axis.Axis
import io.data2viz.color.colors.black
import io.data2viz.color.colors.steelblue
import io.data2viz.color.colors.white
import io.data2viz.color.rgba
import io.data2viz.interpolate.linkedTo
import io.data2viz.interpolate.scale
import io.data2viz.interpolate.uninterpolate
import io.data2viz.svg.Margins
import io.data2viz.svg.svg
import kotlin.browser.window
import kotlin.js.Date


fun chart() {

    val datas = listOf(55, 44, 30, 23, 17, 14, 16, 25, 41, 61, 85,
            101, 95, 105, 114, 150, 180, 210, 125, 100, 71,
            75, 72, 67)

    val maxValue = datas.max()!!
    val barWidth = 20
    val barPadding = 3
    val graphWidth = datas.size * (barPadding + barWidth) - barPadding
    val margin = Margins(10, 10, 10, 50)

    val totalWidth = graphWidth + margin.horizontalMargins
    val totalHeight = 400

    val xLoc = { i: Int -> i * (barPadding + barWidth) }
    val yLoc = scale.linear.numberToNumber(
            0 linkedTo (totalHeight - margin.verticalMargins),
            maxValue linkedTo 0
    )
    val yScale = scale.linear.numberToNumber(
            0 linkedTo 0,
            maxValue linkedTo totalHeight - margin.verticalMargins
    )

    svg {
        width = totalWidth
        height = totalHeight

        rect {
            width = totalWidth
            height = totalHeight
            stroke = black
            strokeWidth = "1"
            fill = white
        }

        g {
            transform {
                translate(47,10)
            }
            Axis(0, maxValue, yLoc).appendTo(this)
        }

        g {
            transform {
                translate(margin.left, margin.top)
            }

            rect {
                width = totalWidth - margin.horizontalMargins
                height = totalHeight - margin.verticalMargins
                fill = rgba(0, 0, 0, .1)
            }

            datas.forEachIndexed { index, data ->
                g {

                    val animate = animate(1000, index * 40)

                    transform {
                        translate(xLoc(index), yLoc(0))
                    }

                    animate { time ->
                        transform {
                            translate(xLoc(index), yLoc(time * data))
                        }
                    }

                    rect {
                        width = barWidth
                        fill = steelblue
                        height = 0
                        animate { time ->
                            height = time * yScale(data).toDouble()
                        }
                    }
                    text {
                        fill = null
                        animate { time ->
                            fill = rgba(255,255,255, time)
                        }

                        text = data.toString()

                        transform {
                            translate(barWidth / 2)
                        }
                        setAttribute("alignment-baseline", "before-edge")
                        setAttribute("text-anchor", "middle")
                        style {
                            fontFamily("sans-serif")
                            fontSize("10px")
                        }
                    }
                }
            }
        }
    }
}

//------------   API test -----------------------

fun animate(duration: Int = 250, delay: Int = 0, timeFunction:(Double) -> Double = ::cubicInOut) =
        Animation(duration, delay, timeFunction)

class Animation(duration:Int, delay: Int, val timeFunction:(Double) -> Double){

    val startTime = Date().getTime() + delay
    val endTime = startTime + duration

    val blocksOfAnimation  = mutableListOf<(Double) -> Unit>()

    init {
        val time = uninterpolate(startTime, endTime)
        fun animate(){
            val currentTime = Date().getTime()
            when {
                currentTime < startTime -> window.requestAnimationFrame { animate() }
                currentTime > endTime -> { blocksOfAnimation.forEach { it(1.0) } }
                else -> {
                    window.requestAnimationFrame {
                        blocksOfAnimation.forEach { it(timeFunction(time(currentTime).toDouble())) }
                        animate()
                    }
                }
            }

        }
        animate()
    }

    operator fun invoke(animation: (Double) -> Unit){
        blocksOfAnimation.add(animation)
    }

}

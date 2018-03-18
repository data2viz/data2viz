import io.data2viz.scale.scales
import io.data2viz.timer.timer
import io.data2viz.viz.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

val widthHeight = 600.0
val domainToViz = { pos: Double -> (widthHeight / 2) + pos * .3 * widthHeight }


val fps = newText().apply {
    transform { translate(y = 20.0) }
}
val particuleCount = newText().apply {
    transform { translate(y = 40.0) }
}
val root = newGroup().apply {
    add(fps)
    add(particuleCount)
    timer { now ->
        loop(now)
    }

}
val particules: MutableList<Particule> = mutableListOf()
var add = 1
var noAdd = 0

fun loop(now: Double) {

    FPS.eventuallyUpdate(now)

    if (FPS.value >= 0) {
        fps.textContent = "${FPS.value.roundToInt()} FPS"
        particuleCount.textContent = "${particules.count()} particules"
    }

    if (FPS.value > 25 && noAdd > 0) {
        particules.addAll((1..50).map { Particule() })
        noAdd = 0
        println("Total particules :: ${particules.size}")
    } else {
        noAdd++
    }

    
    if ( noAdd == 200 ) {
        add = -1
    }
    
    if (add < 0 && particules.size > 0){
        (1..50).forEach { particules.removeAt(0) }
        println("Total particules :: ${particules.size}")
    }


    root.selectElement(circle, particules) {
        onEnter = {
            element.apply {
                stroke = null
                radius = 5.0
                fill = colors(now % 1000 / 1000.0)
            }
            root.add(element)
        }

        onUpdate = {
            datum.nextState()
            element.cx = domainToViz(datum.x)
            element.cy = domainToViz(datum.y)
            element.radius = (1.0 + (datum.vx * datum.vy).absoluteValue * 1000).coerceAtMost(10.0)
        }

        onExit = {
            root.remove(element)
        }

    }
}


object FPS {
    val averageCount = 10
    var value = .0
    var count = 0
    var lastStart = Double.NaN

    /**
     * current: current time in ms.
     */
    fun eventuallyUpdate(current: Double) {
        if (lastStart == Double.NaN)
            lastStart = current
        if (count++ == averageCount) {
            val totalTime = current - lastStart
            value = 1.0e3 * averageCount / totalTime
            lastStart = current
            count = 0
        }
    }
}


data class Particule(
    var x: Double = .0,
    var y: Double = .0,
    var vx: Double = .0,
    var vy: Double = .0
) {

    fun nextState() {
        x += vx
        y += vy
        vx += .04 * (random() - .5) - .05 * vx - .005 * x
        vy += .04 * (random() - .5) - .05 * vy - .005 * y
    }
}

var colors = scales.colors.sequentialPlasma()

expect fun random(): Double
import io.data2viz.color.colors
import io.data2viz.core.Point
import io.data2viz.core.Speed
import io.data2viz.core.random
import io.data2viz.hexbin.hexbinGenerator
import io.data2viz.math.TAU
import io.data2viz.scale.scales
import io.data2viz.timer.timer
import io.data2viz.viz.circle
import io.data2viz.viz.newGroup
import io.data2viz.viz.newPath
import io.data2viz.viz.selectElement
import kotlin.math.cos
import kotlin.math.sin

data class Particule(
    var position: Point,
    var speed: Speed
)

private val numParticule = 10000
private val particules: MutableList<Particule> = mutableListOf()

private val colors = scales.colors.sequentialViridis()

val vizWidth = 600.0
val vizHeight = 600.0
var hexView = newGroup()
val root = newGroup().apply {
    init()
    timer { now ->
        loop()
    }
    add(hexView)
}
val hexbin = hexbinGenerator {
    radius = 20.0
    width = vizWidth
    height = vizHeight
}

fun init() {
    (0 until numParticule).forEach {
        val angle = random() * TAU
        val celerity = (.5 + random()) / 5
        val vx = cos(angle) * celerity
        val vy = sin(angle) * celerity
        particules.add(Particule(Point(vizWidth * random(), vizHeight * random()), Speed(vx, vy)))
    }
}

fun loop() {
    particules.forEach { particule ->
        val next = particule.position + particule.speed
        if (next.x < 0 || next.x > vizWidth) {
            particule.speed = Speed(-particule.speed.vx, particule.speed.vy)
        }
        if (next.y < 0 || next.y > vizHeight) {
            particule.speed = Speed(particule.speed.vx, -particule.speed.vy)
        }
        particule.position += particule.speed
    }

    root.selectElement(circle, particules) {
        onEnter = {
            element.apply {
                stroke = null
                radius = 1.0
                fill = colors.lightgray
            }
            root.add(element)
        }

        onUpdate = {
            element.cx = datum.position.x
            element.cy = datum.position.y
        }
    }

    val hexList = hexbin(particules.map { it.position })

    root.remove(hexView)
    hexView = newGroup()

    val max = hexList.maxBy {it.points.size }!!.points.size.toDouble()
    hexList.forEach { bin ->
        val path = newPath()
        val color = colors(bin.points.size / max)
        color.alpha = .7
        path.fill = color
        path.strokeWidth = .0
        hexbin.hexagon(path, Point(bin.x, bin.y))
        hexView.add(path)
    }

    root.add(hexView)
}


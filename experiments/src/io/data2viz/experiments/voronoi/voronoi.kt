package io.data2viz.experiments.voronoi

import io.data2viz.color.colors.black
import io.data2viz.svg.svg
import io.data2viz.voronoi.Diagram
import io.data2viz.voronoi.Point
import io.data2viz.voronoi.Site
import kotlin.js.Date
import kotlin.js.Math.random


fun voronoi() {

    data class Foo(val value: Int)

    val size = 100_000

    fun <T> time(msg: String = "", block: () -> T): T {
        val start = Date()
        val ret = block()
        println("$msg::${Date().getTime() - start.getTime()} ms")
        return ret
    }

    val foosAsArray = time("Instanciate foos as array") {
        Array<Array<Int>>(size) { i -> arrayOf(i) }
    }

    val sum = time("sum foosAsArray with for") {
        var sum = 0
        for (i in 0..foosAsArray.size - 1) {
            sum += foosAsArray[i][0]
        }
    }

    time("sum foosAsArray with forEach") {
        var sum = 0
        foosAsArray.forEach { sum += it[0] }
    }

//    time("sum foosAsArray with fold") {
//        var sum = 0
//        foosAsArray.fold(0) { (a,b:Array<Int>) -> a + b[0]}
//    }

    val foos = time("Instanciate foos") {
        Array<Foo>(size) { i -> Foo(i) }
    }
    time("sum foos with forEach") {
        var sum = 0
        foos.forEach { sum += it.value }
    }

    println("test voronoi")

    val xMax = 600.0
    val yMax = 600.0

    fun randomPoints(count:Int,xMax: Double, yMax:Double) = (1..count).map { pt(random() * xMax, random() * yMax) }
    val points = randomPoints(200, xMax, yMax)

    time {
//        val diagram = Diagram(points.sites(), clipEnd = Point(xMax, yMax))
        val diagram = Diagram(points.sites())
        val svg = svg {
            width = xMax
            height = yMax

        }

        (1..50).forEach {
            time {
                svg.removeChildren()
                svg.apply {
                    diagram.edges
                            .filterNotNull()
                            .forEach { edge ->
                                if( edge.start != null && edge.end != null)
                                    line(edge.start!!.x, edge.start!!.y, edge.end!!.x, edge.end!!.y, black )
                            }
                }
            }
        }
    }
}

fun pt(x: Number, y: Number) = Point(x.toDouble(), y.toDouble())
fun List<Point>.sites() = mapIndexed { index, point -> Site(point, index) }.toTypedArray()


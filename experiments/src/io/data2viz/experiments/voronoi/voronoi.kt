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
    time {
        val points = (1..1000).map {
            pt(random() * 600, random() * 600)
        }
        val diagram = Diagram(points.sites())
        svg {
            width = 600
            height = 600

            diagram.edges.forEach { edge ->
                if(edge?.start != null && edge.end != null){
                    line(edge.start!!.x, edge.start!!.y, edge.end!!.x, edge.end!!.y, black )
                }
            }
        }
    }
}

fun pt(x: Number, y: Number) = Point(x.toDouble(), y.toDouble())
fun List<Point>.sites() = mapIndexed { index, point -> Site(point, index) }.toTypedArray()


package io.data2viz.shape.apitest

import kotlin.js.Date


fun exemple_of_api(){

    /**
     * Configuring a LineGenerator based on the domain object Match.
     *
     * x, y are used to defined the coordinate of the point representing the match.
     *
     * defined is a function that indicates if the current instance should be
     * displayed. It can be use to filter elements.
     *
     * curve configure how points of the line are connected (linear by default).
     */
    val line = line<Match> {

        // x defined through a detailed lambda
        x = { match ->  match.dateToDouble()}

        // using the it default lambda parameter
        x = { it.dateToDouble() }

        // accessing the current index of the match, and the complete array of datas
        x = { it.dateToDouble() * datas.size + i*10}


        // using a function reference (that can be a local function extension)
        x = Match::dateToDouble

        // definining as a constant
        x = const(.1)


        define = { match -> match.time > 100 }

    }

}


/**
 * Example of domain object used in the Genenrator
 */
data class Match(val tournamentName: String, val time:Int, val setCount:Int, val date: Date)


/**
 * The transformation of a domain value into a datavisualization can be
 * done in a local extension fonction.
 */
fun Match.dateToDouble() = Date().getTime() - date.getTime()

/**
 * Common pattern for starting DSL
 */
fun <T> line(init:LineGenerator<T>.()->Unit)  = LineGenerator<T>().apply(init)

class LineGenerator<T> {

    /**
     * the datas array available during generation
     */
    var datas:Array<T> = emptyArray()

    /**
     * current index of the data
     */
    var i:Int = -1

    var define: (T) -> Boolean = const(true)
    var x: (T) -> Double = {.0}
    var y: (T) -> Double = {.0}
}

fun <T, D> const(constantValue: T): (D) -> T = { constantValue }



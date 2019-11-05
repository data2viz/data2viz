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

package io.data2viz.shape.apitest

import kotlin.js.Date


fun exemple_of_api(){



    /**
     * The transformation of a domain value into a datavisualization can be
     * done in a local extension fonction.
     */
    fun Match.dateToDouble() = Date().getTime() - date.getTime()


    @Suppress("UNUSED_VARIABLE")
            /**
     * Configuring a LineBuilder based on the domain object Match.
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



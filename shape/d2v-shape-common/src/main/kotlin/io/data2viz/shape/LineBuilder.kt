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

package io.data2viz.shape

import io.data2viz.geom.Path

fun <T> line(init: LineBuilder<T>.() -> Unit) = LineBuilder<T>().apply(init)

fun <T, D> const(constantValue: T): (D) -> T = { constantValue }

class LineBuilder<T> {

    var curve: (Path) -> Curve = curves.linear
    var x: (T) -> Double = const(.0)
    var y: (T) -> Double = const(.0)
    var defined: (T) -> Boolean = const(true)

    /**
     * Use the data to generate a line on the path
     */
    fun <C : Path> buildLine(data: List<T>, path: C): C {
        val dataSize = data.size

        var defined0 = false
        val output = curve(path)

        for (i in 0..dataSize) {
            if (!(i < dataSize && defined(data[i])) == defined0) {
                defined0 = !defined0
                if (defined0) output.lineStart() else output.lineEnd()
            }

            if (defined0) {
                val d = data[i]
                output.point(x(d), y(d))
            }
        }
        return path
    }
}
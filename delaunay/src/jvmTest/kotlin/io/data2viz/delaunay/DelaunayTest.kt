/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.delaunay

import java.lang.Math.random
import kotlin.test.Test



class DelaunayTest {

    @Test
    fun bench() {

        val points = (1..10_000).map { arrayOf(1000 * random(), 1000 * random()) }.toTypedArray()

        val time = System.currentTimeMillis()
        (1..50).forEach {
            Delaunator(points)
        }
        val meanTime = (System.currentTimeMillis() - time) / 50.0
        val ops = 1000.0 / meanTime
        println("${ops.toInt()} ops/sec")

    }




}

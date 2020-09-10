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

package io.data2viz.voronoi

import io.data2viz.geom.Point
import io.data2viz.test.matchers.Matchers
import kotlin.random.Random
import kotlin.test.Test


class DiagramTests : Matchers {

    fun List<Point>.sites() = mapIndexed { index, point -> Site(point, index) }.toTypedArray()

    @Test
    fun diagram1Site() {
        Diagram(listOf(pt(10, 10)).sites())
    }

    @Test
    fun diagram5sites() {
        val diagram = Diagram(listOf(
                pt(10, 10),
                pt(20, 10),
                pt(10, 20),
                pt(20, 20),
                pt(15, 15)
        ).sites())

        val triangles = diagram.triangles()
        triangles.size shouldBe 4

        val polygons = diagram.polygons()
        polygons.filterNotNull().size shouldBe polygons.size
        polygons.size shouldBe 5

        diagram.find(pt(14, 10)) shouldBe diagram.cells!![0]?.site

        polygons.all { polygon ->
            polygon.all { point ->
                point != null
            }
        } shouldBe true

    }

    @Test
    fun diagram2sites() {
        val diagram = Diagram(listOf(
                pt(10, 10),
                pt(30, 10)
        ).sites())

        println(diagram)
    }

    @Test
    fun diagram16384sites() {


        val points = (1..16384).map {
            pt(Random.nextDouble(100.0), Random.nextDouble(100.0))
        }

        val diagram = Diagram(points.sites(), clipEnd = Point(100.0, 100.0))
        val polygons = diagram.polygons()
                .mapIndexed { i, polygon ->
                    var x = 0.0
                    var y = 0.0
                    polygon.forEach { point -> x += point.x; y += point.y }
                    return@mapIndexed Site(Point(x / polygon.size, y / polygon.size), i)
                }.toTypedArray()



        println("polygons.count :: ${polygons.size}")
        val links = diagram.links()
        println("links.count :: ${links.size}")
        val triangles = diagram.triangles()
        println("triangles.count :: ${triangles.size}")
    }

}


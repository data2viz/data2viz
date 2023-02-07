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

package io.data2viz.hexbin

import io.data2viz.geom.Extent
import io.data2viz.geom.Point
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.test.TestBase
import kotlin.js.JsName
import kotlin.test.Test

private fun Extent.toArray() = arrayOf(this.x0, this.y0, this.x1, this.y1)
private fun pt(a: Int, b: Int) = Point(a.toDouble(), b.toDouble())

class HexbinTests : TestBase() {

    fun path(): PathGeom = PathGeom()

    val points = listOf(
        pt(0, 0), pt(0, 1), pt(0, 2),
        pt(1, 0), pt(1, 1), pt(1, 2),
        pt(2, 0), pt(2, 1), pt(2, 2)
    )

    @Test
    @JsName("hexbin_test_1")
    fun `hexbin has the expected defaults`() {
        val hexbin = hexbinGenerator()

        hexbin.extent.toArray() shouldBe Extent(.0, .0, 1.0, 1.0).toArray()
        hexbin.width shouldBeClose 1.0
        hexbin.height shouldBeClose 1.0
        hexbin.x(Point(41.0, 42.0), 0, listOf()) shouldBeClose 41.0
        hexbin.y(Point(41.0, 42.0), 0, listOf()) shouldBeClose 42.0
        hexbin.radius shouldBeClose 1.0
    }

    @Test
    @JsName("hexbin_test_2")
    fun `hexbin (points) bins the specified points into hexagonal bins`() {
        val hexbin = hexbinGenerator()
        val bins = hexbin(points)

        bins.size shouldBe 4
        bins[0].x shouldBeClose .0
        bins[0].y shouldBeClose .0
        bins[0].points.size shouldBe 1
        bins[1].x shouldBeClose 0.8660254037844386
        bins[1].y shouldBeClose 1.5
        bins[1].points.size shouldBe 4
        bins[2].x shouldBeClose 1.7320508075688772
        bins[2].y shouldBeClose .0
        bins[2].points.size shouldBe 2
        bins[3].x shouldBeClose 2.598076211353316
        bins[3].y shouldBeClose 1.5
        bins[3].points.size shouldBe 2
    }

    @Test
    @JsName("hexbin_test_3")
    fun `hexbin (points) observes the current radius`() {
        val hexbin = hexbinGenerator {
            radius = 2.0
        }
        val bins = hexbin(points)

        bins.size shouldBe 3
        bins[0].x shouldBeClose .0
        bins[0].y shouldBeClose .0
        bins[0].points.size shouldBe 4
        bins[1].x shouldBeClose 1.7320508075688772
        bins[1].y shouldBeClose 3.0
        bins[1].points.size shouldBe 3
        bins[2].x shouldBeClose 3.4641016151377544
        bins[2].y shouldBeClose .0
        bins[2].points.size shouldBe 2
    }

    @Test
    @JsName("hexbin_test_4")
    fun `hexbin width height gets or sets the extent`() {
        val hexbin = hexbinGenerator {
            width = 2.0
            height = 3.0
        }

        hexbin.extent.toArray() shouldBe arrayOf(.0, .0, 2.0, 3.0)

        hexbin.extent = Extent(1.0, 2.0, 4.0, 8.0)
        hexbin.width shouldBeClose 3.0
        hexbin.height shouldBeClose 6.0
    }

    /*@Test
    // TODO convert relative to absolute
    @JsName("hexbin_test_5")
    fun `hexbin mesh() observes the extent`() {
        val hexbin = hexbinGenerator {
            radius = .5
            extent = Extent(-1.1, -1.1, 1.1, 1.1)
        }

        val path = path()
        hexbin.mesh(path)
        path.path.round().substring(0, 427) shouldBe "M-0.433013,-1.25L0,-1L0,1.5L-0.433013,1.7M0.433013,-1.250000L0.866026,1.000000L0.866026,1.500000L0.4330130,1.750000M1.299038,-1.250000L1.732051,1.000000L1.732051,1.500000L1.2990380,1.750000M-1.366025,0L-0.933012,0.250000L-0.933012,0.750000L-1.366025,1.000000M0,-0.500000L0.433013,-0.250000L0.433013,0.250000L0.0000000,0.500000M0.866025,-0.500000L1.299038,-0.250000L1.299038,0.250000L0.8660250,0.500000".round() //M-0.433013,0.750000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M0.433013,0.750000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M1.299038,0.750000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M-0.866025,1.500000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M0,1.500000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M0.866025,1.500000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000".round()
    }*/

    @Test
    @JsName("hexbin_test_6")
    fun `hexbin hexagon() returns the expected path`() {
        val hexbin = hexbinGenerator ()

        val path = path()
        hexbin.hexagon(path, Point.origin)

        path.svgPath.round() shouldBe "M0,-1L0.866025,-0.5L0.866025,0.5L0,1.0L-0.866025,0.5L-0.866025,-0.5Z".round()
    }

    @Test
    @JsName("hexbin_test_7")
    fun `hexbin hexagon() observes the current bin radius`() {
        val hexbin = hexbinGenerator {
            radius = 2.0
        }

        val path = path()
        hexbin.hexagon(path, Point.origin)
        path.svgPath.round() shouldBe "M0,-2L1.732051,-1L1.732051,1L0,2L-1.732051,1L-1.732051,-1Z".round()

        path.clearPath()
        hexbin.radius = 4.0
        hexbin.hexagon(path, Point.origin)
        path.svgPath.round() shouldBe "M0,-4L3.464102,-2L3.464102,2L0,4L-3.464102,2L-3.464102,-2Z".round()
    }

    @Test
    @JsName("hexbin_test_8")
    fun `hexbin hexagon() observes the specified bin radius`() {
        val hexbin = hexbinGenerator()

        val path = path()
        hexbin.hexagon(path, Point.origin, 2.0)
        path.svgPath.round() shouldBe "M0,-2L1.732051,-1L1.732051,1L0,2L-1.732051,1L-1.732051,-1Z".round()

        path.clearPath()
        hexbin.hexagon(path, Point.origin, 4.0)
        path.svgPath.round() shouldBe "M0,-4L3.464102,-2L3.464102,2L0,4L-3.464102,2L-3.464102,-2Z".round()
    }

    @Test
    @JsName("hexbin_test_9")
    fun `hexbin centers() returns an array of bin centers`() {
        val hexbin = hexbinGenerator()

        val centers = hexbin.centers()
        centers[0] shouldBe Point(.0, .0)
        centers[1] shouldBe Point(1.7320508075688772, .0)
        centers[2] shouldBe Point(0.8660254037844386, 1.5)
        centers.size shouldBe 3
    }

    @Test
    @JsName("hexbin_test_10")
    fun `hexbin centers() observes the current bin radius`() {
        val hexbin = hexbinGenerator {
            radius = .5
        }

        val centers = hexbin.centers()
        centers[0] shouldBe Point(.0, .0)
        centers[1] shouldBe Point(0.8660254037844386, .0)
        centers[2] shouldBe Point(0.4330127018922193, 0.75)
        centers[3] shouldBe Point(1.299038105676658, 0.75)
        centers.size shouldBe 4
    }

    @Test
    @JsName("hexbin_test_11")
    fun `hexbin centers() observes the current extent`() {
        val hexbin = hexbinGenerator {
            radius = .5
            extent = Extent(-1.1, -1.1, 1.1, 1.1)
        }

        val centers = hexbin.centers()
        centers[0] shouldBe Point(-0.4330127018922193, -0.75)
        centers[1] shouldBe Point(0.4330127018922193, -0.75)
        centers[2] shouldBe Point(1.299038105676658, -0.75)
        centers[3] shouldBe Point(-0.8660254037844386, .0)
        centers[4] shouldBe Point(.0, .0)
        centers[5] shouldBe Point(0.8660254037844386, .0)
        centers[6] shouldBe Point(-0.4330127018922193, 0.75)
        centers[7] shouldBe Point(0.4330127018922193, 0.75)
        centers[8] shouldBe Point(1.299038105676658, 0.75)
        centers[9] shouldBe Point(-0.8660254037844386, 1.5)
        centers[10] shouldBe Point(.0, 1.5)
        centers[11] shouldBe Point(0.8660254037844386, 1.5)
        centers.size shouldBe 12
    }
}
/*

// TODO some path to absolute check below

tape("hexbin.mesh() returns the expected path", function(test) {
test.pathEqual(d3.hexbin().mesh(), "M0,0m0,-1l0.866025,0.500000l0,1l-0.866025,0.500000M1.732051,0m0,-1l0.866025,0.500000l0,1l-0.866025,0.500000M0.866025,1.500000m0,-1l0.866025,0.500000l0,1l-0.866025,0.500000");
test.end();
});

tape("hexbin.mesh() observes the bin radius", function(test) {
test.pathEqual(d3.hexbin().radius(0.5).mesh(), "M0,0m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M0.866025,0m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M0.433013,0.750000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000M1.299038,0.750000m0,-0.500000l0.433013,0.250000l0,0.500000l-0.433013,0.250000");
test.end();
});
 */

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

package io.data2viz.geo.projection


import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.geometry.clip.CirclePreClip
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test


val generateHtml = true

class CircleClippingTests : TestBase() {

    fun getProjection() = equirectangularProjection {
        translateX = 480.0
        translateY = 350.0
        scale = 200.0
        precision = .0
//        center(.0.deg, .0.deg)
    }

    /**                25            50
     *                x————————————————x
     *        - 25  / |                 \
     * -----------O---|------------------x 75---
     *              \ |                 /
     *                x————————————————x
     *                -25
     */
    val polygon = MultiPolygon(
        arrayOf(
            arrayOf(
                arrayOf(
                    arrayOf(.0, -25.0),
                    arrayOf(-25.0, .0),
                    arrayOf(.0, 25.0),
                    arrayOf(50.0, 25.0),
                    arrayOf(75.0, .0),
                    arrayOf(50.0, -25.0),
                    arrayOf(.0, -25.0)
                )
            )
        )
    )

    @Test
    fun no_clipping() {
        val path = PathGeom()
        geoPath(getProjection(), path).project(polygon)
        path.svgPath.round() shouldBe "M480,437.2664625997165L392.7335374002835,350L480,262.7335374002835L654.532925199433,262.7335374002835L741.7993877991494,350L654.532925199433,437.2664625997165Z".round()
    }

    @Test
    fun clipping_radius_45() {
        val projection = getProjection()
        projection.preClip = CirclePreClip(60.deg.rad)
        val path = PathGeom()
        geoPath(projection, path).project(polygon)
        val svg = path.svgPath.round()
        if (generateHtml){
            println(generateHtmlWithSvg(svg))
        }
        svg shouldBe "M683.777313,411.146783L654.532925,437.266463L480,437.266463L480,437.266463L392.733537,350L480,262.733537L654.532925,262.733537L683.777313,288.853217L683.777346,288.853387L686.677678,306.771700L688.516820,324.816908L689.367380,342.933016L689.262404,361.068632L688.197834,379.173618L686.132199,397.195868Z".round()
    }
}



fun generateHtmlWithSvg(svg:String) = """
<html lang="en"><body><svg width="960" height="700">
    <path d="$svg"
          style="fill:none; stroke:black"></path></svg>
</body>
</html>
""".trimIndent()
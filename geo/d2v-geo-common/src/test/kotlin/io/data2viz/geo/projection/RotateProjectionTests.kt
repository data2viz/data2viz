package io.data2viz.geo.projection

import io.data2viz.geo.geometry.calculateSvgPath
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test


class RotateProjectionTests : TestBase() {


    @Test
    fun project_rotation__of_degenerate_polygon_should_not_break() {
        val projection = mercatorProjection {
            rotate((-134.300).deg, 25.776.deg, rotateGamma)
            scale = 750.0
            translate(0.0, 0.0)
        }


        val actualSvgPath = calculateSvgPath(
            projection, """{
            "type": "Polygon",
            "coordinates": [
            [
                [125.67351590459046, -14.17673705310531],
                [125.67351590459046, -14.173276873687367],
                [125.67351590459046, -14.173276873687367],
                [125.67351590459046, -14.169816694269425],
                [125.67351590459046, -14.17673705310531]
            ]
            ]
        }""".toGeoJsonObject())
        actualSvgPath shouldBe "M-111.644162,-149.157654L-111.647235,-149.203744L-111.647235,-149.203744L-111.650307,-149.249835Z"
    }

}
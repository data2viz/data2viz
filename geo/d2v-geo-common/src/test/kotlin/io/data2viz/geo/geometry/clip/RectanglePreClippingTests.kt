package io.data2viz.geo.projection


import io.data2viz.geo.geojson.geoPath
import io.data2viz.geo.geometry.clip.RectangleClip
import io.data2viz.geo.geometry.clip.ExtentClip
import io.data2viz.geojson.LineString
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geojson.Polygon
import io.data2viz.geom.Extent
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.math.toRadians
import io.data2viz.test.TestBase
import kotlin.test.Test

class RectanglePreClippingTests : TestBase() {

    fun getProjection() = equirectangularProjection {
        translate(480.0, 350.0)
        precision = .0
    }

    val polygon = Polygon(
            arrayOf(
                arrayOf(
                    arrayOf(-8.0, 15.0),
                    arrayOf(-6.0, 15.0),
                    arrayOf(-6.0, -15.0),
                    arrayOf(-8.0, -15.0),
                    arrayOf(-8.0, 15.0)
                )
            )
    )

    val line = LineString(
        arrayOf(
            arrayOf(6.0, 15.0),
            arrayOf(6.0, -15.0)
        )

    )

    val clipZonePolygon = Polygon(
            arrayOf(
                arrayOf(
                    arrayOf(-10.0, 10.0),
                    arrayOf(10.0, 10.0),
                    arrayOf(10.0, -10.0),
                    arrayOf(-10.0, -10.0),
                    arrayOf(-10.0, 10.0)
                )
            )
    )


    @Test
    fun rectangle_clipping_east() {
        val path = PathGeom()
        val projection = getProjection()
//        geoPath(projection, path).project(clipZonePolygon)

        projection.preClip = RectangleClip( //in radians
            -10.0.toRadians(), -10.0.toRadians(),
            10.0.toRadians(), 10.0.toRadians())

        geoPath(projection, path).project(polygon)
        geoPath(projection, path).project(line)
        val svg = path.svgPath.round()
        println(generateHtmlWithSvg(svg))
        svg shouldBe "M464.016624,323.361040L464.016624,376.638960L458.688832,376.638960L458.688832,323.361040L464.016624,323.361040ZM495.983376,323.361040L495.983376,376.638960".round()
    }

}
package io.data2viz.geo.projection

import io.data2viz.geo.clip.clipRectangle
import io.data2viz.geo.path.geoPath
import io.data2viz.geojson.MultiPolygon
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class VariousClippingTests : TestBase() {

    fun getProjection() = equirectangularProjection {
        translate = doubleArrayOf(480.0, 350.0)
        scale = 2000.0
        precision = .0
        center = doubleArrayOf(10.0, 5.0)
    }

    val polygon = MultiPolygon(
        arrayOf(
            arrayOf(
                arrayOf(
                    arrayOf(5.0, 5.0),
                    arrayOf(2.50, 7.5),
                    arrayOf(5.0, 10.0),
                    arrayOf(10.0, 10.0),
                    arrayOf(12.5, 7.5),
                    arrayOf(10.0, 5.0),
                    arrayOf(5.0, 5.0)
                )
            )
        )
    )

    @Test
    fun equirectangular_no_clipping() {
        val geoPath = geoPath(getProjection(), svgPath())
        val path: SvgPath = geoPath.path(polygon) as SvgPath

        path.path.round() shouldBe "M305.46707480056705,350L218.20061220085057,262.7335374002835L305.46707480056705,175.46707480056705L480,175.46707480056705L567.2664625997165,262.7335374002835L480,350Z".round()
    }

    @Test
    fun equirectangular_rectangle_clipping_east() {
        val projection = getProjection()
        projection.postClip = clipRectangle(Extent(48.0, 50.0, 498.0, 500.0))

        val geoPath = geoPath(projection, svgPath())
        val path: SvgPath = geoPath.path(polygon) as SvgPath

        path.path.round() shouldBe "M498,332L480,350L305.46707480056705,350L305.46707480056705,350L218.20061220085057,262.7335374002835L305.46707480056705,175.46707480056705L480,175.46707480056705L498,193.46707480056705L498,332Z".round()
    }
}
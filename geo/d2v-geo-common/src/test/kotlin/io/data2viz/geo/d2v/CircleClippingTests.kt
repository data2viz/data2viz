package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.clipCircle
import io.data2viz.geo.geojson.geoPath
import io.data2viz.geojson.MultiPolygon
import io.data2viz.geom.PathGeom
import io.data2viz.geom.svgPath
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class CircleClippingTests : TestBase() {

    fun getProjection() = equirectangularProjection {
        x = 480.0
        y = 350.0
        scale = 200.0
        precision = .0
        center = arrayOf(.0.deg, .0.deg)
    }

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
        val geoPath = geoPath(getProjection(), PathGeom())
        val path = geoPath.path(polygon) as PathGeom

        path.svgPath.round() shouldBe "M480,437.2664625997165L392.7335374002835,350L480,262.7335374002835L654.532925199433,262.7335374002835L741.7993877991494,350L654.532925199433,437.2664625997165Z".round()
    }

    @Test
    fun clipping_radius_45() {
        val projection = getProjection()
        projection.preClip = clipCircle(45.0)

        val geoPath = geoPath(projection, PathGeom())
        val path = geoPath.path(polygon) as PathGeom

        path.svgPath.round() shouldBe "M675.7886257900888,418.685523802076L654.532925199433,437.2664625997165L480,437.2664625997165L480,437.2664625997165L392.7335374002835,350L480,262.7335374002835L654.532925199433,262.7335374002835L675.7886257900888,281.314476197924L675.7886656908231,281.31464217957637L679.3545901831021,298.8052113817325L681.7795637396509,316.4659453862484L683.1556330961773,334.23260712949025L683.5340983646468,352.0476277567145L682.9289450521389,369.85641947567416L681.3177849368545,387.6040127963977L678.6403508664818,405.23165234608393Z".round()
    }
}

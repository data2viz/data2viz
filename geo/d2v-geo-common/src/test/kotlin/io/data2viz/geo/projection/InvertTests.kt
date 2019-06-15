package io.data2viz.geo.projection

import io.data2viz.geo.projection.common.Projection
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test


class InvertProjectionsTests : TestBase() {

    val util = ProjectionTests()

    val worldPoints = arrayOf(
        doubleArrayOf(-0.0, 0.0),
        doubleArrayOf(30.3, 24.1),
        doubleArrayOf(-10.0, 42.0),
        doubleArrayOf(-2.0, -5.0)
    )


    @Test
    fun albersUsa_invert_symmetric() {
        val usaPoints = arrayOf(
            doubleArrayOf(-122.4194, 37.7749),
            doubleArrayOf(-74.0059, 40.7128),
            doubleArrayOf(-149.9003, 61.2181),
            doubleArrayOf(-157.8583, 21.3069)
        )

        testProjection(albersUSAProjection(), usaPoints)
    }

    @Test
    fun albers_invert_symmetric() = testProjection(albersProjection(), worldPoints)

    @Test
    fun azimuthalEqualArea_invert_symmetric() = testProjection(azimuthalEqualAreaProjection(), worldPoints)

    @Test
    fun azimuthalEquidistant_invert_symmetric() = testProjection(azimuthalEquidistant(), worldPoints)

    @Test
    fun conicConformal_invert_symmetric() {
        testProjection(conicConformalProjection(), worldPoints)
        testProjection(conicConformalProjection {
            parallels(20.0.deg, 30.0.deg)
        }, worldPoints)
        testProjection(conicConformalProjection {
            parallels(30.0.deg, 30.0.deg)
        }, worldPoints)
        testProjection(conicConformalProjection {
            parallels((-35.0).deg, (-50.0).deg)
        }, worldPoints)
    }

    @Test
    fun conicEqualArea_invert_symmetric() {
        testProjection(conicEqualAreaProjection(), worldPoints)
        testProjection(conicEqualAreaProjection {
            parallels(20.0.deg, 30.0.deg)
        }, worldPoints)
        testProjection(conicEqualAreaProjection {
            parallels((-30.0).deg, 30.0.deg)
        }, worldPoints)
        testProjection(conicEqualAreaProjection {
            parallels((-35.0).deg, (-50.0).deg)
        }, worldPoints)
    }

    @Test
    fun conicEquidistant_invert_symmetric() {
        testProjection(conicEquidistantProjection(), worldPoints)
        testProjection(conicEquidistantProjection {
            parallels(20.0.deg, 30.0.deg)
        }, worldPoints)
        testProjection(conicEquidistantProjection {
            parallels(30.0.deg, 30.0.deg)
        }, worldPoints)
        testProjection(conicEquidistantProjection {
            parallels((-35.0).deg, (-50.0).deg)
        }, worldPoints)
    }

    @Test
    fun equirectangular_invert_symmetric() = testProjection(equirectangularProjection(), worldPoints)


    @Test
    fun equalEarth_invert_symmetric() = testProjection(equalEarthProjection(), worldPoints)


    @Test
    fun gnomonic_invert_symmetric() = testProjection(gnomonicProjection(), worldPoints)

    @Test
    fun mercator_invert_symmetric() = testProjection(mercatorProjection(), worldPoints)


    @Test
    fun orthographic_invert_symmetric() = testProjection(orthographicProjection(), worldPoints)


    @Test
    fun stereographic_invert_symmetric() = testProjection(stereographicProjection(), worldPoints)

    @Test
    fun transverseMercator_invert_symmetric() = testProjection(transverseMercatorProjection(), worldPoints)


    private fun testProjection(projection: Projection, points: Array<DoubleArray>) {
        points.forEach { point ->

            val projected = projection.project(point[0], point[1])
            util.checkProjection(projection, point[0], point[1], projected[0], projected[1])
        }
    }
}
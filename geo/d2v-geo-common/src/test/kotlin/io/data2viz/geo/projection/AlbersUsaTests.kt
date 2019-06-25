package io.data2viz.geo.projection

import io.data2viz.test.TestBase
import kotlin.test.Test

class AlbersUsaTests : TestBase() {

    

    @Test
    fun point_invert_returns_the_expected_result() {
        val albersUsa = albersUSAProjection()

        // 0.1 precision taken from d3
        checkProjectAndInvert(albersUsa, -122.4194, 37.7749, 107.4, 214.1, 0.1) // San Francisco, CA
        checkProjectAndInvert(albersUsa, -74.0059, 40.7128, 794.6, 176.5, 0.1) // New York, NY
        checkProjectAndInvert(albersUsa, -95.9928, 36.1540, 488.8, 298.0, 0.1) // Tulsa, OK


        checkProject(albersUsa, -149.9003, 61.2181, 171.2, 446.9, 0.1) // Anchorage, AK
        checkProject(albersUsa, -157.8583, 21.3069, 298.5, 451.0, 0.1) // Honolulu, HI
        val projectedParis = albersUsa.project(2.3522, 48.8566) // Paris, France

        projectedParis[0] shouldEqual Double.NaN
        projectedParis[1] shouldEqual Double.NaN
    }
}

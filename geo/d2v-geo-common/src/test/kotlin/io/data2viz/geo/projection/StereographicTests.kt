package io.data2viz.geo.projection


import kotlin.test.Test

class StereographicTests {

    @Test
    fun stereographic_returns_expected_result() {
        val projection = stereographicProjection {
            translate(0.0, 0.0)
            scale = 1.0
        }

        checkProjectAndInvert(projection, 0.0, 0.0, 0.0, 0.0)
        checkProjectAndInvert(projection, -90.0, 0.0, -1.0, 0.0)
        checkProjectAndInvert(projection, 90.0, 0.0, 1.0, 0.0)
        checkProjectAndInvert(projection, 0.0, -90.0, 0.0, 1.0)
        checkProjectAndInvert(projection, 0.0, 90.0, 0.0, -1.0)

    }
}

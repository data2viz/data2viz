package io.data2viz.geo.projection

import io.data2viz.geojson.Point
import io.data2viz.geojson.lat
import io.data2viz.geojson.lon
import io.data2viz.geojson.toGeoJsonObject
import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class GeoJsonTests : TestBase() {

    @Test
    fun pointJson() {
        val json = """{"type":"Point", "coordinates":[1.0, 2.0]}"""
        val p = json.toGeoJsonObject() as Point
        assertEquals(p.coordinates.lon, 1.0)
        assertEquals(p.coordinates.lat, 2.0)
    }

}
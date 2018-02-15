package io.data2viz.geo.projection

import io.data2viz.geo.*
import io.data2viz.geo.path.geoPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test

class PathCentroidTests : TestBase() {

    val equirectangular = io.data2viz.geo.projection.equirectangular() {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_centroid_of_a_point_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(Point(doubleArrayOf(.0, .0))) shouldBe doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_an_empty_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(MultiPoint(listOf())) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_single_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(MultiPoint(listOf(doubleArrayOf(.0, .0)))) shouldBe doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_double_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(
            MultiPoint(
                listOf(
                    doubleArrayOf(-122.0, 37.0),
                    doubleArrayOf(-74.0, 40.0)
                )
            )
        ) shouldBe doubleArrayOf(-10.0, 57.5)
    }

    @Test
    fun geopath_centroid_of_an_empty_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(LineString(listOf())) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_2_and_3_points_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(LineString(listOf(doubleArrayOf(100.0, .0), doubleArrayOf(.0, .0)))) shouldBe doubleArrayOf(730.0, 250.0)
        geoPath.centroid(
            LineString(
                listOf(
                    doubleArrayOf(.0, .0),
                    doubleArrayOf(100.0, .0),
                    doubleArrayOf(101.0, .0)
                )
            )
        ) shouldBe doubleArrayOf(732.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_2_points_one_unique_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(LineString(listOf(doubleArrayOf(-122.0, 37.0),doubleArrayOf(-122.0, 37.0)))) shouldBe doubleArrayOf(-130.0,65.0)
        geoPath.centroid(
            LineString(
                listOf(
                    doubleArrayOf(-74.0, 40.0),
                    doubleArrayOf(-74.0, 40.0)
                )
            )
        ) shouldBe doubleArrayOf(110.0, 50.0)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_3_points_2_unique_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            LineString(
                listOf(
                    doubleArrayOf(-122.0, 37.0),
                    doubleArrayOf(-74.0, 40.0),
                    doubleArrayOf(-74.0, 40.0)
                )
            )
        ) shouldBe doubleArrayOf(-10.0, 57.5)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_3_points_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            LineString(
                listOf(
                    doubleArrayOf(-122.0, 37.0),
                    doubleArrayOf(-74.0, 40.0),
                    doubleArrayOf(-100.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(17.389135, 103.563545)
    }

    @Test
    fun geopath_centroid_of_a_multilineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            MultiLineString(
                listOf(
                    listOf(doubleArrayOf(100.0, .0),doubleArrayOf(.0, .0)),
                    listOf(doubleArrayOf(-10.0, .0),doubleArrayOf(.0, .0))
                )
            )
        ) shouldBe doubleArrayOf(705.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_single_ring_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(doubleArrayOf(100.0, .0),
                        doubleArrayOf(100.0, 1.0),
                        doubleArrayOf(101.0, 1.0),
                        doubleArrayOf(101.0, .0),
                        doubleArrayOf(100.0, .0))
                )
            )
        ) shouldBe doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_zero_area_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(doubleArrayOf(1.0, .0),
                        doubleArrayOf(2.0, .0),
                        doubleArrayOf(3.0, .0),
                        doubleArrayOf(1.0, .0))
                )
            )
        ) shouldBe doubleArrayOf(490.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_polygon_with_2_rings_one_with_a_zero_area_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(doubleArrayOf(100.0, .0),
                        doubleArrayOf(100.0, 1.0),
                        doubleArrayOf(101.0, 1.0),
                        doubleArrayOf(101.0, .0),
                        doubleArrayOf(100.0, .0)
                    ),
                    listOf(doubleArrayOf(100.1, .0),
                        doubleArrayOf(100.2, .0),
                        doubleArrayOf(100.3, .0),
                        doubleArrayOf(100.1, .0)
                    )
                )
            )
        ) shouldBe doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_polygon_with_clockwise_exterior_and_anticlockwise_interior_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(doubleArrayOf(-2.0, -2.0),
                        doubleArrayOf(2.0, -2.0),
                        doubleArrayOf(2.0, 2.0),
                        doubleArrayOf(-2.0, 2.0),
                        doubleArrayOf(-2.0, -2.0)
                    ).reversed(),
                    listOf(doubleArrayOf(0.0, -1.0),
                        doubleArrayOf(1.0, -1.0),
                        doubleArrayOf(1.0, 1.0),
                        doubleArrayOf(0.0, 1.0),
                        doubleArrayOf(0.0, -1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(479.642857, 250.0)
    }

    @Test
    fun geopath_centroid_of_an_empty_multipolygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf())) shouldBe doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_singleton_multipolygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf(listOf(listOf(
            doubleArrayOf(100.0, 0.0),
            doubleArrayOf(100.0, 1.0),
            doubleArrayOf(101.0, 1.0),
            doubleArrayOf(101.0, 0.0),
            doubleArrayOf(100.0, 0.0)
        ))))) shouldBe doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_multipolygon_with_2_polygons_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf(listOf(listOf(
            doubleArrayOf(100.0, 0.0),
            doubleArrayOf(100.0, 1.0),
            doubleArrayOf(101.0, 1.0),
            doubleArrayOf(101.0, 0.0),
            doubleArrayOf(100.0, 0.0)
        ), listOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(1.0, 0.0),
            doubleArrayOf(1.0, -1.0),
            doubleArrayOf(0.0, -1.0),
            doubleArrayOf(0.0, 0.0)
        ))))) shouldBe doubleArrayOf(732.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_multipolygon_with_2_polygons_1_zone_area_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf(listOf(listOf(
            doubleArrayOf(100.0, 0.0),
            doubleArrayOf(100.0, 1.0),
            doubleArrayOf(101.0, 1.0),
            doubleArrayOf(101.0, 0.0),
            doubleArrayOf(100.0, 0.0)
        ), listOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(1.0, 0.0),
            doubleArrayOf(2.0, 0.0),
            doubleArrayOf(0.0, 0.0)
        ))))) shouldBe doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_single_point_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(GeometryCollection(listOf(Point(doubleArrayOf(.0, .0))))) shouldBe doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_point_and_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(GeometryCollection(listOf(
            LineString(listOf(doubleArrayOf(179.0, .0), doubleArrayOf(180.0, .0))),
            Point(doubleArrayOf(.0, .0)))
        )) shouldBe doubleArrayOf(1377.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_point_and_lineString_and_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(GeometryCollection(listOf(
            Polygon(listOf(listOf(doubleArrayOf(-180.0, .0), doubleArrayOf(-180.0, 1.0), doubleArrayOf(-179.0, 1.0), doubleArrayOf(-179.0, .0), doubleArrayOf(-180.0, .0)))),
            LineString(listOf(doubleArrayOf(179.0, .0), doubleArrayOf(180.0, .0))),
            Point(doubleArrayOf(.0, .0)))
        )) shouldBe doubleArrayOf(-417.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_featureCollection_with_point_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(FeatureCollection(listOf(Feature(Point(doubleArrayOf(.0, .0)))))) shouldBe doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_FeatureCollection_with_point_and_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(FeatureCollection(listOf(
            Feature(LineString(listOf(doubleArrayOf(179.0, .0), doubleArrayOf(180.0, .0)))),
            Feature(Point(doubleArrayOf(.0, .0))))
        )) shouldBe doubleArrayOf(1377.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_FeatureCollection_with_point_and_lineString_and_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(FeatureCollection(listOf(
            Feature(Polygon(listOf(listOf(doubleArrayOf(-180.0, .0), doubleArrayOf(-180.0, 1.0), doubleArrayOf(-179.0, 1.0), doubleArrayOf(-179.0, .0), doubleArrayOf(-180.0, .0))))),
            Feature(LineString(listOf(doubleArrayOf(179.0, .0), doubleArrayOf(180.0, .0)))),
            Feature(Point(doubleArrayOf(.0, .0))))
        )) shouldBe doubleArrayOf(-417.5, 247.5)
    }

    // TODO : when clipping will be OK activate this one
    @Test @Ignore
    fun geopath_centroid_of_a_sphere_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(Sphere()) shouldBe doubleArrayOf(480.0, 250.0)
    }
}
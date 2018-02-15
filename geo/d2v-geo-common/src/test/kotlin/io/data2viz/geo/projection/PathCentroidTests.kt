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
        geoPath.centroid(Point(pt(.0, .0))) shouldBe pt(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_an_empty_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(MultiPoint(listOf())) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_single_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(MultiPoint(listOf(pt(.0, .0)))) shouldBe pt(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_double_multipoint_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(
            MultiPoint(
                listOf(
                    pt(-122.0, 37.0),
                    pt(-74.0, 40.0)
                )
            )
        ) shouldBe pt(-10.0, 57.5)
    }

    @Test
    fun geopath_centroid_of_an_empty_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())
        geoPath.centroid(LineString(listOf())) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_2_and_3_points_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(LineString(listOf(pt(100.0, .0), pt(.0, .0)))) shouldBe pt(730.0, 250.0)
        geoPath.centroid(
            LineString(
                listOf(
                    pt(.0, .0),
                    pt(100.0, .0),
                    pt(101.0, .0)
                )
            )
        ) shouldBe pt(732.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_2_points_one_unique_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(LineString(listOf(pt(-122.0, 37.0),pt(-122.0, 37.0)))) shouldBe pt(-130.0,65.0)
        geoPath.centroid(
            LineString(
                listOf(
                    pt(-74.0, 40.0),
                    pt(-74.0, 40.0)
                )
            )
        ) shouldBe pt(110.0, 50.0)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_3_points_2_unique_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            LineString(
                listOf(
                    pt(-122.0, 37.0),
                    pt(-74.0, 40.0),
                    pt(-74.0, 40.0)
                )
            )
        ) shouldBe pt(-10.0, 57.5)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_3_points_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            LineString(
                listOf(
                    pt(-122.0, 37.0),
                    pt(-74.0, 40.0),
                    pt(-100.0, .0)
                )
            )
        ) shouldBeClose pt(17.389135, 103.563545)
    }

    @Test
    fun geopath_centroid_of_a_multilineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            MultiLineString(
                listOf(
                    listOf(pt(100.0, .0),pt(.0, .0)),
                    listOf(pt(-10.0, .0),pt(.0, .0))
                )
            )
        ) shouldBe pt(705.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_single_ring_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0))
                )
            )
        ) shouldBe pt(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_zero_area_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(pt(1.0, .0),
                        pt(2.0, .0),
                        pt(3.0, .0),
                        pt(1.0, .0))
                )
            )
        ) shouldBe pt(490.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_polygon_with_2_rings_one_with_a_zero_area_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
                    ),
                    listOf(pt(100.1, .0),
                        pt(100.2, .0),
                        pt(100.3, .0),
                        pt(100.1, .0)
                    )
                )
            )
        ) shouldBe pt(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_polygon_with_clockwise_exterior_and_anticlockwise_interior_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(
            Polygon(
                listOf(
                    listOf(pt(-2.0, -2.0),
                        pt(2.0, -2.0),
                        pt(2.0, 2.0),
                        pt(-2.0, 2.0),
                        pt(-2.0, -2.0)
                    ).reversed(),
                    listOf(pt(0.0, -1.0),
                        pt(1.0, -1.0),
                        pt(1.0, 1.0),
                        pt(0.0, 1.0),
                        pt(0.0, -1.0)
                    )
                )
            )
        ) shouldBeClose pt(479.642857, 250.0)
    }

    @Test
    fun geopath_centroid_of_an_empty_multipolygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf())) shouldBe pt(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_singleton_multipolygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf(listOf(listOf(
            pt(100.0, 0.0),
            pt(100.0, 1.0),
            pt(101.0, 1.0),
            pt(101.0, 0.0),
            pt(100.0, 0.0)
        ))))) shouldBe pt(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_multipolygon_with_2_polygons_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf(listOf(listOf(
            pt(100.0, 0.0),
            pt(100.0, 1.0),
            pt(101.0, 1.0),
            pt(101.0, 0.0),
            pt(100.0, 0.0)
        ), listOf(
            pt(0.0, 0.0),
            pt(1.0, 0.0),
            pt(1.0, -1.0),
            pt(0.0, -1.0),
            pt(0.0, 0.0)
        ))))) shouldBe pt(732.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_multipolygon_with_2_polygons_1_zone_area_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(MultiPolygon(listOf(listOf(listOf(
            pt(100.0, 0.0),
            pt(100.0, 1.0),
            pt(101.0, 1.0),
            pt(101.0, 0.0),
            pt(100.0, 0.0)
        ), listOf(
            pt(0.0, 0.0),
            pt(1.0, 0.0),
            pt(2.0, 0.0),
            pt(0.0, 0.0)
        ))))) shouldBe pt(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_single_point_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(GeometryCollection(listOf(Point(pt(.0, .0))))) shouldBe pt(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_point_and_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(GeometryCollection(listOf(
            LineString(listOf(pt(179.0, .0), pt(180.0, .0))),
            Point(pt(.0, .0)))
        )) shouldBe pt(1377.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_point_and_lineString_and_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(GeometryCollection(listOf(
            Polygon(listOf(listOf(pt(-180.0, .0), pt(-180.0, 1.0), pt(-179.0, 1.0), pt(-179.0, .0), pt(-180.0, .0)))),
            LineString(listOf(pt(179.0, .0), pt(180.0, .0))),
            Point(pt(.0, .0)))
        )) shouldBe pt(-417.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_featureCollection_with_point_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(FeatureCollection(listOf(Feature(Point(pt(.0, .0)))))) shouldBe pt(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_FeatureCollection_with_point_and_lineString_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(FeatureCollection(listOf(
            Feature(LineString(listOf(pt(179.0, .0), pt(180.0, .0)))),
            Feature(Point(pt(.0, .0))))
        )) shouldBe pt(1377.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_FeatureCollection_with_point_and_lineString_and_polygon_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(FeatureCollection(listOf(
            Feature(Polygon(listOf(listOf(pt(-180.0, .0), pt(-180.0, 1.0), pt(-179.0, 1.0), pt(-179.0, .0), pt(-180.0, .0))))),
            Feature(LineString(listOf(pt(179.0, .0), pt(180.0, .0)))),
            Feature(Point(pt(.0, .0))))
        )) shouldBe pt(-417.5, 247.5)
    }

    // TODO : when clipping will be OK activate this one
    @Test @Ignore
    fun geopath_centroid_of_a_sphere_LEGACY() {
        val geoPath = geoPath(equirectangular, svgPath())

        geoPath.centroid(Sphere()) shouldBe pt(480.0, 250.0)
    }
}
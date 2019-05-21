package io.data2viz.geo.projection

import io.data2viz.geo.stream.Sphere
import io.data2viz.geo.path.geoPath
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class PathCentroidTests : TestBase() {

    val equirectangular = equirectangularProjection() {
        scale = 900.0 / PI
        precision = .0
    }

    @Test
    fun geopath_centroid_of_a_point() {
        val geoPath = geoPath(equirectangular)
        geoPath.centroid(Point(pt(.0, .0))) shouldBeClose doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_an_empty_multipoint() {
        val geoPath = geoPath(equirectangular)
        geoPath.centroid(MultiPoint(arrayOf())) shouldBeClose doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_single_multipoint() {
        val geoPath = geoPath(equirectangular)
        geoPath.centroid(MultiPoint(arrayOf(pt(.0, .0)))) shouldBeClose doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_double_multipoint() {
        val geoPath = geoPath(equirectangular)
        geoPath.centroid(
            MultiPoint(
                arrayOf(
                    pt(-122.0, 37.0),
                    pt(-74.0, 40.0)
                )
            )
        ) shouldBeClose doubleArrayOf(-10.0, 57.5)
    }

    @Test
    fun geopath_centroid_of_an_empty_lineString() {
        val geoPath = geoPath(equirectangular)
        geoPath.centroid(LineString(arrayOf())) shouldBeClose doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_2_and_3_points() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(LineString(arrayOf(pt(100.0, .0), pt(.0, .0)))) shouldBeClose doubleArrayOf(730.0, 250.0)
        geoPath.centroid(
            LineString(
                arrayOf(
                    pt(.0, .0),
                    pt(100.0, .0),
                    pt(101.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(732.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_2_points_one_unique() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(LineString(arrayOf(pt(-122.0, 37.0), pt(-122.0, 37.0)))) shouldBeClose doubleArrayOf(-130.0, 65.0)
        geoPath.centroid(
            LineString(
                arrayOf(
                    pt(-74.0, 40.0),
                    pt(-74.0, 40.0)
                )
            )
        ) shouldBeClose doubleArrayOf(110.0, 50.0)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_3_points_2_unique() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            LineString(
                arrayOf(
                    pt(-122.0, 37.0),
                    pt(-74.0, 40.0),
                    pt(-74.0, 40.0)
                )
            )
        ) shouldBeClose doubleArrayOf(-10.0, 57.5)
    }

    @Test
    fun geopath_centroid_of_a_lineString_with_3_points() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            LineString(
                arrayOf(
                    pt(-122.0, 37.0),
                    pt(-74.0, 40.0),
                    pt(-100.0, .0)
                )
            )
        ) shouldBeClose doubleArrayOf(17.389135, 103.563545)
    }

    @Test
    fun geopath_centroid_of_a_multilineString() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            MultiLineString(
                arrayOf(
                    arrayOf(pt(100.0, .0), pt(.0, .0)),
                    arrayOf(pt(-10.0, .0), pt(.0, .0))
                )
            )
        ) shouldBeClose doubleArrayOf(705.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_single_ring_polygon() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_zero_area_polygon() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(1.0, .0),
                        pt(2.0, .0),
                        pt(3.0, .0),
                        pt(1.0, .0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(490.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_polygon_with_2_rings_one_with_a_zero_area() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(100.0, .0),
                        pt(100.0, 1.0),
                        pt(101.0, 1.0),
                        pt(101.0, .0),
                        pt(100.0, .0)
                    ),
                    arrayOf(
                        pt(100.1, .0),
                        pt(100.2, .0),
                        pt(100.3, .0),
                        pt(100.1, .0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_polygon_with_clockwise_exterior_and_anticlockwise_interior() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            Polygon(
                arrayOf(
                    arrayOf(
                        pt(-2.0, -2.0),
                        pt(2.0, -2.0),
                        pt(2.0, 2.0),
                        pt(-2.0, 2.0),
                        pt(-2.0, -2.0)
                    ).reversedArray(),
                    arrayOf(
                        pt(0.0, -1.0),
                        pt(1.0, -1.0),
                        pt(1.0, 1.0),
                        pt(0.0, 1.0),
                        pt(0.0, -1.0)
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(479.642857, 250.0)
    }

    @Test
    fun geopath_centroid_of_an_empty_multipolygon() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(MultiPolygon(arrayOf())) shouldBeClose doubleArrayOf(Double.NaN, Double.NaN)
    }

    @Test
    fun geopath_centroid_of_a_singleton_multipolygon() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(100.0, 0.0),
                            pt(100.0, 1.0),
                            pt(101.0, 1.0),
                            pt(101.0, 0.0),
                            pt(100.0, 0.0)
                        )
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_multipolygon_with_2_polygons() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(100.0, 0.0),
                            pt(100.0, 1.0),
                            pt(101.0, 1.0),
                            pt(101.0, 0.0),
                            pt(100.0, 0.0)
                        ), arrayOf(
                            pt(0.0, 0.0),
                            pt(1.0, 0.0),
                            pt(1.0, -1.0),
                            pt(0.0, -1.0),
                            pt(0.0, 0.0)
                        )
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(732.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_multipolygon_with_2_polygons_1_zone_area() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            pt(100.0, 0.0),
                            pt(100.0, 1.0),
                            pt(101.0, 1.0),
                            pt(101.0, 0.0),
                            pt(100.0, 0.0)
                        ), arrayOf(
                            pt(0.0, 0.0),
                            pt(1.0, 0.0),
                            pt(2.0, 0.0),
                            pt(0.0, 0.0)
                        )
                    )
                )
            )
        ) shouldBeClose doubleArrayOf(982.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_single_point() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(GeometryCollection(arrayOf(Point(pt(.0, .0))))) shouldBeClose doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_point_and_lineString() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            GeometryCollection(
                arrayOf(
                    LineString(arrayOf(pt(179.0, .0), pt(180.0, .0))),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose doubleArrayOf(1377.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_geometryCollection_with_point_and_lineString_and_polygon() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            GeometryCollection(
                arrayOf(
                    Polygon(
                        arrayOf(
                            arrayOf(
                                pt(-180.0, .0),
                                pt(-180.0, 1.0),
                                pt(-179.0, 1.0),
                                pt(-179.0, .0),
                                pt(-180.0, .0)
                            )
                        )
                    ),
                    LineString(arrayOf(pt(179.0, .0), pt(180.0, .0))),
                    Point(pt(.0, .0))
                )
            )
        ) shouldBeClose doubleArrayOf(-417.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_featureCollection_with_point() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(FeatureCollection(arrayOf(Feature(Point(pt(.0, .0)))))) shouldBeClose doubleArrayOf(480.0, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_FeatureCollection_with_point_and_lineString() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            FeatureCollection(
                arrayOf(
                    Feature(LineString(arrayOf(pt(179.0, .0), pt(180.0, .0)))),
                    Feature(Point(pt(.0, .0)))
                )
            )
        ) shouldBeClose doubleArrayOf(1377.5, 250.0)
    }

    @Test
    fun geopath_centroid_of_a_FeatureCollection_with_point_and_lineString_and_polygon() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(
            FeatureCollection(
                arrayOf(
                    Feature(
                        Polygon(
                            arrayOf(
                                arrayOf(
                                    pt(-180.0, .0),
                                    pt(-180.0, 1.0),
                                    pt(-179.0, 1.0),
                                    pt(-179.0, .0),
                                    pt(-180.0, .0)
                                )
                            )
                        )
                    ),
                    Feature(LineString(arrayOf(pt(179.0, .0), pt(180.0, .0)))),
                    Feature(Point(pt(.0, .0)))
                )
            )
        ) shouldBeClose doubleArrayOf(-417.5, 247.5)
    }

    @Test
    fun geopath_centroid_of_a_sphere() {
        val geoPath = geoPath(equirectangular)

        geoPath.centroid(Sphere()) shouldBeClose doubleArrayOf(480.0, 250.0)
    }
}
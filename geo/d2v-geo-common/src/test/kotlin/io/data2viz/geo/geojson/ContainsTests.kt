package io.data2viz.geo.geojson

import io.data2viz.geo.geojson.path.GeoCircle
import io.data2viz.geo.geometry.shouldBeBoolean
import io.data2viz.geo.projection.pt
import io.data2viz.geojson.*
import io.data2viz.test.TestBase
import kotlin.test.Test

class ContainsTests : TestBase() {

    @Test
    fun a_sphere_contains_any_point() {
        Sphere().contains(pt(.0, .0)) shouldBe true
        Sphere().contains(pt(10000.0, .0)) shouldBe true
        Sphere().contains(pt(10000.0, -964524.0)) shouldBe true
    }

    @Test
    fun a_point_contains_itself_and_not_some_other_point() {
        Point(pt(.0, .0)).contains(pt(.0, .0)) shouldBe true
        Point(pt(1.0, 2.0)).contains(pt(1.0, 2.0)) shouldBe true
        Point(pt(.0, .0)).contains(pt(.0, 1.0)) shouldBe false
        Point(pt(1.0, 1.0)).contains(pt(1.0, .0)) shouldBe false
    }

    @Test
    fun a_multipoint_contains_any_of_its_points() {
        val multiPoint = MultiPoint(arrayOf(pt(.0, .0), pt(1.0, 2.0)))

        multiPoint.contains(pt(.0, .0)) shouldBe true
        multiPoint.contains(pt(1.0, 2.0)) shouldBe true
        multiPoint.contains(pt(1.0, 3.0)) shouldBe false
    }

    @Test
    fun a_linestring_contains_any_point_on_the_great_circle_path() {
        val lineString = LineString(arrayOf(pt(.0, .0), pt(1.0, 2.0)))

        lineString.contains(pt(.0, .0)) shouldBe true
        lineString.contains(pt(1.0, 2.0)) shouldBe true

        val interpolateFunction = geoInterpolate(doubleArrayOf(0.0, 0.0), doubleArrayOf(1.0, 2.0))

        lineString.contains(interpolateFunction.interpolate(0.3).toTypedArray()) shouldBe true
        lineString.contains(interpolateFunction.interpolate(1.3).toTypedArray()) shouldBe false
        lineString.contains(interpolateFunction.interpolate(-0.3).toTypedArray()) shouldBe false

    }

    @Test
    fun a_multilinestring_contains_any_point_on_one_of_its_components() {
        val multiLineString = MultiLineString(
            arrayOf(
                arrayOf(pt(.0, .0), pt(1.0, 2.0)),
                arrayOf(pt(2.0, 3.0), pt(4.0, 5.0))
            )
        )

        multiLineString.contains(pt(2.0, 3.0)) shouldBe true
        multiLineString.contains(pt(5.0, 6.0)) shouldBe false
    }

    @Test
    fun a_GeometryCollection_contains_a_point() {
        val collection = GeometryCollection(
            arrayOf(
                LineString(arrayOf(pt(-45.0, .0), pt(.0, .0))),
                LineString(arrayOf(pt(.0, .0), pt(45.0, .0)))
            )
        )

        collection.contains(pt(-45.0, .0)) shouldBe true
        collection.contains(pt(45.0, .0)) shouldBe true
        collection.contains(pt(12.0, 25.0)) shouldBe false
    }

    @Test
    fun a_feature_contains_a_point() {
        val feature = Feature(LineString(arrayOf(pt(.0, .0), pt(45.0, .0))))

        feature.contains(pt(45.0, .0)) shouldBe true
        feature.contains(pt(12.0, 25.0)) shouldBe false
    }

    @Test
    fun a_FeatureCollection_contains_a_point() {
        val featureCollection = FeatureCollection(
            arrayOf(
                Feature(LineString(arrayOf(pt(.0, .0), pt(45.0, .0)))),
                Feature(LineString(arrayOf(pt(-45.0, .0), pt(.0, .0))))
            )
        )

        featureCollection.contains(pt(45.0, .0)) shouldBe true
        featureCollection.contains(pt(-45.0, .0)) shouldBe true
        featureCollection.contains(pt(12.0, 25.0)) shouldBe false
    }

    @Test
    fun null_contains_nothing() {
        val featureCollection = FeatureCollection(arrayOf())

        featureCollection.contains(pt(.0, .0)) shouldBe false
        featureCollection.contains(pt(-45.0, .0)) shouldBe false
        featureCollection.contains(pt(12.0, 25.0)) shouldBe false
    }


    @Test
    fun a_Polygon_contains_a_point() {

        val geoCircle = GeoCircle<Int>()
        geoCircle.radius = { 60.0 }
        val polygon = geoCircle.circle()

        println("polygon = ${polygon.coordinates[0].size}")
        println("polygon = ${polygon.coordinates.joinToString{ ""+it.joinToString { "\n("+it.joinToString()+")"}}}")

        polygon.contains(pt(1.0, 1.0)) shouldBeBoolean  true
        polygon.contains(pt(-180.0, 0.0)) shouldBeBoolean false

    }

    @Test
    fun a_Polygon_with_a_hole_doesnt_contain_a_point() {

        val geoCircle = GeoCircle<Int>()
        geoCircle.radius = { 60.0 }
        val outer = geoCircle.circle().coordinates[0]
        geoCircle.radius = { 3.0 }
        val inner = geoCircle.circle().coordinates[0]

        val polygon = Polygon(arrayOf(outer, inner))

        polygon.contains(pt(1.0, 1.0)) shouldBeBoolean false
        polygon.contains(pt(5.0, 0.0)) shouldBeBoolean true
        polygon.contains(pt(65.0, 0.0)) shouldBeBoolean false

    }


    @Test
    fun a_MultiPolygon_contains_a_point() {

        val geoCircle = GeoCircle<Int>()
        geoCircle.radius = { 6.0 }
        val p1 = geoCircle.circle().coordinates
        geoCircle.center = { doubleArrayOf(90.0, 0.0) }
        val p2 = geoCircle.circle().coordinates

        val polygon = MultiPolygon(arrayOf(p1, p2))

        polygon.contains(pt(1.0, 1.0)) shouldBeBoolean true
        polygon.contains(pt(90.0, 1.0)) shouldBeBoolean true
        polygon.contains(pt(90.0, 45.0)) shouldBeBoolean false

    }
}
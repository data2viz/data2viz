package io.data2viz.contour

import io.data2viz.test.matchers.Matchers
//import kotlin.test.assertEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class ContourTests : Matchers {


    @Test
    fun `contours(values) returns the expected result for an empty polygon`() {

        val result = contour(2).contours(
                values(
                    0, 1,
                    0, 1
                ))[0].coordinates[0][0]


        val expected = listOf(
                arrayOf(2.0, 1.5),
                arrayOf(2.0, 0.5),
                arrayOf(1.5, 0.0),
                arrayOf(1.0, 0.5),
                arrayOf(1.0, 1.5),
                arrayOf(1.5, 2.0),
                arrayOf(2.0, 1.5)
        )

        checkValues(expected, result)
    }

    @Test
    fun `contours(values) returns the expected result for a simple polygon`(){
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val result = contours[0].coordinates[0][0]

        val expected = listOf(
            pt(6.0, 7.5),
            pt(6.0, 6.5),
            pt(6.0, 5.5),
            pt(6.0, 4.5),
            pt(6.0, 3.5),
            pt(5.5, 3.0),
            pt(4.5, 3.0),
            pt(3.5, 3.0),
            pt(3.0, 3.5),
            pt(3.0, 4.5),
            pt(3.0, 5.5),
            pt(3.0, 6.5),
            pt(3.0, 7.5),
            pt(3.5, 8.0),
            pt(4.5, 8.0),
            pt(5.5, 8.0),
            pt(6.0, 7.5)
        )
        checkValues(expected, result)
    }

    @Test
    fun `contours smooth(false)(values) returns the expected result for a simple polygon`(){
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                        0, 0, 0, 1, 2, 1, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                        0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val result = contours[0].coordinates[0][0]

        val expected = listOf(
            pt(6.0, 7.5),
            pt(6.0, 6.5),
            pt(6.0, 5.5),
            pt(6.0, 4.5),
            pt(6.0, 3.5),
            pt(5.5, 3.0),
            pt(4.5, 3.0),
            pt(3.5, 3.0),
            pt(3.0, 3.5),
            pt(3.0, 4.5),
            pt(3.0, 5.5),
            pt(3.0, 6.5),
            pt(3.0, 7.5),
            pt(3.5, 8.0),
            pt(4.5, 8.0),
            pt(5.5, 8.0),
            pt(6.0, 7.5)
        )
        checkValues(expected, result)
    }



    @Test
    fun `contours(values) returns the expected result for a polygon with a hole`(){
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val extern = contours[0].coordinates[0][0]
//        val hole = contours[0].coordinates[0][1]

        val expected = listOf(
            pt(6.0, 7.5),
            pt(6.0, 6.5),
            pt(6.0, 5.5),
            pt(6.0, 4.5),
            pt(6.0, 3.5),
            pt(5.5, 3.0),
            pt(4.5, 3.0),
            pt(3.5, 3.0),
            pt(3.0, 3.5),
            pt(3.0, 4.5),
            pt(3.0, 5.5),
            pt(3.0, 6.5),
            pt(3.0, 7.5),
            pt(3.5, 8.0),
            pt(4.5, 8.0),
            pt(5.5, 8.0),
            pt(6.0, 7.5)
        )
        checkValues(expected, extern)
    }


    fun pt(x:Double, y:Double) = arrayOf(x, y)


    private fun checkValues(expected: List<Array<Double>>, result: List<Array<Double>>) {
        val zip = expected.zip(result)
        zip.forEach{
            assertEquals(it.first[0], it.second[0], .000001)
            assertEquals(it.first[1], it.second[1], .000001)
        }
    }


    private fun values(vararg vals: Int) = vals
                .map { it.toDouble() }
                .toTypedArray()

    @Test
    fun `empty polygon`(){

    }


    private fun contour(size:Int) = contour {
        size(size, size)
        thresholds = { arrayOf(0.5) }
    }


    private fun polyg(vararg pts: Array<Double>):GeoJson = geoJson(coordinates = listOf(arrayOf(pts.toList())))


    private fun geoJson(type: String = "MultiPolygon", value: Double = 0.5, coordinates: List<Array<List<Array<Double>>>>) =
            GeoJson(type, value, coordinates)
}

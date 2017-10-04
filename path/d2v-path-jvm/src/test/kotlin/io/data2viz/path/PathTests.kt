package io.data2viz.path

import org.junit.Test
import kotlin.test.assertTrue

import javafx.scene.shape.*
import kotlin.math.PI


fun main(args: Array<String>) {
    PathTests().main()
}

class PathTests {

    @Test
    fun math() {
        assertTrue("Pi should be between 3 and 4") { 3.14 < PI && PI < 3.15 }
    }



    fun main(){
        val path = Path()

        val moveTo = MoveTo().apply {
            x = 0.0
            y = 0.0
        }

        val hLineTo = HLineTo().apply {
            x = 70.0
        }


        val quadCurveTo = QuadCurveTo().apply {
            x = 120.0
            y = 60.0
            controlX = 100.0
            controlY = 0.0
        }

        val lineTo = LineTo().apply {
            x = 175.0
            y = 55.0
        }

        val arcTo = ArcTo().apply {
            x = 50.0
            y = 50.0
            radiusX = 50.0
            radiusY = 50.0
        }
        with(path.elements) {
            add(moveTo)
            add(hLineTo)
            add(quadCurveTo)
            add(lineTo)
            add(arcTo)
        }
    }
}



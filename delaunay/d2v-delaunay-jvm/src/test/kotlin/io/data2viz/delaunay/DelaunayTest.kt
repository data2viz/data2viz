package io.data2viz.delaunay

import java.lang.Math.random
import kotlin.test.Test



class DelaunayTest {
    
    @Test
    fun bench() {

        val points = (1..10_000).map { arrayOf(1000 * random(), 1000 * random()) }.toTypedArray()

        val time = System.currentTimeMillis()
        (1..50).forEach {
            Delaunator(points)
        }
        val meanTime = (System.currentTimeMillis() - time) / 50.0
        val ops = 1000.0 / meanTime
        println("${ops.toInt()} ops/sec")

    }
    
    
    
    
}
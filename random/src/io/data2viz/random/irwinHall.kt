package io.data2viz.random

import kotlin.js.Math

fun randomIrwinHall(n: Number = 1): () -> Double = {
    var sum = 0.0
    (0 until n.toInt()).forEach {
        sum += Math.random()
    }
    sum
}
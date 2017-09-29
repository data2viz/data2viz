package io.data2viz.random


fun randomIrwinHall(n: Number = 1): () -> Double = {
    var sum = 0.0
    (0 until n.toInt()).forEach {
        sum += random()
    }
    sum
}

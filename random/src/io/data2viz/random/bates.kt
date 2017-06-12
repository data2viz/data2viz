package io.data2viz.random

fun randomBates(n: Number = 1): () -> Double = {
    randomIrwinHall(n)() / n.toDouble()
}
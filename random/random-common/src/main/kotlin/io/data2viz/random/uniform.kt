package io.data2viz.random

fun randomUniform(min: Number = 0, max: Number = 1): () -> Double = {
    random() * (max.toDouble() - min.toDouble()) + min.toDouble()
}

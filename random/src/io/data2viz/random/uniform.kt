package io.data2viz.random

import kotlin.js.Math

fun randomUniform(min: Number = 0, max: Number = 1): () -> Double = {
    Math.random() * (max.toDouble() - min.toDouble()) + min.toDouble()
}
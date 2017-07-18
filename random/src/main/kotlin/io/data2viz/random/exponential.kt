package io.data2viz.random

import kotlin.js.Math

fun randomExponential(lambda: Number = 1): () -> Double = {
    -Math.log(1 - Math.random()) / lambda.toDouble()
}
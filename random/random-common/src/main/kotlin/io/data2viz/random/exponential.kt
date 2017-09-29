package io.data2viz.random

import kotlin.math.ln

fun randomExponential(lambda: Number = 1): () -> Double = {
    -ln(1 - random()) / lambda.toDouble()
}

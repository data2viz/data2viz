package io.data2viz.random

import kotlin.math.ln
import kotlin.math.sqrt


fun randomNormal(mu: Number = 0, sigma: Number = 1): () -> Double = {
    var x: Double? = null
    var r = 0.0
    var y = 0.0

    if (x != null) {
        y = x
    } else {
        while (r == 0.0 || r > 1) {
            x = random() * 2 - 1
            y = random() * 2 - 1
            r = x * x + y * y
        }
    }
    mu.toDouble() + sigma.toDouble() * y * sqrt(-2 * ln(r) / r)
}

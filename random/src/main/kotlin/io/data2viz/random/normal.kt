package io.data2viz.random

import kotlin.js.Math

fun randomNormal(mu: Number = 0, sigma: Number = 1): () -> Double = {
    var x: Double? = null
    var r: Double = 0.0

    var y: Double = 0.0

    if (x != null) {
        y = x
        x = null
    } else {
        while (r == 0.0 || r > 1) {
            x = Math.random() * 2 - 1
            y = Math.random() * 2 - 1
            r = x * x + y * y
        }
    }
    mu.toDouble() + sigma.toDouble() * y * Math.sqrt(-2 * Math.log(r) / r)
}
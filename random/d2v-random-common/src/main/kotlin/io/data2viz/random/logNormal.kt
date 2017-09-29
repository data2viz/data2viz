package io.data2viz.random

import kotlin.math.exp


fun randomLogNormal(mu: Number = 0, sigma: Number = 1): () -> Double = {
    exp(randomNormal(mu, sigma)())
}
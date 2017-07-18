package io.data2viz.random

import kotlin.js.Math

fun randomLogNormal(mu: Number = 0, sigma: Number = 1): () -> Double = {
    Math.exp(randomNormal(mu, sigma)())
}
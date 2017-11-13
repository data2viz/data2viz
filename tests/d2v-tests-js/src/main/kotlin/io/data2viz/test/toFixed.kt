package io.data2viz.test

import kotlin.math.abs
import kotlin.math.round

actual fun Double.toFixed(): String {
    return if (abs(this - round(this)) < 1e-6)
        round(this).toString()
    else this.asDynamic().toFixed(6)
}

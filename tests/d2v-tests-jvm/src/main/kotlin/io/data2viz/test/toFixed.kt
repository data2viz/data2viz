package io.data2viz.test

import java.math.BigDecimal
import java.math.RoundingMode


actual fun Double.toFixed(): String {
    return if (kotlin.math.abs(this - kotlin.math.round(this)) < 1e-6)
        kotlin.math.round(this).toString().dropLast(2)
    else BigDecimal(this).setScale(6, RoundingMode.HALF_UP).toString()
}

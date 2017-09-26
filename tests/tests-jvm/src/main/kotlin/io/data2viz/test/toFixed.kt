package io.data2viz.test

import java.math.BigDecimal


impl fun Double.toFixed(): String {
    return if (kotlin.math.abs(this - kotlin.math.round(this)) < 1e-6)
        kotlin.math.round(this).toString()
    else BigDecimal(this).setScale(6).toString()
}

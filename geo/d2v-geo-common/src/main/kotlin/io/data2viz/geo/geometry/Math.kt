package io.data2viz.geo.geometry

import io.data2viz.math.HALFPI
import io.data2viz.math.PI
import kotlin.math.acos
import kotlin.math.asin

/**
 * @return HALFPI if value > 1 or value < -1, in other cases asin(value)
 */
val Double.limitedAsin: Double
    get() = when {
        this > 1 -> HALFPI
        this < -1 -> -HALFPI
        else -> asin(this)
    }

/**
 * @return 0 if value > 1, PI if value < -1, in other cases acos(value)
 */
val Double.limitedAcos: Double
    get() = when {
        this > 1 -> .0
        this < -1 -> PI
        else -> acos(this)
    }

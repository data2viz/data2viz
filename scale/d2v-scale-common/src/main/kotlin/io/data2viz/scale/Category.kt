package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.color.EncodedColors

/**
 * Category Scales
 */

class CategoryScale<D>(range: List<Color>) : OrdinalScale<D, Color>() {
    init {
        super.range = range
    }
}

fun <D> scaleCategory10()       = CategoryScale<D>(EncodedColors.category10.colors)
fun <D> scaleCategory20()       = CategoryScale<D>(EncodedColors.category20.colors)
fun <D> scaleCategory20b()      = CategoryScale<D>(EncodedColors.category20b.colors)
fun <D> scaleCategory20c()      = CategoryScale<D>(EncodedColors.category20c.colors)
fun <D> scaleCategoryViridis()  = CategoryScale<D>(EncodedColors.viridis.colors)
fun <D> scaleCategoryMagma()    = CategoryScale<D>(EncodedColors.magma.colors)
fun <D> scaleCategoryInferno()  = CategoryScale<D>(EncodedColors.inferno.colors)
fun <D> scaleCategoryPlasma()   = CategoryScale<D>(EncodedColors.plasma.colors)
package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.extentPostClip
import io.data2viz.geo.projection.common.ComposedProjection
import io.data2viz.geo.projection.common.Projection
import io.data2viz.geom.Extent
import io.data2viz.math.EPSILON
import io.data2viz.math.deg

fun albersUSAProjection() = albersUSAProjection {

}

fun albersUSAProjection(init: AlbersUSAProjection.() -> Unit) = AlbersUSAProjection().also {
    it.scale = 1070.0
}.also(init)

//// A composite projection for the United States, configured by default for
//// 960×500. The projection also works quite well at 960×600 if you change the
//// scale to 1285 and adjust the translate accordingly. The set of standard
//// parallels for each region comes from USGS, which is published here:
//// http://egsc.usgs.gov/isb/pubs/MapProjections/projections.html#albers
class AlbersUSAProjection() : ComposedProjection() {


    val lower48 = albersProjection()
    val alaska = conicEqualAreaProjection {
        rotate(154.0.deg, 0.0.deg)
        center((-2.0).deg, 58.5.deg)
        parallels(55.0.deg, 65.0.deg)
    }
    val hawaii = conicEqualAreaProjection {
        rotate(157.0.deg, 0.0.deg)
        center((-3.0).deg, 19.9.deg)
        parallels(8.0.deg, 18.0.deg)

    }
    override val mainProjection: Projection
        get() = lower48
    override val allProjections: Collection<Projection> = listOf(lower48, alaska, hawaii)


    override var scale: Double
        get() = lower48.scale
        set(value) {
            lower48.scale = value
            alaska.scale = value * 0.35
            hawaii.scale = value
            reset()
        }

    override var precision: Double
        get() = lower48.precision
        set(value) {
            lower48.precision = value
            alaska.precision = value * 0.35
            hawaii.precision = value
            reset()
        }


    // TODO: Strange logic from d3, but without custom translate projection not properly centered
    var customTranslateX = 0.0
    var customTranslateY = 0.0

    override var translateX: Double
        get() = super.translateX
        set(value) {
            super.translateX = value
            customTranslateX += value
            translateNestedProjections()
        }

    override var translateY: Double
        get() = super.translateY
        set(value) {
            super.translateY = value
            customTranslateY += value
            translateNestedProjections()
        }

    private fun translateNestedProjections() {
        var k = lower48.scale

        val x = customTranslateX
        val y = customTranslateY
        lower48.translate(x, y)
        lower48.extentPostClip = Extent(x - 0.455 * k, y - 0.238 * k, x + 0.455 * k, y + 0.238 * k)

        alaska.translate(x - 0.307 * k, y + 0.201 * k)
        alaska.extentPostClip = Extent(
            x - 0.425 * k + EPSILON,
            y + 0.120 * k + EPSILON,
            x - 0.214 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )

        hawaii.translate(x - 0.205 * k, y + 0.212 * k)
        hawaii.extentPostClip = Extent(
            x - 0.214 * k + EPSILON,
            y + 0.166 * k + EPSILON,
            x - 0.115 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )

        reset()
    }

    override fun chooseNestedProjection(lambda: Double, phi: Double): Projection {
        val k = lower48.scale

        val newX = (lambda - lower48.translateX) / k
        val newY = (phi - lower48.translateY) / k

        return when {
            newY >= 0.120 && newY < 0.234 && newX >= -0.425 && newX < -0.214 -> alaska
            newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115 -> hawaii
            else -> lower48
        }
    }
}

package io.data2viz.geo.projection

import io.data2viz.geo.ComposedProjection
import io.data2viz.geo.Projection
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
        rotate = arrayOf(154.0.deg, 0.0.deg)
        center = arrayOf((-2.0).deg, 58.5.deg)
        parallels = arrayOf(55.0.deg, 65.0.deg)
    }
    val hawaii = conicEqualAreaProjection {
        rotate = arrayOf(157.0.deg, 0.0.deg)
        center = arrayOf((-3.0).deg, 19.9.deg)
        parallels = arrayOf(8.0.deg, 18.0.deg)

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

    var translateX = 0.0
    var translateY = 0.0

    override var x: Double
        get() = super.x
        set(value) {
            super.x = value
            translateX += value
            translateNestedProjections()
        }

    override var y: Double
        get() = super.y
        set(value) {
            super.y = value
            translateY += value
            translateNestedProjections()
        }

    private fun translateNestedProjections() {
        var k = lower48.scale

        val x = translateX
        val y = translateY
        lower48.translate(x, y)
        lower48.clipExtent = Extent(x - 0.455 * k, y - 0.238 * k, x + 0.455 * k, y + 0.238 * k)

        alaska.translate(x - 0.307 * k, y + 0.201 * k)
        alaska.clipExtent = Extent(
            x - 0.425 * k + EPSILON,
            y + 0.120 * k + EPSILON,
            x - 0.214 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )

        hawaii.translate(x - 0.205 * k, y + 0.212 * k)
        hawaii.clipExtent = Extent(
            x - 0.214 * k + EPSILON,
            y + 0.166 * k + EPSILON,
            x - 0.115 * k - EPSILON,
            y + 0.234 * k - EPSILON
        )

        reset()
    }

    override fun chooseNestedProjection(lambda: Double, phi: Double): Projection {
        val k = lower48.scale

        val newX = (lambda - lower48.x) / k
        val newY = (phi - lower48.y) / k

        return when {
            newY >= 0.120 && newY < 0.234 && newX >= -0.425 && newX < -0.214 -> alaska
            newY >= 0.166 && newY < 0.234 && newX >= -0.214 && newX < -0.115 -> hawaii
            else -> lower48
        }
    }
}

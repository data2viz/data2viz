package io.data2viz.geo.geojson.path

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geometry.cartesian
import io.data2viz.geo.geometry.cartesianNormalize
import io.data2viz.geo.projection.common.rotateRadians
import io.data2viz.geojson.Polygon
import io.data2viz.math.EPSILON
import io.data2viz.math.TAU
import io.data2viz.math.toDegrees
import io.data2viz.math.toRadians
import kotlin.math.*

class GeoCircle<D> {

    private var ring: MutableList<Array<Double>> = mutableListOf()
    private var rotate: ((x: Double, y: Double) -> DoubleArray)? = null

    private val circleStream: Stream = object : Stream {
        override fun point(x: Double, y: Double, z: Double) {
            val value = rotate!!(x, y)
            ring.add(arrayOf(value[0].toDegrees(), value[1].toDegrees()))
        }
    }


    /**
     * Sets the circle center to the specified point [longitude, latitude] in degrees, specified as a function;
     * this function will be invoked whenever a circle is generated, being passed any arguments passed to the circle
     * generator.
     */
    var center: (D?) -> DoubleArray = { doubleArrayOf(.0, .0) }

    /**
     * Sets the circle radius as specified; this function will be invoked whenever a circle is generated,
     * being passed any arguments passed to the circle generator.
     */
    var radius: (D?) -> Double = { 90.0 }

    /**
     * Sets the circle precision as specified; this function will be invoked whenever a circle is generated,
     * being passed any arguments passed to the circle generator.
     * Small circles do not follow great arcs and thus the generated polygon is only an approximation.
     * Specifying a smaller precision angle improves the accuracy of the approximate polygon, but also increase the
     * cost to generate and render it.
     */
    var precision: (D?) -> Double = { 6.0 }

    /**
     * Returns a new GeoJSON geometry object of type “Polygon” approximating a circle on the surface of a sphere,
     * with the current center, radius and precision.
     * TODO : keep data ?
     */
    fun circle(data: D? = null): Polygon {

        val c = center(data).map { -it.toRadians() }.toDoubleArray()
        val r = radius(data).toRadians()
        val p = precision(data).toRadians()

        rotate = rotateRadians(c[0], c[1], .0)::invert
        geoCircle(circleStream, r, p, 1)
        val result = Polygon(arrayOf(ring.toTypedArray()))

        ring.clear()
        rotate = null

        return result
    }
}

/**
 * TODO: docs
 * Generates a circle centered at [0°, 0°], with a given radius and precision.
 */
fun geoCircle(
    stream: Stream,
    radius: Double,
    delta: Double,
    direction: Int,
    t0: DoubleArray? = null,
    t1: DoubleArray? = null
) {
    if (delta == .0) return

    val cosRadius = cos(radius)
    val sinRadius = sin(radius)
    val step = direction * delta

    var newT0: Double
    val newT1: Double
    if (t0 == null) {
        newT0 = radius + direction * TAU
        newT1 = radius - (step / 2)
    } else {
        newT0 = circleRadius(cosRadius, t0)
        newT1 = circleRadius(cosRadius, t1!!)
        if ((direction > 0 && newT0 < newT1) || (direction < 0 && newT0 > newT1)) {
            newT0 += direction * TAU
        }
    }

    var t = newT0
    while (if (direction > 0) t > newT1 else t < newT1) {
        val cartesian0 = cosRadius
        val cartesian1 = -sinRadius * cos(t)
        val cartesian2 = -sinRadius * sin(t)

        val spher0 = atan2(cartesian1, cartesian0)
        val spher1 = asin(cartesian2)
        stream.point(spher0, spher1, .0)
        t -= step
    }
}

/**
 * Returns the signed angle of a cartesian point relative to [cosRadius, 0, 0].
 */
private fun circleRadius(cosRadius: Double, point: DoubleArray): Double {
    var p = cartesian(point)
    p[0] -= cosRadius
    p = cartesianNormalize(p)
    val radius = acos(-p[1])
    return ((if (-p[2] < 0) -radius else radius) + TAU - EPSILON) % TAU
}
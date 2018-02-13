package io.data2viz.geo.path

import io.data2viz.geo.projection.Extent
import io.data2viz.geo.projection.Stream

class PathBounds : Stream {

    private var bounds =
        Extent(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)

    fun result(): Extent {
        val result = bounds.copy()
        bounds = Extent(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)
        return result
    }

    override fun point(x: Double, y: Double, z: Double) {
        if (x < bounds.x0) bounds.x0 = x
        if (x > bounds.x1) bounds.x1 = x
        if (y < bounds.y0) bounds.y0 = y
        if (y > bounds.y1) bounds.y1 = y
    }
}
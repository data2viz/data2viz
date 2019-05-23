package io.data2viz.geo.stream

import io.data2viz.math.toRadians


/**
 * This stream delegates all calls to the delegate, allowing to override only what should
 * be treated another way.
 */
open class DelegateStreamAdapter(val delegate: Stream) : Stream {
    override fun point(x: Double, y: Double, z: Double)     = delegate.point(x, y, z)
    override fun lineStart()                                = delegate.lineStart()
    override fun lineEnd()                                  = delegate.lineEnd()
    override fun polygonStart()                             = delegate.polygonStart()
    override fun polygonEnd()                               = delegate.polygonEnd()
    override fun sphere()                                   = delegate.sphere()
}



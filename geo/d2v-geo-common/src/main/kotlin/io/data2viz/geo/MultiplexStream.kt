package io.data2viz.geo


class MultiplexStream(val streams: Collection<Stream>) : Stream {
    override fun point(x: Double, y: Double, z: Double) = streams.forEach {
        it.point(x, y, z)
    }

    override fun lineStart() = streams.forEach {
        it.lineStart()
    }

    override fun lineEnd() = streams.forEach {
        it.lineEnd()
    }

    override fun polygonStart() = streams.forEach {
        it.polygonStart()
    }

    override fun polygonEnd() = streams.forEach {
        it.polygonEnd()
    }

    override fun sphere() = streams.forEach {
        it.sphere()
    }
}
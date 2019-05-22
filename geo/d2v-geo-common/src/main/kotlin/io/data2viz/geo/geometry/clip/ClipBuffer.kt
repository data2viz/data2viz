package io.data2viz.geo.geometry.clip

import io.data2viz.geo.stream.Stream

class ClipBuffer : Stream {
    private var lines: MutableList<List<DoubleArray>> = mutableListOf()
    private lateinit var line: MutableList<DoubleArray>

    override fun point(x: Double, y: Double, z: Double) {
        line.add(doubleArrayOf(x, y))
    }

    override fun lineStart() {
        line = mutableListOf()

        lines.add(line)
    }

    fun rejoin() {
        if (lines.size > 1) {
            val l = mutableListOf<List<DoubleArray>>()
            l.add(lines.removeAt(lines.lastIndex))
            l.add(lines.removeAt(0))
            lines.addAll(l)
        }
    }

    fun result(): MutableList<List<DoubleArray>> {
        val oldLines = lines
        lines = mutableListOf()

        return oldLines
    }
}
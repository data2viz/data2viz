package io.data2viz.geo.clip

import io.data2viz.geo.projection.Stream

class ClipBuffer : Stream {
    private val lines: MutableList<List<DoubleArray>> = mutableListOf()
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

    fun result(): List<List<DoubleArray>> {
        val result = mutableListOf<List<DoubleArray>>()
        result.addAll(lines)
        lines.clear()
        return result
    }
}
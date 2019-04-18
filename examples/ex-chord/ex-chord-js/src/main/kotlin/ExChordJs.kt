package io.data2viz.examples.chord

import io.data2viz.viz.bindRendererOn

@Suppress("unused")
fun main(args: Array<String>) {
    val chordViz = chordViz()
    chordViz.bindRendererOn("chord")
    chordViz.addEvents()
}

package io.data2viz.viz

class LineNode : Node(),
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x1: Double = .0
    var y1: Double = .0
    var x2: Double = .0
    var y2: Double = .0

}
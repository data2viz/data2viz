package io.data2viz.quadtree

@JsName("quadtree")
fun quadtree(points: Array<Array<Double>>) = quadtree({ d: Array<Double> -> d[0] }, { d: Array<Double> -> d[1] }, points.asList())
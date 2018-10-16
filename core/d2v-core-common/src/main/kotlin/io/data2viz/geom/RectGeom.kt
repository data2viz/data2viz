package io.data2viz.geom



class RectGeom: Rect {
	override var x: Double = .0
	override var y: Double = .0
	override var width: Double = .0
	override var height: Double = .0
}

interface Rect {
	var x: Double
	var y: Double
	var width: Double
	var height: Double

	val top
		get() = y

	val bottom
		get() = y + height

	val left
		get() = x

	val right
		get() = x + width

	val center:Point
		get() = Point(x + .5 * width, y + .5 * height)

}

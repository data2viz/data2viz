package io.data2viz.geom

interface Rect: HasSize {

	var x: Double
	var y: Double

	val top
		get() = y

	val bottom
		get() = y + height

	val left
		get() = x

	val right
		get() = x + width

	val topLeft:Point
		get() = Point(x, y)

	val topRight:Point
		get() = Point(x + width, y)

	val bottomLeft:Point
		get() = Point(x, y + height)

	val bottomRight:Point
		get() = Point(x + width, y + height)

	val center: Point
		get() = Point(x + .5 * width, y + .5 * height)

}
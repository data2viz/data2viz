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

	operator fun contains(point: Point):Boolean {
		val x = point.x
		val y = point.y
		return x >= this.x && y >= this.y
				&& x <= this.x + this.width
				&& y <= this.y + this.height
	}

	operator fun contains(rect: Rect):Boolean {
		val x = rect.x
		val y = rect.y
		return x >= this.x && y >= this.y
                && x + rect.width <= this.x + this.width
                && y + rect.height <= this.y + this.height
	}

}
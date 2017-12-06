# d2v-path

d2v-path provides an unified way of rendering path into different contexts and platforms.
It can be deployed through kotlin multiplatform:
- in the browser as SVG or canvas, 
- in a JVM as a SVGPath inside JavaFx. 

It uses a simplified API that is the base of a lot of rendering.


```kotlin
interface PathAdapter {
    fun moveTo(x:Double, y: Double)
    fun lineTo(x:Double, y: Double)
    fun closePath()
    fun quadraticCurveTo(x1: Double, y1: Double, x: Double, y: Double)
    fun bezierCurveTo(x1: Double, y1: Double,x2: Double, y2: Double, x: Double, y: Double)
    fun arcTo(fromX:Double, fromY:Double, toX:Double, toY:Double, radius:Double)
    fun arc(centerX:Double, centerY:Double, radius:Double, startAngle:Double, endAngle:Double, counterClockWise:Boolean = false)
    fun rect(x:Double, y:Double, w:Double, h:Double)
}
```


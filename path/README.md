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

The easiest way of using it is through [d2v-viz module]()


From example this code  from a kotlin-js application

```kotlin
fun main(args: Array<String>) {

    val svg = document.querySelector("svg") as SVGElement

    svg.viz {
        path {
            fill = colors.orange
            moveTo(.0, .0)
            lineTo(100.0, 0.0)
            lineTo(.0, 100.0)
            closePath()
        }

        path {
            fill = colors.steelblue
            moveTo(.0, .0)
            lineTo(50.0, 0.0)
            lineTo(.0, 50.0)
            closePath()
        }

        path {
            fill = colors.steelblue
            moveTo(50.0, 50.0)
            lineTo(100.0, 100.0)
            lineTo(.0, 100.0)
            closePath()
        }    }
}
```

produces this rendering:
 
 <img src="../docs/img/path-in-viz.png">



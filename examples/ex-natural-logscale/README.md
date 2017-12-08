This example shows how to plot a mathematical function. It uses axis and scales 
(linear scale for absciss and log scale for ordinate).

This version is multiplatform: the visualization code is written in a common module
and then is just rendered in JavaFx or Js SVG element.

<img src="docs/natural-log-scale-jfx.png" width="900">

This is the code that manage
 visual elements: [<>](https://github.com/data2viz/data2viz/blob/master/examples/ex-natural-logscale/ex-natural-logscale-common/src/main/kotlin/NaturalLogScale.kt)

```kotlin
  group {
      transform {
          translate(x = -10.0)
      }
      axis(Orient.LEFT, yScale) {
          tickFormat = { "e${superscript[round(ln(it)).toInt()]}" } // <- specific formatter to add exponents (ex: e¹)
      }
  }

  group {
      transform {
          translate(y = height + 10.0)
      }
      axis(Orient.BOTTOM, xScale)       // <- default axis. Labels are provided by the x scale.
  }

  group {
      path {                            // <- the curve is rendered with a path.
          fill = null
          stroke = colors.steelblue     // <- code completion due to typed system
          strokeWidth = 1.5

          moveTo(xScale(points[0].x), yScale(points[0].y))
          (1..100).forEach {
              lineTo(xScale(points[it].x), yScale(points[it].y))
          }
      }
  }
```

It's then possible to bootstrap this code inside the DOM :[<>](https://github.com/data2viz/data2viz/blob/master/examples/ex-natural-logscale/ex-natural-logscale-js/src/main/kotlin/NaturalLogScaleJs.kt)

```kotlin
    val root = selectOrCreateSvg().apply {
        setAttribute("width", "${width + margins.hMargins}")
        setAttribute("height", "${height + margins.vMargins}")
    }

    root.viz {
        naturalLogScale()
    }

```

or in a Java FX application: [<>](https://github.com/data2viz/data2viz/blob/master/examples/ex-natural-logscale/ex-natural-logscale-jvm/src/main/kotlin/NaturalLogScaleJvm.kt)

```kotlin
    override fun start(primaryStage: Stage?) {
        val root = Group()

        root.viz {
            naturalLogScale()
        }

        primaryStage?.let {
            it.scene = (Scene(root, width + margins.hMargins, height + margins.vMargins))
            it.show()
        }
    }
```

This example is a port of this [D3js example](https://bl.ocks.org/mbostock/7621155) 
in data2viz.



This module defines the root DSL of data2viz. It manage the boostraping of dataviz on
different platform contexts (SVG-js, JavaFx).

# Bootstraping a dataviz

The idea of Data2Viz is to propose a single DSL that applies to different deployment target.

Depending on the current platform, you have to call the `viz` extension fonction 
on :
- a `SVG Element` if you are on the browser
- a `Group` node if you are in a JavaFx application.

### In a kotlin JS application

```kotlin
fun showViz() {           // <- entry point of this JS application

    val svgElement = (document.querySelector("svg") as SVGElement)  // ref on an existing SVG Element
    svgElement.setAttribute("width", "500")
    svgElement.setAttribute("height", "500")

    svgElement.viz {      // <- Extension function on SVGElement  
        circle {          // <- starting from here we have the same DSL             
            radius = 3.0
        }
    }

}
```

### In a JavaFX application

```kotlin
class HelloApp : Application() {                    // <- Jfx Application
    override fun start(primaryStage: Stage?) {
        val root = Group()
        root.viz {      //   <- Extension function on Group  
            circle {
                radius = 3.0
            }
        }

        primaryStage!!.scene = (Scene(root, 500.0, 500.0))
        primaryStage.show()
    }
}
```

### Deploying a common dataviz on different targets
You can also define your dataviz in a common module and then deploy it in various 
context:
```kotlin

/**
 *  Dataviz defined as an extension on VizContext in a common module 
 */
fun VizContext.commonViz(data: List<Domain>) {

    data.forEach { datum ->
        circle {
            cx = datum.val1
            cy = datum.val2
        }
    }
}

```

And then use it in the context of your choice.


```kotlin
fun showViz() {

    val svgElement = (document.querySelector("svg") as SVGElement)
    
    svgElement.setAttribute("width", "500")
    svgElement.setAttribute("height", "500")

    svgElement.viz {
        commonViz(data)
    }

}

```


#DSL Flavours

There are two versions of it, a static version
and a dynamic one.

### Static DSL
By static DSL, we mean that the create elements are not going to change after the first
display. We have data, we use it to create the dataviz and that's it.

The main interest of this DSL is its simplicity. As we don't have to manage a behaviour on 
how to react to new data, update, or remove of data, the definition of a dataviz is 
straightforward.

### Dynamic DSL
TODO



# README #

### What is this repository for? ###

data2viz is a data visualization library which targets several platform: browser, jvm, android, ... It relies on 
kotlin which offers advanced language paradigms and can compile to different platforms.

data2viz proposes to develop data visualizations through a fully typed DSL. It simplifies the creation of complex
 visualizations by helping developer with IDE’s suggestions based on the current context.

### Current status ###

data2viz is in its early age. It should not be used in production now. We are experimenting various DSL
 implementations. DSL will be validated after having implemented all the use cases. It should last few months.

### Some DSL samples  

The internal DSL allows to create data visualization using hierarchical 
code that should be easy to understand.

```kotlin
g {
    transform {
        translate(margin.left, margin.top)
        rotate(20.deg)
    }
    
    rect {
        width = totalWidth - margin.horizontalMargins
        height = totalHeight - margin.verticalMargins
        fill = rgba(0, 0, 0, .1)
    }
}
```

In that code, `g` adds a new group. The next `{` starts a new block of code that is
applicable in the current context. Inside a group, we can apply a transformation. The 
`transform {` code starts a new block of code to define the properties of the transformation.

Having a typed DSL, the IDE proposes accurate suggestions depending on the position of 
  the caret. Inside a `transform` block, we can call `translate`, `rotate`, `scale`, 
  `skewX`, `skewY`,... 

The `rect {` code opens a block for adding a rectangle and configure it. Its width
height, and fill color are defined using an affectation. Again, having a strong DSL
allows to benefit from the IDE assistance to choose the correct values. `fill` is a
property of type `Color`. It can be created from a call on `rgb`, `rgba`, `hsl`, `hsla` functions
 or converted from an hex string. `"#ab1212".col()` or just by referencing a CSS color 
 (`steelblue`, `grey`,... ).

In any case, due to the strong typed language used, any error will be notified during the 
compilation phase.

<a href="http://data2viz.io/dist/chart.html">
 <img src="http://data2viz.io/img/chart.png" width="300">
 <br>a sample with axis, animation, scaling, ... 
</a>

### Performances

Even if current dist files are not optimized/minified you can try to 
[load this page](http://data2viz.io/dist/svgperfs.html) 
 to see how svg element manipulations from data2viz perform on your browser.
 
<a href="http://data2viz.io/dist/svgperfs.html">
 <img src="http://data2viz.io/img/perfs.png" width="300">
</a>
 

### Tests
Currently tests are executed as a single html page containing all tests. The modules
`tests` contains source taken from kotlintest framework for kotlin.js execution.

You can display the test page on your browser by clicking the following image. 

<a href="http://data2viz.io/dist/tests.html" target="_blank">
 <img src="http://data2viz.io/img/data2viz.tests.png" width="300">
</a>

## Inspirations
d3js, kotlinx.html, kotlintest

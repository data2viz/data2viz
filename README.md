
[![Download](https://api.bintray.com/packages/data2viz/data2viz/data2viz/images/download.svg) ](https://bintray.com/data2viz/data2viz/data2viz/_latestVersion)
[![Build Status](https://travis-ci.org/data2viz/data2viz.svg?branch=master)](https://travis-ci.org/data2viz/data2viz)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) 
### What is this repository for? ###

data2viz is a data visualization library which targets several platform: browser, jvm, android, ... It relies on
kotlin which offers advanced language paradigms and can compile to different platforms.

data2viz proposes to develop data visualizations through a fully typed DSL. It simplifies the creation of complex
 visualizations by helping developer with IDE’s suggestions based on the current context.

### Current status

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

<a href="http://data2viz.io/examples/chart/index.html">
 <img src="http://data2viz.io/img/chart.png" width="300">
 <br>a sample with axis, animation, scaling, ...
</a>


### Performances

It's difficult subject because a lot of parameter have an influence on the global 
performance of an javascript application: the browser, the type of executed code.

However, this is a example of quite heavy algorithm with a lot of rendering. The
number of points is increased until FPS gets lower than 40. Click on the link to 
test it inside your browser.

<a href="http://data2viz.io/examples/voronoisphere/index.html">
 <img src="http://data2viz.io/img/voronoisphere.png" width="300">
 <br>a sample with voronoi, animation.
</a>

### Tests
Tests are executed through karma and mocha and included in the build.

### How can I use it?

Remember: you should not use it in production now.

Artefacts are published on [Bintray](https://bintray.com/data2viz/data2viz/data2viz).

You can have a look on the project [data2viz-examples](https://github.com/data2viz/data2viz-examples) to
see how it is possible to use it.

## Inspirations
d3js, kotlinx.html, kotlintest

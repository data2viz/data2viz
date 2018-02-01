package io.data2viz.sankey

import io.data2viz.shape.link.linkHorizontal

val sankeyLinkHorizontal = linkHorizontal<SankeyLink<*>> {
    x0 = { it.source.x1 }
    y0 = { it.y0 }
    x1 = { it.target.x0 }
    y1 = { it.y1 }
}
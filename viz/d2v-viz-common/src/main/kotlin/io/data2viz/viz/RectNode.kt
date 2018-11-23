package io.data2viz.viz

import io.data2viz.geom.*


class RectNode(rect: Rect = RectGeom()) : Node(),
        Rect by rect,
        HasFill,
        HasStroke

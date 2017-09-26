package io.data2viz.path

import javafx.scene.shape.SVGPath

fun SvgPath.toJfxPath() = SVGPath().apply { content = this@toJfxPath.path }

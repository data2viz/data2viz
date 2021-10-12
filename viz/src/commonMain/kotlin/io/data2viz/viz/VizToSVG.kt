/**
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.Gradient
import io.data2viz.geom.*
import io.data2viz.math.EPSILON
import io.data2viz.math.TAU
import io.data2viz.math.TAU_EPSILON
import kotlin.math.*


public fun Viz.toSVG(): String = buildSvgString {
    add(
        type = "svg",
        attributes = {
            add("xmlns", "http://www.w3.org/2000/svg")
            add("width", width.toString())
            add("height", height.toString())
        }
    ) {
        layers.forEach { layer ->
            add(layer)
        }
    }
}

internal interface SvgStringBuilder {
    val builder: StringBuilder

    val gradients: MutableList<Any> // TODO

    fun add(
        type: String,
        attributes: (AttributesBuilder.() -> Unit)? = null,
        renderChildren: (SvgStringBuilder.() -> Unit)? = null,
    ) {
        builder.apply {
            append("<$type")
            if (attributes != null) {
                append(" ")
                attributes(AttributesBuilder)
                if (builder.takeLast(1) == " ") deleteAt(lastIndex)
            }

            if (renderChildren == null) {
                append("/>")
            } else {
                append(">")
                renderChildren()
                append("</$type>")
            }
        }
    }

    operator fun String.unaryPlus() {
        builder.append(this)
    }
}


private fun buildSvgString(build: SvgStringBuilder.() -> Unit): String = buildString {
    val svgStringBuilder = object : SvgStringBuilder {
        override val builder: StringBuilder = this@buildString
        override val gradients: MutableList<Any> get() = TODO("Not yet implemented")
    }
    svgStringBuilder.apply {
        builder.append("""<?xml version="1.0"?>""")
        build()
    }
}

internal object AttributesBuilder {
    fun SvgStringBuilder.add(key: String, value: String) {
        builder.append("$key=\"$value\" ")
    }

    fun SvgStringBuilder.addStyle(stylesBuilder: StylesBuilder.() -> Unit) {
        builder.apply {
            append("style=\"")
            stylesBuilder(StylesBuilder)
            if (builder.takeLast(1) == ";") deleteAt(lastIndex)
            append("\" ")
        }
    }

    fun SvgStringBuilder.addTransform(transformBuilder: TransformBuilder.() -> Unit) {
        builder.apply {
            append("transform=\"")
            transformBuilder(TransformBuilder)
            if (builder.takeLast(1) == "\n") deleteAt(lastIndex)
            append("\" ")
        }
    }

    fun SvgStringBuilder.addPath(pathBuilder: StringBuilder.() -> Unit) {
        builder.apply {
            append("d=\"")
            pathBuilder(this)
            append("\"")
        }
    }

    fun SvgStringBuilder.addStylesIfAvailableFor(node: Node) {
        if (node.hasStyles) addStyle {
            addStylesFor(node)
        }
    }

    fun SvgStringBuilder.addTransformsIfAvailableFor(node: HasTransform) {
        if (node.transform != null) addTransform {
            addTransformsFor(node)
        }
    }
}

internal object StylesBuilder {
    fun SvgStringBuilder.add(key: String, value: String) {
        builder.append("$key:$value;")
    }

    fun SvgStringBuilder.addStylesFor(node: Node) {
        if (node is HasFill) {
            node.fill?.let {
                when (it) {
                    is Gradient -> error("Gradients are not yet supported") // TODO
                    is Color -> add("fill", it.rgba)
                    else -> error("")
                }
            }
        }
        if (node is HasStroke) {
            node.strokeColor?.let {
                when (it) {
                    is Gradient -> error("Gradients are not yet supported") // TODO
                    is Color -> add("stroke", it.rgba)
                    else -> error("")
                }
            }
            node.strokeWidth?.let {
                add("stroke-width", it.toString())
            }
            node.dashedLine?.let {
                add("stroke-dasharray", it.joinToString(","))
            }
        }
    }
}

internal object TransformBuilder {
    fun SvgStringBuilder.add(transformType: String, vararg values: Number) {
        builder.append("$transformType(${values.joinToString(" ")})\n")
    }

    fun SvgStringBuilder.addTransformsFor(node: HasTransform) {
        node.transform!!.transformations.forEach {
            when (it) {
                is Translation -> add("translate", it.x, it.y)
                is Rotation -> add("rotate", it.delta)
                else -> error("unsupported transformation")
            }
        }
    }
}

internal fun SvgStringBuilder.add(groupNode: GroupNode) {
    add(type = "g") {
        groupNode.children.forEach { node ->
            when (node) {
                is RectNode -> add(node)
                is CircleNode -> add(node)
                is ImageNode -> error("Image nodes are not yet supported")
                is LineNode -> add(node)
                is PathNode -> add(node)
                is TextNode -> add(node)
                is GroupNode -> add(node)
                else -> error("${node::class.simpleName} node unsupported")
            }
        }
    }
}

internal val Node.hasStyles
    get() = when (this) {
        is HasStroke -> stroke != null || strokeColor != null || strokeWidth != null || dashedLine != null
        is HasFill -> fill != null
        else -> false
    }

private fun SvgStringBuilder.add(rectNode: RectNode) {
    with(rectNode) {
        add(
            type = "rect",
            attributes = {
                add("x", x.toString())
                add("y", y.toString())
                add("width", width.toString())
                add("height", height.toString())
                addStylesIfAvailableFor(rectNode)
            },
        )
    }
}

private fun SvgStringBuilder.add(lineNode: LineNode) {
    with(lineNode) {
        add(
            type = "line",
            attributes = {
                add("x1", x1.toString())
                add("y1", y1.toString())
                add("x2", x2.toString())
                add("y2", y2.toString())
                addStylesIfAvailableFor(lineNode)
                addTransformsIfAvailableFor(lineNode)
            }
        )
    }
}

private fun SvgStringBuilder.add(circleNode: CircleNode) {
    with(circleNode) {
        add(
            type = "circle",
            attributes = {
                add("cx", circle.x.toString())
                add("cy", circle.y.toString())
                add("r", circle.radius.toString())
                addStylesIfAvailableFor(circleNode)
                addTransformsIfAvailableFor(circleNode)
            }
        )
    }
}

private fun SvgStringBuilder.add(textNode: TextNode) {
    with(textNode) {
        add(
            type = "text",
            attributes = {
                add("x", x.toString())
                add("y", y.toString())
                add("font-size", fontSize.toString())
                add("font-family", fontFamily.name)
                add("font-weight", fontWeight.name)
                add("font-style", fontStyle.name)

                addStylesIfAvailableFor(textNode)
                addTransformsIfAvailableFor(textNode)
            }
        ) {
            +textNode.textContent
        }
    }
}

//private fun SvgStringBuilder.add(imageNode: ImageNode) {
//    with(imageNode) {
//        add(
//            type = "image",
//            attributes = {
//                add("x", x.toString())
//                add("y", y.toString())
//                size?.let {
//                    add("width", it.width.toString())
//                    add("height", it.height.toString())
//                }
//
//            }
//        )
//    }
//}

private fun SvgStringBuilder.add(pathNode: PathNode) {
    with(pathNode) {
        add(
            type = "path",
            attributes = {
                addStylesIfAvailableFor(pathNode)
                addTransformsIfAvailableFor(pathNode)
                addPath {
                    var tempX0 = 0.0
                    var tempY0 = 0.0
                    var tempX1: Double? = null
                    var tempY1: Double? = null

                    path.commands.forEach { cmd ->
                        when (cmd) {
                            is MoveTo -> {
                                tempX0 = cmd.x
                                tempY0 = cmd.y
                                tempX1 = cmd.x
                                tempY1 = cmd.y
                                append("M${cmd.x},${cmd.y}")
                            }

                            is LineTo -> {
                                tempX1 = cmd.x
                                tempY1 = cmd.y
                                append("L${cmd.x},${cmd.y}")
                            }

                            is ClosePath -> {
                                if (tempX1 != null) {
                                    tempX1 = tempX0
                                    tempY1 = tempY0
                                    append("Z")
                                }
                            }

                            is QuadraticCurveTo -> {
                                tempX1 = cmd.x
                                tempY1 = cmd.y
                                append("Q${cmd.cpx},${cmd.cpy},${cmd.x},${cmd.y}")
                            }

                            is RectCmd -> {
                                tempX0 = cmd.x
                                tempX1 = cmd.x
                                tempY0 = cmd.y
                                tempY1 = cmd.y
                                append("M${cmd.x},${cmd.y}h${cmd.w}v${cmd.h}h${-cmd.w}Z")
                            }

                            is BezierCurveTo -> {
                                tempX1 = cmd.x
                                tempY1 = cmd.y
                                append("C${cmd.cpx1},${cmd.cpy1},${cmd.cpx2},${cmd.cpy2},${cmd.x},${cmd.y}")

                            }

                            is ArcTo -> {
                                val X0 = tempX1 ?: .0
                                val Y0 = tempY1 ?: .0

                                val x21 = cmd.x - cmd.fromX
                                val y21 = cmd.y - cmd.fromY
                                val x01 = X0 - cmd.fromX
                                val y01 = Y0 - cmd.fromY
                                val l01_2 = x01 * x01 + y01 * y01

                                with(tempX1) {
                                    //path is empty, introduce private function?
                                    if (this == null) {
                                        // Is this path empty? Move to (x1,y1).
                                        tempX1 = cmd.fromX
                                        tempY1 = cmd.fromY
                                        append("M${cmd.fromX},${cmd.fromY}")
                                    }
                                    // Or, is (x1,y1) coincident with (x0,y0)? Do nothing.
                                    else if (l01_2 <= EPSILON) {
                                    }

                                    // Or, are (x0,y0), (x1,y1) and (x2,y2) collinear?
                                    // Equivalently, is (x1,y1) coincident with (x2,y2)?
                                    // Or, is the radius zero? Line to (x1,y1).
                                    else if (abs(y01 * x21 - y21 * x01) <= EPSILON || cmd.radius == .0) {
                                        tempX1 = cmd.fromX
                                        tempY1 = cmd.fromY
                                        append("L${cmd.fromX},${cmd.fromY}")
                                    }

                                    // Otherwise, draw an arc!
                                    else {
                                        val x20 = cmd.x - X0
                                        val y20 = cmd.y - Y0
                                        val l21_2 = x21 * x21 + y21 * y21
                                        val l20_2 = x20 * x20 + y20 * y20
                                        val l21 = sqrt(l21_2)
                                        val l01 = sqrt(l01_2)
                                        val l =
                                            cmd.radius * tan((PI - acos((l21_2 + l01_2 - l20_2) / (2 * l21 * l01))) / 2)
                                        val t01 = l / l01
                                        val t21 = l / l21

                                        // If the start tangent is not coincident with (x0,y0), line to.
                                        if (abs(t01 - 1) > EPSILON) {
                                            append("L${cmd.fromX + t01 * x01},${cmd.fromY + t01 * y01}")
                                        }

                                        tempX1 = cmd.fromX + t21 * x21
                                        tempY1 = cmd.fromY + t21 * y21
                                        val yes = if (y01 * x20 > x01 * y20) 1 else 0
                                        append("A${cmd.radius},${cmd.radius},0,0,$yes,${tempX1},${tempY1}")
                                    }
                                }
                            }

                            is Arc -> {
                                val dx = cmd.radius * cos(cmd.startAngle)
                                val dy = cmd.radius * sin(cmd.startAngle)
                                val x0 = cmd.centerX + dx
                                val y0 = cmd.centerY + dy
                                val cw = if (cmd.counterClockWise) 0 else 1
                                var da =
                                    if (cmd.counterClockWise) cmd.startAngle - cmd.endAngle else cmd.endAngle - cmd.startAngle

                                with(tempX1) {

                                    //path is empty, introduce private function?
                                    if (this == null) {
                                        append("M$x0,$y0")
                                    } else if (abs(this.toDouble() - x0) > EPSILON || abs(
                                            tempY1!!.toDouble() - y0
                                        ) > EPSILON
                                    ) {
                                        append("L$x0,$y0")
                                    } else {
                                    }
                                }

                                if (cmd.radius < EPSILON) return@forEach

                                if (da < 0) da = da % TAU + TAU

                                //complete circle
                                if (da > TAU_EPSILON) {
                                    tempX1 = x0
                                    tempY1 = y0
                                    append("A${cmd.radius},${cmd.radius},0,1,$cw,${cmd.centerX - dx},${cmd.centerY - dy}A${cmd.radius},${cmd.radius},0,1,$cw,$x0,$y0")
                                }

                                // Is this arc non-empty? Draw an arc!
                                else if (da > EPSILON) {
                                    tempX1 = cmd.centerX + cmd.radius * cos(cmd.endAngle)
                                    tempY1 = cmd.centerY + cmd.radius * sin(cmd.endAngle)
                                    append("A${cmd.radius},${cmd.radius},0,${if (da >= PI) 1 else 0},$cw,$tempX1,$tempY1")
                                }
                            }
                        }

                    }
                }
            }
        )
    }
}

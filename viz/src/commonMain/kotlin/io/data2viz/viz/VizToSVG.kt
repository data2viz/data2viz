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


public fun Viz.toSVG(): String {
    val builder = StringBuilder("<?xml version=\"1.0\"?>")
    builder.append("""<svg xmlns="http://www.w3.org/2000/svg" width="$width" height="$height">""")

    layers.forEach { layer ->
        layer.toSVG(builder)
    }

    builder.append("""</svg>""")
    return builder.toString()
}

internal fun GroupNode.toSVG(builder: StringBuilder) {
    builder.append("<g>")
    children.forEach { node ->
        when (node) {
            is RectNode -> node.toSVG(builder)
            else -> error("${node::class.simpleName} node unsupported")
        }
    }
    builder.append("</g>")
}

private fun RectNode.toSVG(builder: StringBuilder) {
    builder.append("""<rect x="$x" y="$y" width="$width" height="$height">""")
    builder.append("</rect>")
}

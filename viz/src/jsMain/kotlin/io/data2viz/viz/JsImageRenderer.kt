/*
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

import org.w3c.dom.CanvasImageSource
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLImageElement

public fun ImageNode.render(context: CanvasRenderingContext2D) {

    image?.let { img ->
        val canvasImageSource = when (img){
            is LocalImage -> img.toCanvasImageSource()
            else -> error("Unknown image type:: $img")
        }

        size?.let { s ->
            context.drawImage(canvasImageSource, x, y, s.width, s.height)
        } ?: {
            context.drawImage(canvasImageSource, x, y)
        }

    }

}

/**
 * Instanciate a LocalImage from an HTML Image.
 */
public fun HTMLImageElement.toLocalImage(): LocalImage = LocalImage(this)

public class LocalImage(
    public val image: HTMLImageElement): ImageHandler {
    public fun toCanvasImageSource(): CanvasImageSource = image

}


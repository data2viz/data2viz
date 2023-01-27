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

import io.data2viz.color.Color
import kotlinx.browser.document
import org.w3c.dom.*

public fun ImageNode.render(context: CanvasRenderingContext2D) {

    image?.let { img ->

        val canvasImageSource = when (img) {
            is LocalImage -> img.toCanvasImageSource()
            is BitmapImage -> img.toCanvasImageSource()
            else -> error("Unknown image type:: $img")
        }

        context.imageSmoothingEnabled = smoothing
        if (smoothing) context.imageSmoothingQuality = ImageSmoothingQuality.HIGH

        when (val s = size) {
            null -> context.drawImage(canvasImageSource, x, y)
            else -> context.drawImage(canvasImageSource, x, y, s.width, s.height)
        }
    }

}

/**
 * Instantiate a LocalImage from an HTML Image.
 */
public fun HTMLImageElement.toLocalImage(): LocalImage = LocalImage(this)

/**
 * [ImageHandler] from a locally loaded image, use HTMLImageElement.toLocalImage() to instantiate one.
 */
public class LocalImage(private val image: HTMLImageElement): ImageHandler {
    public fun toCanvasImageSource(): CanvasImageSource = image

}

public actual class BitmapImage actual constructor(
    private val pixels: Array<Color>,
    private val width: Int,
    private val height: Int
): ImageHandler {

    init {
        require(pixels.size == (width * height)) {
            "The pixel list (pixels) size must be equal to (width x height) of the BitmapImage."
        }
    }
    public fun toCanvasImageSource(): CanvasImageSource {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        canvas.width = width
        canvas.height = height

        val context = canvas.getContext("2d") as CanvasRenderingContext2D
        val imgData = context.createImageData(canvas.width.toDouble(), canvas.height.toDouble())

        for (i in pixels.indices) {
            val index = i*4
            val color = pixels[i]
            imgData.asDynamic().data[index] = color.r
            imgData.asDynamic().data[index + 1] = color.g
            imgData.asDynamic().data[index + 2] = color.b
            imgData.asDynamic().data[index + 3] = (color.alpha.value * 255).toInt()
        }

        context.putImageData(imgData, .0, .0)
        return context.canvas
    }
}
